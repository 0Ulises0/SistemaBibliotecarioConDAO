package DBConexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
	@SuppressWarnings("finally")
	public static Connection GetConexion() throws SQLException{
        Connection conn = null;
        String url = "jdbc:sqlserver://localhost:1433;databaseName=DBTopicos;encrypt=true;trustServerCertificate=true;";
        String usuario = "topicos";
        String contrasena = "appConexion1234!";

        try{
            conn = DriverManager.getConnection(url, usuario, contrasena);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            return conn;
        }
    }
}
