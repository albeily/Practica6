package logical;

import entidades.Producto;
import entidades.Usuario;
import entidades.Ventas;
import services.ServicioProducto;
import services.ServicioUsuario;
import services.ServicioVentas;
import services.ServicioVentasProductos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EstadoDefecto {


    public EstadoDefecto() {
        productosDefecto();
        ventasDefecto();
        usuarioDefecto();
    }

    public static void productosDefecto() {

        Producto platano = new Producto();
        platano.setNombre("Platano");
        platano.setPrecio(BigDecimal.valueOf(10.00));
        platano.setDescripcion("Vivere de calidad. 100% Natural!");
        Producto salami = new Producto();
        salami.setNombre("Salami");
        salami.setPrecio(BigDecimal.valueOf(120.00));
        salami.setDescripcion("El mejor salami del cibao!");
        Producto refresco = new Producto();
        refresco.setNombre("Refresco");
        refresco.setPrecio(BigDecimal.valueOf(60.00));
        refresco.setDescripcion("Le queda chiquita la CocaCola!");
        ServicioProducto.getInstancia().crearProducto(platano);
        ServicioProducto.getInstancia().crearProducto(salami);
        ServicioProducto.getInstancia().crearProducto(refresco);


    }

    public static void ventasDefecto() {


        Ventas ventas = null;
        List<CompraProducto> compraProductos = new ArrayList<>();
        compraProductos.add(new CompraProducto(2, ServicioProducto.getInstancia().getProducto(1)));
        compraProductos.add(new CompraProducto(5, ServicioProducto.getInstancia().getProducto(2)));
        compraProductos.add(new CompraProducto(7, ServicioProducto.getInstancia().getProducto(3)));
        ventas = new Ventas();
        ventas.setFechaCompra(new Date(System.currentTimeMillis()));
        ventas.setNombreCliente("Bibi");
        ventas.setVentasProductos(ServicioVentasProductos.getInstancia().misVentasProductos(compraProductos));
        ServicioVentas.getInstancia().crearVenta(ventas);


    }

    public static void usuarioDefecto() {

        Usuario administrador = new Usuario("admin", "Albeily Romano", "admin");
        ServicioUsuario.getInstancia().crearUsuario(administrador);

    }


}
