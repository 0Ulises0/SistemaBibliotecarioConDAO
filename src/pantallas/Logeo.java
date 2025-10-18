package pantallas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import componentes.JImageStrech;

public class Logeo extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	JImageStrech jisLogo;
	JLabel jlBienvenido, jlUsuario, jlContrasena;
	JTextField txtUsuario, txtContrasena;
	JButton jbEntrar, jbSalir;
	
	JPanel contenedor;
	
	public Logeo(String title) {
		super(title);
		setSize(800,600);
		
		ContenedorPrincipal();
	}
	
	public void ContenedorPrincipal() {
		contenedor = new JPanel();
		contenedor.setLayout(new GridBagLayout());
		InitComponents();
		add(contenedor);
	}
	
	public void InitComponents() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		jisLogo = new JImageStrech(new ImageIcon());
		contenedor.add(jisLogo, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		jlBienvenido = new JLabel("Bienvenido!");
		contenedor.add(jlBienvenido, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		jlUsuario = new JLabel("Usuario:");
		contenedor.add(jlUsuario, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		txtUsuario = new JTextField(15);
		contenedor.add(txtUsuario, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		jlContrasena = new JLabel("Contraseña:");
		contenedor.add(jlContrasena, gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		txtContrasena = new JTextField(15);
		contenedor.add(txtContrasena, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		jbEntrar = new JButton("ENTRAR");
		contenedor.add(jbEntrar, gbc);
		gbc.gridx = 0;
		gbc.gridy = 7;
		jbSalir = new JButton("SALIR");
		contenedor.add(jbSalir, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
