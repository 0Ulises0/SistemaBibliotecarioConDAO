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
	        ps.setString(3, String.valueOf(usuario.getGenero()));
	        ps.setString(4, usuario.getTelefono());
	        ps.setString(5, usuario.getEmail());
	        ps.setString(6, usuario.getFechaNacimiento());

	        ps.executeUpdate();
	        
	    } 
	}

	public static void ModificarUsuario(Usuario usuario, int idUsuario) throws SQLException {
	    
	    String sql = "UPDATE Usuario SET Nombre = ?, Apellido = ?, Genero = ?, "
	               + "Telefono = ?, Email = ?, FechaNacimiento = ? "
	               + "WHERE IDUsuario = ?";
	    
	    try (Connection conn = GetConexion();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        // Establecer los parámetros para SET
	        ps.setString(1, usuario.getNombre());
	        ps.setString(2, usuario.getApellido());
	        ps.setString(3, String.valueOf(usuario.getGenero()));
	        ps.setString(4, usuario.getTelefono());
	        ps.setString(5, usuario.getEmail());
	        ps.setString(6, usuario.getFechaNacimiento());
	        ps.setInt(7, idUsuario);
	        
	        ps.executeUpdate();
	    }
	}

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

	    String select = "SELECT IDUsuario, Nombre, Apellido, Genero, Telefono, Email, FechaNacimiento "
	                  + "FROM Usuario "
	                  + "WHERE Nombre LIKE ?";
	    
	    ArrayList<Object[]> listado = new ArrayList<>();

	    try {
	        conn = GetConexion();
	        ps = conn.prepareStatement(select);

	        ps.setString(1, nombre + "%");

	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Object[] fila = new Object[7];
	            
	            fila[0] = rs.getInt("IDUsuario");
	            fila[1] = rs.getString("Nombre");
	            fila[2] = rs.getString("Apellido");
	            fila[3] = rs.getString("Genero");
	            fila[4] = rs.getString("Telefono");
	            fila[5] = rs.getString("Email");
	            fila[6] = rs.getString("FechaNacimiento");

	            listado.add(fila);
	        }

	    } catch (Exception e) {
	        throw new Exception("Error al buscar usuarios: " + e.getMessage());
	    } finally {
	        if (rs != null) rs.close();
	        if (ps != null) ps.close();
	        if (conn != null) conn.close();
	    }
	  
	    return listado.toArray(new Object[listado.size()][]);
	}
	
}