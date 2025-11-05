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
	 	
	 	String select = "SELECT Prestamo.IDPrestamo, Usuario.Nombre + ' ' + Usuario.Apellido as Nombre, Libro.Titulo, "
	 				  + "Prestamo.FechaPrestamo, Prestamo.FechaDevolucion, Prestamo.Estado "
	 				  + "FROM Prestamo "
	 				  + "INNER JOIN Usuario ON Prestamo.IDUsuario = Usuario.IDUsuario "
	 				  + "INNER JOIN Libro ON Prestamo.IDLibro = Libro.IDLibro";
                     
	 	ArrayList<Object[]> listado = new ArrayList<>();
	 	try (Connection conn = GetConexion();
	 		  PreparedStatement ps = conn.prepareStatement(select);
	 		  ResultSet rs = ps.executeQuery()) {
	 		
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
	 		throw new Exception("E: " + e.getMessage());
	 	}

	 	return listado.toArray(new Object[listado.size()][]);
	}
    

    public static Object [][] BuscarPrestamo (String textoBusqueda) throws Exception{
	 	
	 	String select = "SELECT Prestamo.IDPrestamo, Usuario.Nombre + ' ' + Usuario.Apellido as Nombre, Libro.Titulo, "
                      + "Prestamo.FechaPrestamo, Prestamo.FechaDevolucion, Prestamo.Estado "
                      + "FROM Prestamo "
	 				  + "INNER JOIN Usuario ON Prestamo.IDUsuario = Usuario.IDUsuario "
	 				  + "INNER JOIN Libro ON Prestamo.IDLibro = Libro.IDLibro "
                      + "WHERE Usuario.Nombre LIKE ? OR Libro.Titulo LIKE ?";
            
	 	ArrayList<Object[]> listado = new ArrayList<>();
	 	
	 	try (Connection conn = GetConexion();
	 		  PreparedStatement ps = conn.prepareStatement(select)) {

            ps.setString(1, textoBusqueda + "%");
            ps.setString(2, textoBusqueda + "%");

            
            try(ResultSet rs = ps.executeQuery()){
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
            }

	 		
	 	} catch (Exception e) {
	 		throw new Exception("E: " + e.getMessage());
	 	}
	 	return listado.toArray(new Object[listado.size()][]);
	}
    

    public static void RealizarPrestamo(int idUsuario, int idLibro) throws SQLException, Exception {
        Connection conn = null;
        PreparedStatement psVerificarStock = null;
        PreparedStatement psRestarStock = null;
        PreparedStatement psInsertarPrestamo = null;
        ResultSet rs = null;
        
        //Ver el stock del libro
        String sqlVerificarStock = "SELECT Stock FROM Libro WHERE IDLibro = ?";
        //Restar 1 al stock actual
        String sqlRestarStock = "UPDATE Libro SET Stock = Stock - 1 WHERE IDLibro = ?";
        //Crear un nuevo prestamo
        String sqlInsertarPrestamo = "INSERT INTO Prestamo (IDUsuario, IDLibro, FechaPrestamo, FechaDevolucion, Estado) "
                                   + "VALUES (?, ?, GETDATE(), DATEADD(day, 15, GETDATE()), 'Pendiente')";
        
        try {
            conn = GetConexion();
            
            //AutoCommit es una transaccion (se pone en false para que no haga nada hasta que se le indique)
            conn.setAutoCommit(false); 
            
            psVerificarStock = conn.prepareStatement(sqlVerificarStock);
            psVerificarStock.setInt(1, idLibro);
            rs = psVerificarStock.executeQuery();
            
            //Verificar si devolvio un valor
            if (rs.next()) {
            	//obtener el valor de stock
                int stock = rs.getInt("Stock");
                //Si no hay stock se lanza un error
                if (stock <= 0) {
                    throw new Exception("No hay stock disponible para el libro seleccionado.");
                }
                //Si no devuelve nada es que el id del libro no existe
            } else {
                throw new Exception("No se encontro el libro ( " + idLibro + ").");
            }
            

            //Si se llega aqui es porque el libro existe y tiene stock	
            psRestarStock = conn.prepareStatement(sqlRestarStock);
            psRestarStock.setInt(1, idLibro);
            psRestarStock.executeUpdate();
            
            //Inserat el prestamo
            psInsertarPrestamo = conn.prepareStatement(sqlInsertarPrestamo);
            psInsertarPrestamo.setInt(1, idUsuario);
            psInsertarPrestamo.setInt(2, idLibro);
            psInsertarPrestamo.executeUpdate();
            
            //Se finaliza la transaccion, si se llego aqui es porque no salto ningun error
            conn.commit(); 

        } catch (Exception e) {
            if (conn != null) {
            	//Si algo salio mal se deshace todo lo que se hizo anteriormente (update y el insert)
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
        
        //Encontrar el prestamo y ver su estado
        String sqlVerificar = "SELECT IDLibro, Estado FROM Prestamo WHERE IDPrestamo = ?";
        
        //Consulta para actualizar el estado del prestamo a devuelto
        String sqlActualizarPrestamo = "UPDATE Prestamo SET Estado = 'Devuelto', FechaDevolucion = GETDATE() WHERE IDPrestamo = ?";
        
        //Consulta para sumar el stock del prestamo cuando se devuelve
        String sqlSumarStock = "UPDATE Libro SET Stock = Stock + 1 WHERE IDLibro = ?";
        
        int idLibroDevuelto = 0;

        try {
            conn = GetConexion();
            
            //Inicia la transaccion
            conn.setAutoCommit(false);
            
            //Se verifica el prestamo 
            psVerificarPrestamo = conn.prepareStatement(sqlVerificar);
            psVerificarPrestamo.setInt(1, idPrestamo);
            rs = psVerificarPrestamo.executeQuery();
            
            //Si se obtiene un registro 
            if (rs.next()) {
            	
            	//Guardamos el estado y el id del prestamo
                String estado = rs.getString("Estado");
                idLibroDevuelto = rs.getInt("IDLibro");
                
                //Comprobar si el libro ya esta devuelto
                if (estado.equalsIgnoreCase("Devuelto")) {
                    throw new Exception("Este prestamo ya ha sido devuelto.");
                }
                //si no hay registro el id no existe
            } else {
                throw new Exception("No se encontro el prestamo (" + idPrestamo + ").");
            }
            
            //Actualizar el prestamo
            psActualizarPrestamo = conn.prepareStatement(sqlActualizarPrestamo);
            psActualizarPrestamo.setInt(1, idPrestamo);
            psActualizarPrestamo.executeUpdate();
            
            //Sumar el stock cuando se devuelve el prestamo
            psSumarStock = conn.prepareStatement(sqlSumarStock);
            psSumarStock.setInt(1, idLibroDevuelto);
            psSumarStock.executeUpdate();
            
            //Fin de la transaccion
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