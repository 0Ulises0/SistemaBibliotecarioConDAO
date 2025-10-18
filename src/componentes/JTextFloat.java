package componentes;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class JTextFloat extends JTextField implements KeyListener {

	private static final long serialVersionUID = 1L;
	private int tamanio, entero, mantisa, pe, pd;

	public JTextFloat() {
		this(1000,1000);
	}

	public JTextFloat(int digEnteros, int mantisa) {
		super(digEnteros+mantisa+1);
		tamanio = digEnteros+mantisa+1;
		entero= digEnteros;
		this.mantisa=mantisa;
		pe=0;
		pd=0;
		addKeyListener(this);
	}

	public void keyPressed(KeyEvent e) {
		if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
			e.consume();
			return;
		}		

		if (getText().isEmpty())
			//componente vacio
			e.consume();
		else {
			//componente con datos

			//Cancela una posible seleccion
			setSelectionEnd(getCaretPosition());
			setSelectionStart(getCaretPosition());

			//System.out.println(getCaretPosition());
			//System.out.println(getText().indexOf('.')+1);
			if ((e.getKeyCode() == KeyEvent.VK_BACK_SPACE && 
					getCaretPosition()==getText().indexOf('.')+1) ||
					(e.getKeyCode() == KeyEvent.VK_DELETE && 
					getCaretPosition()==getText().indexOf('.')))
			{
				//cuando el punto el que se quiere borrar
				BorraPunto(e);

			}
			else	
				// cuando no se va a borrar nada
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE 
				&& getCaretPosition()==0 || 
				e.getKeyCode() == KeyEvent.VK_DELETE && 
				getCaretPosition()==getText().length())
					e.consume();
				else 
					// cuando se va a borrar un digito
					if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || 
					e.getKeyCode() == KeyEvent.VK_DELETE) {

						if (getCaretPosition()<=getText().indexOf('.')||
								getText().indexOf('.')==-1)
							//se borra un digito en la parte entera
							pe--;
						else 
							if (getCaretPosition()>getText().indexOf('.')&&
									getText().contains("."))
								//se borra un digito en la parte decimal
								pd--;
					}
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	private void BorraPunto(KeyEvent e) {
		if(pe+pd>entero)
			e.consume();
		else {
			pe+=pd;
			pd=0;
		}			
	}

	public void keyTyped(KeyEvent e) {
		//Cancela una posible seleccion
		setSelectionEnd(getCaretPosition());
		setSelectionStart(getCaretPosition());

		if (getText().length() == tamanio)
			//capacidad de digitos agotada
			e.consume();
		else 
			if (e.getKeyChar()=='.' && getText().contains("."))
				//si ya contiene un punto no permite otro
				e.consume();
			else
				if (!(e.getKeyChar()>='0' && e.getKeyChar()<='9' 
				|| e.getKeyChar()=='.'))
					//si no es un numero o un punto
					e.consume();
				else {
					String parte1=getText().substring(0, getCaretPosition());
					String parte2=getText().substring(getCaretPosition());
					if (!CumpleFormato(parte1+e.getKeyChar()+parte2)) 
						e.consume();
				}

	}

	private boolean CumpleFormato(String cantidad) {
		int punto= cantidad.indexOf('.');
		if (punto==-1) { //cuando no hay punto			
			if (cantidad.length()<=entero)
				pe++;
			pd=0;
			return cantidad.length()<=entero;
		}
		if ((cantidad.substring(0, punto).length()<=entero) &&
				(cantidad.substring(punto+1).length()<=mantisa)) {
			pe=cantidad.substring(0, punto).length();
			pd=cantidad.substring(punto+1).length();
			return true;
		}
		return false;
	}

}