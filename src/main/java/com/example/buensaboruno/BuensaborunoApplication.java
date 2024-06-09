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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@SpringBootApplication
public class BuensaborunoApplication {

    private static final Logger logger = LoggerFactory.getLogger(BuensaborunoApplication.class);

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private ImagenSucursalRepository imagenSucursalRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ImagenEmpresaRepository imagenEmpresaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ArticuloInsumoRepository articuloInsumoRepository;

    @Autowired
    private ArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Autowired
    private ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

    @Autowired
    private ImagenArticuloRepository imagenArticuloRepository;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private PromocionDetalleRepository promocionDetalleRepository;

    @Autowired
    private ImagenPromocionRepository imagenPromocionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ImagenClienteRepository imagenClienteRepository;

    @Autowired
    private UsuarioClienteRepository usuarioClienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ImagenEmpleadoRepository imagenEmpleadoRepository;

    @Autowired
    private UsuarioEmpleadoRepository usuarioEmpleadoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    public static void main(String[] args) {
        SpringApplication.run(BuensaborunoApplication.class, args);
        logger.info("Estoy activo en el main Alberto");
        logger.info("Usar: http://localhost:8080/h2-console/");
    }

    @Bean
    @Transactional
    CommandLineRunner init(PaisRepository paisRepository,
                           ProvinciaRepository provinciaRepository,
                           LocalidadRepository localidadRepository,
                           DomicilioRepository domicilioRepository,
                           SucursalRepository sucursalRepository,
                           ImagenSucursalRepository imagenSucursalRepository,
                           EmpresaRepository empresaRepository,
                           ImagenEmpresaRepository imagenEmpresaRepository,
                           CategoriaRepository categoriaRepository,
                           ArticuloInsumoRepository articuloInsumoRepository,
                           ArticuloManufacturadoRepository articuloManufacturadoRepository,
                           ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository,
                           ImagenArticuloRepository imagenArticuloRepository,
                           UnidadMedidaRepository unidadMedidaRepository,
                           PromocionRepository promocionRepository,
                           PromocionDetalleRepository promocionDetalleRepository,
                           ImagenPromocionRepository imagenPromocionRepository,
                           ClienteRepository clienteRepository,
                           ImagenClienteRepository imagenClienteRepository,
                           UsuarioClienteRepository usuarioClienteRepository,
                           EmpleadoRepository empleadoRepository,
                           ImagenEmpleadoRepository imagenEmpleadoRepository,
                           UsuarioEmpleadoRepository usuarioEmpleadoRepository,
                           PedidoRepository pedidoRepository,
                           FacturaRepository facturaRepository, PedidoDetalleRepository pedidoDetalleRepository)
    {
        return args -> {
            logger.info("----------------ESTOY----FUNCIONANDO---------------------");

            // Crear Pais
            Pais pais1 = Pais.builder().nombre("Argentina").build();
            paisRepository.save(pais1);

            // Crear Provincias
            Provincia provincia1 = Provincia.builder().nombre("Mendoza").pais(pais1).build();
            Provincia provincia2 = Provincia.builder().nombre("Buenos Aires").pais(pais1).build();
            provinciaRepository.saveAll(Arrays.asList(provincia1, provincia2));

            // Crear Localidades
            Localidad localidad1 = Localidad.builder().nombre("Lujan de Cuyo").provincia(provincia1).build();
            Localidad localidad2 = Localidad.builder().nombre("Guaymallen").provincia(provincia1).build();
            Localidad localidad3 = Localidad.builder().nombre("Mar del Plata").provincia(provincia2).build();
            Localidad localidad4 = Localidad.builder().nombre("Mar de las Pampas").provincia(provincia2).build();
            localidadRepository.saveAll(Arrays.asList(localidad1, localidad2, localidad3, localidad4));
            logger.info("-----> Terminada la creacion de Pais, Provincias y Localidades");

            // Crear Empresa, la Imagen y asociarla
            Empresa empresaCarlos = Empresa.builder()
                    .nombre("San Carlos")
                    .cuil(30546780L)
                    .razonSocial("Venta de Alimentos Manufacturados")
                    .build();
            ImagenEmpresa imgEmpCarlos = ImagenEmpresa.builder()
                    .url("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSI2INnfz2sb25ROCczmEQdKYAIlCFbbItGDg&s")
                    .empresa(empresaCarlos)
                    .build();
            empresaCarlos.getImagenesEmpresa().add(imgEmpCarlos);

            // Guardar Empresa e Imagen
            empresaRepository.save(empresaCarlos);
            imagenEmpresaRepository.save(imgEmpCarlos);

            // Crear Sucursales, las Imagenes y asociarlas
            Sucursal sucursalGuaymallen = Sucursal.builder()
                    .nombre("En Guaymallen")
                    .horarioApertura(LocalTime.of(17, 0))
                    .horarioCierre(LocalTime.of(23, 0))
                    .empresa(empresaCarlos)
                    .build();
            Sucursal sucursalMarDelPlata = Sucursal.builder()
                    .nombre("En MDP")
                    .horarioApertura(LocalTime.of(16, 0))
                    .horarioCierre(LocalTime.of(23, 30))
                    .empresa(empresaCarlos)
                    .build();
            ImagenSucursal imgSucGuay = ImagenSucursal.builder()
                    .url("https://www.guaymallen.gob.ar/wp-content/uploads/2019/01/escudo-1-229x300.png")
                    .sucursal(sucursalGuaymallen)
                    .build();
            ImagenSucursal imgSucMDP = ImagenSucursal.builder()
                    .url("https://upload.wikimedia.org/wikipedia/commons/2/22/Escudo_de_General_Pueyrred%C3%B3n_%28color%29.svg")
                    .sucursal(sucursalMarDelPlata)
                    .build();
            sucursalGuaymallen.getImagenesSucursal().add(imgSucGuay);
            sucursalMarDelPlata.getImagenesSucursal().add(imgSucMDP);

            // Guardar Sucursales e Imagenes
            sucursalRepository.saveAll(Arrays.asList(sucursalGuaymallen, sucursalMarDelPlata));
            imagenSucursalRepository.saveAll(Arrays.asList(imgSucGuay, imgSucMDP));

            // Crear Domicilios
            Domicilio domicilioBerutti = Domicilio.builder().cp(5519).calle("Berutti").numero(2684).piso(0).nroDpto(5).localidad(localidad1).build();
            Domicilio domicilioGaboto = Domicilio.builder().cp(7600).calle("Gaboto").numero(3475).localidad(localidad2).build();
            domicilioRepository.saveAll(Arrays.asList(domicilioBerutti, domicilioGaboto));

            // Asignar Domicilios a Sucursales
            sucursalGuaymallen.setDomicilio(domicilioBerutti);
            sucursalMarDelPlata.setDomicilio(domicilioGaboto);

            // Guardar Sucursales
            sucursalRepository.saveAll(Arrays.asList(sucursalGuaymallen, sucursalMarDelPlata));
            logger.info("-----> Terminada la creacion de Empresa, Sucursales y Domicilios, y asignacion de Domicilio a Sucursal");

            // Crear Categorías y Sub-Categorías
            Categoria categoriaBebidas = Categoria.builder().denominacion("Bebidas").build();
            Categoria categoriaGaseosas = Categoria.builder().denominacion("Gaseosas").build();
            Categoria categoriaTragos = Categoria.builder().denominacion("Tragos").build();
            Categoria categoriaPizzas = Categoria.builder().denominacion("Pizzas").build();
            Categoria categoriaInsumos = Categoria.builder().denominacion("Insumos").build();
            categoriaRepository.saveAll(Arrays.asList(categoriaBebidas, categoriaGaseosas, categoriaTragos, categoriaPizzas, categoriaInsumos));

            // Asignar Sub-Categorías
            categoriaBebidas.getSubCategorias().addAll(Arrays.asList(categoriaGaseosas, categoriaTragos));
            categoriaRepository.save(categoriaBebidas);

            // Asignar Categorías a sucursales
            sucursalGuaymallen.getCategorias().addAll(Arrays.asList(categoriaInsumos, categoriaBebidas, categoriaGaseosas, categoriaTragos, categoriaPizzas));
            sucursalRepository.save(sucursalGuaymallen);
            logger.info("-----> Terminada la creacion de Categorias, asignacion de Sub-Categorias y asignacion de Categorias a Sucursal");

            // Crear unidades de medida
            UnidadMedida unidadMedidaLitros = UnidadMedida.builder().denominacion("Litros").build();
            UnidadMedida unidadMedidaGramos = UnidadMedida.builder().denominacion("Gramos").build();
            UnidadMedida unidadMedidaCantidad = UnidadMedida.builder().denominacion("Cantidad").build();
            UnidadMedida unidadMedidaPorciones = UnidadMedida.builder().denominacion("Porciones").build();
            unidadMedidaRepository.saveAll(Arrays.asList(unidadMedidaLitros, unidadMedidaGramos, unidadMedidaCantidad, unidadMedidaPorciones));
            logger.info("-----> Terminada la creacion de Unidades de Medida");

            // Crear Articulos Insumos
            // Crear CocaCola, la imagen y asociarla
            ArticuloInsumo cocaCola = ArticuloInsumo.builder()
                    .denominacion("Coca Cola")
                    .unidadMedida(unidadMedidaLitros)
                    .esParaElaborar(false)
                    .categoria(categoriaGaseosas)
                    .stockActual(1500.0)
                    .stockMinimo(10)
                    .precioCompra(50.0)
                    .precioVenta(70.0)
                    .build();
            ImagenArticulo imagenCoca = ImagenArticulo.builder()
                    .url("https://m.media-amazon.com/images/I/51v8nyxSOYL._SL1500_.jpg")
                    .articulo(cocaCola)
                    .build();
            cocaCola.getImagenesArticulo().add(imagenCoca);

            // Crear Harina, la imagen y asociarla
            ArticuloInsumo harina = ArticuloInsumo.builder()
                    .denominacion("Harina")
                    .unidadMedida(unidadMedidaGramos)
                    .esParaElaborar(true)
                    .categoria(categoriaInsumos)
                    .stockActual(10000.0)
                    .stockMinimo(10)
                    .precioCompra(40.0)
                    .precioVenta(60.5)
                    .build();
            ImagenArticulo imagenHarina = ImagenArticulo.builder()
                    .url("https://mandolina.co/wp-content/uploads/2023/03/648366622-1024x683.jpg")
                    .articulo(harina)
                    .build();
            harina.getImagenesArticulo().add(imagenHarina);

            // Crear Tomate, la imagen y asociarla
            ArticuloInsumo tomate = ArticuloInsumo.builder()
                    .denominacion("Tomate")
                    .unidadMedida(unidadMedidaCantidad)
                    .esParaElaborar(true)
                    .categoria(categoriaInsumos)
                    .stockActual(10000.0)
                    .stockMinimo(10)
                    .precioCompra(23.6)
                    .precioVenta(66.6)
                    .build();
            ImagenArticulo imagenTomate = ImagenArticulo.builder()
                    .url("https://thefoodtech.com/wp-content/uploads/2020/06/Componentes-de-calidad-en-el-tomate-828x548.jpg")
                    .articulo(tomate)
                    .build();
            tomate.getImagenesArticulo().add(imagenTomate);

            // Crear Queso, la imagen y asociarla
            ArticuloInsumo queso = ArticuloInsumo.builder()
                    .denominacion("Queso")
                    .unidadMedida(unidadMedidaGramos)
                    .esParaElaborar(true)
                    .categoria(categoriaInsumos)
                    .stockActual(10000.0)
                    .stockMinimo(10)
                    .precioCompra(23.6)
                    .precioVenta(66.6)
                    .build();
            ImagenArticulo imagenQueso = ImagenArticulo.builder()
                    .url("https://superdepaso.com.ar/wp-content/uploads/2021/06/SANTAROSA-PATEGRAS-04.jpg")
                    .articulo(queso)
                    .build();
            queso.getImagenesArticulo().add(imagenQueso);

            // Guardar Articulos Insumos e Imagenes
            articuloInsumoRepository.saveAll(Arrays.asList(cocaCola, harina, tomate, queso));
            imagenArticuloRepository.saveAll(Arrays.asList(imagenCoca, imagenHarina, imagenTomate, imagenQueso));
            logger.info("-----> Terminada la creacion de Articulos Insumos");

            // Crear Articulos Manufacturados
            // Crear Pizza Muzarella, la imagen y asociarla
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
            // Crear Detalles de Pizza Muzarella y asociarla
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

            // Crear Pizza Napolitana, la imagen y asociarla
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
            // Crear Detalles de Pizza Napolitana y asociarla
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

            // Guardar los Artículos Manufacturados y los Artículos Manufacturados Detalles e Imagenes
            articuloManufacturadoRepository.saveAll(Arrays.asList(pizzaMuzarella, pizzaNapolitana));
            articuloManufacturadoDetalleRepository.saveAll(Arrays.asList(detalleHarinaPizzaMuzarella, detalleHarinaPizzaNapolitana));
            imagenArticuloRepository.saveAll(Arrays.asList(imagenPizzaMuzarella, imagenPizzaNapolitana));

            // Asignar Pizzas a Categoria Pizzas
            categoriaPizzas.getArticulos().addAll(Arrays.asList(pizzaMuzarella, pizzaNapolitana));
            categoriaRepository.save(categoriaPizzas);
            logger.info("-----> Terminada la creacion de Articulos Manufacturados (Pizzas) y asignacion a la Categoria Pizzas");

            // Crear Promociones
            // Crear Promocion Dia de los Enamorados
            Promocion promoDiaEnamorados = Promocion.builder()
                    .denominacion("Dia de los Enamorados")
                    .fechaDesde(LocalDate.of(2024, 2, 13))
                    .fechaHasta(LocalDate.of(2024, 2, 15))
                    .horaDesde(LocalTime.of(0, 0))
                    .horaHasta(LocalTime.of(23, 59))
                    .descripcionDescuento("Dia 13, 14 y 15 con descuento - 2 Pizzas Muzarella + 2 Coca Cola 1.5lts")
                    .precioPromocional(150.0)
                    .tipoPromocion(TipoPromocion.PROMOCION)
                    .build();
            // ACA VA LA CREACION DE "ImagenPromocion" CON LA ASOCIACION A LA PROMO
            // Crear Detalles de Promocion Dia de los Enamorados y asociarlos con la Promocion
            PromocionDetalle detallePromo1 = PromocionDetalle.builder()
                    .cantidad(2)
                    .articulo(pizzaMuzarella)
                    .build();
            PromocionDetalle detallePromo2 = PromocionDetalle.builder()
                    .cantidad(2)
                    .articulo(cocaCola)
                    .build();
            promoDiaEnamorados.getPromocionDetalles().addAll(Arrays.asList(detallePromo1, detallePromo2));

            // Guardar Promocion y los Detalles de Promocion
            promocionDetalleRepository.save(detallePromo1);
            promocionDetalleRepository.save(detallePromo2);
            promocionRepository.save(promoDiaEnamorados);

            // Crear Promocion Pizza + Coca
            Promocion promoPizzaConCocas = Promocion.builder()
                    .denominacion("Pizza + Cocas")
                    .fechaDesde(LocalDate.of(2024, 1, 1))
                    .fechaHasta(LocalDate.of(2024, 12, 31))
                    .horaDesde(LocalTime.of(20, 0))
                    .horaHasta(LocalTime.of(23, 0))
                    .descripcionDescuento("Pizza Napolitana + 2 Coca Cola 1.5lts")
                    .precioPromocional(100.0)
                    .tipoPromocion(TipoPromocion.PROMOCION)
                    .build();
            // ACA VA LA CREACION DE "ImagenPromocion" CON LA ASOCIACION A LA PROMO
            // Crear Detalles de Promocion Pizza + Cocas y asociarlos con la Promocion
            PromocionDetalle detallePromo3 = PromocionDetalle.builder()
                    .cantidad(1)
                    .articulo(pizzaNapolitana)
                    .build();
            PromocionDetalle detallePromo4 = PromocionDetalle.builder()
                    .cantidad(2)
                    .articulo(cocaCola)
                    .build();
            promoPizzaConCocas.getPromocionDetalles().addAll(Arrays.asList(detallePromo3, detallePromo4));

            // Guardar Promocion y los Detalles de Promocion
            promocionDetalleRepository.save(detallePromo3);
            promocionDetalleRepository.save(detallePromo4);
            promocionRepository.save(promoPizzaConCocas);
            logger.info("-----> Terminada la creacion de Promociones");
            logger.info("--------------------------------------------------->");
            logger.info("--------------------------------------------------->");

            //----------------------------------------------------------------------------------------------------------
            // TODO LO DE ARRIBA ESTA BIEN ESTRUCTURADO
            //----------------------------------------------------------------------------------------------------------

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
            Pedido pedido = Pedido.builder().fechaPedido(LocalDate.now()).horaEstimadaFinalizacion(LocalDateTime.now()).total(300.0).totalCosto(170.6).estado(Estado.PREPARACION).formaPago(FormaPago.MERCADO_PAGO).tipoEnvio(TipoEnvio.DELIVERY).sucursal(sucursalGuaymallen).domicilio(domicilioCliente).build();

            PedidoDetalle detallePedido1 = PedidoDetalle.builder().articulo(pizzaMuzarella).cantidad(1).subTotal(200.0).pedido(pedido).build();
            PedidoDetalle detallePedido2 = PedidoDetalle.builder().articulo(cocaCola).cantidad(2).subTotal(100.0).pedido(pedido).build();

            pedido.getPedidoDetalles().add(detallePedido1);
            pedido.getPedidoDetalles().add(detallePedido2);


            pedido.setCliente(cliente);
            pedido.setEmpleado(empleado);
            pedidoRepository.save(pedido);

            Random random = new Random();
            Factura facturaBuilder = Factura.builder().fechaFacturacion(LocalDate.now()).mpPaymentId(random.nextInt(1000))  // Se asume un rango máximo de 1000
                    .mpMerchantOrderId(random.nextInt(1000)) // Se asume un rango máximo de 1000
                    .mpPreferenceId("MP-" + random.nextInt(10000))  // Se asume un rango máximo de 10000
                    .mpPaymentType("Tipo" + random.nextInt(10)) // Se asume un rango máximo de 10
                    .formaPago(FormaPago.EFECTIVO).totalVenta(random.nextDouble() * 1000).build();

            Factura facturaBuilder2 = Factura.builder().fechaFacturacion(LocalDate.now()).mpPaymentId(random.nextInt(1000))  // Se asume un rango máximo de 1000
                    .mpMerchantOrderId(random.nextInt(1000)) // Se asume un rango máximo de 1000
                    .mpPreferenceId("MP-" + random.nextInt(10000))  // Se asume un rango máximo de 10000
                    .mpPaymentType("Tipo" + random.nextInt(10)) // Se asume un rango máximo de 10
                    .formaPago(FormaPago.EFECTIVO).totalVenta(random.nextDouble() * 1000).build();

            facturaRepository.save(facturaBuilder);
            facturaRepository.save(facturaBuilder2);

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



