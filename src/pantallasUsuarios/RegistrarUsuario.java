package pantallasUsuarios;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.Dimension; // Para los tamaños

// --- CAMBIOS PARA JSpinner ---
import javax.swing.JSpinner;      // El componente de fecha/número
import javax.swing.SpinnerDateModel;  // El "modelo" para que el spinner entienda de fechas

import DAO.DAOUsuarios;
import objetos.Usuario;

import java.util.Date;            // Para manejar el objeto de fecha
import java.util.Calendar;        // Para configurar el modelo

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegistrarUsuario extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JPanel jpContenedor;
	private JLabel jlRegistrarUsuario, jlNombre, jlApellido, jlGenero, jlTelefono, jlEmail, jlFechaNacimiento;
	private JTextField jtfNombre, jtfApellido, jtfEmail, jtfTelefono;
	
	
	// --- CAMBIO 1: Declaración de los nuevos componentes ---
	// Ya no usamos JDateChooser ni JTextField para genero/fecha
	
	private JComboBox<String> jcbGenero; // Para el género
	private JSpinner spinnerFechaNacimiento; // Para la fecha de nacimiento
	
	private JButton jbRegistrarUsuario, jbCancelar;
	
	public RegistrarUsuario (String title) {
		super(title);
		setSize(800,600);
		//setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		
		contenedorPrincipal();
	}
	
	public void contenedorPrincipal() {
		jpContenedor = new JPanel();
		jpContenedor.setLayout(new GridBagLayout());
		InitComponents();
		add(jpContenedor);
	}
	
	public void InitComponents() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		jlRegistrarUsuario = new JLabel("Registrar Usuario");
		jlRegistrarUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
		jpContenedor.add(jlRegistrarUsuario, gbc);
		
		
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		
		//Nombre:
		gbc.gridx = 0;
		gbc.gridy = 1;
		jlNombre = new JLabel("Nombre:");
		jpContenedor.add(jlNombre, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		jtfNombre = new JTextField(15);
		jpContenedor.add(jtfNombre, gbc);
		
		//Apellido:
		gbc.gridx = 0;
		gbc.gridy = 2;
		jlApellido = new JLabel("Apellido:");
		jpContenedor.add(jlApellido, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		jtfApellido = new JTextField(15);
		jpContenedor.add(jtfApellido, gbc);
		
		//Email:
		gbc.gridx = 0;
		gbc.gridy = 3;
		jlEmail = new JLabel("Email:");
		jpContenedor.add(jlEmail, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		jtfEmail = new JTextField(15);
		jpContenedor.add(jtfEmail, gbc);
		
		//Telefono:
		gbc.gridx = 0;
		gbc.gridy = 4;
		jlTelefono = new JLabel("Telefono:");
		jpContenedor.add(jlTelefono, gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		jtfTelefono = new JTextField(15);
		jpContenedor.add(jtfTelefono, gbc);
		
		// --- CAMBIO 2: JComboBox para Género ---
		gbc.gridx = 0;
		gbc.gridy = 5;
		jlGenero = new JLabel("Género:");
		jpContenedor.add(jlGenero, gbc);
		gbc.gridx = 1;
		gbc.gridy = 5;
		
		String[] generos = {"Masculino", "Femenino"};
		jcbGenero = new JComboBox<>(generos);
		jcbGenero.setPreferredSize(new Dimension(170, 25)); // Tamaño similar al JTextField
		jpContenedor.add(jcbGenero, gbc);
		
		
		// --- CAMBIO 3: JSpinner para Fecha de Nacimiento ---
		gbc.gridx = 0;
		gbc.gridy = 6;
		jlFechaNacimiento = new JLabel("Fecha de Nacimiento:");
		jpContenedor.add(jlFechaNacimiento, gbc);
		gbc.gridx = 1;
		gbc.gridy = 6;
		
		// 1. Creamos un "modelo" de fecha para el Spinner
		SpinnerDateModel dateModel = new SpinnerDateModel(
			new Date(), // Valor inicial (hoy)
			null,       // Sin fecha mínima
			null,       // Sin fecha máxima
			Calendar.DAY_OF_MONTH // Al pulsar flechas, cambia el día
		);
		
		// 2. Creamos el JSpinner con ese modelo
		spinnerFechaNacimiento = new JSpinner(dateModel);
		
		// 3. (Importante) Le decimos cómo queremos ver la fecha (dd/MM/yyyy)
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerFechaNacimiento, "dd/MM/yyyy");
		spinnerFechaNacimiento.setEditor(editor);
		
		// 4. Ajustamos el tamaño
		spinnerFechaNacimiento.setPreferredSize(new Dimension(170, 25));
		
		jpContenedor.add(spinnerFechaNacimiento, gbc);
		
		
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		
		//Registrar, Cancelar Usuario, boton
		gbc.gridx = 0;
		gbc.gridy = 7;
		jbRegistrarUsuario = new JButton("Registrar");
		jbRegistrarUsuario.addActionListener(this); // Añadir listener
		jpContenedor.add(jbRegistrarUsuario, gbc);
		gbc.gridx = 0;
		gbc.gridy = 8;
		jbCancelar = new JButton("Cancelar");
		jbCancelar.addActionListener(this); // Añadir listener
		jpContenedor.add(jbCancelar, gbc);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == jbRegistrarUsuario) {
			
			String generoSeleccionado = (String) jcbGenero.getSelectedItem();
			
			// --- INICIA EL CAMBIO ---
			
			// 1. Obtienes el objeto Date (como ya lo hacías)
			Date fechaNacimiento = (Date) spinnerFechaNacimiento.getValue();
			
			// 2. Creas el formateador con el patrón que quieres
			//    yyyy -> Año con 4 dígitos
			//    MM   -> Mes con 2 dígitos (ej. 03 para Marzo)
			//    dd   -> Día con 2 dígitos
			SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
			
			// 3. Usas el formateador para convertir el objeto Date a un String
			String fechaFormateada = formateador.format(fechaNacimiento);
			
			// --- FIN DEL CAMBIO ---
			
			
			// Imprimir en consola para probar
			System.out.println("--- Nuevo Usuario ---");
			System.out.println("Género: " + generoSeleccionado);
			
			// 4. Ahora imprimes el String formateado
			System.out.println("Fecha Nac: " + fechaFormateada); 
			
			Usuario usuario = new Usuario(jtfNombre.getText(),jtfApellido.getText(),generoSeleccionado.charAt(0),jtfTelefono.getText(),jtfEmail.getText(),fechaFormateada);
			
			try {
				DAOUsuarios.AgregarUsuario(usuario);
				JOptionPane.showMessageDialog(this, 
                        "Usuario registrado correctamente.", 
                        "Registro Exitoso", 
                        JOptionPane.INFORMATION_MESSAGE);
                
                this.dispose();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		} else if (e.getSource() == jbCancelar) {
			System.out.println("Registro cancelado.");
			this.dispose(); // Cierra esta ventana
		}
	}

}