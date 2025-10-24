package objetos;

public class Prestamo {
	private int IDUsuario;
	private int IDLibro;
	private String FechaPrestamo;
	private String FechaDevolucion;
	private String Estado;
	
	public Prestamo() {
		this(0,0,"","","");
	}
	
	public Prestamo(int iDUsuario, int iDLibro, String fechaPrestamo, String fechaDevolucion, String estado) {
		super();
		IDUsuario = iDUsuario;
		IDLibro = iDLibro;
		FechaPrestamo = fechaPrestamo;
		FechaDevolucion = fechaDevolucion;
		Estado = estado;
	}

	public int getIDUsuario() {
		return IDUsuario;
	}

	public void setIDUsuario(int iDUsuario) {
		IDUsuario = iDUsuario;
	}

	public int getIDLibro() {
		return IDLibro;
	}

	public void setIDLibro(int iDLibro) {
		IDLibro = iDLibro;
	}

	public String getFechaPrestamo() {
		return FechaPrestamo;
	}

	public void setFechaPrestamo(String fechaPrestamo) {
		FechaPrestamo = fechaPrestamo;
	}

	public String getFechaDevolucion() {
		return FechaDevolucion;
	}

	public void setFechaDevolucion(String fechaDevolucion) {
		FechaDevolucion = fechaDevolucion;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}
}
