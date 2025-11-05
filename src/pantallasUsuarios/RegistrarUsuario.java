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

	
	private JComboBox<String> jcbGenero;
	private JSpinner spinnerFechaNacimiento;
	
	private JButton jbRegistrarUsuario, jbCancelar;
	
	public RegistrarUsuario (String title) {
		super(title);
		setSize(800,600);
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
		
		//Genero:
		gbc.gridx = 0;
		gbc.gridy = 5;
		jlGenero = new JLabel("Género:");
		jpContenedor.add(jlGenero, gbc);
		gbc.gridx = 1;
		gbc.gridy = 5;
		
		String[] generos = {"Masculino", "Femenino"};
		jcbGenero = new JComboBox<>(generos);
		jcbGenero.setPreferredSize(new Dimension(170, 25));
		jpContenedor.add(jcbGenero, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		jlFechaNacimiento = new JLabel("Fecha de Nacimiento:");
		jpContenedor.add(jlFechaNacimiento, gbc);
		gbc.gridx = 1;
		gbc.gridy = 6;
		

		SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null,null,Calendar.DAY_OF_MONTH);

		spinnerFechaNacimiento = new JSpinner(dateModel);

		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerFechaNacimiento, "dd/MM/yyyy");
		spinnerFechaNacimiento.setEditor(editor);

		spinnerFechaNacimiento.setPreferredSize(new Dimension(170, 25));
		
		jpContenedor.add(spinnerFechaNacimiento, gbc);
		
		
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;

		gbc.gridx = 0;
		gbc.gridy = 7;
		jbRegistrarUsuario = new JButton("Registrar");
		jbRegistrarUsuario.addActionListener(this);
		jpContenedor.add(jbRegistrarUsuario, gbc);
		gbc.gridx = 0;
		gbc.gridy = 8;
		jbCancelar = new JButton("Cancelar");
		jbCancelar.addActionListener(this);
		jpContenedor.add(jbCancelar, gbc);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == jbRegistrarUsuario) {
			
			String generoSeleccionado = (String) jcbGenero.getSelectedItem();

			Date fechaNacimiento = (Date) spinnerFechaNacimiento.getValue();

			SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

			String fechaFormateada = formateador.format(fechaNacimiento);
			
			Usuario usuario = new Usuario(jtfNombre.getText(),jtfApellido.getText(),generoSeleccionado.charAt(0),jtfTelefono.getText(),jtfEmail.getText(),fechaFormateada);
			
			try {
				DAOUsuarios.AgregarUsuario(usuario);
				JOptionPane.showMessageDialog(this, 
                        "Usuario registrado correctamente.", 
                        "Registro Exitoso", 
                        JOptionPane.INFORMATION_MESSAGE);
                
                this.dispose();
			} catch (SQLException e1) {
				String error = e1.getMessage();
				if (error.contains("chk_telefono")) {
			        JOptionPane.showMessageDialog(this, 
			                "El formato del teléfono no es válido.\n" +
			                "La base de datos rechazó el valor.", 
			                "Datos Inválidos", 
			                JOptionPane.ERROR_MESSAGE);
				}
				else if(error.contains("chk_email")) {
					JOptionPane.showMessageDialog(this, 
			                "El formato del email no es válido.\n" +
			                "La base de datos rechazó el valor.", 
			                "Datos Inválidos", 
			                JOptionPane.ERROR_MESSAGE);
				}
			}
			
		} else if (e.getSource() == jbCancelar) {
			System.out.println("Registro cancelado.");
			this.dispose();
		}
	}

}