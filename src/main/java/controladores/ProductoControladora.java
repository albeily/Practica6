package controladores;

import entidades.Comentarios;
import entidades.Producto;
import entidades.Usuario;
import io.javalin.Javalin;
import io.javalin.http.Context;
import logical.CarroCompra;
import logical.Market;
import services.ServicioComentarios;
import services.ServicioProducto;
import services.ServicioUsuario;
import util.ControladorBase;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ProductoControladora extends ControladorBase {

    Context ctx;
    private Market market;
    public static Producto provisional;
    private static CarroCompra carrito;
    private Map<String, Object> model;
    private ServicioUsuario servicioUsuario;

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Producto getProvisional() {
        return provisional;
    }

    public void setProvisional(Producto provisional) {
        this.provisional = provisional;
    }


    public ProductoControladora(Javalin app, Market miMarket) {
        super(app);
        this.market = miMarket;

    }

    @Override
    public void rutas() {


        app.routes(() -> {

            path("/producto/agregarcomentario", () -> {

                get(ctx -> {
                    String id = ctx.queryParam("id");
                    model = model = new HashMap<String, Object>();
                    model.put("numProductos", ((CarroCompra) ctx.sessionAttribute("carrito")).calcularCantidad());
                    Producto producto = ServicioProducto.getInstancia().getProducto(Integer.parseInt(id));
                    model.put("miProducto", producto);
                    model.put("sesion",ctx.sessionAttribute("user"));
                    model.put("comentarios", producto.getComentariosList());
                    model.put("user", (Usuario) ctx.sessionAttribute("user"));
                    ctx.render("/design/comentarios.html", model);

                });

                post(ctx -> {
                    String comentario = ctx.formParam("comentario");
                    String id = ctx.formParam("id");
                    model = model = new HashMap<String, Object>();
                    Producto producto;
                    ServicioComentarios.getInstancia().agregarCometarios(Integer.parseInt(id), comentario);
                    producto = ServicioProducto.getInstancia().getProducto(Integer.parseInt(id));
                    model.put("numProductos", ((CarroCompra) ctx.sessionAttribute("carrito")).calcularCantidad());
                    model.put("sesion", ctx.sessionAttribute("user"));
                    model.put("miProducto", producto);
                    model.put("comentarios", producto.getComentariosList());
                    model.put("user", (Usuario) ctx.sessionAttribute("user"));
                    ctx.render("/design/comentarios.html", model);

                });

            });


            path("/producto/eliminarcomentario", () -> {

                post(ctx -> {
                    Producto producto;
                    String id = ctx.formParam("id");
                    String comentario = ctx.formParam("comentario");
                    String fecha = ctx.formParam("fecha");
                    ServicioComentarios.getInstancia().eliminarComentario(Integer.parseInt(id),comentario,fecha);
                    producto = ServicioProducto.getInstancia().getProducto(Integer.parseInt(id));
                    model.put("numProductos", ((CarroCompra) ctx.sessionAttribute("carrito")).calcularCantidad());
                    model.put("sesion", ctx.sessionAttribute("user"));
                    model.put("miProducto", producto);
                    model.put("comentarios", producto.getComentariosList());
                    model.put("user", (Usuario) ctx.sessionAttribute("user"));
                    ctx.render("/design/comentarios.html", model);




                });


            });

        });


    }
}
