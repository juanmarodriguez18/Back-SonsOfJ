package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.*;
import com.example.buensaboruno.domain.enums.Estado;
import com.example.buensaboruno.exceptions.StockMinimoException;
import com.example.buensaboruno.repositories.*;
import com.example.buensaboruno.services.FacturaService;
import com.example.buensaboruno.services.PedidoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PedidoServiceImpl extends BaseServiceImpl<Pedido, Long> implements PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    PedidoDetalleRepository pedidoDetalleRepository;

    @Autowired
    ArticuloRepository articuloRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    ArticuloManufacturadoRepository articuloManufacturadoRepository;


    public PedidoServiceImpl(BaseRepository<Pedido, Long> baseRepository) {
        super(baseRepository);
    }


    @Override
    public Pedido save(Pedido request) {
        Set<PedidoDetalle> detalles = request.getPedidoDetalles(); // Guardar los detalles del body en un set
        Set<PedidoDetalle> detallesPersistidos = new HashSet<>(); // Inicializar un set que contendrá los detalles que pasen las validaciones

        if (detalles != null && !detalles.isEmpty()) {
            double costoTotal = 0;
            // Iterar los detalles
            for (PedidoDetalle detalle : detalles) {
                Articulo articulo = detalle.getArticulo(); // Obtener el articulo presente en el detalle
                if (articulo == null || articulo.getId() == null) {
                    throw new RuntimeException("El articulo del detalle no puede ser nulo");
                }
                // Validar que el articulo exista
                articulo = articuloRepository.findById(detalle.getArticulo().getId())
                        .orElseThrow(() -> new RuntimeException("El articulo con id " + detalle.getArticulo().getId() + " no existe"));
                detalle.setArticulo(articulo);
                PedidoDetalle savedDetalle = pedidoDetalleRepository.save(detalle); // Guardar los detalles en la bd
                costoTotal += calcularTotalCosto(articulo.getId(), detalle.getCantidad()); // Calcular costo total por cada iteración de detalle
                descontarStock(articulo.getId(), detalle.getCantidad()); // Descontar el stock por cada iteración de detalle

                detallesPersistidos.add(savedDetalle);
            }
            request.setTotalCosto(costoTotal); // Asignarle el total costo al pedido
            request.setPedidoDetalles(detallesPersistidos); // Después de la iteración, asignarle todos los detalles al pedido
        } else {
            throw new IllegalArgumentException("El pedido debe contener al menos un detalle");
        }

        // Validar que se haya pasado una sucursal en el body
        if (request.getSucursal() == null) {
            throw new RuntimeException("No se ha asignado una sucursal al pedido");
        }
        Sucursal sucursal = sucursalRepository.getById(request.getSucursal().getId());
        // Validar que la sucursal existe
        if (sucursal == null) {
            throw new RuntimeException("El sucursal con id " + request.getSucursal().getId() + " no existe");
        }

        request.setSucursal(sucursal); // Asignar la sucursal al pedido

        request.setEstado(Estado.PENDIENTE); // Asignar el estado inicial

        request.setFechaPedido(LocalDate.now()); // Asignar la fecha

        return pedidoRepository.save(request); // Guardar el nuevo pedido
    }

    @Transactional
    public void descontarStock(Long idArticulo, int cantidad) {
        // Buscar Insumo por ID
        Optional<ArticuloInsumo> optionalInsumo = articuloInsumoRepository.findById(idArticulo);

        System.out.println("Cantidad: " + cantidad);
        // si el articulo es un insumo
        if (optionalInsumo.isPresent()) {
            ArticuloInsumo insumo = optionalInsumo.get();
            System.out.println("Stock antes de descontar: " + insumo.getStockActual());
            Double stockDescontado = insumo.getStockActual() - cantidad; // Descontar cantidad a stock actual
            System.out.println("Stock despues de restarle la cantidad: " + stockDescontado);
            // Validar que el stock actual no supere el minimo
            if (stockDescontado <= insumo.getStockMinimo()) {
                throw new StockMinimoException("El local se quedó sin " + insumo.getDenominacion());
            }
            insumo.setStockActual(stockDescontado); // Asignarle al insumo el stock descontado
            articuloInsumoRepository.save(insumo); // Guardar cambios
        } else {
            // Si no es insumo, es manufacturado
            Optional<ArticuloManufacturado> optionalManufacturado = articuloManufacturadoRepository.findById(idArticulo);

            if (optionalManufacturado.isPresent()) {
                ArticuloManufacturado manufacturado = optionalManufacturado.get();
                // Obtener los detalles del manufacturado
                Set<ArticuloManufacturadoDetalle> detalles = manufacturado.getArticuloManufacturadoDetalles();

                if (detalles != null && !detalles.isEmpty()) {
                    for (ArticuloManufacturadoDetalle detalle : detalles) { // Recorrer los detalles
                        ArticuloInsumo insumo = detalle.getArticuloInsumo();
                        Double cantidadInsumo = (double) (detalle.getCantidad() * cantidad); // Multiplicar la cantidad necesaria de insumo por la cantidad de manufacturados del pedido
                        double stockDescontado = insumo.getStockActual() - cantidadInsumo; // Descontar el stock actual
                        if (stockDescontado <= insumo.getStockMinimo()) {
                            throw new StockMinimoException("El local no puede preparar " + manufacturado.getDenominacion());
                        }
                        insumo.setStockActual(stockDescontado); // Asignarle al insumo el stock descontado
                        articuloInsumoRepository.save(insumo); // Guardar cambios
                    }
                }
            } else {
                throw new RuntimeException("Articulo con id " + idArticulo + " no existe");
            }
        }
    }


    public Double calcularTotalCosto(Long idArticulo, Integer cantidad) {
        // Buscar ArticuloInsumo por ID
        Optional<ArticuloInsumo> optionalInsumo = articuloInsumoRepository.findById(idArticulo);

        // Si el articulo es un insumo multiplicar el precio del insumo por la cantidad
        if (optionalInsumo.isPresent()) {
            ArticuloInsumo insumo = optionalInsumo.get();
            return insumo.getPrecioCompra() * cantidad;
        }

        // Buscar ArticuloManufacturado por ID
        Optional<ArticuloManufacturado> optionalManufacturado = articuloManufacturadoRepository.findById(idArticulo);

        // Si el articulo es un manufacturado, obtener sus detalles
        if (optionalManufacturado.isPresent()) {
            ArticuloManufacturado manufacturado = optionalManufacturado.get();
            Set<ArticuloManufacturadoDetalle> detalles = manufacturado.getArticuloManufacturadoDetalles();

            if (detalles != null && !detalles.isEmpty()) {
                double totalCosto = 0;
                for (ArticuloManufacturadoDetalle detalle : detalles) { // Recorrer los detalles
                    double precioCompraInsumo = detalle.getArticuloInsumo().getPrecioCompra(); // Obtener el precioCompra del insumo presente en el detalle
                    double cantidadInsumo = detalle.getCantidad(); // Obtener la cantidad del insumo presente en el detalle
                    totalCosto += (precioCompraInsumo * cantidadInsumo);
                }
                return totalCosto * cantidad; // Multiplicar por la cantidad de articulos manufacturados
            }
        }

        return 0.0; // Si no se encuentra el articulo, devuelve 0
    }


    @Transactional
    public Pedido update(Long id, Pedido request) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El pedido con id " + id + " no se ha encontrado"));

        Estado estadoAnterior = pedido.getEstado();
        Estado nuevoEstado = request.getEstado();

        // Actualizar el estado del pedido
        pedido.setEstado(nuevoEstado);

        // Lógica según los cambios de estado
        if (estadoAnterior == Estado.PENDIENTE && nuevoEstado == Estado.RECHAZADO) {
            // Sumar todo el stock tanto de insumos para elaborar como no para elaborar
            sumarStockCompleto(pedido);
        } else if (estadoAnterior == Estado.PREPARACION && nuevoEstado == Estado.CANCELADO) {
            // Sumar el stock del insumo que no es para elaborar
            sumarStockNoParaElaborar(pedido);
        } else if (estadoAnterior == Estado.LISTO_PARA_ENTREGA && nuevoEstado == Estado.CANCELADO) {
            // Sumar el stock del insumo que no es para elaborar
            sumarStockNoParaElaborar(pedido);
        }

        return pedidoRepository.save(pedido);
    }

    private void sumarStockCompleto(Pedido pedido) {
        for (PedidoDetalle detalle : pedido.getPedidoDetalles()) {
            sumarStock(detalle.getArticulo().getId(), detalle.getCantidad());
        }
    }

    private void sumarStockNoParaElaborar(Pedido pedido) {
        for (PedidoDetalle detalle : pedido.getPedidoDetalles()) {
            sumarStockNoParaElaborar(detalle.getArticulo().getId(), detalle.getCantidad());
        }
    }

    @Transactional
    public void sumarStock(Long idArticulo, int cantidad) {
        // Buscar Insumo por ID
        Optional<ArticuloInsumo> optionalInsumo = articuloInsumoRepository.findById(idArticulo);

        if (optionalInsumo.isPresent()) {
            ArticuloInsumo insumo = optionalInsumo.get();
            Double stockSumado = insumo.getStockActual() + cantidad; // Sumar cantidad al stock actual
            insumo.setStockActual(stockSumado); // Asignarle al insumo el stock sumado
            articuloInsumoRepository.save(insumo); // Guardar cambios
        } else {
            Optional<ArticuloManufacturado> optionalManufacturado = articuloManufacturadoRepository.findById(idArticulo);
            if (optionalManufacturado.isPresent()) {
                ArticuloManufacturado manufacturado = optionalManufacturado.get();
                Set<ArticuloManufacturadoDetalle> detalles = manufacturado.getArticuloManufacturadoDetalles();
                if (detalles != null && !detalles.isEmpty()) {
                    for (ArticuloManufacturadoDetalle detalle : detalles) {
                        ArticuloInsumo insumo = detalle.getArticuloInsumo();
                        Double cantidadInsumo = (double) (detalle.getCantidad() * cantidad);
                        double stockSumado = insumo.getStockActual() + cantidadInsumo;
                        insumo.setStockActual(stockSumado);
                        articuloInsumoRepository.save(insumo);
                    }
                }
            } else {
                throw new RuntimeException("Articulo con id " + idArticulo + " no existe");
            }
        }
    }



    @Transactional
    public void sumarStockNoParaElaborar(Long idArticulo, int cantidad) {
        // Buscar Insumo por ID
        Optional<ArticuloInsumo> optionalInsumo = articuloInsumoRepository.findById(idArticulo);

        // si el articulo es un insumo
        if (optionalInsumo.isPresent()) {
            ArticuloInsumo insumo = optionalInsumo.get();
            // Validar que el insumo no sea para elaborar
            if (!insumo.getEsParaElaborar()) {
                Double stockSumado = insumo.getStockActual() + cantidad; // Sumar cantidad al stock actual
                insumo.setStockActual(stockSumado); // Asignarle al insumo el stock sumado
                articuloInsumoRepository.save(insumo); // Guardar cambios
            }
        } else {
            // Si no es insumo, es manufacturado
            Optional<ArticuloManufacturado> optionalManufacturado = articuloManufacturadoRepository.findById(idArticulo);

            if (optionalManufacturado.isPresent()) {
                ArticuloManufacturado manufacturado = optionalManufacturado.get();
                // Obtener los detalles del manufacturado
                Set<ArticuloManufacturadoDetalle> detalles = manufacturado.getArticuloManufacturadoDetalles();

                if (detalles != null && !detalles.isEmpty()) {
                    for (ArticuloManufacturadoDetalle detalle : detalles) { // Recorrer los detalles
                        ArticuloInsumo insumo = detalle.getArticuloInsumo();
                        // Validar que el insumo no sea para elaborar
                        if (!insumo.getEsParaElaborar()) {
                            Double cantidadInsumo = (double) (detalle.getCantidad() * cantidad); // Multiplicar la cantidad necesaria de insumo por la cantidad de manufacturados del pedido
                            double stockSumado = insumo.getStockActual() + cantidadInsumo; // Sumar el stock actual
                            insumo.setStockActual(stockSumado); // Asignarle al insumo el stock sumado
                            articuloInsumoRepository.save(insumo); // Guardar cambios
                        }
                    }
                }
            } else {
                throw new RuntimeException("Articulo con id " + idArticulo + " no existe");
            }
        }
    }


    public void deleteById(Long id) {
        Pedido pedido = pedidoRepository.getById(id);
        if (pedido == null) {
            throw new RuntimeException("El pedido con id " + id + " no se ha encontrado");
        }

        if (pedido.getEstado() != Estado.PENDIENTE) {
            throw new RuntimeException("El pedido no se puede eliminar porque su estado es distinto a pendiente");
        }

        pedidoRepository.delete(pedido);
    }

    @Override
    public List<Pedido> findPedidosByFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return pedidoRepository.findPedidosByFecha(fechaInicio, fechaFin);
    }

}
