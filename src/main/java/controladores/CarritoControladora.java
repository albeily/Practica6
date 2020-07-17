package controladores;

import entidades.Producto;
import io.javalin.Javalin;
import io.javalin.http.Context;
import logical.CarroCompra;
import logical.Market;
import services.ServicioUsuario;
import util.ControladorBase;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class CarritoControladora extends ControladorBase {

    Context ctx;
    private Market market;
    private Producto provisional;
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


    public CarritoControladora(Javalin app, Market miMarket) {
        super(app);
        this.market = miMarket;

    }

    @Override
    public void rutas() {

        app.routes(() -> {

            path("/carrito", () -> {

                get(ctx -> {

                    carrito = ctx.sessionAttribute("carrito");
                    model = new HashMap<String, Object>();
                    if (carrito != null) {
                        model.put("miCarrito", carrito.getListaCompraProductos());
                        model.put("total", carrito.totalCarrito(carrito));
                        model.put("numProductos", ((CarroCompra) ctx.sessionAttribute("carrito")).calcularCantidad());
                        model.put("sesion", ctx.sessionAttribute("user"));
                        ctx.render("/design/carrito.html", model);
                    }
                });

                path("/procesarCompra", () -> {

                    post(ctx -> {

                        String nombreCliente = ctx.formParam("cliente");
                        carrito = ctx.sessionAttribute("carrito");
                        market.procesarCompra(nombreCliente, carrito);
                        ctx.redirect("/carrito/limpiarCarrito");


                    });

                });

                path("/limpiarCarrito", () -> {

                    get(ctx -> {
                        carrito = ctx.sessionAttribute("carrito");
                        carrito.limpiar();
                        ctx.redirect("/carrito");
                    });


                });

                path("/eliminar", () -> {

                    get(ctx -> {
                        carrito = ctx.sessionAttribute("carrito");
                        String id = ctx.queryParam("id");
                        if (id != null) {
                            market.eliminarProductoCarrito(carrito, Integer.parseInt(id));
                            ctx.redirect("/carrito");
                        }
                    });


                });

            });
        });

    }
}
