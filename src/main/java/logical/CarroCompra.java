package logical;

import services.ServicioProducto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarroCompra {

    private long id;
    private List<CompraProducto> listaCompraProductos;

    public CarroCompra(long id) {
        this.id = id;
        this.listaCompraProductos = new ArrayList<CompraProducto>();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<CompraProducto> getListaCompraProductos() {
        return this.listaActualizada(this.listaCompraProductos);
    }

    public void setListaCompraProductos(List<CompraProducto> listaCompraProductos) {
        this.listaCompraProductos = listaCompraProductos;
    }

    public void productosCarrito(CompraProducto compraProducto) {
        this.listaCompraProductos.add(compraProducto);
    }


    public BigDecimal totalCarrito(CarroCompra carrito) {
        long total = 0;
        for (int i = 0; i < carrito.listaCompraProductos.size(); i++) {
            total += carrito.getListaCompraProductos().get(i).totalCompra().longValue();

        }

        return BigDecimal.valueOf(total);

    }

    public void agregarProducto(CompraProducto compraProducto, CarroCompra carrito) {
        CompraProducto carroAux = null;

        for (CompraProducto carro : carrito.getListaCompraProductos()
        ) {

            if (carro.getProducto().getId() == compraProducto.getProducto().getId()) {
                carroAux = carro;
                break;
            }

        }
        if (carroAux != null) {
            carroAux.setCantidad(carroAux.getCantidad() + compraProducto.getCantidad());
        } else {
            carrito.getListaCompraProductos().add(compraProducto);
        }
    }


    public void limpiar() {
        this.listaCompraProductos = new ArrayList<CompraProducto>();
    }

    public CompraProducto buscarProductoCarrito(int id) {
        for (CompraProducto compra : this.getListaCompraProductos()
        ) {
            if (compra.getProducto().getId() == id) {
                return compra;
            }
        }
        return null;
    }

    public int calcularCantidad() {
        int cantidad = 0;

        for (CompraProducto compraProducto : this.getListaCompraProductos()
        ) {

            cantidad += compraProducto.getCantidad();

        }
        return cantidad;
    }


    private List<CompraProducto> listaActualizada(List<CompraProducto> compraProductos) {

        for (CompraProducto producto : compraProductos
        ) {
            producto.setProducto(ServicioProducto.getInstancia().getProducto(producto.getProducto().getId()));
        }
        return compraProductos;
    }
}
