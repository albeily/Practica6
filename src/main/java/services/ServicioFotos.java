package services;

import entidades.Fotos;
import entidades.Producto;

import java.util.ArrayList;
import java.util.List;

public class ServicioFotos extends GestionBD<Fotos> {

    private static ServicioFotos instancia;
    private List<Fotos> fotosList = new ArrayList<>();

    private ServicioFotos() {
        super(Fotos.class);
    }

    public static ServicioFotos getInstancia() {
        if (instancia == null) {
            instancia = new ServicioFotos();
        }
        return instancia;
    }

    public Fotos buscar(int producto, String foto) {
        Producto aux;
        Fotos fotos = null;
        aux = ServicioProducto.getInstancia().getProducto(producto);

        for (int i = 0; i < aux.getFotosList().size(); i++) {
            if (aux.getFotosList().get(i).getNombre().equals(foto)) {
                fotos = aux.getFotosList().get(i);

            }
        }
        return fotos;
    }

    public void eliminar(int producto, String foto) {

        Producto producto1;
        producto1 = ServicioProducto.getInstancia().getProducto(producto);
        producto1.setId(producto);
        for (int i = 0; i < producto1.getFotosList().size(); i++) {
            if (producto1.getFotosList().get(i).getNombre().equals(foto)) {
                producto1.getFotosList().remove(producto1.getFotosList().get(i));
                ServicioProducto.getInstancia().editarProducto(producto1);

            }
        }

    }

}
