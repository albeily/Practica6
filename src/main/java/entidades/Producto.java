package entidades;

import services.ServicioProducto;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "PRODUCTO")
public class Producto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    @ElementCollection(fetch = FetchType.EAGER)
    List<Fotos> fotosList;
    @ElementCollection
    List<Comentarios> comentariosList;

    public Producto() {

    }

    public Producto(int id, String nombre, BigDecimal precio, List<Fotos> fotosList, String descripcion, List<Comentarios> comentarios) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.fotosList = fotosList;
        this.descripcion = descripcion;
        this.comentariosList = comentarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Fotos> getFotosList() {
        return fotosList;
    }

    public void setFotosList(List<Fotos> fotosList) {
        this.fotosList = fotosList;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Comentarios> getComentariosList() {

        return ServicioProducto.getInstancia().getComentarioList(this.id);
    }

    public void setComentariosList(List<Comentarios> comentariosList) {
        this.comentariosList = comentariosList;
    }

}
