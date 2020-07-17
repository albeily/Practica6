package entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "VENTAS")
public class Ventas implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date fechaCompra;
    private String nombreCliente;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<VentasProductos> ventasProductos;


    public Ventas(long id, Date fechaCompra, String nombreCliente, List<VentasProductos> ventasProductos) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.nombreCliente = nombreCliente;
        this.ventasProductos = ventasProductos;

    }

    public Ventas() {

    }

    public List<VentasProductos> getVentasProductos() {

        return this.ventasProductos;
    }

    public void setVentasProductos(List<VentasProductos> ventasProductos) {
        this.ventasProductos = ventasProductos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }


    public BigDecimal totalVenta() {
        double total = 0.00;

        for (int i = 0; i < this.getVentasProductos().size(); i++) {
            total = total + this.getVentasProductos().get(i).totalCompra().doubleValue();
        }
        return BigDecimal.valueOf(total);

    }


}
