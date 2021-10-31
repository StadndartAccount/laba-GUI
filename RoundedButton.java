import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class RoundedButton extends JButton {
    private int cornerRadius = 10;

    public RoundedButton(String text, Color colorText) {
        super(text);
        this.setBorder(null);
        this.setForeground(colorText);
        setContentAreaFilled(false);
    }

    public RoundedButton(ImageIcon image) {
        super(image);
        this.setBorder(null);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.setOpaque(false);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed())
            graphics.setColor(Color.lightGray);
        else
            graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        super.paintComponent(graphics);
    }
}
