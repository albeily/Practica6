package logical;

import entidades.Producto;

import java.math.BigDecimal;


public class CompraProducto {

    private int cantidad;
    private Producto producto;


    public CompraProducto(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;

    }

    public CompraProducto() {

    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }


    public BigDecimal totalCompra() {

        return BigDecimal.valueOf(this.getProducto().getPrecio().doubleValue() * this.getCantidad());
    }
}



