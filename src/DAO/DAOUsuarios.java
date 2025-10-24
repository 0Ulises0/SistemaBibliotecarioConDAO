package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import DBConexion.ConexionDB;

public class DAOUsuarios extends ConexionDB{
	
	
	//Consultar todos los datos
public static Object[][] ConsultarTodo() throws Exception {
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // Es mejor práctica listar las columnas explícitamente que usar *
        String select = "SELECT IDUsuario, Nombre, Apellido, Genero, Telefono, Email, FechaNacimiento FROM Usuario";
        
        // Usamos un ArrayList para guardar dinámicamente cada fila (Object[])
        ArrayList<Object[]> listado = new ArrayList<>();

        try {
            conn = GetConexion();
            ps = conn.prepareStatement(select);
            rs = ps.executeQuery();

            // Obtenemos la metadata para saber el número de columnas
            ResultSetMetaData metaData = rs.getMetaData();
            int numeroColumnas = metaData.getColumnCount(); // En este caso, sabemos que son 7

            while (rs.next()) {
                // Creamos un arreglo de Object para representar una fila
                Object[] fila = new Object[numeroColumnas];

                // Llenamos la fila. 
                // Usar getObject es genérico, pero especifico es mejor:
                fila[0] = rs.getInt("IDUsuario");
                fila[1] = rs.getString("Nombre");
                fila[2] = rs.getString("Apellido");
                fila[3] = rs.getString("Genero"); // JTable mostrará "F" o "M"
                fila[4] = rs.getInt("Telefono");
                fila[5] = rs.getString("Email");
                fila[6] = rs.getDate("FechaNacimiento"); // Usar getDate es mejor para fechas

                // Añadimos la fila al listado
                listado.add(fila);
            }

        } catch (Exception e) {
            // Es buena idea manejar o relanzar la excepción
            throw new Exception("Error al consultar usuarios: " + e.getMessage());
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
