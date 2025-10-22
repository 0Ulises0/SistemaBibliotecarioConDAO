package pantallasUsuarios;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ModificarUsuario extends JFrame implements ActionListener {

	
	private static final long serialVersionUID = 1L;
	
	private JPanel jpContenedor;
	private JLabel jlModificarUsuario, jlNombre, jlApellido, jlGenero, jlTelefono, jlEmail, jlFechaNacimiento;
	private JTextField jtfNombre, jtfApellido, jtfEmail, jtfTelefono, jtfGenero, jtfFechaNacimiento;
	
	private JButton jbModificarUsuario, jbCancelar;
	
	public ModificarUsuario (String title) {
		super(title);
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
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
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		jlModificarUsuario = new JLabel("Modificar Usuario");
		jlModificarUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
		jpContenedor.add(jlModificarUsuario, gbc);
		
		
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
		
		//Genero:
		gbc.gridx = 0;
		gbc.gridy = 5;
		jlGenero = new JLabel("Genero:");
		jpContenedor.add(jlGenero, gbc);
		gbc.gridx = 1;
		gbc.gridy = 5;
		jtfGenero = new JTextField(15);
		jpContenedor.add(jtfGenero, gbc);
		
		//Fecha de Nacimiento:
		gbc.gridx = 0;
		gbc.gridy = 6;
		jlFechaNacimiento = new JLabel("Fecha de Nacimiento:");
		jpContenedor.add(jlFechaNacimiento, gbc);
		gbc.gridx = 1;
		gbc.gridy = 6;
		jtfFechaNacimiento = new JTextField(15);
		jpContenedor.add(jtfFechaNacimiento, gbc);
		
		//Registrar, Cancelar Usuario, boton
		gbc.gridx = 0;
		gbc.gridy = 7;
		jbModificarUsuario = new JButton("Modificar");
		jpContenedor.add(jbModificarUsuario, gbc);
		gbc.gridx = 1;
		gbc.gridy = 7;
		jbCancelar = new JButton("Cancelar");
		jpContenedor.add(jbCancelar, gbc);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
