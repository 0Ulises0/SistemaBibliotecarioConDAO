package pantallas;

import java.awt.BorderLayout;
import java.awt.Color;
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

import componentes.JImageStrech;
import componentes.JLabelReloj;

public class Principal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	//Parametros de la pantalla principal
	private static final int ANCHO = 1366;
	private static final int ALTO = 768;
	
	//Colores
	private static final Color COLOR_MENU = Color.GRAY;
	private static final Color COLOR_NORTE = Color.WHITE;
	private static final Color COLOR_SUR = Color.WHITE;
	private static final Color COLOR_CENTRO = Color.WHITE;
	
	
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
	
	//Metodos para la creacion de contenedores
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
		jpCentro = new JPanel();
		jpCentro.setBackground(COLOR_CENTRO);
		jpCentro.setLayout(new GridBagLayout());
		componentesCentro();
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
		jisLogoSist.setPreferredSize(new java.awt.Dimension(60, 60));
		jpLogo.add(jisLogoSist, gbc);
		
		//Vistas para Usuarios
		gbc.gridx = 1;
		gbc.gridy = 0;
		jbUsuarios = new JButton(new ImageIcon(getClass().getResource("/imagenes/usuarios.png")));
		jbUsuarios.setPreferredSize(new java.awt.Dimension(60, 60));
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
		jpBotonesULPD.add(jbPrestYDev, gbc);
		gbc.gridx = 3;
		gbc.gridy = 1;
		jlPrestYDev = new JLabel("Prestamos");
		jpBotonesULPD.add(jlPrestYDev, gbc);
		
		//Boton para CerrarSesion
		gbc.gridx = 4;
		gbc.gridy = 0;
		jbCerrarSesion = new JButton(new ImageIcon(getClass().getResource("/imagenes/cerrarsesion.png")));
		jbCerrarSesion.setPreferredSize(new java.awt.Dimension(60, 60));
		jpCS.add(jbCerrarSesion, gbc);
		
		//Boton para Salir
		gbc.gridx = 5;
		gbc.gridy = 0;
		jbSalir = new JButton(new ImageIcon(getClass().getResource("/imagenes/salir.png")));
		jbSalir.setPreferredSize(new java.awt.Dimension(60, 60));
		jpCS.add(jbSalir, gbc);
		
		jpNorte.add(jpLogo, BorderLayout.WEST);
		jpNorte.add(jpBotonesULPD, BorderLayout.CENTER);
		jpNorte.add(jpCS, BorderLayout.EAST);
	}
	public void componentesSur() {
		reloj = new JLabelReloj(3);
		jpSur.add(reloj, BorderLayout.EAST);
	}
	public void componentesCentro() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		jlBienvenido = new JLabel("Bienvenido al Sistema Bibliotecario");
		jpCentro.add(jlBienvenido, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		jisBienvenido = new JImageStrech(new ImageIcon(getClass().getResource("/imagenes/biblioteca.png")));
		jisBienvenido.setPreferredSize(new java.awt.Dimension(740, 414));
		jpCentro.add(jisBienvenido, gbc);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
