package services;

import entidades.Comentarios;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServicioComentarios extends GestionBD<Comentarios> {

    private static ServicioComentarios instancia;
    private List<Comentarios> comentariosListList = new ArrayList<>();

    private ServicioComentarios() {
        super(Comentarios.class);
    }

    public static ServicioComentarios getInstancia() {
        if (instancia == null) {
            instancia = new ServicioComentarios();
        }
        return instancia;
    }


    @Transactional
    public void agregarCometarios(int id, String comentario) {
        Date fecha = new Date(System.currentTimeMillis());
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Query query = em.createNativeQuery("INSERT INTO PRODUCTO_COMENTARIOSLIST (PRODUCTO_ID,COMENTARIO,FECHA)VALUES(:id,:comentario,:fecha)");
        query.setParameter("comentario", comentario);
        query.setParameter("id", id);
        query.setParameter("fecha", fecha.toString());
        query.executeUpdate();
        em.getTransaction().commit();


    }

    public void eliminarComentario (int id , String comentario, String fecha){

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Query query = em.createNativeQuery("DELETE FROM PRODUCTO_COMENTARIOSLIST WHERE PRODUCTO_ID = :id AND COMENTARIO like :comentario AND FECHA like :fecha");
        query.setParameter("comentario", comentario);
        query.setParameter("id", id);
        query.setParameter("fecha", fecha);
        query.executeUpdate();
        em.getTransaction().commit();



    }
}
