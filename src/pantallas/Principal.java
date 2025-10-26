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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import DAO.DAOLibros;
import DAO.DAOPresYDev;
import DAO.DAOUsuarios;
import componentes.JImageStrech;
import componentes.JLabelReloj;
import pantallasLibros.ModificarLibro;
import pantallasLibros.RegistrarLibro;
import pantallasUsuarios.ModificarUsuario;
import pantallasUsuarios.RegistrarUsuario;

//Imports para las tablas y Pantalla de Modificar
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

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
	private JButton jbUsuarios, jbLibros, jbPrestYDev;
	//Botones para Cerrar sesion y Salir
	private JButton jbCerrarSesion, jbSalir;
	//Labels para los botones de los Paneles de Usuarios, Libros y Prestamos y Devoluciones
	private JLabel jlUsuarios, jlLibros, jlPrestYDev;
	//JImageStrech para logo del Sistema Bibliotecario
	private JImageStrech jisLogoSist;
	//JLabelReloj para el contenedor Sur
	private JLabelReloj reloj;
	//MenuBar
	private JMenuBar jmbMenu;
	//SubMenus del MenuBar
	private JMenu jmOpciones;
	//Menu Items del SubMenu Opciones
	private JMenuItem jmiUsuarios, jmiLibros, jmiPrestYDev;
	//Paneles de los contenedores Principal
	private JPanel jpNorte, jpSur, jpCentro;
	//Panel para los Botones
	private JPanel jpLogo, jpBotonesULPD, jpCS;
	//Label de Bienvenido
	private JLabel jlBienvenido;
	//Imagen de Bienvenido
	private JImageStrech jisBienvenido;
	
	
	//CardLayout Para las Pantallas de Usuario, Libros y Prestamos y Devoluciones
	private CardLayout cardLayoutULPD;
	
	//COMPONENTES PARA EL PANEL DE USUARIOS
	//----
	//Panel de usuarios
	private JPanel jpInicio, jpUsuarios, jpLibros, jpPrestYDev;
	//Labels de los paneles ULPD
	private JLabel jlGestionUsuarios, jlGestionLibros, jlGestionPrestYDev;
	//Botones
	private JButton jbRegistrarUsuario, jbModificarUsuario, jbEliminarUsuario, jbBuscarUsuario, jbLimpiarUsuario;
	//JTextField
	private JTextField jtfBuscarUsuarios;
	//Tabla de Usuarios
	private JTable jtUsuarios;
	//Scroll para la tabla de Usuarios
	private JScrollPane jspUsuarios;
	
	//COMPONENTES PARA EL PANEL DE LIBROS
	//----
	//Botones
	private JButton jbRegistrarLibro, jbModificarLibro, jbEliminarLibro, jbBuscarLibro, jbLimpiarLibro;
	//JTextFiel para buscar libros
	private JTextField jtfBuscarLibros;
	//Tabla para los libros
	private JTable jtLibros;
	//Scroll para la tabla de libros
	private JScrollPane jspLibros;
	//CheckBox para Disponibilidad
	private JCheckBox cbLibrosDisponibles;
	
	
	//COMPONENTES PARA EL PANEL DE PRESTAMOS Y DEVOLUCIONES
	//----
	//Labels de titulo Libros, Usuarios, Prestamos Y Devoluciones
	private JLabel jlLibrosPrestYDev, jlUsuariosPrestYDev, jlPrestYDevPrestYDev;
	//Botones
	private JButton jbPrestar, jbDevolver, jbLimpiarLibrosPrestYDev, jbLimpiarUsuariosPrestYDev, jbLimpiarPrestYDev;
	//JTextField para buscar Libros, Usuarios y Prestamos
	private JTextField jtfBuscarLibrosPrestYDev, jtfBuscarUsuariosPrestYDev, jtfBuscarPrestYDev;
	//Tablas para libros, usuarios y prestamos y devoluciones
	private JTable jtLibrosPrestYDev, jtUsuariosPrestYDev, jtPrestYDev;
	//Scroll Pane para las tablas respectivas
	private JScrollPane jspUsuariosPrestYDev, jspLibrosPrestYDev, jspPrestYDev;
	
	//Pantallas para Registrar y Modificar
	//Usuarios, Libros
	private RegistrarUsuario ruPantalla;
	private RegistrarLibro rlPantalla;
	private ModificarUsuario muPantalla;
	private ModificarLibro mlPantalla;
	
	//Pantalla de logue
	private Logeo logeo;
	
	//COLUMNAS USUARIO
	private String [] columnasUsuario = {"ID", "NOMBRE", "APELLIDO", "GENERO", "TELEFONO", "EMAIL", "FECHA NACIMIENTO"};
	//COLUMNAS LIBRO
	private String [] columnasLibro = {"ID", "TITULO", "AUTOR", "CATEGORIA", "EDICION", "STOCK"};
	//COLUMNAS PRESTAMI
	private String [] columnasPrestamo = {"NOMBRE", "LIBRO", "PRESTAMO", "DEVOLUCION", "ESTADO"};
	
	
	public Principal(String title) throws Exception {
		super(title);
		setSize(ANCHO,ALTO);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
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
	public void contenedorCentro() throws Exception {
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
	public void componentesCentroUsuarios() throws Exception {
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
		jbRegistrarUsuario.addActionListener(this);
		jpUsuarios.add(jbRegistrarUsuario, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 3;
		jbModificarUsuario = new JButton(new ImageIcon(getClass().getResource("/imagenes/modificar.png")));
		jbModificarUsuario.setPreferredSize(new java.awt.Dimension(40, 40));
		jbModificarUsuario.addActionListener(this);
		jpUsuarios.add(jbModificarUsuario, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 3;
		jbEliminarUsuario = new JButton(new ImageIcon(getClass().getResource("/imagenes/eliminar.png")));
		jbEliminarUsuario.setPreferredSize(new java.awt.Dimension(40, 40));
		jbEliminarUsuario.addActionListener(this);
		jpUsuarios.add(jbEliminarUsuario, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		jtfBuscarUsuarios = new JTextField(35);
		jtfBuscarUsuarios.addKeyListener(new java.awt.event.KeyAdapter() {
		    public void keyReleased(java.awt.event.KeyEvent evt) {
		        // Este método se llama CADA VEZ que sueltas una tecla
		        jtfBuscarUsuariosKeyReleased(evt);
		    }
		});
		jpUsuarios.add(jtfBuscarUsuarios, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		jbBuscarUsuario = new JButton(new ImageIcon(getClass().getResource("/imagenes/buscarusuario.png")));
		jbBuscarUsuario.setPreferredSize(new java.awt.Dimension(40, 40));
		jbBuscarUsuario.addActionListener(this);
		jpUsuarios.add(jbBuscarUsuario, gbc);
		 
		gbc.gridx = 3;
		gbc.gridy = 2;
		jbLimpiarUsuario = new JButton(new ImageIcon(getClass().getResource("/imagenes/limpiar.png")));
		jbLimpiarUsuario.setPreferredSize(new java.awt.Dimension(40, 40));
		jbLimpiarUsuario.addActionListener(this);
		jpUsuarios.add(jbLimpiarUsuario, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		jtUsuarios = new JTable(DAOUsuarios.ConsultarTodo(), columnasUsuario);
		jtUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Hace que solo se pueda seleccionar una fila a la vez
		jspUsuarios = new JScrollPane(jtUsuarios);
		jpUsuarios.add(jspUsuarios, gbc);
	}
	public void componentesCentroLibros() throws Exception {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		jlGestionLibros = new JLabel("GESTION DE LIBROS");
		jlGestionLibros.setFont(new Font("Arial", Font.PLAIN, 24));
		jpLibros.add(jlGestionLibros, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		jbRegistrarLibro = new JButton(new ImageIcon(getClass().getResource("/imagenes/registrar.png")));
		jbRegistrarLibro.setPreferredSize(new java.awt.Dimension(40, 40));
		jbRegistrarLibro.addActionListener(this);
		jpLibros.add(jbRegistrarLibro, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 4;
		jbModificarLibro = new JButton(new ImageIcon(getClass().getResource("/imagenes/modificar.png")));
		jbModificarLibro.setPreferredSize(new java.awt.Dimension(40, 40));
		jbModificarLibro.addActionListener(this);
		jpLibros.add(jbModificarLibro, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 4;
		jbEliminarLibro = new JButton(new ImageIcon(getClass().getResource("/imagenes/eliminar.png")));
		jbEliminarLibro.setPreferredSize(new java.awt.Dimension(40, 40));
		jbEliminarLibro.addActionListener(this);
		jpLibros.add(jbEliminarLibro, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		jtfBuscarLibros = new JTextField(35);
		jtfBuscarLibros.addKeyListener(new java.awt.event.KeyAdapter() {
		    public void keyReleased(java.awt.event.KeyEvent evt) {
		        jtfBuscarLibrosKeyReleased(evt); // Llama al nuevo método
		    }
		});
		jpLibros.add(jtfBuscarLibros, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		cbLibrosDisponibles = new JCheckBox("Disponible");
		cbLibrosDisponibles.addActionListener(this);
		jpLibros.add(cbLibrosDisponibles, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		jbBuscarLibro = new JButton(new ImageIcon(getClass().getResource("/imagenes/buscarlibro.png")));
		jbBuscarLibro.setPreferredSize(new java.awt.Dimension(40, 40));
		jbBuscarLibro.addActionListener(this);
		jpLibros.add(jbBuscarLibro, gbc);
		 
		gbc.gridx = 3;
		gbc.gridy = 2;
		jbLimpiarLibro = new JButton(new ImageIcon(getClass().getResource("/imagenes/limpiar.png")));
		jbLimpiarLibro.setPreferredSize(new java.awt.Dimension(40, 40));
		jbLimpiarLibro.addActionListener(this);
		jpLibros.add(jbLimpiarLibro, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		jtLibros = new JTable(DAOLibros.ConsultarTodo(false), columnasLibro);
		jtLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jspLibros = new JScrollPane(jtLibros);
		jpLibros.add(jspLibros, gbc);

	}
	public void componentesCentroPrestYDev() throws Exception {
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // --- FILA 0: TÍTULO PRINCIPAL ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Ocupa las 3 columnas
        gbc.anchor = GridBagConstraints.CENTER;
        jlGestionPrestYDev = new JLabel("GESTION DE PRESTAMOS Y DEVOLUCIONES");
        jlGestionPrestYDev.setFont(new Font("Arial", Font.PLAIN, 24));
        jpPrestYDev.add(jlGestionPrestYDev, gbc);

        // --- Reseteamos gbc ---
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        // --- FILA 1: ETIQUETAS "USUARIOS" Y "LIBROS" ---
        gbc.gridx = 0;
        gbc.gridy = 1;
        jlUsuariosPrestYDev = new JLabel("USUARIOS:");
        jpPrestYDev.add(jlUsuariosPrestYDev, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        jlLibrosPrestYDev = new JLabel("LIBROS:");
        jpPrestYDev.add(jlLibrosPrestYDev, gbc);
        // Columna 2 (gridx=2) en esta fila queda vacía

        
        // --- FILA 2: JTEXTFIELDS Y BOTONES LIMPIAR (Arriba) ---
        //   *** INICIO DE CAMBIOS (FILA 2) ***
        
        // --- Panel para Búsqueda de Usuarios (TextField + Botón) ---
        JPanel pnlBusquedaUsuario = new JPanel(new BorderLayout(5, 0)); // 5px de gap horizontal
        jtfBuscarUsuariosPrestYDev = new JTextField(15);
        jbLimpiarUsuariosPrestYDev = new JButton(new ImageIcon(getClass().getResource("/imagenes/limpiar.png")));
        jbLimpiarUsuariosPrestYDev.setPreferredSize(new java.awt.Dimension(40, 40));
        pnlBusquedaUsuario.add(jtfBuscarUsuariosPrestYDev, BorderLayout.CENTER); // El TextField se estira
        pnlBusquedaUsuario.add(jbLimpiarUsuariosPrestYDev, BorderLayout.EAST);  // El Botón se pega a la derecha

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Damos peso a esta columna para que crezca
        jpPrestYDev.add(pnlBusquedaUsuario, gbc);

        // --- Panel para Búsqueda de Libros (TextField + Botón) ---
        JPanel pnlBusquedaLibro = new JPanel(new BorderLayout(5, 0)); // 5px de gap horizontal
        jtfBuscarLibrosPrestYDev = new JTextField(15);
        jbLimpiarLibrosPrestYDev = new JButton(new ImageIcon(getClass().getResource("/imagenes/limpiar.png")));
        jbLimpiarLibrosPrestYDev.setPreferredSize(new java.awt.Dimension(40, 40));
        pnlBusquedaLibro.add(jtfBuscarLibrosPrestYDev, BorderLayout.CENTER);
        pnlBusquedaLibro.add(jbLimpiarLibrosPrestYDev, BorderLayout.EAST);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        // gbc.fill y gbc.weightx ya están seteados
        jpPrestYDev.add(pnlBusquedaLibro, gbc);

        // La columna 2 (gridx=2) queda libre en esta fila
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0.0; // Columna de botones no debe crecer
        
        //   *** FIN DE CAMBIOS (FILA 2) ***


        // --- FILA 3: TABLAS Y BOTÓN PRESTAR ---
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; // La columna 0 puede crecer
        gbc.weighty = 1.0; // La fila 3 puede crecer mucho
        jtUsuariosPrestYDev = new JTable(DAOUsuarios.ConsultarTodo(), columnasUsuario);
        jspUsuariosPrestYDev = new JScrollPane(jtUsuariosPrestYDev);
        jpPrestYDev.add(jspUsuariosPrestYDev, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        // gbc.fill, weighty, weightx ya están seteados
        jtLibrosPrestYDev = new JTable(DAOLibros.ConsultarTodo(true), columnasLibro);
        jspLibrosPrestYDev = new JScrollPane(jtLibrosPrestYDev);
        jpPrestYDev.add(jspLibrosPrestYDev, gbc);

        // --- Botón Prestar en la columna de acciones (Correcto como lo tenías) ---
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Que el botón llene su celda
        gbc.weightx = 0.0; // Columna de botones no crece
        gbc.weighty = 0.0; // No crece verticalmente
        gbc.anchor = GridBagConstraints.NORTH; // Se alinea con la parte superior
        jbPrestar = new JButton("PRESTAR");
        jpPrestYDev.add(jbPrestar, gbc);

        // --- FILA 4: ETIQUETA "PRESTAMOS Y DEVOLUCIONES" ---
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3; // Abarca las 3 columnas
        gbc.weightx = 0.0; // Reseteamos
        gbc.weighty = 0.0; // Reseteamos
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        jlPrestYDevPrestYDev = new JLabel("Prestamos y Devoluciones");
        jpPrestYDev.add(jlPrestYDevPrestYDev, gbc);

        // --- FILA 5: JTEXTFIELD+LIMPIAR Y BOTÓN DEVOLVER (Abajo) ---
        //   *** INICIO DE CAMBIOS (FILA 5) ***
        gbc.gridwidth = 1; // Reseteamos

        // --- Panel para Búsqueda de Préstamos (TextField + Botón Limpiar) ---
        JPanel pnlBusquedaPrestamo = new JPanel(new BorderLayout(5, 0)); // 5px de gap
        jtfBuscarPrestYDev = new JTextField(15);
        jbLimpiarPrestYDev = new JButton(new ImageIcon(getClass().getResource("/imagenes/limpiar.png")));
        jbLimpiarPrestYDev.setPreferredSize(new java.awt.Dimension(40, 40));
        pnlBusquedaPrestamo.add(jtfBuscarPrestYDev, BorderLayout.CENTER);
        pnlBusquedaPrestamo.add(jbLimpiarPrestYDev, BorderLayout.EAST);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // El panel de búsqueda ocupa las 2 columnas de las tablas
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Que crezca
        jpPrestYDev.add(pnlBusquedaPrestamo, gbc);

        // --- Botón Devolver en la columna de acciones ---
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 1; // Ocupa solo 1 columna
        gbc.fill = GridBagConstraints.HORIZONTAL; // Que llene su celda
        gbc.weightx = 0.0; // Columna de botones no crece
        gbc.anchor = GridBagConstraints.NORTH; // Alinear con el botón Prestar
        jbDevolver = new JButton("DEVOLVER");
        jpPrestYDev.add(jbDevolver, gbc);
        
        //   *** FIN DE CAMBIOS (FILA 5) ***


        // --- FILA 6: TABLA DE PRESTAMOS ---
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // Abarca las 3 columnas
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // Que la tabla de abajo también crezca
        jtPrestYDev = new JTable(DAOPresYDev.ConsultarTodo(), columnasPrestamo);
        jspPrestYDev = new JScrollPane(jtPrestYDev);
        jpPrestYDev.add(jspPrestYDev, gbc);
	}
	
	//Metodo para buscar Usuarios mientras se escribe
	private void jtfBuscarUsuariosKeyReleased(java.awt.event.KeyEvent evt) {
	    try {
	        // 1. Obtener el texto que el usuario está escribiendo
	        String textoBusqueda = jtfBuscarUsuarios.getText();
	        
	        // 2. Llamar al método del DAO que acabamos de crear
	        Object[][] datos = DAOUsuarios.BuscarUsuario(textoBusqueda);
	        
	        // 3. Actualizar la JTable con los nuevos datos
	        // (columnasUsuario es el array de String que ya tienes definido en tu clase Principal)
	        jtUsuarios.setModel(new DefaultTableModel(datos, columnasUsuario));
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error al buscar usuarios: " + e.getMessage());
	    }
	}
	//Metodo para actualizar la tabla de usuarios 
	public void actualizarTablaUsuarios() {
	    try {
	        // 1. Obtiene los datos frescos de la base de datos
	        Object[][] datos = DAOUsuarios.ConsultarTodo();
	        
	        // 2. Asigna los nuevos datos a la tabla
	        // (columnasUsuario es el array de String que ya tienes definido)
	        jtUsuarios.setModel(new DefaultTableModel(datos, columnasUsuario));
	        
	        // 3. Volvemos a aplicar la configuración de selección (sin método extra)
	        jtUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, 
	            "Error al actualizar la tabla de usuarios: " + e.getMessage(), 
	            "Error de Carga", 
	            JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	//Metodos para el panel de libros
	public void actualizarTablaLibros() {
	    try {
	        // 1. Obtiene los valores de los filtros
	        String textoBusqueda = jtfBuscarLibros.getText();
	        boolean soloDisponibles = cbLibrosDisponibles.isSelected();
	        
	        Object[][] datos;
	        
	        // 2. Decide qué método DAO llamar
	        if (textoBusqueda.isEmpty()) {
	            // Si no hay texto, consulta todo (respetando el checkbox)
	            datos = DAOLibros.ConsultarTodo(soloDisponibles);
	        } else {
	            // Si hay texto, busca por título (respetando el checkbox)
	            datos = DAOLibros.BuscarLibro(textoBusqueda, soloDisponibles);
	        }
	        
	        // 3. Asigna los nuevos datos a la tabla
	        jtLibros.setModel(new DefaultTableModel(datos, columnasLibro));
	        
	        // 4. Aplicar la configuración de selección
	        jtLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, 
	            "Error al actualizar la tabla de libros: " + e.getMessage(), 
	            "Error de Carga", 
	            JOptionPane.ERROR_MESSAGE);
	    }
	}

	//Metodo para buscar Libros mientras se escribe
	private void jtfBuscarLibrosKeyReleased(java.awt.event.KeyEvent evt) {
	    // Cada vez que se teclea, llama al actualizador
        actualizarTablaLibros();
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		//SALIR Y CERRAR SESION
		if(e.getSource() == jbSalir) {
			int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea salir de la aplicacion?", "CONFIRMAR SALIDA", JOptionPane.YES_NO_OPTION);
			if(respuesta == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			else {
				return;
			}
		}
		else if(e.getSource() == jbCerrarSesion) {
			int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea cerrar sesion?", "CERRAR SESION", JOptionPane.YES_NO_OPTION);
			if(respuesta == JOptionPane.YES_OPTION) {
				logeo = new Logeo("INICIAR SESION");
				logeo.setVisible(true);
				setVisible(false);
			}
			else {
				return;
			}
		}
		
		//MOVERSE ENTRE LOS PANELES USUARIOS, LIBROS Y PRESTAMOS
		if(e.getSource() == jbUsuarios || e.getSource() == jmiUsuarios) {
			cardLayoutULPD.show(jpCentro, "USUARIOS");
		}
		else if(e.getSource() == jbLibros || e.getSource() == jmiLibros) {
			cardLayoutULPD.show(jpCentro, "LIBROS");
		}
		else if(e.getSource() == jbPrestYDev || e.getSource() == jmiPrestYDev) {
			cardLayoutULPD.show(jpCentro, "PRESTYDEV");
		}
		
		//ELEMENTOS DE USUARIOS
		else if(e.getSource() == jbRegistrarUsuario) {
			ruPantalla = new RegistrarUsuario("Registrar Usuario");
			
			ruPantalla.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent windowEvent) {
                    // Cuando la ventana de registro se cierra,
                    // mandamos actualizar la tabla principal.
                    actualizarTablaUsuarios(); 
                }
            });
			
			ruPantalla.setVisible(true);
		}
		else if(e.getSource() == jbModificarUsuario) {
			
			int filaSeleccionada = jtUsuarios.getSelectedRow();
			
			//Comprobar que haya una fila seleccionada
			if(filaSeleccionada == -1) {
				JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario de la tabla para modificar.", "Ningún usuario seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
			}
			
			//Para obetener los datos de la tabla se hace lo siguiente 
			int idUsuario = (Integer) jtUsuarios.getValueAt(filaSeleccionada, 0);
			String nombre = (String) jtUsuarios.getValueAt(filaSeleccionada, 1);
            String apellido = (String) jtUsuarios.getValueAt(filaSeleccionada, 2);
            String genero = (String) jtUsuarios.getValueAt(filaSeleccionada, 3);
            String telefono = (String) jtUsuarios.getValueAt(filaSeleccionada, 4);
            String email = (String) jtUsuarios.getValueAt(filaSeleccionada, 5);
            String fechaNac = (String) jtUsuarios.getValueAt(filaSeleccionada, 6);
			
			
			muPantalla = new ModificarUsuario("Modificar Usuario");
			//Metodo para mostrar los datos en la pantalla de ModificarUsuario
			muPantalla.cargarDatos(idUsuario, nombre, apellido, genero, telefono, email, fechaNac);
			
			muPantalla.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent windowEvent) {
                    // Cuando la ventana de modificar se cierra,
                    // mandamos actualizar la tabla principal.
                    actualizarTablaUsuarios();
                }
            });
			
			muPantalla.setVisible(true);
		}
		else if(e.getSource() == jbEliminarUsuario) {
			int filaSeleccionada = jtUsuarios.getSelectedRow();
			if(filaSeleccionada == -1) {
				JOptionPane.showMessageDialog(this, "No hay usuario seleccionado a eliminar.", "Ningún usuario seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
			}
			int idUsuario = (Integer) jtUsuarios.getValueAt(filaSeleccionada, 0);
			String nombre = (String) jtUsuarios.getValueAt(filaSeleccionada, 1);
			String apellido = (String) jtUsuarios.getValueAt(filaSeleccionada, 2);
			
			try {
				int respuesta = JOptionPane.showConfirmDialog(this, "¿Estas seguro de eliminar a "+nombre+ " "+apellido+"?\nSe eliminara se historial de prestamos", "CERRAR SESION", JOptionPane.YES_NO_OPTION);
				if(respuesta == JOptionPane.YES_OPTION) {
					DAOUsuarios.EliminarUsuarioPorId(idUsuario);
					actualizarTablaUsuarios();
					JOptionPane.showMessageDialog(this, 
	                        "Usuario eliminado correctamente.", "Eliminacion Exitosa", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					return;
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == jbLimpiarUsuario) {
			jtfBuscarUsuarios.setText("");
			actualizarTablaUsuarios();
		}
		
		//ELEMENTOS DE LIBROS
		else if(e.getSource() == jbRegistrarLibro) {
			rlPantalla = new RegistrarLibro ("Registrar Libro");
            
            // Añadimos el listener para actualizar al cerrar
            rlPantalla.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent windowEvent) {
                    actualizarTablaLibros(); // Llama al actualizador de libros
                }
            });
            
			rlPantalla.setVisible(true);
		}
		else if(e.getSource() == jbModificarLibro) {
            int filaSeleccionada = jtLibros.getSelectedRow();
            if(filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un libro para modificar.", "Ningún libro seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Obtenemos datos de la JTable
            int idLibro = (Integer) jtLibros.getValueAt(filaSeleccionada, 0);
            String titulo = (String) jtLibros.getValueAt(filaSeleccionada, 1);
            String autor = (String) jtLibros.getValueAt(filaSeleccionada, 2);
            String categoria = (String) jtLibros.getValueAt(filaSeleccionada, 3);
            int edicion = (Integer) jtLibros.getValueAt(filaSeleccionada, 4);
            int stock = (Integer) jtLibros.getValueAt(filaSeleccionada, 5);

			mlPantalla = new ModificarLibro ("Modificar Libro");
            
            // --- ¡AQUÍ ESTÁ LA CONEXIÓN! ---
            // Llamamos al método 'cargarDatos' que acabamos de crear
            mlPantalla.cargarDatos(idLibro, titulo, autor, categoria, edicion, stock);
            
            // Añadimos el listener para actualizar al cerrar
            mlPantalla.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent windowEvent) {
                    actualizarTablaLibros(); // Llama al actualizador de libros
                }
            });

			mlPantalla.setVisible(true);
		}
		else if(e.getSource() == jbEliminarLibro) {
            // --- CÓDIGO AÑADIDO (PARA QUE FUNCIONE ELIMINAR LIBRO) ---
            int filaSeleccionada = jtLibros.getSelectedRow();
            if(filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "No hay libro seleccionado a eliminar.", "Ningún libro seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int idLibro = (Integer) jtLibros.getValueAt(filaSeleccionada, 0);
            String titulo = (String) jtLibros.getValueAt(filaSeleccionada, 1);
            
            try {
                int respuesta = JOptionPane.showConfirmDialog(this, 
                    "¿Estas seguro de eliminar el libro: "+titulo+"?", 
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                
                if(respuesta == JOptionPane.YES_OPTION) {
                    DAOLibros.EliminarLibroPorId(idLibro);
                    actualizarTablaLibros(); // Actualiza la tabla
                    JOptionPane.showMessageDialog(this, 
                            "Libro eliminado correctamente.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                }
                
            } catch (SQLException e1) {
                // Esto capturará el error si el libro está en un Préstamo
                JOptionPane.showMessageDialog(this, 
                        "No se puede eliminar el libro. Probablemente está asignado a un préstamo.\nError: " + e1.getMessage(), 
                        "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
        }
		
		
		else if(e.getSource() == jbLimpiarLibro) {
			jtfBuscarLibros.setText("");
            // --- CORRECCIÓN ---
            // Le decimos al checkbox que se desmarque
            cbLibrosDisponibles.setSelected(false);
			actualizarTablaLibros();
		}
		else if (e.getSource() == cbLibrosDisponibles) {
            // Cada vez que se marca o desmarca, actualiza la tabla
            actualizarTablaLibros();
        }
		
	}
}
