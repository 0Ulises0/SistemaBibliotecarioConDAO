package objetos;

public class Libro {
	private String Titulo;
	private String Autor;
	private String Categoria;
	private int Edicion;
	private int Stock;
	
	public Libro() {
		this("","","",0,0);
	}
	
	public Libro(String titulo, String autor, String categoria, int edicion, int stock) {
		super();
		Titulo = titulo;
		Autor = autor;
		Categoria = categoria;
		Edicion = edicion;
		Stock = stock;
	}

	public String getTitulo() {
		return Titulo;
	}


	public void setTitulo(String titulo) {
		Titulo = titulo;
	}


	public String getAutor() {
		return Autor;
	}


	public void setAutor(String autor) {
		Autor = autor;
	}


	public String getCategoria() {
		return Categoria;
	}


	public void setCategoria(String categoria) {
		Categoria = categoria;
	}


	public int getEdicion() {
		return Edicion;
	}


	public void setEdicion(int edicion) {
		Edicion = edicion;
	}


	public int getStock() {
		return Stock;
	}


	public void setStock(int stock) {
		Stock = stock;
	}
}
