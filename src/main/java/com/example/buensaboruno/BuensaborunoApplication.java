package com.example.buensaboruno;

import com.example.buensaboruno.domain.entities.*;
import com.example.buensaboruno.domain.enums.*;
import com.example.buensaboruno.repositories.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@SpringBootApplication
public class BuensaborunoApplication {

    private static final Logger logger = LoggerFactory.getLogger(BuensaborunoApplication.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ImagenClienteRepository imagenPersonaRepository;

    @Autowired
    private PromocionDetalleRepository promocionDetalleRepository;

    @Autowired
    private UsuarioEmpleadoRepository usuarioEmpleadoRepository;

    @Autowired
    private UsuarioClienteRepository usuarioClienteRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    private ArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Autowired
    private ImagenArticuloRepository imagenArticuloRepository;

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public static void main(String[] args) {
        SpringApplication.run(BuensaborunoApplication.class, args);
        logger.info("Estoy activo en el main Alberto");
        logger.info("Usar: http://localhost:8080/h2-console/");
    }

    @Bean
    @Transactional
    CommandLineRunner init(ClienteRepository clienteRepository,
                           ImagenClienteRepository imagenClienteRepository,
                           ImagenPromocionRepository imagenPromocionRepository,
                           ImagenEmpleadoRepository imagenEmpleadoRepository,
                           PromocionDetalleRepository promocionDetalleRepository,
                           UsuarioClienteRepository usuarioClienteRepository,
                           UsuarioEmpleadoRepository usuarioEmpleadoRepository,
                           PaisRepository paisRepository,
                           ProvinciaRepository provinciaRepository,
                           LocalidadRepository localidadRepository,
                           EmpresaRepository empresaRepository,
                           SucursalRepository sucursalRepository,
                           DomicilioRepository domicilioRepository,
                           UnidadMedidaRepository unidadMedidaRepository,
                           CategoriaRepository categoriaRepository,
                           ArticuloInsumoRepository articuloInsumoRepository,
                           ArticuloManufacturadoRepository articuloManufacturadoRepository,
                           ImagenArticuloRepository imagenArticuloRepository,
                           PromocionRepository promocionRepository,
                           PedidoRepository pedidoRepository,
                           EmpleadoRepository empleadoRepository,
                           FacturaRepository facturaRepository)
    {
        return args -> {
            logger.info("----------------ESTOY----FUNCIONANDO---------------------");
            // Etapa del dashboard
            // Crear 1 pais
            // Crear 2 provincias para ese pais
            // crear 2 localidades para cada provincia

            // CREACION DE PAIS
            Pais pais1 = Pais.builder().nombre("Argentina").build();
            paisRepository.save(pais1);

            // CREACION DE PROVINCIAS
            Provincia provincia1 = Provincia.builder().nombre("Mendoza").pais(pais1).build();
            Provincia provincia2 = Provincia.builder().nombre("Buenos Aires").pais(pais1).build();
            provinciaRepository.save(provincia1);
            provinciaRepository.save(provincia2);

            // CREACION DE LOCALIDADES
            Localidad localidad1 = Localidad.builder().nombre("Lujan de Cuyo").provincia(provincia1).build();
            Localidad localidad2 = Localidad.builder().nombre("Guaymallen").provincia(provincia1).build();
            Localidad localidad3 = Localidad.builder().nombre("Mar del Plata").provincia(provincia2).build();
            Localidad localidad4 = Localidad.builder().nombre("Mar de las Pampas").provincia(provincia2).build();
            localidadRepository.save(localidad1);
            localidadRepository.save(localidad2);
            localidadRepository.save(localidad3);
            localidadRepository.save(localidad4);

            // Crear 1 empresa, 2 sucursales para esa empresa y los Domicilios para esas sucursales
            Empresa empresaCarlos = Empresa.builder().nombre("Lo de Carlos").cuil(30546780L).razonSocial("Venta de Alimentos").build();
            empresaRepository.save(empresaCarlos);

            Sucursal sucursalGuaymallen = Sucursal.builder().nombre("En Guaymallen").horarioApertura(LocalTime.of(17, 0)).horarioCierre(LocalTime.of(23, 0)).build();
            Sucursal sucursalMarDelPlata = Sucursal.builder().nombre("En MDP").horarioApertura(LocalTime.of(16, 0)).horarioCierre(LocalTime.of(23, 30)).build();

            Domicilio domicilioBerutti = Domicilio.builder().cp(5519).calle("Berutti").numero(2684).piso(0).nroDpto(5).localidad(localidad1).build();
            Domicilio domicilioGaboto = Domicilio.builder().cp(7600).calle("Gaboto").numero(3475).localidad(localidad2).build();

            // GRABAMOS DOMICILIOS
            domicilioRepository.save(domicilioBerutti);
            domicilioRepository.save(domicilioGaboto);

            // ASOCIAMOS LOS DOMICILIOS A SUCURSAL
            sucursalGuaymallen.setDomicilio(domicilioBerutti);
            sucursalMarDelPlata.setDomicilio(domicilioGaboto);

            // ASOCIAMOS SUCURSALES A EMPRESA
            empresaCarlos.getSucursales().add(sucursalGuaymallen);
            empresaCarlos.getSucursales().add(sucursalMarDelPlata);

            // ASIGNAMOS EMPRESA A SUCURSALES
            sucursalGuaymallen.setEmpresa(empresaCarlos);
            sucursalMarDelPlata.setEmpresa(empresaCarlos);

            // Grabo las sucursales
            sucursalRepository.save(sucursalGuaymallen);
            sucursalRepository.save(sucursalMarDelPlata);

            // Grabi empresa
            //empresaRepository.save(empresaCarlos);

            // Crear Categorías de productos y subCategorías de los mismos
            Categoria categoriaBebidas = Categoria.builder().denominacion("Bebidas").build();
            categoriaRepository.save(categoriaBebidas);

            Categoria categoriaGaseosas = Categoria.builder().denominacion("Gaseosas").build();
            categoriaRepository.save(categoriaGaseosas);

            Categoria categoriaTragos = Categoria.builder().denominacion("Tragos").build();
            categoriaRepository.save(categoriaTragos);

            Categoria categoriaPizzas = Categoria.builder().denominacion("Pizzas").build();
            Categoria categoriaInsumos = Categoria.builder().denominacion("Insumos").build();

            // Grabo la categoría de insumos y de Manufacturados
            categoriaRepository.save(categoriaPizzas);
            categoriaRepository.save(categoriaInsumos);



            // Asigno subCategorías
            categoriaBebidas.getSubCategorias().add(categoriaGaseosas);
            categoriaBebidas.getSubCategorias().add(categoriaTragos);

            // Grabo las Subcategorías
            categoriaRepository.save(categoriaBebidas);

            logger.info("---------------Voy a asignar a Guaymallen--------------------");
            // ASOCIAMOS CATEGORIAS CON SUCURSAL
            categoriaInsumos.getSucursales().add(sucursalGuaymallen);

            // Cargo las categorias a la sucursal Guaymallen
            sucursalGuaymallen.getCategorias().add(categoriaInsumos);
            sucursalGuaymallen.getCategorias().add(categoriaBebidas);
            sucursalGuaymallen.getCategorias().add(categoriaGaseosas);
            sucursalGuaymallen.getCategorias().add(categoriaTragos);
            sucursalGuaymallen.getCategorias().add(categoriaPizzas);
            logger.info("{}", sucursalGuaymallen);

            // Grabo las categorias que vende esa sucursal
            sucursalRepository.save(sucursalGuaymallen);
            logger.info("---------------Grabe Guaymallen--------------------");

            logger.info("---------------Voy a asignar a Mardel Plata--------------------");

            // Crear Unidades de medida
            UnidadMedida unidadMedidaLitros = UnidadMedida.builder().denominacion("Litros").build();
            UnidadMedida unidadMedidaGramos = UnidadMedida.builder().denominacion("Gramos").build();
            UnidadMedida unidadMedidaCantidad = UnidadMedida.builder().denominacion("Cantidad").build();
            UnidadMedida unidadMedidaPorciones = UnidadMedida.builder().denominacion("Porciones").build();
            unidadMedidaRepository.save(unidadMedidaLitros);
            unidadMedidaRepository.save(unidadMedidaGramos);
            unidadMedidaRepository.save(unidadMedidaCantidad);
            unidadMedidaRepository.save(unidadMedidaPorciones);

            // Crear Insumos
            ArticuloInsumo cocaCola = ArticuloInsumo.builder()
                    .denominacion("Coca cola")
                    .unidadMedida(unidadMedidaLitros)
                    .esParaElaborar(false)
                    .categoria(categoriaGaseosas)
                    .stockActual(5)
                    .stockMaximo(50)
                    .precioCompra(50.0)
                    .precioVenta(70.0)
                    .build();
            ImagenArticulo imagenCoca = ImagenArticulo.builder()
                    .url("https://m.media-amazon.com/images/I/51v8nyxSOYL._SL1500_.jpg")
                    .articulo(cocaCola)
                    .build();
            cocaCola.getImagenesArticulo().add(imagenCoca);
            logger.info("Insumo {}", cocaCola);

            ArticuloInsumo harina = ArticuloInsumo.builder()
                    .denominacion("Harina")
                    .unidadMedida(unidadMedidaGramos)
                    .esParaElaborar(true)
                    .categoria(categoriaInsumos)
                    .stockActual(4)
                    .stockMaximo(40)
                    .precioCompra(40.0)
                    .precioVenta(60.5)
                    .build();
            ImagenArticulo imagenHarina = ImagenArticulo.builder()
                    .url("https://mandolina.co/wp-content/uploads/2023/03/648366622-1024x683.jpg")
                    .articulo(harina)
                    .build();
            harina.getImagenesArticulo().add(imagenHarina);
            logger.info("Insumo {}", harina);

            ArticuloInsumo tomate = ArticuloInsumo.builder()
                    .denominacion("Tomate")
                    .unidadMedida(unidadMedidaCantidad)
                    .esParaElaborar(true)
                    .categoria(categoriaInsumos)
                    .stockActual(20)
                    .stockMaximo(50)
                    .precioCompra(23.6)
                    .precioVenta(66.6)
                    .build();
            ImagenArticulo imagenTomate = ImagenArticulo.builder()
                    .url("https://thefoodtech.com/wp-content/uploads/2020/06/Componentes-de-calidad-en-el-tomate-828x548.jpg")
                    .articulo(tomate)
                    .build();
            tomate.getImagenesArticulo().add(imagenTomate);
            logger.info("Insumo {}", tomate);

            ArticuloInsumo queso = ArticuloInsumo.builder()
                    .denominacion("Queso")
                    .unidadMedida(unidadMedidaGramos)
                    .esParaElaborar(true)
                    .categoria(categoriaInsumos)
                    .stockActual(20)
                    .stockMaximo(50)
                    .precioCompra(23.6)
                    .precioVenta(66.6)
                    .build();
            ImagenArticulo imagenQueso = ImagenArticulo.builder()
                    .url("https://superdepaso.com.ar/wp-content/uploads/2021/06/SANTAROSA-PATEGRAS-04.jpg")
                    .articulo(queso)
                    .build();
            queso.getImagenesArticulo().add(imagenQueso);
            logger.info("Insumo {}", queso);

            // Guardar los insumos
            articuloInsumoRepository.saveAll(Arrays.asList(cocaCola, harina, tomate, queso));


            // Crear Articulos Manufacturados
            ArticuloManufacturado pizzaMuzarella = ArticuloManufacturado.builder()
                    .denominacion("Pizza Muzarella")
                    .descripcion("Una pizza clasica")
                    .unidadMedida(unidadMedidaPorciones)
                    .precioVenta(130.0)
                    .tiempoEstimadoMinutos(15)
                    .preparacion("Esto se prepara asi")
                    .categoria(categoriaPizzas)
                    .build();
            ImagenArticulo imagenPizzaMuzarella = ImagenArticulo.builder()
                    .url("https://storage.googleapis.com/fitia-api-bucket/media/images/recipe_images/1002846.jpg")
                    .articulo(pizzaMuzarella)
                    .build();
            pizzaMuzarella.getImagenesArticulo().add(imagenPizzaMuzarella);

            ArticuloManufacturadoDetalle detalleHarinaPizzaMuzarella = ArticuloManufacturadoDetalle.builder()
                    .cantidad(1)
                    .articuloInsumo(harina)
                    .articuloManufacturado(pizzaMuzarella)
                    .build();
            ArticuloManufacturadoDetalle detalleQuesoPizzaMuzarella = ArticuloManufacturadoDetalle.builder()
                    .cantidad(2)
                    .articuloInsumo(queso)
                    .articuloManufacturado(pizzaMuzarella)
                    .build();
            pizzaMuzarella.setArticuloManufacturadoDetalles(Set.of(detalleHarinaPizzaMuzarella, detalleQuesoPizzaMuzarella));

            logger.info("Manufacturado {}", pizzaMuzarella);

            ArticuloManufacturado pizzaNapolitana = ArticuloManufacturado.builder()
                    .denominacion("Pizza Napolitana")
                    .descripcion("Una pizza clasica con tomate")
                    .unidadMedida(unidadMedidaPorciones)
                    .precioVenta(150.0)
                    .tiempoEstimadoMinutos(15)
                    .preparacion("Esto se prepara asi")
                    .categoria(categoriaPizzas)
                    .build();
            ImagenArticulo imagenPizzaNapolitana = ImagenArticulo.builder()
                    .url("https://assets.elgourmet.com/wp-content/uploads/2023/03/8metlvp345_portada-pizza-1024x686.jpg.webp")
                    .articulo(pizzaNapolitana)
                    .build();
            pizzaNapolitana.getImagenesArticulo().add(imagenPizzaNapolitana);
            ArticuloManufacturadoDetalle detalleHarinaPizzaNapolitana = ArticuloManufacturadoDetalle.builder()
                    .cantidad(3)
                    .articuloInsumo(harina)
                    .articuloManufacturado(pizzaNapolitana)
                    .build();
            ArticuloManufacturadoDetalle detalleQuesoPizzaNapolitana = ArticuloManufacturadoDetalle.builder()
                    .cantidad(1)
                    .articuloInsumo(queso)
                    .articuloManufacturado(pizzaNapolitana)
                    .build();
            ArticuloManufacturadoDetalle detalleTomatePizzaNapolitana = ArticuloManufacturadoDetalle.builder()
                    .cantidad(2)
                    .articuloInsumo(tomate)
                    .articuloManufacturado(pizzaNapolitana)
                    .build();
            pizzaNapolitana.setArticuloManufacturadoDetalles(Set.of(detalleHarinaPizzaNapolitana, detalleQuesoPizzaNapolitana, detalleTomatePizzaNapolitana));

            logger.info("Manufacturado {}", pizzaNapolitana);

            // Guardar los artículos manufacturados
            articuloManufacturadoRepository.saveAll(Arrays.asList(pizzaMuzarella, pizzaNapolitana));


            categoriaRepository.save(categoriaInsumos);
            categoriaRepository.save(categoriaGaseosas);


            // Establecer relaciones de las categorias - Cada Producto Manufacturado Pertenece a una categoria
            categoriaPizzas.getArticulos().add(pizzaMuzarella);
            categoriaPizzas.getArticulos().add(pizzaNapolitana);

            // Graba la categoria y los productos asociados
            categoriaRepository.save(categoriaPizzas);

            // Crear promocion para sucursal - Dia de los enamorados
            Promocion promocionDiaEnamorados = Promocion.builder()
                    .denominacion("Dia de los Enamorados")
                    .fechaDesde(LocalDate.of(2024, 2, 13))
                    .fechaHasta(LocalDate.of(2024, 2, 15))
                    .horaDesde(LocalTime.of(0, 0))
                    .horaHasta(LocalTime.of(23, 59))
                    .descripcionDescuento("El descuento que se hace por san valentin, un dia antes y un dia despues")
                    .precioPromocional(100.0)
                    .tipoPromocion(TipoPromocion.PROMOCION)
                    .build();

            // Agregamos los Manufacturados y alguna bebida que figura como insumo
            PromocionDetalle detallePromo1 = PromocionDetalle.builder()
                    .cantidad(2)
                    .articulo(pizzaNapolitana)
                    .build();
            PromocionDetalle detallePromo2 = PromocionDetalle.builder()
                    .cantidad(1)
                    .articulo(cocaCola)
                    .build();
            promocionDetalleRepository.save(detallePromo1);
            promocionDetalleRepository.save(detallePromo2);

            promocionDiaEnamorados.getPromocionDetalles().add(detallePromo1);
            promocionDiaEnamorados.getPromocionDetalles().add(detallePromo2);

            promocionRepository.save(promocionDiaEnamorados);

            // Crear promocion para sucursal - Pizza + Coca
            Promocion pizzaConCoca = Promocion.builder()
                    .denominacion("Piza + coca")
                    .fechaDesde(LocalDate.of(2024, 2, 13))
                    .fechaHasta(LocalDate.of(2024, 2, 15))
                    .horaDesde(LocalTime.of(0, 0))
                    .horaHasta(LocalTime.of(23, 59))
                    .descripcionDescuento("Pizza + Coca Cola 1.5lts")
                    .precioPromocional(100.0)
                    .tipoPromocion(TipoPromocion.PROMOCION)
                    .build();

            // Agregamos los Manufacturados y alguna bebida que figura como insumo
            PromocionDetalle detallePromo3 = PromocionDetalle.builder()
                    .cantidad(1)
                    .articulo(pizzaNapolitana)
                    .build();
            PromocionDetalle detallePromo4 = PromocionDetalle.builder()
                    .cantidad(2)
                    .articulo(cocaCola)
                    .build();
            promocionDetalleRepository.save(detallePromo3);
            promocionDetalleRepository.save(detallePromo4);

            pizzaConCoca.getPromocionDetalles().add(detallePromo3);
            pizzaConCoca.getPromocionDetalles().add(detallePromo4);

            promocionRepository.save(pizzaConCoca);

            //sucursalRepository.save(sucursalGuaymallen);
            //sucursalRepository.save(sucursalMarDelPlata);
            //sucursalRepository.guardarSucursalConValidacion(sucursalGuaymallen);
            //sucursalRepository.guardarSucursalConValidacion(sucursalMarDelPlata);

            //Crea un cliente y un usuario
            ImagenCliente imagenCliente = ImagenCliente.builder().url("https://hips.hearstapps.com/hmg-prod/images/la-la-land-final-1638446140.jpg").build();
            imagenClienteRepository.save(imagenCliente);
            ImagenEmpleado imagenEmpleado = ImagenEmpleado.builder().url("https://hips.hearstapps.com/hmg-prod/images/la-la-land-final-1638446140.jpg").build();
            imagenEmpleadoRepository.save(imagenEmpleado);
            Domicilio domicilioCliente = Domicilio.builder().cp(5519).calle("Cangallo").numero(800).piso(0).nroDpto(1).localidad(localidad1).build();
            domicilioRepository.save(domicilioCliente);
            UsuarioCliente usuarioCliente = UsuarioCliente.builder().username("sebastian").auth0Id("9565a49d-ecc1-4f4e-adea-6cdcb7edc4a3").build();
            usuarioClienteRepository.save(usuarioCliente);
            UsuarioEmpleado usuarioEmpleado = UsuarioEmpleado.builder().username("martin").auth0Id("9565a49d-ecc1-4f4e-adea-6cdcb7edc43a").build();
            usuarioEmpleadoRepository.save(usuarioEmpleado);

            Cliente cliente = new Cliente();

            cliente.setImagenCliente(imagenCliente);
            cliente.setEmail("correoFalso@gmail.com");
            cliente.setNombre("Sebastian");
            cliente.setApellido("Wilder");
            cliente.setUsuarioCliente(usuarioCliente);
            cliente.setTelefono("2615920825");
            //cliente.setEstaActivo(true);
            cliente.getDomicilios().add(domicilioCliente);
            clienteRepository.save(cliente);

            Empleado empleado = new Empleado();

            empleado.setEmail("correoFalso@hotmail.com");
            empleado.setTipoEmpleado(Rol.CAJERO);
            empleado.setNombre("CorreoFalso");
            empleado.setApellido("Falsin");
            empleado.setUsuarioEmpleado(usuarioEmpleado);
            empleado.setTelefono("2612151170");
            //empleado.setEstaActivo(true);
            empleado.setImagenEmpleado(imagenEmpleado);
            empleado.setSucursal(sucursalGuaymallen);
            empleado.setTipoEmpleado(Rol.ADMIN);
            sucursalGuaymallen.getEmpleados().add(empleado);
            empleadoRepository.save(empleado);
            logger.info("Empleado{}:", empleado);


            //Crea un pedido para el cliente
            Pedido pedido = Pedido.builder().fechaPedido(LocalDate.now()).horaEstimadaFinalizacion(LocalTime.now()).total(300.0).totalCosto(170.6).estado(Estado.PREPARACION).formaPago(FormaPago.MERCADO_PAGO).tipoEnvio(TipoEnvio.DELIVERY).sucursal(sucursalGuaymallen).domicilio(domicilioCliente).build();

            PedidoDetalle detallePedido1 = PedidoDetalle.builder().articulo(pizzaMuzarella).cantidad(1).subTotal(200.0).build();
            PedidoDetalle detallePedido2 = PedidoDetalle.builder().articulo(cocaCola).cantidad(2).subTotal(100.0).build();

            pedido.getPedidoDetalles().add(detallePedido1);
            pedido.getPedidoDetalles().add(detallePedido2);
            pedido.setCliente(cliente);
            pedido.setEmpleado(empleado);
            pedidoRepository.save(pedido);

            Random random = new Random();
            Factura facturaBuilder = Factura.builder().fechaFcturacion(LocalDate.now()).mpPaymentId(random.nextInt(1000))  // Se asume un rango máximo de 1000
                    .mpMerchantOrderId(random.nextInt(1000)) // Se asume un rango máximo de 1000
                    .mpPreferenceId("MP-" + random.nextInt(10000))  // Se asume un rango máximo de 10000
                    .mpPaymentType("Tipo" + random.nextInt(10)) // Se asume un rango máximo de 10
                    .formaPago(FormaPago.EFECTIVO).totalVenta(random.nextDouble() * 1000).build();

            facturaRepository.save(facturaBuilder);

            pedido.setFactura(facturaBuilder);

            pedidoRepository.save(pedido);


            //Prueba de carga perezosa
            //Empresa-Sucursal
            //Sucursal-Promocion
            //Sucursal-Categoria
            //Sucursal-Empleado

            Domicilio domicilioSucu1 = Domicilio.builder().cp(5519).calle("calle1").numero(2684).piso(0).nroDpto(5).localidad(localidad1).build();
            domicilioRepository.save(domicilioSucu1);

            Domicilio domicilioSucu2 = Domicilio.builder().cp(5523).calle("calle2").numero(2668).piso(1).nroDpto(3).localidad(localidad2).build();
            domicilioRepository.save(domicilioSucu2);

            Empresa empresa = Empresa.builder().nombre("Empresa de prueba").cuil(999999999L).razonSocial("Razon social").build();
            empresaRepository.save(empresa);

            Sucursal sucursal1 = Sucursal.builder().nombre("sucursal 1")
                    .domicilio(domicilioSucu1)
                    .horarioApertura(LocalTime.of(12, 30, 00))
                    .horarioCierre(LocalTime.of(20, 00, 00))
                    .empresa(empresa)
                    .build();
            sucursalRepository.save(sucursal1);

            Sucursal sucursal2 = Sucursal.builder().nombre("sucursal 2")
                    .domicilio(domicilioSucu2)
                    .horarioApertura(LocalTime.of(12, 30, 00))
                    .horarioCierre(LocalTime.of(20, 00, 00))
                    .empresa(empresa)
                    .build();
            sucursalRepository.save(sucursal2);


            Categoria categoria = Categoria.builder().denominacion("Categoria de prueba").build();
            categoriaRepository.save(categoria);
			/*
			//PRUEBA LAZY -> FALLA
			var empresaRepo = empresaRepository.findById(2L);
			if(empresaRepo.isPresent()){
				Optional<Sucursal> sucursalRepo = sucursalRepository.findById(3L);
				Optional<Sucursal> sucursalRepo2 = sucursalRepository.findById(4L);
				if(sucursalRepo2.isPresent() && sucursalRepo.isPresent()){
					empresaRepo.get().getSucursales().add(sucursalRepo.get());
					empresaRepo.get().getSucursales().add(sucursalRepo2.get());
					empresaRepository.save(empresaRepo.get());
				}
			}*/

            var categoriaRep = categoriaRepository.findWithSucursalesById(6L);//CON FINDBYID NO SE PUEDE AÑADIR SUCURSALES POR LAZY
            var empresaRepo = empresaRepository.findWithSucursalesById(2L);
            Sucursal sucursalRepo = sucursalRepository.findWithEmpleadosById(3L);//CON FINDBYID NO SE PUEDE AÑADIR EMPLEADOS POR LAZY
            Optional<Sucursal> sucursalRepo2 = sucursalRepository.findById(4L);
            Sucursal sucursalRepo3 = sucursalRepository.findWithCategoriasById(3L);//CON FINDBYID NO SE PUEDE AÑADIR CATEGORIAS POR LAZY
            if (sucursalRepo2.isPresent()) {
                empresaRepo.getSucursales().add(sucursalRepo);
                empresaRepo.getSucursales().add(sucursalRepo2.get());
                empresaRepository.save(empresaRepo);
                sucursalRepo.setEmpresa(empresaRepo);
                sucursalRepo2.get().setEmpresa(empresaRepo);
                sucursalRepo3.getCategorias().add(categoriaRep);
                sucursalRepository.save(sucursalRepo);
                sucursalRepository.save(sucursalRepo2.get());
                sucursalRepository.save(sucursalRepo3);
                categoriaRep.getSucursales().add(sucursalRepo);
                categoriaRep.getSucursales().add(sucursalRepo2.get());
                var empleado1 = empleadoRepository.findById(2L);
                if (empleado1.isPresent()) {
                    sucursalRepo.getEmpleados().add(empleado1.get());

                    sucursalRepository.save(sucursalRepo);
                }
            }

            logger.info("------------Nombre de sucursales de la empresa id 2------------");
            empresaRepo.getSucursales().stream().map(Sucursal::getNombre).forEach(logger::info);

            logger.info("------------Nombre de empresa de la sucursal id 3------------");
            logger.info("{}", sucursalRepo.getEmpresa().getNombre());
            logger.info("------------Empleados de la sucursal id 3------------");
            sucursalRepo.getEmpleados().stream().map(Empleado::getNombre).forEach(logger::info);

            logger.info("------------Nombre de empresa de la sucursal id 4------------");
            logger.info("{}", sucursalRepo2.get().getEmpresa().getNombre());

            logger.info("----------------Sucursal Guaymallen ---------------------");
            logger.info("{}", sucursalGuaymallen);
            logger.info("----------------Sucursal Mardel Plata ---------------------");
            logger.info("{}", sucursalMarDelPlata);
            logger.info("----------------Pedido ---------------------");
            logger.info("{}", pedido);

        };
    }

}



