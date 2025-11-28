package CreacionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class InstaladorDB {

    private static final String USUARIO = "topicos";
    private static final String PASSWORD = "appConexion1234!";
    private static final String HOST = "localhost:1433";
    private static final String DB_NAME = "DBTopicos";

    public static void main(String[] args) {
        System.out.println("INICIANDO INSTALACION DE BASE DE DATOS");
        if (crearBaseDeDatos()) {
            crearTablas();
        }
    }

    private static boolean crearBaseDeDatos() {
        String urlMaster = "jdbc:sqlserver://" + HOST + ";databaseName=master;encrypt=true;trustServerCertificate=true;";
        
        try (Connection conn = DriverManager.getConnection(urlMaster, USUARIO, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String checkSql = "SELECT count(*) FROM sys.databases WHERE name = '" + DB_NAME + "'";
            var rs = stmt.executeQuery(checkSql);
            rs.next();
            int existe = rs.getInt(1);

            if (existe > 0) {
                System.out.println("LA BASE DE DATOS ''" + DB_NAME + "' YA EXISTE. Se omite.");
                return true;
            } else {
                System.out.println("La base de datos no existe.");
                String createSql = "CREATE DATABASE " + DB_NAME;
                stmt.executeUpdate(createSql);
                System.out.println("BASE DE DATOS ''" + DB_NAME + "' CREADA correctamente.");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static void crearTablas() {
        String urlDB = "jdbc:sqlserver://" + HOST + ";databaseName=" + DB_NAME + ";encrypt=true;trustServerCertificate=true;";

        try (Connection conn = DriverManager.getConnection(urlDB, USUARIO, PASSWORD)) {
            
            System.out.println("VERIFICANDO TABLAS");

            String sqlUsuario = "CREATE TABLE Usuario (" +
                    "IDUsuario INT IDENTITY(1,1) PRIMARY KEY, " +
                    "Nombre VARCHAR(50) NOT NULL, " +
                    "Apellido VARCHAR(50) NOT NULL, " +
                    "Genero CHAR(1) NOT NULL, " +
                    "Telefono VARCHAR(15) NOT NULL, " +
                    "Email VARCHAR(100) NOT NULL, " +
                    "FechaNacimiento DATE NOT NULL, " +
                    "CONSTRAINT chk_telefono CHECK (Telefono NOT LIKE '%[^0-9]%'), " +
                    "CONSTRAINT chk_email CHECK (Email LIKE '%@%') " +
                    ");";
            gestionarCreacionTabla(conn, "Usuario", sqlUsuario);

            String sqlLibro = "CREATE TABLE Libro (" +
                    "IDLibro INT IDENTITY(1,1) PRIMARY KEY, " +
                    "Titulo VARCHAR(100) NOT NULL, " +
                    "Autor VARCHAR(100) NOT NULL, " +
                    "Categoria VARCHAR(50), " +
                    "Edicion INT, " +
                    "Stock INT DEFAULT 0 " +
                    ");";
            gestionarCreacionTabla(conn, "Libro", sqlLibro);

            String sqlPrestamo = "CREATE TABLE Prestamo (" +
                    "IDPrestamo INT IDENTITY(1,1) PRIMARY KEY, " +
                    "IDUsuario INT NOT NULL, " +
                    "IDLibro INT NOT NULL, " +
                    "FechaPrestamo DATETIME DEFAULT GETDATE(), " +
                    "FechaDevolucion DATETIME, " +
                    "Estado VARCHAR(20) DEFAULT 'Pendiente', " +
                    "FOREIGN KEY (IDUsuario) REFERENCES Usuario(IDUsuario), " +
                    "FOREIGN KEY (IDLibro) REFERENCES Libro(IDLibro) " +
                    ");";
            gestionarCreacionTabla(conn, "Prestamo", sqlPrestamo);

        } catch (SQLException e) {
            System.err.println("Error " + e.getMessage());
        }
    }

    private static void gestionarCreacionTabla(Connection conn, String nombreTabla, String sqlCreate) throws SQLException {
        boolean existe = false;
        try (var rs = conn.getMetaData().getTables(null, null, nombreTabla, null)) {
            if (rs.next()) {
                existe = true;
            }
        }
        if (existe) {
            System.out.println("La tabla '" + nombreTabla + "' YA EXISTE. (Se omite)");
        } else {
            System.out.print("Creando tabla '" + nombreTabla + "' ");
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sqlCreate);
                System.out.println("CREADA");
            } catch (SQLException e) {
                System.out.println("ERROR");
                throw e;
            }
        }
    }
}