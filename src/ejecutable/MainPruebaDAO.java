package ejecutable;

import DAO.DAOUsuarios;

public class MainPruebaDAO {
	public static void main(String[] args) {
		try {
			Object[][] datos = DAOUsuarios.ConsultarTodo();
			
            for (int i = 0; i < 4; i++) {
            	for(int j = 0; j<7 ; j++) {
            		System.out.print(datos[i][j]+" ");
            	}
            	System.out.println();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
	}

}
