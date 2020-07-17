package services;

import javassist.tools.web.Webserver;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

//import org.h2.tools.Server;

public class ServicioBD {

    private static ServicioBD servicioBD;
    private static final String URL = "jdbc:h2:tcp://localhost/~/Universal";
    private static Server tcpServer;
    private static Server bd;


    public static ServicioBD getInstance() throws SQLException {
        if (servicioBD == null) {
            servicioBD = new ServicioBD();
        }
        return servicioBD;

    }

    private ServicioBD() throws SQLException {
        startBD();
        realizarConexion();

    }



    public static Connection getConexion() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException e) {
            Logger.getLogger(ServicioBD.class.getName()).log(Level.SEVERE, null, e);
        }
        return connection;
    }


    public static void startBD() throws SQLException {
        tcpServer = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-tcpDaemon", "-ifNotExists").start();
         bd = Server.createWebServer( "-webPort", "9000","-webDaemon","-webAllowOthers").start();
    }

    public static void stopBD() throws SQLException {
        tcpServer.shutdown();
        bd.shutdown();
    }


    public void realizarConexion() {
        try {
            getConexion().close();
            System.out.println("Conexion realizada satisfactoriamente...");
        } catch (SQLException e) {
            Logger.getLogger(ServicioBD.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}

