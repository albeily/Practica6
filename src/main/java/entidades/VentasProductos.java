package entidades;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
public class VentasProductos implements Serializable {

    private int cantidad;
    @OneToOne
    private VentaProducto ventaProducto;


    public VentasProductos(int cantidad, VentaProducto ventaProducto) {
        this.cantidad = cantidad;
        this.ventaProducto = ventaProducto;
    }

    public VentasProductos() {

    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public VentaProducto getVentaProducto() {
        return ventaProducto;
    }

    public void setVentaProducto(VentaProducto ventaProducto) {
        this.ventaProducto = ventaProducto;
    }

    public BigDecimal totalCompra() {

        return BigDecimal.valueOf(this.getVentaProducto().getPrecio().doubleValue() * this.getCantidad());
    }
}
