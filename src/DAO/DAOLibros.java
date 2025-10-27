package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConexion.ConexionDB;
// ¡Importa la clase Libro que acabas de mostrar!
import objetos.Libro; 

public class DAOLibros extends ConexionDB {
	
	public static void AgregarLibro(Libro libro) throws SQLException {
        String select = "INSERT INTO Libro (Titulo, Autor, Categoria, Edicion, Stock) VALUES (?,?,?,?,?)";

        try (Connection conn = GetConexion();
             PreparedStatement ps = conn.prepareStatement(select)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getCategoria());
            ps.setInt(4, libro.getEdicion());
            ps.setInt(5, libro.getStock());
            
            ps.executeUpdate();
        }
    }
	
	public static void ModificarLibro(Libro libro, int idLibro) throws SQLException {
        String select = "UPDATE Libro SET Titulo = ?, Autor = ?, Categoria = ?, Edicion = ?, Stock = ? "
                   + "WHERE IDLibro = ?";
        
        try (Connection conn = GetConexion();
             PreparedStatement ps = conn.prepareStatement(select)) {
            
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getCategoria());
            ps.setInt(4, libro.getEdicion());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, idLibro); 
            
            ps.executeUpdate();
        }
    }
    

    public static void EliminarLibroPorId(int idLibro) throws SQLException {
        String select = "DELETE FROM Libro WHERE IDLibro = ?";
        
        try (Connection conn = GetConexion();
             PreparedStatement ps = conn.prepareStatement(select)) {
            
            ps.setInt(1, idLibro);
            ps.executeUpdate();
        }
    }
	
	public static Object [][] ConsultarTodo (boolean soloDisponibles) throws Exception{
		 Connection conn = null;
	     PreparedStatement ps = null;
	     ResultSet rs = null;

	     String select = "SELECT IDLibro, Titulo, Autor, Categoria, Edicion, Stock FROM Libro";
         
         if (soloDisponibles) {
             select += " WHERE Stock > 0"; // si esta marcado el checkbox se añade el where
         }
         
	     ArrayList<Object[]> listado = new ArrayList<>();
	     try {
	            conn = GetConexion();
	            ps = conn.prepareStatement(select);
	            rs = ps.executeQuery();
	            ResultSetMetaData metaData = rs.getMetaData(); //obtiene los datos de la tabla
	            int numeroColumnas = metaData.getColumnCount(); //obtiene el numero de columnas que tiene

	            while (rs.next()) {
	                Object[] fila = new Object[numeroColumnas];

	                fila[0] = rs.getInt("IDLibro");
	                fila[1] = rs.getString("Titulo");
	                fila[2] = rs.getString("Autor");
	                fila[3] = rs.getString("Categoria");
	                fila[4] = rs.getInt("Edicion");
	                fila[5] = rs.getInt("Stock");

	                listado.add(fila);
	            }

	        } catch (Exception e) {
	            throw new Exception("E: " + e.getMessage());
	        } finally {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        }
	        
	        return listado.toArray(new Object[listado.size()][]);
	}
    

    public static Object[][] BuscarLibro(String titulo, boolean soloDisponibles) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String select = "SELECT IDLibro, Titulo, Autor, Categoria, Edicion, Stock FROM Libro "
                      + "WHERE Titulo LIKE ?";
        
        
        if (soloDisponibles) {
            select += " AND Stock > 0"; // si esta en solo diponible se añade el filtro
        }
        
        ArrayList<Object[]> listado = new ArrayList<>();

        try {
            conn = GetConexion();
            ps = conn.prepareStatement(select);
            
            ps.setString(1, titulo + "%"); // para que java interprete el parametro de like se tiene que concatenar asi
            
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int numeroColumnas = metaData.getColumnCount();

            while (rs.next()) {
                Object[] fila = new Object[numeroColumnas];
                fila[0] = rs.getInt("IDLibro");
                fila[1] = rs.getString("Titulo");
                fila[2] = rs.getString("Autor");
                fila[3] = rs.getString("Categoria");
                fila[4] = rs.getInt("Edicion");
                fila[5] = rs.getInt("Stock");
                listado.add(fila);
            }

        } catch (Exception e) {
            throw new Exception("E: " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
        
        return listado.toArray(new Object[listado.size()][]);
    }
    

    
    

    
}