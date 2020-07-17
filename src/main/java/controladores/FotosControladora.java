package controladores;

import entidades.Fotos;
import entidades.Producto;
import io.javalin.Javalin;
import io.javalin.http.Context;
import logical.CarroCompra;
import logical.Market;
import services.ServicioFotos;
import services.ServicioProducto;
import util.ControladorBase;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class FotosControladora extends ControladorBase {

    Context ctx;
    private Market market;
    private Producto provisional;
    private static CarroCompra carrito;
    private Map<String, Object> model;

    private ServicioFotos fotoServices = ServicioFotos.getInstancia();

    public FotosControladora(Javalin app, Market miMarket) {
        super(app);
        this.market = miMarket;


    }


    @Override
    public void rutas() {
        app.routes(() -> {

            path("/fotos", () -> {

                get("/listar", ctx -> {
                    List<Fotos> fotos = fotoServices.buscarTodos();

                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Ejemplo de funcionalidad Thymeleaf");
                    modelo.put("fotos", fotos);


                    ctx.render("/design/pruebacrud.html", modelo);
                });

                post("/procesarFoto", ctx -> {
                    ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                        try {
                            byte[] bytes = uploadedFile.getContent().readAllBytes();
                            String encodedString = Base64.getEncoder().encodeToString(bytes);
                            Fotos foto = new Fotos(uploadedFile.getFilename(), uploadedFile.getContentType(), encodedString);
                            AdministrarControladora.provisional.getFotosList().add(foto);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ctx.redirect("/administrar/creareditar");
                    });
                });

                get("/visualizar/:id/:nombre", ctx -> {
                    try {

                        Fotos foto = fotoServices.buscar(Integer.parseInt(ctx.pathParam("id")), ctx.pathParam("nombre"));
                        if (foto == null) {
                            ctx.redirect("/fotos/listar");
                            return;
                        }
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("foto", foto);
                        ctx.render("/design/visualizar.html", modelo);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                });

                get("/eliminar/:id/:nombre", ctx -> {
                    try {
                        Fotos foto = fotoServices.buscar(Integer.parseInt(ctx.pathParam("id")), ctx.pathParam("nombre"));
                        if (foto != null) {
                            fotoServices.eliminar(Integer.parseInt(ctx.pathParam("id")), ctx.pathParam("nombre"));
                        }

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    AdministrarControladora.provisional = ServicioProducto.getInstancia().getProducto(Integer.parseInt(ctx.pathParam("id")));
                    ctx.redirect("/administrar/creareditar/editar");
                });

            });
        });
    }
}
