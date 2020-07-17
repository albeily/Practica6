package controladores;

import entidades.Producto;
import entidades.Usuario;
import io.javalin.Javalin;
import io.javalin.http.Context;
import logical.CarroCompra;
import logical.Market;
import services.ServicioUsuario;
import util.ControladorBase;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class VentasControladora extends ControladorBase {

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


    public VentasControladora(Javalin app, Market miMarket) {
        super(app);
        this.market = miMarket;

    }

    @Override
    public void rutas() {


        app.routes(() -> {

            before("/ventasrealizadas", ctx -> {


                if ((Usuario) ctx.sessionAttribute("user") == null ) {
                    ctx.redirect("/autenticacionusuario/autenticar");
                }

            });

            path("/ventasrealizadas", () -> {


                get(ctx -> {

                    model = new HashMap<String, Object>();
                    model.put("misVentas", market.getMisVentas());
                    model.put("numProductos", ((CarroCompra) ctx.sessionAttribute("carrito")).calcularCantidad());
                    model.put("sesion", ctx.sessionAttribute("user"));
                    ctx.render("/design/ventasrealizadas.html", model);
                });


            });

        });

    }
}
