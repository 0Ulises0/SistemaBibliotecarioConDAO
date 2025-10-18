package componentes;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class JTextEntero extends JTextField implements KeyListener {
    private static final long serialVersionUID = 1L;
    private int tamaño;

    public JTextEntero (){
        super();
        tamaño = Integer.MAX_VALUE;
        addKeyListener(this);
    }

    public JTextEntero (int columns){
        super(columns);
        tamaño = columns;
        addKeyListener(this);
    }
    
    public void keyPressed (KeyEvent e){
        if(e.isControlDown()){
            if(e.getKeyCode() == KeyEvent.VK_V){ //Elimina que se pueda pegar
                e.consume();
            }
        }
    }

    public void keyReleased (KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            JOptionPane.showMessageDialog(this,"BackSpace");
        }
        if(e.getKeyCode() == KeyEvent.VK_DELETE){
            JOptionPane.showMessageDialog(this,"Delete");
        }

    }

    public void keyTyped (KeyEvent e){
        if(getText().length() == tamaño){
            e.consume();
        }
        else if(!(e.getKeyChar() >= '0' && e.getKeyChar() <='9')){ //Solo acepta caracteres de 0 a 9
            e.consume();
        }
    }
}
