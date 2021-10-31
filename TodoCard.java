import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TodoCard extends JPanel {
    private int cornerRadius = 10;
    public String Title;
    public String Time;
    public Boolean IsDone;
    public Priority Priority;

    private GridBagConstraints gbc = new GridBagConstraints();
    private RoundedButton btnComplete;
    private JLabel TitleLabel;
    private JLabel TimeLabel;
    private RoundedButton removeButton;

    FontsAndColors f = new FontsAndColors();

    public TodoCard(String title, String time, Boolean isDone, Priority priority, TodoListGUI parent) {
        super();
        Title = title;
        Time = time;
        IsDone = isDone;
        Priority = priority;

        this.setLayout(new BorderLayout());
        this.setBackground(f.LightGreen);
        this.setPreferredSize(new Dimension(480, 90));

        JPanel bodyPanel = new JPanel(null);
        bodyPanel.setOpaque(false);
        this.add(bodyPanel);

        btnComplete = new RoundedButton("+", Color.WHITE);

        btnComplete.addActionListener(e -> {
            IsDone = !IsDone;
            UpdateCard();
            parent.UpdateBodyPanel();
        });
        btnComplete.setFont(f.MontserratSemiBold.deriveFont(22f));
        btnComplete.setFocusable(false);

        btnComplete.setBounds(15, 40, 30, 30);
        bodyPanel.add(btnComplete);

        TitleLabel = new JLabel(Title);
        TitleLabel.setFont(f.MontserratMedium.deriveFont(14f));
        TitleLabel.setBounds(60, 40, 350, 30);

        bodyPanel.add(TitleLabel);

        TimeLabel = new JLabel(Time);
        TimeLabel.setFont(f.MontserratSemiBold.deriveFont(16f));
        TimeLabel.setBounds(190, 10, 100, 30);
        TimeLabel.setHorizontalAlignment(JLabel.CENTER);
        bodyPanel.add(TimeLabel);

        BufferedImage myPicture;
        try {
            myPicture = ImageIO.read(new File("dustbin_icon_set-ai_white.png"));
            removeButton = new RoundedButton(new ImageIcon(myPicture));
            removeButton.setBackground(f.Red);
            removeButton.setOpaque(false);
            removeButton.setBounds(440, 40, 30, 30);
            removeButton.setFocusable(false);

            removeButton.addActionListener(e -> {
                parent.RemoveCard(this);
                parent.UpdateBodyPanel();
            });
            bodyPanel.add(removeButton, gbc);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        UpdateCard();
    }

    private void UpdateCard() {
        if (!IsDone) {
            btnComplete.setText("+");
            switch (Priority) {
            case General:
                btnComplete.setBackground(f.Yellow);
                break;
            case High:
                btnComplete.setBackground(f.Red);
                break;
            case Low:
                btnComplete.setBackground(f.LightGreen);
                break;
            }
            this.setForeground(Color.WHITE);
            TitleLabel.setForeground(Color.BLACK);
            TimeLabel.setForeground(Color.BLACK);
        } else {
            btnComplete.setText("−");
            btnComplete.setBackground(f.LightGreen);
            this.setForeground(f.DarkGreen);
            TitleLabel.setForeground(Color.WHITE);
            TimeLabel.setForeground(Color.WHITE);
        }

        this.revalidate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setOpaque(false);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    }
}
