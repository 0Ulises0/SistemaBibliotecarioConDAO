package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConexion.ConexionDB;

public class DAOPresYDev extends ConexionDB{
	

	public static Object [][] ConsultarTodo () throws Exception{
		Connection conn = null;
	 	PreparedStatement ps = null;
	 	ResultSet rs = null;
	 	
	 	String select = "SELECT Prestamo.IDPrestamo, Usuario.Nombre + ' ' + Usuario.Apellido as Nombre, Libro.Titulo, "
	 				  + "Prestamo.FechaPrestamo, Prestamo.FechaDevolucion, Prestamo.Estado "
	 				  + "FROM Prestamo "
	 				  + "INNER JOIN Usuario ON Prestamo.IDUsuario = Usuario.IDUsuario "
	 				  + "INNER JOIN Libro ON Prestamo.IDLibro = Libro.IDLibro";
                     
	 	ArrayList<Object[]> listado = new ArrayList<>();
	 	try {
	 		conn = GetConexion();
	 		ps = conn.prepareStatement(select);
	 		rs = ps.executeQuery();
	 		ResultSetMetaData metaData = rs.getMetaData();
	 		int numeroColumnas = metaData.getColumnCount(); 

	 		while (rs.next()) {
	 			Object[] fila = new Object[numeroColumnas];

	 			fila[0] = rs.getInt("IDPrestamo");
	 			fila[1] = rs.getString("Nombre"); 
	 			fila[2] = rs.getString("Titulo"); 
	 			fila[3] = rs.getString("FechaPrestamo");
	 			fila[4] = rs.getString("FechaDevolucion"); 
	 			fila[5] = rs.getString("Estado"); 

	 			listado.add(fila);
	 		}

	 	} catch (Exception e) {
	 		throw new Exception("Error al consultar prestamo: " + e.getMessage());
	 	} finally {
	 		if (rs != null) rs.close();
	 		if (ps != null) ps.close();
	 		if (conn != null) conn.close();
	 	}
	 	
	 	return listado.toArray(new Object[listado.size()][]);
	}
    

    public static Object [][] BuscarPrestamo (String textoBusqueda) throws Exception{
		Connection conn = null;
	 	PreparedStatement ps = null;
	 	ResultSet rs = null;
	 	
	 	String select = "SELECT Prestamo.IDPrestamo, Usuario.Nombre + ' ' + Usuario.Apellido as Nombre, Libro.Titulo, "
                      + "Prestamo.FechaPrestamo, Prestamo.FechaDevolucion, Prestamo.Estado "
                      + "FROM Prestamo "
	 				  + "INNER JOIN Usuario ON Prestamo.IDUsuario = Usuario.IDUsuario "
	 				  + "INNER JOIN Libro ON Prestamo.IDLibro = Libro.IDLibro "
                      + "WHERE Usuario.Nombre LIKE ? OR Libro.Titulo LIKE ?";
            
	 	ArrayList<Object[]> listado = new ArrayList<>();
	 	try {
	 		conn = GetConexion();
	 		ps = conn.prepareStatement(select);
            ps.setString(1, textoBusqueda + "%");
            ps.setString(2, textoBusqueda + "%");

	 		rs = ps.executeQuery();
	 		ResultSetMetaData metaData = rs.getMetaData();
	 		int numeroColumnas = metaData.getColumnCount();

	 		while (rs.next()) {
	 			Object[] fila = new Object[numeroColumnas];
	 			fila[0] = rs.getInt("IDPrestamo");
	 			fila[1] = rs.getString("Nombre");
	 			fila[2] = rs.getString("Titulo");
	 			fila[3] = rs.getString("FechaPrestamo");
	 			fila[4] = rs.getString("FechaDevolucion");
	 			fila[5] = rs.getString("Estado");
	 			listado.add(fila);
	 		}
	 	} catch (Exception e) {
	 		throw new Exception("Error al buscar prestamo: " + e.getMessage());
	 	} finally {
	 		if (rs != null) rs.close();
	 		if (ps != null) ps.close();
	 		if (conn != null) conn.close();
	 	}
	 	return listado.toArray(new Object[listado.size()][]);
	}
    

