package controladores;

import entidades.Comentarios;
import entidades.Fotos;
import entidades.Producto;
import entidades.Usuario;
import io.javalin.Javalin;
import io.javalin.http.Context;
import logical.CarroCompra;
import logical.Market;
import services.ServicioProducto;
import services.ServicioUsuario;
import util.ControladorBase;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class AdministrarControladora extends ControladorBase {

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


    public AdministrarControladora(Javalin app, Market miMarket) {
        super(app);
        this.market = miMarket;

    }

    @Override
    public void rutas() {


        app.routes(() -> {

            path("/administrar", () -> {

                before(ctx -> {

                    if ((Usuario) ctx.sessionAttribute("user") == null ) {
                        ctx.redirect("/autenticacionusuario/autenticar");
                    }
                });


                path("/crud", () -> {

                    get(ctx -> {
                        model = new HashMap<String, Object>();
                        model.put("misProductos", market.getMisProductos());
                        model.put("numProductos", ((CarroCompra) ctx.sessionAttribute("carrito")).calcularCantidad());
                        model.put("sesion", ctx.sessionAttribute("user"));
                        ctx.render("/design/crud.html", model);
                    });

                    path("/eliminar", () -> {

                        post(ctx -> {
                            String idAux = ctx.formParam("id");
                            market.eliminarProducto(Integer.parseInt(idAux));
                            ctx.redirect("/administrar/crud");
                        });

                    });
                });


                path("/creareditar", () -> {


                    path("/crear", () -> {

                        get(ctx -> {
                            List<Fotos> fotosList = new ArrayList<>();
                            List<Comentarios> comentariosList = new ArrayList<>();
                            provisional = new Producto(0, "", BigDecimal.valueOf(0), fotosList, "", comentariosList);
                            model = new HashMap<String, Object>();
                            model.put("producto", provisional);
                            model.put("numProductos", ((CarroCompra) ctx.sessionAttribute("carrito")).calcularCantidad());
                            model.put("sesion",ctx.sessionAttribute("user"));
                            model.put("fotos", provisional.getFotosList());
                            ctx.render("/design/crear.html", model);
                        });

                    });

                    path("/editar", () -> {

                        post(ctx -> {
                            String idAux1 = ctx.formParam("id");
                            provisional = ServicioProducto.getInstancia().getProducto(Integer.parseInt(idAux1));
                            model = new HashMap<String, Object>();
                            model.put("producto", provisional);
                            model.put("numProductos", ((CarroCompra) ctx.sessionAttribute("carrito")).calcularCantidad());
                            model.put("sesion", ctx.sessionAttribute("user"));
                            model.put("fotos", provisional.getFotosList());
                            ctx.render("/design/editar.html", model);
                        });

                        get(ctx -> {

                            model = new HashMap<String, Object>();
                            model.put("producto", provisional);
                            model.put("numProductos", ((CarroCompra) ctx.sessionAttribute("carrito")).calcularCantidad());
                            model.put("sesion", ctx.sessionAttribute("user"));
                            model.put("fotos", provisional.getFotosList());
                            ctx.render("/design/editar.html", model);

                        });


                    });

                    path("/acepto", () -> {

                        post(ctx -> {
                            String idAux2 = ctx.formParam("id");
                            String nombre = ctx.formParam("nombre");
                            String descripcion = ctx.formParam("descripcion");
                            BigDecimal precio = BigDecimal.valueOf(Double.valueOf(ctx.formParam("precio")));
                            ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                                try {
                                    byte[] bytes = uploadedFile.getContent().readAllBytes();
                                    String encodedString = Base64.getEncoder().encodeToString(bytes);
                                    Fotos foto = new Fotos(uploadedFile.getFilename(), uploadedFile.getContentType(), encodedString);
                                    if (!uploadedFile.getContentType().equals("application/octet-stream")) {
                                        provisional.getFotosList().add(foto);
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            });
                            System.out.println(descripcion);
                            Producto nuevoProductito = new Producto(Integer.parseInt(idAux2), nombre, precio, provisional.getFotosList(), descripcion, provisional.getComentariosList());
                            market.crearEditarProducto(nuevoProductito);
                            provisional = new Producto(0, "", BigDecimal.valueOf(0), new ArrayList<>(), "", new ArrayList<>());
                            ctx.redirect("/administrar/crud");

                        });


                    });


                });


            });
        });
    }
}
