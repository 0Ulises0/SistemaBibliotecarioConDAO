package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConexion.ConexionDB;
import objetos.Usuario;

public class DAOUsuarios extends ConexionDB{
	
	
	//Consultar todos los datos
	public static Object[][] ConsultarTodo() throws Exception {
	        
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        
	        String select = "SELECT IDUsuario, Nombre, Apellido, Genero, Telefono, Email, FechaNacimiento FROM Usuario";
	        
	        ArrayList<Object[]> listado = new ArrayList<>();
	
	        try {
	            conn = GetConexion();
	            ps = conn.prepareStatement(select);
	            rs = ps.executeQuery();
	
	            ResultSetMetaData metaData = rs.getMetaData();
	            int numeroColumnas = metaData.getColumnCount();
	
	            while (rs.next()) {
	                Object[] fila = new Object[numeroColumnas];
	
	                fila[0] = rs.getInt("IDUsuario");
	                fila[1] = rs.getString("Nombre");
	                fila[2] = rs.getString("Apellido");
	                fila[3] = rs.getString("Genero");
	                
	                // --- MODIFICADO ---
	                // Ahora leemos el teléfono como String, no como int
	                fila[4] = rs.getString("Telefono"); 
	                
	                fila[5] = rs.getString("Email");
	                fila[6] = rs.getString("FechaNacimiento"); 
	
	                listado.add(fila);
	            }
	
	        } catch (Exception e) {
	            throw new Exception("Error al consultar usuarios: " + e.getMessage());
	        } finally {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        }
	        
	        return listado.toArray(new Object[listado.size()][]);
	}
	
	public static void AgregarUsuario(Usuario usuario) throws SQLException {
	    
	    String sql = "INSERT INTO Usuario (Nombre, Apellido, Genero, Telefono, Email, FechaNacimiento) "
	               + "VALUES (?,?,?,?,?,?)";

	    try (Connection conn = GetConexion();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, usuario.getNombre());
	        ps.setString(2, usuario.getApellido());
	        
	        // Convertimos el char 'M' o 'F' a un String "M" o "F"
	        ps.setString(3, String.valueOf(usuario.getGenero()));
	        
	        // --- MODIFICADO ---
	        // Usamos setString porque la clase Usuario ahora usa String
	        ps.setString(4, usuario.getTelefono());
	        
	        ps.setString(5, usuario.getEmail());
	        ps.setString(6, usuario.getFechaNacimiento()); // "yyyy-MM-dd"

	        ps.executeUpdate();
	        
	    } // conn y ps se cierran solos aquí
	}
	
	
	// --- NUEVO MÉTODO ---
	/**
	 * Actualiza un usuario existente en la base de datos, buscándolo por su ID.
	 *
	 * @param usuario El objeto Usuario con la *nueva* información.
	 * @param idUsuario El ID del registro que se va a modificar.
	 * @throws SQLException
	 */
	public static void ModificarUsuario(Usuario usuario, int idUsuario) throws SQLException {
	    
	    String sql = "UPDATE Usuario SET Nombre = ?, Apellido = ?, Genero = ?, "
	               + "Telefono = ?, Email = ?, FechaNacimiento = ? "
	               + "WHERE IDUsuario = ?"; // Asumiendo que tu llave primaria se llama 'IDUsuario'
	    
	    try (Connection conn = GetConexion();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        // Establecer los parámetros para SET
	        ps.setString(1, usuario.getNombre());
	        ps.setString(2, usuario.getApellido());
	        ps.setString(3, String.valueOf(usuario.getGenero())); // Convertir char a String
	        ps.setString(4, usuario.getTelefono()); // <-- Corregido a String
	        ps.setString(5, usuario.getEmail());
	        ps.setString(6, usuario.getFechaNacimiento());
	        
	        // Establecer el parámetro para WHERE
	        ps.setInt(7, idUsuario);
	        
	        ps.executeUpdate();
	    }
	}

	
	// --- NUEVO MÉTODO ---
	/**
	 * Elimina un usuario de la base de datos usando su ID.
	 *
	 * @param idUsuario El ID del usuario a eliminar.
	 * @throws SQLException
	 */
	public static void EliminarUsuarioPorId(int idUsuario) throws SQLException {
	    
	    String sql = "DELETE FROM Usuario WHERE IDUsuario = ?";
	    
	    try (Connection conn = GetConexion();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setInt(1, idUsuario);
	        ps.executeUpdate();
	    }
	}
	
	public static Object[][] BuscarUsuario(String nombre) throws Exception {
	    
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    // 1. Corregimos el SELECT:
	    //    - Especificamos las columnas (mejor que SELECT *)
	    //    - Añadimos el filtro de 'Activo'
	    String select = "SELECT IDUsuario, Nombre, Apellido, Genero, Telefono, Email, FechaNacimiento "
	                  + "FROM Usuario "
	                  + "WHERE Nombre LIKE ?";
	    
	    // 2. Usamos un ArrayList para guardar los resultados
	    ArrayList<Object[]> listado = new ArrayList<>();

	    try {
	        conn = GetConexion();
	        ps = conn.prepareStatement(select);
	        
	        // 3. Corregimos el parámetro del LIKE
	        ps.setString(1, nombre + "%");
	        
	        // 4. Corregimos el 'void': Capturamos y procesamos los resultados
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            // Creamos una fila con 7 columnas (igual que en ConsultarTodo)
	            Object[] fila = new Object[7];
	            
	            fila[0] = rs.getInt("IDUsuario");
	            fila[1] = rs.getString("Nombre");
	            fila[2] = rs.getString("Apellido");
	            fila[3] = rs.getString("Genero");
	            fila[4] = rs.getString("Telefono");
	            fila[5] = rs.getString("Email");
	            fila[6] = rs.getString("FechaNacimiento");

	            // Añadimos la fila a la lista
	            listado.add(fila);
	        }

	    } catch (Exception e) {
	        // Relanzamos la excepción
	        throw new Exception("Error al buscar usuarios: " + e.getMessage());
	    } finally {
	        // Cerramos todos los recursos
	        if (rs != null) rs.close();
	        if (ps != null) ps.close();
	        if (conn != null) conn.close();
	    }
	    
	    // 5. Corregimos el 'void': Devolvemos la matriz de datos para la JTable
	    return listado.toArray(new Object[listado.size()][]);
	}
	
}