    public static void RealizarPrestamo(int idUsuario, int idLibro) throws SQLException, Exception {
        Connection conn = null;
        PreparedStatement psVerificarStock = null;
        PreparedStatement psRestarStock = null;
        PreparedStatement psInsertarPrestamo = null;
        ResultSet rs = null;
        
        String sqlVerificarStock = "SELECT Stock FROM Libro WHERE IDLibro = ?";
        String sqlRestarStock = "UPDATE Libro SET Stock = Stock - 1 WHERE IDLibro = ?";
        String sqlInsertarPrestamo = "INSERT INTO Prestamo (IDUsuario, IDLibro, FechaPrestamo, FechaDevolucion, Estado) "
                                   + "VALUES (?, ?, GETDATE(), DATEADD(day, 15, GETDATE()), 'Pendiente')";
        
        try {
            conn = GetConexion();
            conn.setAutoCommit(false); 
            
            psVerificarStock = conn.prepareStatement(sqlVerificarStock);
            psVerificarStock.setInt(1, idLibro);
            rs = psVerificarStock.executeQuery();
            
            if (rs.next()) {
                int stock = rs.getInt("Stock");
                if (stock <= 0) {
                    throw new Exception("No hay stock disponible para el libro seleccionado.");
                }
            } else {
                throw new Exception("No se encontró el libro (ID: " + idLibro + ").");
            }
            

            psRestarStock = conn.prepareStatement(sqlRestarStock);
            psRestarStock.setInt(1, idLibro);
            psRestarStock.executeUpdate();
            

            psInsertarPrestamo = conn.prepareStatement(sqlInsertarPrestamo);
            psInsertarPrestamo.setInt(1, idUsuario);
            psInsertarPrestamo.setInt(2, idLibro);
            psInsertarPrestamo.executeUpdate();
            
            conn.commit(); 

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e; 
        } finally {
            if (rs != null) rs.close();
            if (psVerificarStock != null) psVerificarStock.close();
            if (psRestarStock != null) psRestarStock.close();
            if (psInsertarPrestamo != null) psInsertarPrestamo.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
    

    public static void RealizarDevolucion(int idPrestamo) throws SQLException, Exception {
        Connection conn = null;
        PreparedStatement psVerificarPrestamo = null;
        PreparedStatement psActualizarPrestamo = null;
        PreparedStatement psSumarStock = null;
        ResultSet rs = null;
        
        String sqlVerificar = "SELECT IDLibro, Estado FROM Prestamo WHERE IDPrestamo = ?";
        String sqlActualizarPrestamo = "UPDATE Prestamo SET Estado = 'Devuelto', FechaDevolucion = GETDATE() WHERE IDPrestamo = ?";
        String sqlSumarStock = "UPDATE Libro SET Stock = Stock + 1 WHERE IDLibro = ?";
        
        int idLibroDevuelto = -1;

        try {
            conn = GetConexion();
            conn.setAutoCommit(false);
            

            psVerificarPrestamo = conn.prepareStatement(sqlVerificar);
            psVerificarPrestamo.setInt(1, idPrestamo);
            rs = psVerificarPrestamo.executeQuery();
            
            if (rs.next()) {
                String estado = rs.getString("Estado");
                idLibroDevuelto = rs.getInt("IDLibro");
                
                if (estado.equalsIgnoreCase("Devuelto")) {
                    throw new Exception("Este préstamo ya ha sido devuelto.");
                }
            } else {
                throw new Exception("No se encontró el préstamo (ID: " + idPrestamo + ").");
            }
            

            psActualizarPrestamo = conn.prepareStatement(sqlActualizarPrestamo);
            psActualizarPrestamo.setInt(1, idPrestamo);
            psActualizarPrestamo.executeUpdate();
            

            psSumarStock = conn.prepareStatement(sqlSumarStock);
            psSumarStock.setInt(1, idLibroDevuelto);
            psSumarStock.executeUpdate();

            conn.commit();
            
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e; 
        } finally {
            if (rs != null) rs.close();
            if (psVerificarPrestamo != null) psVerificarPrestamo.close();
            if (psActualizarPrestamo != null) psActualizarPrestamo.close();
            if (psSumarStock != null) psSumarStock.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}