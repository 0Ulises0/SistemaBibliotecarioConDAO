package objetos;

public class Usuario {
	private int IDUsuario;
	private String Nombre;
	private String Apellido;
	private char Genero;
	private int Telefono;
	private String Email;
	private String FechaNacimiento;
	
	//Constructor vacio
	public Usuario() {
		this(0,"","",'-',0,"","");
	}

	//Constructor con parametros
	public Usuario(int iDUsuario, String nombre, String apellido, char genero, int telefono, String email,
			String fechaNacimiento) {
		super();
		IDUsuario = iDUsuario;
		Nombre = nombre;
		Apellido = apellido;
		Genero = genero;
		Telefono = telefono;
		Email = email;
		FechaNacimiento = fechaNacimiento;
	}

	public int getIDUsuario() {
		return IDUsuario;
	}

	public void setIDUsuario(int iDUsuario) {
		IDUsuario = iDUsuario;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		Apellido = apellido;
	}

	public char getGenero() {
		return Genero;
	}

	public void setGenero(char genero) {
		Genero = genero;
	}

	public int getTelefono() {
		return Telefono;
	}

	public void setTelefono(int telefono) {
		Telefono = telefono;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getFechaNacimiento() {
		return FechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		FechaNacimiento = fechaNacimiento;
	}
}
