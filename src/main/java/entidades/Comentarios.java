package entidades;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Comentarios implements Serializable {


    private String comentario;
    private String fecha;

    public Comentarios() {

    }

    public Comentarios(String comentario, String fecha) {
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;

    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
