package services;

import entidades.VentaProducto;

import java.util.List;

public class ServicioVentaProducto extends GestionBD<VentaProducto> {

    private static ServicioVentaProducto instancia;

    private ServicioVentaProducto() {
        super(VentaProducto.class);
    }

    public static ServicioVentaProducto getInstancia() {
        if (instancia == null) {
            instancia = new ServicioVentaProducto();
        }
        return instancia;
    }

    public List<VentaProducto> getVentasProductos() {
        return super.buscarTodos();
    }

    public VentaProducto getVentaProducto(long idVentaProducto) {
        return super.buscar(idVentaProducto);
    }

    public VentaProducto crearVentaProducto(VentaProducto ventaProducto) {
        return super.crear(ventaProducto);
    }
}
