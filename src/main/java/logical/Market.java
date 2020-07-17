package logical;

import entidades.Producto;
import entidades.Usuario;
import entidades.Ventas;
import services.ServicioProducto;
import services.ServicioUsuario;
import services.ServicioVentas;
import services.ServicioVentasProductos;

import java.util.Date;
import java.util.List;

public class Market {

    //private List<Usuario> misUsuarios;
    //private List<Producto> misProductos;
    //private List<CarroCompra> misCarritos;
    //private List<Ventas> misVentas;

    public Market() {
        EstadoDefecto estadoDefecto = new EstadoDefecto();
    }

    public List<Usuario> getMisUsuarios() {
        return ServicioUsuario.getInstancia().listaUsuarios();
    }

    public List<Producto> getMisProductos() {
        return ServicioProducto.getInstancia().listaProductos();
    }

    public List<Ventas> getMisVentas() {
        return ServicioVentas.getInstancia().getVentas();
    }

    /*public void setMisUsuarios(List<Usuario> misUsuarios) {
        this.misUsuarios = misUsuarios;
    }*/

  /*  public void setMisProductos(List<Producto> misProductos) {
        this.misProductos = misProductos;
    }*/

   /* public List<CarroCompra> getMisCarritos() {
        return misCarritos;
    }
    public void setMisCarritos(List<CarroCompra> misCarritos) {
        this.misCarritos = misCarritos;
    }*/

    /*public void setMisVentas(List<Ventas> misVentas) {
        this.misVentas = misVentas;
    }
public long carritoID() {

        long id = 0;

        do {
            id++;
        } while (buscarCarrito(id) != null);

        CarroCompra carrito = new CarroCompra(id);
        this.misCarritos.add(carrito);

        return id;
    }

    public CarroCompra buscarCarrito(long id) {

        for (CarroCompra carrito : this.misCarritos
        ) {
            if (carrito.getId() == id) {
                return carrito;
            }
        }
        return null;
    }*/

    public void eliminarProducto(int id) {
        ServicioProducto.getInstancia().borrarProducto(id);
    }

    public Producto buscarProducto(int id) {
        return ServicioProducto.getInstancia().getProducto(id);
    }

    /*private int generarID() {
        int id = 0;
        do {
            id = (int) (1 + Math.random() * Integer.MAX_VALUE);
        } while (ServicioProducto.getInstancia().getProducto(id) != null);
        return id;
    }*/

    public void crearEditarProducto(Producto producto) {
        Producto aux = new Producto();
        if (producto.getId() == 0) {
            aux.setPrecio(producto.getPrecio());
            aux.setNombre(producto.getNombre());
            aux.setFotosList(producto.getFotosList());
            aux.setDescripcion(producto.getDescripcion());
            ServicioProducto.getInstancia().crearProducto(aux);
        } else {
            ServicioProducto.getInstancia().editarProducto(producto);
        }
    }


    public String verificacionUsuario(String usuario, String password, String norecordar) {
        String sesion = "Iniciar Sesion";
        if (usuario != null && password != null && (norecordar == null)) {
            sesion = "(" + usuario + ") Salir";
        }
        if (usuario == null && password == null && (norecordar != null)) {
            sesion = "(" + norecordar + ") Salir";

        }
        return sesion;
    }


    public void agregarProducto(int idProducto, int cantidad, CarroCompra carrito) {
        CompraProducto compra = new CompraProducto(cantidad, this.buscarProducto(idProducto));
        carrito.agregarProducto(compra, carrito);
    }

    public void eliminarProductoCarrito(CarroCompra carroCompra, int idProducto) {
        System.out.print(carroCompra);
        carroCompra.getListaCompraProductos().remove(carroCompra.buscarProductoCarrito(idProducto));
    }

    public void procesarCompra(String cliente, CarroCompra carroCompra) {
        Ventas ventas = new Ventas();

        if (!carroCompra.getListaCompraProductos().isEmpty()) {
            ventas.setFechaCompra(new Date(System.currentTimeMillis()));
            ventas.setNombreCliente(cliente);
            ventas.setVentasProductos(ServicioVentasProductos.getInstancia().misVentasProductos(carroCompra.getListaCompraProductos()));
            ServicioVentas.getInstancia().crearVenta(ventas);
        }
    }

    public List<Producto> productosPaginacion(int pagina) {
        return ServicioProducto.getInstancia().productosXpagina(pagina);
    }


}