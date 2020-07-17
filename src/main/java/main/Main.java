package main;

import controladores.Controladora;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import logical.Market;
import services.ServicioBD;

import java.sql.SQLException;

public class Main {

    private static Market miMarket;
    private static String modoConexion = "";

    public static void main(String args[]) {


        try {
            String mensaje = "Software ORM - JAP";
            System.out.println(mensaje);
            Javalin app = Javalin.create(config -> {
                config.addStaticFiles("/templates");
                config.addStaticFiles("/design");//desde la carpeta de resources
                config.addStaticFiles("/images");
                config.addStaticFiles("/META-INF");

                // config.registerPlugin(new RouteOverviewPlugin("/rutas")); //aplicando plugins de las rutas
            }).start(7000);
            //Abriendo base de datos
            if(args.length >= 1){
                modoConexion = args[0];
                System.out.println("Modo de Operacion: "+modoConexion);
            }

            //Iniciando la base de datos.
            if(modoConexion.isEmpty()) {
                ServicioBD.getInstance();
            }
            miMarket = new Market();



            templatesRegister();
            Controladora miControladora = new Controladora(app, miMarket);
            miControladora.rutas();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                ServicioBD.stopBD();
            } catch (SQLException e) {

                e.printStackTrace();

            }
            throwables.printStackTrace();
        }


    }

    private static void templatesRegister() {
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000; //Retorna el puerto por defecto en caso de no estar en Heroku.
    }

    public static String getModoConexion(){
        return modoConexion;
    }

}
