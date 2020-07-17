package services;

import entidades.Producto;
import entidades.VentaProducto;
import entidades.VentasProductos;
import logical.CompraProducto;

import java.util.ArrayList;
import java.util.List;

public class ServicioVentasProductos {


    private static ServicioVentasProductos instancia;


    public static ServicioVentasProductos getInstancia() {
        if (instancia == null) {
            instancia = new ServicioVentasProductos();
        }
        return instancia;
    }

    public List<VentasProductos> misVentasProductos(List<CompraProducto> compraProductoList) {

        List<VentasProductos> ventasProductos = new ArrayList<VentasProductos>();
        VentasProductos ventasProductos1 = null;
        VentaProducto ventaProducto = null;
        Producto producto = null;

        for (int i = 0; i < compraProductoList.size(); i++) {
            producto = new Producto();
            ventaProducto = new VentaProducto();
            ventasProductos1 = new VentasProductos();
            producto = compraProductoList.get(i).getProducto();
            ventaProducto.setPrecio(producto.getPrecio());
            ventaProducto.setNombre(producto.getNombre());
            ServicioVentaProducto.getInstancia().crearVentaProducto(ventaProducto);
            ventasProductos1.setVentaProducto(ventaProducto);
            ventasProductos1.setCantidad(compraProductoList.get(i).getCantidad());
            ventasProductos.add(ventasProductos1);
        }

        return ventasProductos;

    }

}
