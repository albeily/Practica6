package services;

//import com.avathartech.demojdbc.encapsulacion.Producto;

import entidades.Comentarios;
import entidades.Producto;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para encapsular los metodos de CRUD
 * Created by vacax on 30/04/16.
 */
public class ServicioProducto extends GestionBD<Producto> {

    private static ServicioProducto instancia;

    private ServicioProducto() {
        super(Producto.class);
    }

    public static ServicioProducto getInstancia() {
        if (instancia == null) {
            instancia = new ServicioProducto();
        }
        return instancia;
    }

    public List<Producto> listaProductos() {
        return super.buscarTodos();
    }

    public Producto getProducto(int id) {
        return super.buscar(id);
    }

    public Producto crearProducto(Producto producto) {

        return super.crear(producto);
    }

    public Producto editarProducto(Producto producto) {

        return super.editar(producto);
    }

    public boolean borrarProducto(int id) {

        return super.eliminar(id);
    }

    public List<Producto> productosXpagina(int pagina) {

        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM PRODUCTO", Producto.class);
        query.setFirstResult((pagina - 1) * 10);
        query.setMaxResults(10);
        List<Producto> productoList = query.getResultList();

        return productoList;


    }

    public List<Comentarios> getComentarioList(int id) {
        List<Comentarios> comentariosList = new ArrayList<>();
        EntityManager em = getEntityManager();
        Query comentarioQuery = em.createNativeQuery("SELECT COMENTARIO FROM PRODUCTO_COMENTARIOSLIST WHERE PRODUCTO_ID = :id");
        comentarioQuery.setParameter("id", id);
        Query fechaQuery = em.createNativeQuery("SELECT FECHA FROM PRODUCTO_COMENTARIOSLIST WHERE PRODUCTO_ID = :id");
        fechaQuery.setParameter("id", id);
        List<String> comentarios = comentarioQuery.getResultList();
        List<String> fechas = fechaQuery.getResultList();
        for (int i = 0; i < comentarios.size(); i++) {
            comentariosList.add(new Comentarios(comentarios.get(i), fechas.get(i)));
        }
        return comentariosList;
    }
}