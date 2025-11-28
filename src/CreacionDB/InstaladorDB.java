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
        
        System.out.println("BASE DE DATOS CREADA");
    }

    private static boolean crearBaseDeDatos() {
        String urlMaster = "jdbc:sqlserver://" + HOST + ";databaseName=master;encrypt=true;trustServerCertificate=true;";
        try (Connection conn = DriverManager.getConnection(urlMaster, USUARIO, PASSWORD);
             Statement stmt = conn.createStatement()) {

            //Se compurba que np exista y se crea la base de datos
            String sql = "IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = '" + DB_NAME + "') " +
                         "BEGIN " +
                         "CREATE DATABASE " + DB_NAME + "; " +
                         "END";
            
            stmt.executeUpdate(sql);
            System.out.println("Base de datos '" + DB_NAME + "' verificada/creada exitosamente.");
            return true;

        } catch (SQLException e) {
            System.err.println("Error crítico al crear la Base de Datos: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static void crearTablas() {
        String urlDB = "jdbc:sqlserver://" + HOST + ";databaseName=" + DB_NAME + ";encrypt=true;trustServerCertificate=true;";

        try (Connection conn = DriverManager.getConnection(urlDB, USUARIO, PASSWORD);
             Statement stmt = conn.createStatement()) {

            System.out.println("Conectado a '" + DB_NAME + "'. Creando tablas...");

            String sqlUsuario = "IF OBJECT_ID('Usuario', 'U') IS NULL " +
                    "CREATE TABLE Usuario (" +
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
            stmt.executeUpdate(sqlUsuario);
            System.out.println("Tabla 'Usuario' creada.");

            String sqlLibro = "IF OBJECT_ID('Libro', 'U') IS NULL " +
                    "CREATE TABLE Libro (" +
                    "IDLibro INT IDENTITY(1,1) PRIMARY KEY, " +
                    "Titulo VARCHAR(100) NOT NULL, " +
                    "Autor VARCHAR(100) NOT NULL, " +
                    "Categoria VARCHAR(50), " +
                    "Edicion INT, " +
                    "Stock INT DEFAULT 0 " +
                    ");";
            stmt.executeUpdate(sqlLibro);
            System.out.println("Tabla 'Libro' creada.");

            String sqlPrestamo = "IF OBJECT_ID('Prestamo', 'U') IS NULL " +
                    "CREATE TABLE Prestamo (" +
                    "IDPrestamo INT IDENTITY(1,1) PRIMARY KEY, " +
                    "IDUsuario INT NOT NULL, " +
                    "IDLibro INT NOT NULL, " +
                    "FechaPrestamo DATETIME DEFAULT GETDATE(), " +
                    "FechaDevolucion DATETIME, " +
                    "Estado VARCHAR(20) DEFAULT 'Pendiente', " +
                    "FOREIGN KEY (IDUsuario) REFERENCES Usuario(IDUsuario), " +
                    "FOREIGN KEY (IDLibro) REFERENCES Libro(IDLibro) " +
                    ");";
            stmt.executeUpdate(sqlPrestamo);
            System.out.println("Tabla 'Prestamo' creada.");

        } catch (SQLException e) {
            System.err.println("Error al crear las tablas: " + e.getMessage());
        }
    }
}