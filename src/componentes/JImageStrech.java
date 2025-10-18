package componentes;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Graphics;

public class JImageStrech extends JLabel{
    static final long serialVersionUID = 1;
    ImageIcon imageIcon;

    public JImageStrech (ImageIcon icon){
        super();
        this.imageIcon = icon;
    }

    @Override
    public void paintComponent (Graphics g){
        super.paintComponent(g);
        g.drawImage (imageIcon.getImage(),0,0,getWidth(),getHeight(),this);
    }
}
