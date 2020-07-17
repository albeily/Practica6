package controladores;

import entidades.Producto;
import entidades.Usuario;
import io.javalin.Javalin;
import io.javalin.http.Context;
import logical.CarroCompra;
import logical.Market;
import services.ServicioUsuario;
import util.ControladorBase;

import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UsuarioControladora extends ControladorBase {

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


    public UsuarioControladora(Javalin app, Market miMarket) {
        super(app);
        this.market = miMarket;

    }

    @Override
    public void rutas() {

        app.routes(() -> {

            path("/autenticacionusuario", () -> {

                post("/autenticar", ctx -> {
                    String usuarioID = ctx.formParam("usuario");
                    String password = ctx.formParam("password");
                    String recordarUsuario = ctx.formParam("recordarusuario");
                    Usuario usuario = ServicioUsuario.getInstancia().autenticarUsuario(usuarioID, password);
                    if (usuario != null) {
                        if (recordarUsuario == null) {
                            recordarUsuario = "off";
                        }
                        switch (recordarUsuario) {
                            case "on":
                                ctx.cookie("user", usuario.getUsuario(), 604800);
                                ctx.cookie("password", usuario.getPassword(), 604800);
                                if ((Usuario) ctx.sessionAttribute("user") != null) {
                                    ctx.redirect("/listaproductos");
                                } else {
                                    ctx.sessionAttribute("user", usuario);
                                    ctx.redirect("/listaproductos");
                                }

                                break;
                            case "off":
                                if ((Usuario) ctx.sessionAttribute("user") != null) {
                                    ctx.redirect("/listaproductos");
                                } else {
                                    ctx.sessionAttribute("user", usuario);
                                    ctx.redirect("/listaproductos");
                                }
                                break;
                        }
                    } else {
                        ctx.redirect("/autenticacionusuario/autenticar");
                    }

                });

                get("/autenticar", ctx -> {
                    if (ctx.sessionAttribute("user") == null) {
                        ctx.render("/design/login.html");
                    } else {
                        ctx.redirect("/listaproductos");
                    }
                });

                get("/desloguear", ctx -> {
                    ctx.removeCookie("user","/");
                    ctx.removeCookie("password","/");
                    ctx.sessionAttribute("user",null);
                    ctx.redirect("/listaproductos");
                });



            });
        });


    }
}
