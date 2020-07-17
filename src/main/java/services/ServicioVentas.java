package services;

import entidades.Ventas;
import entidades.VentasProductos;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ServicioVentas extends GestionBD<Ventas> {


    private static ServicioVentas instancia;

    private ServicioVentas() {
        super(Ventas.class);
    }

    public static ServicioVentas getInstancia() {
        if (instancia == null) {
            instancia = new ServicioVentas();
        }
        return instancia;
    }

    public List<Ventas> getVentas() {
        return super.buscarTodos();
    }

    public Ventas getVenta(long idVenta) {
        return super.buscar(idVenta);
    }

    public Ventas crearVenta(Ventas ventas) {
        return super.crear(ventas);

    }

    public List<VentasProductos> getVentas_Productos(long idVenta) {
        EntityManager em = super.getEntityManager();
        Query query = em.createNativeQuery("select VENTAPRODUCTO_IDVENTAPRODUCTO, CANTIDAD from  VENTAS_VENTASPRODUCTOS vp where vp.ventas_id = :id");
        query.setParameter("id", idVenta);
        List<VentasProductos> ventasProductosList = ((List<VentasProductos>) query.getResultList());
        return ventasProductosList;
    }


}
