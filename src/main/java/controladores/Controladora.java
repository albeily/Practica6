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
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Controladora extends ControladorBase {

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


    public Controladora(Javalin app, Market miMarket) {
        super(app);
        this.market = miMarket;
        startup();

    }

    public void startup() {

        FotosControladora fotosControladora = new FotosControladora(app, this.market);
        AdministrarControladora administrarControladora = new AdministrarControladora(app, this.market);
        CarritoControladora carritoControladora = new CarritoControladora(app, this.market);
        UsuarioControladora usuarioControladora = new UsuarioControladora(app, this.market);
        VentasControladora ventasControladora = new VentasControladora(app, this.market);
        ProductoControladora productoControladora = new ProductoControladora(app, this.market);
        fotosControladora.rutas();
        administrarControladora.rutas();
        carritoControladora.rutas();
        usuarioControladora.rutas();
        ventasControladora.rutas();
        productoControladora.rutas();

    }

    @Override
    public void rutas() {

        app.routes(() -> {

            before(ctx -> {
                Usuario usuario = ctx.sessionAttribute("user");
                carrito = ctx.sessionAttribute("carrito");
                String passcookie;
                String password;
                if (carrito == null) {
                    carrito = new CarroCompra(1);
                    ctx.sessionAttribute("carrito", carrito);
                }
                if(usuario==null){
                    if(ctx.cookie("user") != null && ctx.cookie("password") != null){
                        usuario = ServicioUsuario.getInstancia().getUsuario(ctx.cookie("user"));
                        if (usuario!=null){
                            password = ServicioUsuario.getInstancia().desincryptar(usuario.getPassword());
                            passcookie = ServicioUsuario.getInstancia().desincryptar(ctx.cookie("password"));
                            if(passcookie.equals(password)){
                                ctx.sessionAttribute("user",usuario);
                            }

                        }
                    }
                }

            });

            path("/", () -> {

                get(ctx -> {
                    ctx.redirect("/listaproductos");
                });

                });


            path("/listaproductos", () -> {

                get(ctx -> {
                    List<Producto> productoList;
                    int pagina;
                    if (ctx.queryParam("pagina") == null) {

                        pagina = 1;

                    } else if (Integer.parseInt(ctx.queryParam("pagina")) == 0) {

                        pagina = 1;

                    } else {

                        pagina = Integer.parseInt(ctx.queryParam("pagina"));
                    }

                    productoList = market.productosPaginacion(pagina);
                    model = new HashMap<String, Object>();
                    model.put("pagina", pagina);
                    model.put("cantidad_pagina", (int) Math.ceil((double) market.getMisProductos().size() / 10));
                    model.put("misProductos", productoList);
                    model.put("numProductos", ((CarroCompra) ctx.sessionAttribute("carrito")).calcularCantidad());
                    model.put("sesion", ctx.sessionAttribute("user"));
                    ctx.render("/design/index.html", model);

                });

            });


            path("/agregarproducto", () -> {

                post(ctx -> {

                    String producto = ctx.formParam("id");
                    String cantidad = ctx.formParam("cantidad");
                    carrito = ctx.sessionAttribute("carrito");
                    market.agregarProducto(Integer.parseInt(producto), Integer.parseInt(cantidad), carrito);
                    ctx.redirect("/listaproductos");

                });

            });


        });


    }


}
