package componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class JTextCURP extends JTextField implements KeyListener {

    private int limit;
    private final Font font = new Font("Arial", Font.PLAIN, 14);

    public JTextCURP(int columns, int limit) {
        super(columns);
        this.limit = limit;
        setFont(font);
        setBackground(Color.WHITE);
        addKeyListener(this);
    }

    @Override
    protected Document createDefaultModel() {
        return new LimitDocument();
    }

   
    //validaciones
    private boolean validarCurpCompleta(String curp) {
        // Patrón general CURP: 4 letras, 6 números (fecha), 1 letra (H/M), 2 letras estado, 3 consonantes, 2 dígitos o letras
        if (!curp.matches("[A-Z]{4}[0-9]{6}[HM]{1}[A-Z]{2}[B-DF-HJ-NP-TV-Z]{3}[A-Z0-9]{1}[0-9]{1}")) {
            return false;
        }

        // Fecha (posiciones 4-9)
        if (!validaFecha(curp.substring(4, 10))) return false;

        // Estado (posiciones 11-12)
        if (!validarEstado(curp.substring(11, 13))) return false;

        return true;
    }

    private boolean validaFecha(String fecha) {
        try {
            int anio = Integer.parseInt(fecha.substring(0, 2)); // AA
            int mes = Integer.parseInt(fecha.substring(2, 4));  // MM
            int dia = Integer.parseInt(fecha.substring(4, 6));  // DD

            if (mes < 1 || mes > 12) return false;

            int[] diasMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            if (anio % 4 == 0 && mes == 2) diasMes[1] = 29; // año bisiesto

            return dia >= 1 && dia <= diasMes[mes - 1];
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validarEstado(String estado) {
        String[] entidades = {"AS","BC","BS","CC","CL","CM","CS","CH","DF","DG","GT","GR","HG","JC",
                              "MC","MN","MS","NT","NL","OC","PL","QT","QR","SP","SL","SR","TC","TS",
                              "TL","VZ","YN","ZS","NE"};
        estado = estado.trim().toUpperCase();

        for (String e : entidades) {
            if (e.equals(estado)) return true;
        }
        return false;
    }

    private class LimitDocument extends PlainDocument {
        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) return;
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String text = getText().trim().toUpperCase();
        setText(text);

        if (validarCurpCompleta(text)) {
            setBackground(Color.WHITE);
        } else {
            setBackground(Color.RED);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
}

