import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CircleButton extends JButton {
    private Color backgroundColor;

    public CircleButton(String text, Color color, Color colorText) {
        super(text);
        backgroundColor = color;
        this.setBorder(null);
        this.setForeground(colorText);
        setContentAreaFilled(false);
    }

    public CircleButton(ImageIcon image, Color color) {
        super(image);
        backgroundColor = color;
        this.setBorder(null);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed())
            g.setColor(Color.lightGray);
        else
            g.setColor(backgroundColor);
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }
}
