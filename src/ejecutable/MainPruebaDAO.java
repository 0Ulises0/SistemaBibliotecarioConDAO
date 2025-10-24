package ejecutable;

import DAO.DAOUsuarios;

public class MainPruebaDAO {
	public static void main(String[] args) {
		try {
			Object[][] datos = DAOUsuarios.ConsultarTodo();
			
            for (int i = 0; i < 7; i++) {
            	for(int j = 0; j<4 ; j++) {
            		System.out.println(datos[i][j]);
            	}
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
	}

}
