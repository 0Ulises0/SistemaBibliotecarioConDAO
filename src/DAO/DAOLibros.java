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
	
    /**
     * Consulta libros, opcionalmente filtrando solo los que tienen stock.
     * @param soloDisponibles Si es true, solo devuelve libros con Stock > 0.
     */
	public static Object [][] ConsultarTodo (boolean soloDisponibles) throws Exception{
		 Connection conn = null;
	     PreparedStatement ps = null;
	     ResultSet rs = null;
	     
         // La consulta base
	     String select = "SELECT IDLibro, Titulo, Autor, Categoria, Edicion, Stock FROM Libro";
         
         // Si el checkbox está marcado, añadimos la condición WHERE
         if (soloDisponibles) {
             select += " WHERE Stock > 0";
         }
         
	     ArrayList<Object[]> listado = new ArrayList<>();
	     try {
	            conn = GetConexion();
	            ps = conn.prepareStatement(select); // Prepara la consulta (con o sin WHERE)
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
	            throw new Exception("Error al consultar libros: " + e.getMessage());
	        } finally {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        }
	        
	        return listado.toArray(new Object[listado.size()][]);
	}
    
    /**
     * Busca libros por título, opcionalmente filtrando solo los que tienen stock.
     * @param titulo El texto para buscar con LIKE.
     * @param soloDisponibles Si es true, solo devuelve libros con Stock > 0.
     */
    public static Object[][] BuscarLibro(String titulo, boolean soloDisponibles) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String select = "SELECT IDLibro, Titulo, Autor, Categoria, Edicion, Stock FROM Libro "
                      + "WHERE Titulo LIKE ?";
        
        // Añade el filtro de stock si es necesario
        if (soloDisponibles) {
            select += " AND Stock > 0";
        }
        
        ArrayList<Object[]> listado = new ArrayList<>();

        try {
            conn = GetConexion();
            ps = conn.prepareStatement(select);
            
            ps.setString(1, titulo + "%"); // Parámetro para LIKE
            
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
            throw new Exception("Error al buscar libros: " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
        
        return listado.toArray(new Object[listado.size()][]);
    }
    
    /**
     * Agrega un nuevo libro a la base de datos.
     * Usa la clase objetos.Libro que proporcionaste.
     */
    public static void AgregarLibro(Libro libro) throws SQLException {
        String sql = "INSERT INTO Libro (Titulo, Autor, Categoria, Edicion, Stock) VALUES (?,?,?,?,?)";

        try (Connection conn = GetConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Usa los getters de tu clase Libro
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getCategoria());
            ps.setInt(4, libro.getEdicion());
            ps.setInt(5, libro.getStock());
            
            ps.executeUpdate();
        }
    }
    
    /**
     * Modifica un libro existente basado en su ID.
     * Usa la clase objetos.Libro que proporcionaste.
     */
    public static void ModificarLibro(Libro libro, int idLibro) throws SQLException {
        String sql = "UPDATE Libro SET Titulo = ?, Autor = ?, Categoria = ?, Edicion = ?, Stock = ? "
                   + "WHERE IDLibro = ?";
        
        try (Connection conn = GetConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Usa los getters de tu clase Libro
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getCategoria());
            ps.setInt(4, libro.getEdicion());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, idLibro); // El ID para el WHERE
            
            ps.executeUpdate();
        }
    }
    
    /**
     * Elimina un libro de la base de datos usando su ID.
     */
    public static void EliminarLibroPorId(int idLibro) throws SQLException {
        String sql = "DELETE FROM Libro WHERE IDLibro = ?";
        
        try (Connection conn = GetConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idLibro);
            ps.executeUpdate();
        }
        // ADVERTENCIA: Esto dará error si el libro está en un Préstamo (FK Constraint)
        // Tu JTable debe capturar este error en un try-catch.
    }
}