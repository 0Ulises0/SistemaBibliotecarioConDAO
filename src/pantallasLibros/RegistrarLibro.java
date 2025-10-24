package pantallasLibros;

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

public class RegistrarLibro extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JPanel jpContenedor;
	private JLabel jlRegistrarLibro, jlTitulo, jlAutor, jlCategoria, jlEdicion; 
	private JTextField jtfTitulo, jtfAutor, jtfCategoria, jtfEdicion;
	private JButton jbRegistrarLibro, jbCancelar;
	
	public RegistrarLibro (String title) {
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
		jlRegistrarLibro = new JLabel("Registrar Libro");
		jlRegistrarLibro.setFont(new Font("Arial", Font.PLAIN, 18));
		jpContenedor.add(jlRegistrarLibro, gbc);
		
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		
		//Titulo:
		gbc.gridx = 0;
		gbc.gridy = 1;
		jlTitulo = new JLabel("Titulo:");
		jpContenedor.add(jlTitulo, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		jtfTitulo = new JTextField(15);
		jpContenedor.add(jtfTitulo, gbc);
		
		
		//Autor:
		gbc.gridx = 0;
		gbc.gridy = 2;
		jlAutor = new JLabel("Autor:");
		jpContenedor.add(jlAutor, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		jtfAutor = new JTextField(15);
		jpContenedor.add(jtfAutor, gbc);
		
		//Categoria:
		gbc.gridx = 0;
		gbc.gridy = 3;
		jlCategoria = new JLabel("Categoria:");
		jpContenedor.add(jlCategoria, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		jtfCategoria = new JTextField(15);
		jpContenedor.add(jtfCategoria, gbc);
		
		//Edicion:
		gbc.gridx = 0;
		gbc.gridy = 4;
		jlEdicion = new JLabel("Edicion:");
		jpContenedor.add(jlEdicion, gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		jtfEdicion = new JTextField(15);
		jpContenedor.add(jtfEdicion, gbc);
		
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		
		//BOTONES
		gbc.gridx = 0;
		gbc.gridy = 5;
		jbRegistrarLibro = new JButton("Registrar");
		jpContenedor.add(jbRegistrarLibro, gbc);
		gbc.gridx = 0;
		gbc.gridy = 6;
		jbCancelar = new JButton("Cancelar");
		jpContenedor.add(jbCancelar, gbc);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
