package pantallas;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import componentes.JImageStrech;
import componentes.JLabelReloj;

public class Principal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	//Parametros de la pantalla principal
	private static final int ANCHO = 1366;
	private static final int ALTO = 768;
	
	//Colores
	private static final Color COLOR_MENU = Color.CYAN;
	private static final Color COLOR_NORTE = Color.WHITE;
	private static final Color COLOR_SUR = Color.WHITE;
	private static final Color COLOR_CENTRO = Color.WHITE;
	
	private static final Color COLOR_INICIO = Color.WHITE;
	private static final Color COLOR_USUARIOS = Color.WHITE;
	private static final Color COLOR_LIBROS = Color.WHITE;
	private static final Color COLOR_PRESTYDEV = Color.WHITE;
	
	
	//Botones para cambiar entre Paneles de Usuarios, Lirbos y Prestamos y Devoluciones
	JButton jbUsuarios, jbLibros, jbPrestYDev;
	//Botones para Cerrar sesion y Salir
	JButton jbCerrarSesion, jbSalir;
	//Labels para los botones de los Paneles de Usuarios, Libros y Prestamos y Devoluciones
	JLabel jlUsuarios, jlLibros, jlPrestYDev;
	//JImageStrech para logo del Sistema Bibliotecario
	JImageStrech jisLogoSist;
	//JLabelReloj para el contenedor Sur
	JLabelReloj reloj;
	//MenuBar
	JMenuBar jmbMenu;
	//SubMenus del MenuBar
	JMenu jmOpciones;
	//Menu Items del SubMenu Opciones
	JMenuItem jmiUsuarios, jmiLibros, jmiPrestYDev;
	//Paneles de los contenedores Principal
	JPanel jpNorte, jpSur, jpCentro;
	//Panel para los Botones
	JPanel jpLogo, jpBotonesULPD, jpCS;
	//Label de Bienvenido
	JLabel jlBienvenido;
	//Imagen de Bienvenido
	JImageStrech jisBienvenido;
	
	
	//CardLayout Para las Pantallas de Usuario, Libros y Prestamos y Devoluciones
	CardLayout cardLayoutULPD;
	
	//COMPONENTES PARA EL PANEL DE USUARIOS
	//----
	//Panel de usuarios
	JPanel jpInicio, jpUsuarios, jpLibros, jpPrestYDev;
	//Labels de los paneles ULPD
	JLabel jlGestionUsuarios, jlGestionLibros, jlGestionPrestYDev;
	//Botones
	JButton jbRegistrarUsuario, jbModificarUsuario, jbEliminarUsuario, jbBuscarUsuario, jbLimpiarUsuario;
	//JTextField
	JTextField jtfBuscarUsuarios;
	//Tabla de Usuarios
	JTable jtUsuarios;
	//Scroll para la tabla de Usuarios
	JScrollPane jspUsuarios;
	
	
	//DATOS DE PRUEBA
	Object[][] datos = {
	        { "1", "Ulises", "Papachoris", "ulises@correo.com" },
	        { "2", "Ana", "García", "ana@correo.com" },
	        { "3", "Juan", "Pérez", "juan@correo.com" },
	        { "4", "Maria", "Lopez", "maria@correo.com" }
	    };
	String[] columnas = { "ID", "Nombre", "Apellido", "Email" };
	
	public Principal(String title) {
		super(title);
		setSize(ANCHO,ALTO);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Menu
		menuBar();
		
		//Contenedores Principales
		contenedorNorte();
		contenedorSur();
		contenedorCentro();
	}
	
	
	//Metodo para el Menu
		public void menuBar() {
			jmbMenu = new JMenuBar();
			jmbMenu.setBackground(COLOR_MENU);
			
			jmOpciones = new JMenu("Opciones");
			
			jmiUsuarios = new JMenuItem("Usuarios");
			jmiLibros = new JMenuItem("Libros");
			jmiPrestYDev = new JMenuItem("Prestamos y Devoluciones");
			
			jmOpciones.add(jmiUsuarios);
			jmOpciones.add(jmiLibros);
			jmOpciones.add(jmiPrestYDev);
			
			jmiUsuarios.addActionListener(this);
			jmiLibros.addActionListener(this);
			jmiPrestYDev.addActionListener(this);
			
			jmbMenu.add(jmOpciones);
			setJMenuBar(jmbMenu);
		}
	
	
	//Metodos para la creacion de contenedores en la Pantalla Principal
	public void contenedorNorte() {
		jpNorte = new JPanel();
		jpNorte.setBackground(COLOR_NORTE);
		jpNorte.setLayout(new BorderLayout());
		componentesNorte();
		add(jpNorte, BorderLayout.NORTH);
	}
	public void contenedorSur() {
		jpSur = new JPanel();
		jpSur.setBackground(COLOR_SUR);
		jpSur.setLayout(new BorderLayout());
		componentesSur();
		add(jpSur, BorderLayout.SOUTH);
	}
	public void contenedorCentro() {
		cardLayoutULPD = new CardLayout();
		
		jpCentro = new JPanel(cardLayoutULPD);
		jpCentro.setBackground(COLOR_CENTRO);
		
		jpInicio = new JPanel();
		jpInicio.setLayout(new GridBagLayout());
		jpInicio.setBackground(COLOR_INICIO);
		componentesCentroInicio();
		
		jpUsuarios = new JPanel();
		jpUsuarios.setLayout(new GridBagLayout());
		jpUsuarios.setBackground(COLOR_USUARIOS);
		componentesCentroUsuarios();
		
		jpLibros = new JPanel();
		jpLibros.setLayout(new GridBagLayout());
		jpLibros.setBackground(COLOR_LIBROS);
		componentesCentroLibros();
		
		jpPrestYDev = new JPanel();
		jpPrestYDev.setLayout(new GridBagLayout());
		jpPrestYDev.setBackground(COLOR_PRESTYDEV);
		componentesCentroPrestYDev();
		
		
		jpCentro.add(jpInicio, "INICIO");
		jpCentro.add(jpUsuarios, "USUARIOS");
		jpCentro.add(jpLibros, "LIBROS");
		jpCentro.add(jpPrestYDev, "PRESTYDEV");
		
		
		add(jpCentro, BorderLayout.CENTER);
	}
	
	//Metodos para los componentes de cada contenedor
	public void componentesNorte() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		
		//Crear sub paneles para Botones ULPD y CS
		jpBotonesULPD = new JPanel();
		jpBotonesULPD.setLayout(new GridBagLayout());
		jpBotonesULPD.setBackground(COLOR_NORTE);
		jpCS = new JPanel();
		jpCS.setLayout(new GridBagLayout());
		jpCS.setBackground(COLOR_NORTE);
		jpLogo = new JPanel();
		jpLogo.setLayout(new GridBagLayout());
		jpLogo.setBackground(COLOR_NORTE);
		
		
		//Logo del Sistema Bibliotecario
		gbc.gridx = 0;
		gbc.gridy = 0;
		jisLogoSist = new JImageStrech(new ImageIcon(getClass().getResource("/imagenes/Logo.png")));
		jisLogoSist.setPreferredSize(new java.awt.Dimension(90, 90));
		jpLogo.add(jisLogoSist, gbc);
		
		//Vistas para Usuarios
		gbc.gridx = 1;
		gbc.gridy = 0;
		jbUsuarios = new JButton(new ImageIcon(getClass().getResource("/imagenes/usuarios.png")));
		jbUsuarios.setPreferredSize(new java.awt.Dimension(60, 60));
		jbUsuarios.addActionListener(this);
		jpBotonesULPD.add(jbUsuarios, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		jlUsuarios = new JLabel("Usuarios ");
		jpBotonesULPD.add(jlUsuarios, gbc);
		
		//Vistas para Libros
		gbc.gridx = 2;
		gbc.gridy = 0;
		jbLibros = new JButton(new ImageIcon(getClass().getResource("/imagenes/libros.png")));
		jbLibros.setPreferredSize(new java.awt.Dimension(60, 60));
		jbLibros.addActionListener(this);
		jpBotonesULPD.add(jbLibros, gbc);
		gbc.gridx = 2;
		gbc.gridy = 1;
		jlLibros = new JLabel("Libros");
		jpBotonesULPD.add(jlLibros, gbc);
		
		//Vistas para Prestamos y Devoluciones
		gbc.gridx = 3;
		gbc.gridy = 0;
		jbPrestYDev = new JButton(new ImageIcon(getClass().getResource("/imagenes/prestydev.png")));
		jbPrestYDev.setPreferredSize(new java.awt.Dimension(60, 60));
		jbPrestYDev.addActionListener(this);
		jpBotonesULPD.add(jbPrestYDev, gbc);
		gbc.gridx = 3;
		gbc.gridy = 1;
		jlPrestYDev = new JLabel("Prestamos");
		jpBotonesULPD.add(jlPrestYDev, gbc);
		
		//Boton para CerrarSesion
		gbc.gridx = 4;
		gbc.gridy = 0;
		jbCerrarSesion = new JButton(new ImageIcon(getClass().getResource("/imagenes/cerrarsesion.png")));
		jbCerrarSesion.setPreferredSize(new java.awt.Dimension(40, 40));
		jbCerrarSesion.addActionListener(this);
		jpCS.add(jbCerrarSesion, gbc);
		
		//Boton para Salir
		gbc.gridx = 5;
		gbc.gridy = 0;
		jbSalir = new JButton(new ImageIcon(getClass().getResource("/imagenes/salir.png")));
		jbSalir.setPreferredSize(new java.awt.Dimension(40, 40));
		jbSalir.addActionListener(this);
		jpCS.add(jbSalir, gbc);
		
		jpNorte.add(jpLogo, BorderLayout.WEST);
		jpNorte.add(jpBotonesULPD, BorderLayout.CENTER);
		jpNorte.add(jpCS, BorderLayout.EAST);
	}
	public void componentesSur() {
		reloj = new JLabelReloj(3);
		jpSur.add(reloj, BorderLayout.EAST);
	}
	public void componentesCentroInicio() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		jlBienvenido = new JLabel("Bienvenido al Sistema Bibliotecario");
		jlBienvenido.setFont(new Font("Arial", Font.PLAIN, 36));
		jpInicio.add(jlBienvenido, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		jisBienvenido = new JImageStrech(new ImageIcon(getClass().getResource("/imagenes/biblioteca.png")));
		jisBienvenido.setPreferredSize(new java.awt.Dimension(740, 414));
		jpInicio.add(jisBienvenido, gbc);	
	}
	public void componentesCentroUsuarios() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		jlGestionUsuarios = new JLabel("GESTION DE USUARIOS");
		jlGestionUsuarios.setFont(new Font("Arial", Font.PLAIN, 24));
		jpUsuarios.add(jlGestionUsuarios, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		jbRegistrarUsuario = new JButton(new ImageIcon(getClass().getResource("/imagenes/registrar.png")));
		jbRegistrarUsuario.setPreferredSize(new java.awt.Dimension(40, 40));
		jpUsuarios.add(jbRegistrarUsuario, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 3;
		jbModificarUsuario = new JButton(new ImageIcon(getClass().getResource("/imagenes/modificar.png")));
		jbModificarUsuario.setPreferredSize(new java.awt.Dimension(40, 40));
		jpUsuarios.add(jbModificarUsuario, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 3;
		jbEliminarUsuario = new JButton(new ImageIcon(getClass().getResource("/imagenes/eliminar.png")));
		jbEliminarUsuario.setPreferredSize(new java.awt.Dimension(40, 40));
		jpUsuarios.add(jbEliminarUsuario, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		jtfBuscarUsuarios = new JTextField(35);
		jpUsuarios.add(jtfBuscarUsuarios, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		jbBuscarUsuario = new JButton(new ImageIcon(getClass().getResource("/imagenes/buscarusuario.png")));
		jbBuscarUsuario.setPreferredSize(new java.awt.Dimension(40, 40));
		jpUsuarios.add(jbBuscarUsuario, gbc);
		 
		gbc.gridx = 3;
		gbc.gridy = 2;
		jbLimpiarUsuario = new JButton(new ImageIcon(getClass().getResource("/imagenes/limpiar.png")));
		jbLimpiarUsuario.setPreferredSize(new java.awt.Dimension(40, 40));
		jpUsuarios.add(jbLimpiarUsuario, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		jtUsuarios = new JTable(datos, columnas);
		jspUsuarios = new JScrollPane(jtUsuarios);
		jpUsuarios.add(jspUsuarios, gbc);
	}
	public void componentesCentroLibros() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		jlGestionLibros = new JLabel("GESTION DE LIBROS");
		jlGestionLibros.setFont(new Font("Arial", Font.PLAIN, 24));
		jpLibros.add(jlGestionLibros, gbc);
	}
	public void componentesCentroPrestYDev() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		jlGestionPrestYDev = new JLabel("GESTION DE PRESTAMOS Y DEVOLUCIONES");
		jpPrestYDev.add(jlGestionPrestYDev, gbc);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jbUsuarios) {
			cardLayoutULPD.show(jpCentro, "USUARIOS");
		}
		else if(e.getSource() == jbLibros) {
			cardLayoutULPD.show(jpCentro, "LIBROS");
		}
		else if(e.getSource() == jbPrestYDev) {
			cardLayoutULPD.show(jpCentro, "PRESTYDEV");
		}
	}
}
