package objetos;

public class Usuario {
	private String Nombre;
	private String Apellido;
	private char Genero;
	private String Telefono;
	private String Email;
	private String FechaNacimiento;

	public Usuario() {
		this("","",'-',"","","");
	}

	public Usuario(String nombre, String apellido, char genero, String telefono, String email,
			String fechaNacimiento) {
		super();
		Nombre = nombre;
		Apellido = apellido;
		Genero = genero;
		Telefono = telefono;
		Email = email;
		FechaNacimiento = fechaNacimiento;
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

	public String getTelefono() {
		return Telefono;
	}

	public void setTelefono(String telefono) {
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
