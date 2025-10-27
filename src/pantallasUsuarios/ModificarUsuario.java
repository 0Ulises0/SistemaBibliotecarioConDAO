package pantallasUsuarios;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.Dimension; // Para los tamaños

// --- CAMBIOS PARA JSpinner ---
import javax.swing.JSpinner;      // El componente de fecha/número
import javax.swing.SpinnerDateModel;  // El "modelo" para que el spinner entienda de fechas
import java.util.Date;            // Para manejar el objeto de fecha
import java.util.Calendar;        // Para configurar el modelo

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import java.text.ParseException;
import javax.swing.JOptionPane;
import DAO.DAOUsuarios;
import objetos.Usuario;

public class ModificarUsuario extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JPanel jpContenedor;
	private JLabel jlModificarUsuario, jlNombre, jlApellido, jlGenero, jlTelefono, jlEmail, jlFechaNacimiento;
	private JTextField jtfNombre, jtfApellido, jtfEmail, jtfTelefono;

	private JComboBox<String> jcbGenero; 
	private JSpinner spinnerFechaNacimiento; 
	
	private JButton jbModificarUsuario, jbCancelar;

	private int idUsuarioModificar;
	
	public ModificarUsuario (String title) {
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
		jlModificarUsuario = new JLabel("Modificar Usuario");
		jlModificarUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
		jpContenedor.add(jlModificarUsuario, gbc);
		
		
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

		SpinnerDateModel dateModel = new SpinnerDateModel(new Date(),null,null,Calendar.DAY_OF_MONTH);

		spinnerFechaNacimiento = new JSpinner(dateModel);

		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerFechaNacimiento, "dd/MM/yyyy");
		spinnerFechaNacimiento.setEditor(editor);

		spinnerFechaNacimiento.setPreferredSize(new Dimension(170, 25));
		
		jpContenedor.add(spinnerFechaNacimiento, gbc);
		
		
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		jbModificarUsuario = new JButton("Guardar Cambios");
		jbModificarUsuario.addActionListener(this); 
		jpContenedor.add(jbModificarUsuario, gbc);
		gbc.gridx = 0;
		gbc.gridy = 8;
		jbCancelar = new JButton("Cancelar");
		jbCancelar.addActionListener(this);
		jpContenedor.add(jbCancelar, gbc);
	}
	
	public void cargarDatos(int id, String nombre, String apellido, String genero, String telefono, String email, String fechaStr) {

	        this.idUsuarioModificar = id;

	        jtfNombre.setText(nombre);
	        jtfApellido.setText(apellido);
	        jtfTelefono.setText(telefono);
	        jtfEmail.setText(email);

	        if(genero.equals("M")) {
	        	jcbGenero.setSelectedItem("Masculino");
	        }
	        else if(genero.equals("F")) {
	        	jcbGenero.setSelectedItem("Femenino");
	        }
	        
	        try {
	            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
	            Date fechaNacimiento = formateador.parse(fechaStr);
	            spinnerFechaNacimiento.setValue(fechaNacimiento);
	        } catch (ParseException e) {

	            e.printStackTrace();

	            spinnerFechaNacimiento.setValue(new Date()); 
	        }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == jbModificarUsuario) {
			
			String nombre = jtfNombre.getText();
            String apellido = jtfApellido.getText();
            String telefono = jtfTelefono.getText();
            String email = jtfEmail.getText();

            String generoStr = (String) jcbGenero.getSelectedItem();
            char genero = '-'; 
            if (generoStr.equals("Masculino")) {
                genero = 'M';
            } else if (generoStr.equals("Femenino")) {
                genero = 'F';
            }
            
            Date fechaNac = (Date) spinnerFechaNacimiento.getValue();
			SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
			String fechaFormateada = formateador.format(fechaNac);
			
			Usuario usuarioModificado = new Usuario(nombre, apellido, genero, telefono, email, fechaFormateada);

            try {

                DAOUsuarios.ModificarUsuario(usuarioModificado, this.idUsuarioModificar);

                JOptionPane.showMessageDialog(this, 
                        "Usuario modificado correctamente.", 
                        "Modificación Exitosa", 
                        JOptionPane.INFORMATION_MESSAGE);
                
                this.dispose();
                
            } 
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                        "Error al modificar el usuario:\n" + ex.getMessage(), 
                        "Error de Base de Datos", 
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
			
			} 
		else if (e.getSource() == jbCancelar) {
				System.out.println("Modificación cancelada.");
				this.dispose(); 
		}
	}

}