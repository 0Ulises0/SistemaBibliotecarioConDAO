package pantallasLibros;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane; // --- IMPORTADO ---
import javax.swing.JPanel;
import javax.swing.JTextField;

import DAO.DAOLibros; // --- IMPORTADO ---
import objetos.Libro; // --- IMPORTADO ---

public class RegistrarLibro extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JPanel jpContenedor;
	private JLabel jlRegistrarLibro, jlTitulo, jlAutor, jlCategoria, jlEdicion;
    private JLabel jlStock; 
	private JTextField jtfTitulo, jtfAutor, jtfCategoria, jtfEdicion;
    private JTextField jtfStock; 
	private JButton jbRegistrarLibro, jbCancelar;
	
	public RegistrarLibro (String title) {
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
        
        // --- AÑADIDO: Stock ---
        gbc.gridx = 0;
		gbc.gridy = 5;
		jlStock = new JLabel("Stock (Cantidad):");
		jpContenedor.add(jlStock, gbc);
		gbc.gridx = 1;
		gbc.gridy = 5;
		jtfStock = new JTextField(15);
		jpContenedor.add(jtfStock, gbc);
		
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 6;
		jbRegistrarLibro = new JButton("Registrar");
        jbRegistrarLibro.addActionListener(this);
		jpContenedor.add(jbRegistrarLibro, gbc);
		gbc.gridx = 0;
		gbc.gridy = 7;
		jbCancelar = new JButton("Cancelar");
        jbCancelar.addActionListener(this);
		jpContenedor.add(jbCancelar, gbc);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbRegistrarLibro) {
            try {

                String titulo = jtfTitulo.getText();
                String autor = jtfAutor.getText();
                String categoria = jtfCategoria.getText();

                if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty() || jtfEdicion.getText().isEmpty() || jtfStock.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int edicion = Integer.parseInt(jtfEdicion.getText());
                int stock = Integer.parseInt(jtfStock.getText());

                Libro nuevoLibro = new Libro(titulo, autor, categoria, edicion, stock);

                DAOLibros.AgregarLibro(nuevoLibro);

                JOptionPane.showMessageDialog(this, "Libro registrado correctamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Error: 'Edición' y 'Stock' deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException sqle) {
                JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos: " + sqle.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                sqle.printStackTrace();
            }

        } else if (e.getSource() == jbCancelar) {
            this.dispose();
        }
	}
}