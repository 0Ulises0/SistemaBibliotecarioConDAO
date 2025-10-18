package ejecutable;

import pantallas.Logeo;
import pantallas.Principal;

public class AppMain {

	public static void main(String[] args) {
		
		//Logeo logeo = new Logeo("Inicio de Sesion");
		//logeo.setVisible(true);
		
		Principal pantalla = new Principal("Sistema Bibliotecario");
		pantalla.setVisible(true);
	}
}
