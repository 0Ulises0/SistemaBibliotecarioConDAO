package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import DBConexion.ConexionDB;

public class DAOPresYDev extends ConexionDB{
	
	public static Object [][] ConsultarTodo () throws Exception{
		 Connection conn = null;
	     PreparedStatement ps = null;
	     ResultSet rs = null;
	     
	     String select = "SELECT Nombre + ' ' +Apellido as Nombre, Titulo, FechaPrestamo, FechaDevolucion, Estado FROM Prestamo\n"
	     		+ "    INNER JOIN Usuario on Prestamo.IDUsuario = Usuario.IDUsuario\n"
	     		+ "    INNER JOIN Libro on Prestamo.IDLibro = Libro.IDLibro"
	     		+ "";
	     ArrayList<Object[]> listado = new ArrayList<>();
	     try {
	            conn = GetConexion();
	            ps = conn.prepareStatement(select);
	            rs = ps.executeQuery();
	            ResultSetMetaData metaData = rs.getMetaData();
	            int numeroColumnas = metaData.getColumnCount(); // Obtener el numero de columnas

	            while (rs.next()) {
	                Object[] fila = new Object[numeroColumnas];

	                fila[0] = rs.getString("Nombre");
	                fila[1] = rs.getString("Titulo");
	                fila[2] = rs.getString("FechaPrestamo");
	                fila[3] = rs.getString("FechaDevolucion");
	                fila[4] = rs.getString("Estado");

	                // Añadimos la fila al listado
	                listado.add(fila);
	            }

	        } catch (Exception e) {
	            // Es buena idea manejar o relanzar la excepción
	            throw new Exception("Error al consultar prestamo: " + e.getMessage());
	        } finally {
	            // Cerramos recursos en el orden inverso
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        }
	        
	        // Convertimos el ArrayList<Object[]> en la matriz Object[][]
	        return listado.toArray(new Object[listado.size()][]);
	}
}
