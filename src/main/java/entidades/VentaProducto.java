package entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "VENTA_PRODUCTO")
public class VentaProducto implements Serializable {

    private String nombre;
    private BigDecimal precio;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVentaProducto;

    public VentaProducto(String nombre, BigDecimal precio, int idVentaProducto) {
        this.nombre = nombre;
        this.precio = precio;
        this.idVentaProducto = idVentaProducto;
    }

    public VentaProducto() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getIdVentaProducto() {
        return idVentaProducto;
    }

    public void setIdVentaProducto(int idVentaProducto) {
        this.idVentaProducto = idVentaProducto;
    }
}
