package ejecutable;

import pantallas.Logeo;
import pantallas.Principal;
import pantallasUsuarios.ModificarUsuario;
import pantallasUsuarios.RegistrarUsuario;

public class AppMain {

	public static void main(String[] args) {
		
		Logeo logeo = new Logeo("Inicio de Sesion");
		logeo.setVisible(true);
		
		Principal pantalla = new Principal("Sistema Bibliotecario");
		pantalla.setVisible(true);
		
		RegistrarUsuario ru = new RegistrarUsuario("Registrar Usuario");
		ru.setVisible(true);
		
		ModificarUsuario mu = new ModificarUsuario("Modificar Usuario");
		mu.setVisible(true);
	}
}
