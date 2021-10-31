import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class TodoListGUI extends JFrame {

    FontsAndColors f = new FontsAndColors();

    private JLayeredPane MainPane = new JLayeredPane();

    private JLabel TodoTitle = new JLabel("DAILIST");
    private JLabel FinishedLabel = new JLabel("Finished");
    private JLabel UpcomingLabel = new JLabel("Upcoming");

    private JPanel HeaderPanel = new JPanel(new GridLayout());
    private JPanel TableTitles = new JPanel(new GridLayout(1, 2, 30, 0));
    private JPanel BodyPanel = new JPanel();

    private JPanel UpcomingPanel;
    private GridBagConstraints gbc = new GridBagConstraints();

    private JPanel FinishedPanel;

    private CircleButton AddCardButton = new CircleButton("+", Color.WHITE, f.LightGreen);

    private JScrollPane scrollFinishedCards;
    private JScrollPane scrollUpcomingCards;

    private JPanel panelElementsToNorth1;
    private JPanel panelElementsToNorth2;

    private List<TodoCard> CardsArray = new ArrayList<>();

    public TodoListGUI() {
        super("Dailist");

        this.setResizable(false);
        this.setBounds(100, 100, 1116, 819);
        this.setLayout(null);
        this.getContentPane().setBackground(f.LightGreen);

        MainPane.setOpaque(false);
        MainPane.setBounds(0, 0, 1100, 800);
        this.getContentPane().add(MainPane);

        AddCardButton.setFocusable(false);
        AddCardButton.setBounds(980, 660, 60, 60);
        AddCardButton.addActionListener(e -> {
            try {
                ShowDialogMessage();
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        AddCardButton.setFont(f.MontserratSemiBold.deriveFont(42f));
        MainPane.add(AddCardButton, 10, 0);

        // шапочка приложения
        HeaderPanel.setBounds(0, 0, 1100, 80);
        HeaderPanel.setOpaque(false);
        MainPane.add(HeaderPanel, 0, 0);

        TodoTitle.setForeground(Color.WHITE);
        TodoTitle.setFont(f.MontserratSemiBold.deriveFont(24f));
        TodoTitle.setVerticalAlignment(JLabel.CENTER);
        TodoTitle.setHorizontalAlignment(JLabel.CENTER);
        HeaderPanel.add(TodoTitle);

        TableTitles.setBounds(50, 80, 1000, 60);
        TableTitles.setOpaque(false);
        TableTitles.setAlignmentX(Component.CENTER_ALIGNMENT);
        MainPane.add(TableTitles, 0, 0);

        BodyPanel.setBounds(50, 150, 1000, 580);
        BodyPanel.setOpaque(false);
        MainPane.add(BodyPanel, 0, 0);

        UpcomingLabel.setVerticalAlignment(JLabel.CENTER);
        FinishedLabel.setVerticalAlignment(JLabel.CENTER);

        UpcomingLabel.setHorizontalAlignment(JLabel.LEFT);
        FinishedLabel.setHorizontalAlignment(JLabel.LEFT);

        UpcomingLabel.setForeground(Color.WHITE);
        FinishedLabel.setForeground(Color.WHITE);

        UpcomingLabel.setFont(f.MontserratSemiBold.deriveFont(20f));
        FinishedLabel.setFont(f.MontserratSemiBold.deriveFont(20f));

        TableTitles.add(UpcomingLabel);
        TableTitles.add(FinishedLabel);

        UpdateBodyPanel();

        this.setVisible(true);
    }

    public void UpdateBodyPanel() {
        if (CardsArray.size() == 0) {
            BodyPanel.removeAll();
            BodyPanel.revalidate();
            BodyPanel.repaint();
            BodyPanel.setLayout(new GridLayout());

            BufferedImage myPicture;
            try {
                TableTitles.setVisible(false);
                BodyPanel.removeAll();
                BodyPanel.revalidate();
                BodyPanel.repaint();
                myPicture = ImageIO.read(new File("4211920-ai.png"));
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));
                picLabel.setVerticalAlignment(JLabel.CENTER);
                picLabel.setHorizontalAlignment(JLabel.CENTER);
                BodyPanel.add(picLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            TableTitles.setVisible(true);
            BodyPanel.removeAll();
            BodyPanel.revalidate();
            BodyPanel.repaint();
            BodyPanel.setLayout(new GridLayout(1, 2, 30, 0));

            UpcomingPanel = new JPanel(new GridBagLayout());

            panelElementsToNorth1 = new JPanel(new BorderLayout());
            panelElementsToNorth1.add(UpcomingPanel, BorderLayout.NORTH);
            panelElementsToNorth1.setOpaque(false);

            scrollUpcomingCards = new JScrollPane(panelElementsToNorth1);
            scrollUpcomingCards.getViewport().addChangeListener(e -> {
                scrollUpcomingCards.revalidate();
            });
            scrollUpcomingCards.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
            scrollUpcomingCards.getVerticalScrollBar().setUnitIncrement(10);
            scrollUpcomingCards.setWheelScrollingEnabled(true);

            scrollUpcomingCards.setBorder(null);
            scrollUpcomingCards.getViewport().setOpaque(false);
            scrollUpcomingCards.setOpaque(false);
            UpcomingPanel.setOpaque(false);

            BodyPanel.add(scrollUpcomingCards);

            FinishedPanel = new JPanel(new GridBagLayout());

            panelElementsToNorth2 = new JPanel(new BorderLayout());
            panelElementsToNorth2.add(FinishedPanel, BorderLayout.NORTH);
            panelElementsToNorth2.setOpaque(false);

            scrollFinishedCards = new JScrollPane(panelElementsToNorth2);
            scrollFinishedCards.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
            scrollFinishedCards.getVerticalScrollBar().setUnitIncrement(16);
            scrollFinishedCards.setBorder(null);
            scrollFinishedCards.getViewport().setOpaque(false);
            scrollFinishedCards.setOpaque(false);
            FinishedPanel.setOpaque(false);

            BodyPanel.add(scrollFinishedCards);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            gbc.insets = new Insets(5, 0, 5, 0);
            gbc.gridx = 0;

            int numberOfFinishedCards = 0;
            int numberOfUpcomingCards = 0;
            for (TodoCard todoCard : CardsArray) {
                if (todoCard.IsDone) {
                    gbc.gridy = numberOfFinishedCards;
                    FinishedPanel.add(todoCard, gbc);
                    numberOfFinishedCards++;
                } else {
                    gbc.gridy = numberOfUpcomingCards;
                    UpcomingPanel.add(todoCard, gbc);
                    numberOfUpcomingCards++;
                }
            }

            FinishedPanel.revalidate();
            UpcomingPanel.revalidate();
        }
    }

    private void ShowDialogMessage() throws ParseException {

        JPanel dialogPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                this.setOpaque(false);
                super.paintComponent(g);
                Dimension arcs = new Dimension(10, 10);
                int width = getWidth();
                int height = getHeight();
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height); // paint background
            }
        };

        dialogPanel.setBounds(300, 250, 500, 300);
        dialogPanel.setForeground(Color.WHITE);

        JLabel cardTitleLabel = new JLabel("Card title");
        cardTitleLabel.setBounds(50, 20, 100, 40);
        cardTitleLabel.setHorizontalAlignment(JLabel.LEFT);
        cardTitleLabel.setFont(f.MontserratMedium.deriveFont(14f));
        dialogPanel.add(cardTitleLabel);

        JComboBox comboBox = new JComboBox(Priority.values());
        comboBox.setFont(f.MontserratMedium.deriveFont(14f));

        comboBox.setSelectedItem(Priority.General);
        comboBox.setBounds(160, 150, 300, 40);
        comboBox.setBackground(Color.WHITE);
        dialogPanel.add(comboBox);

        JPanel backgroundPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                this.setOpaque(false);
                super.paintComponent(g);
                Dimension arcs = new Dimension(10, 10);
                int width = getWidth();
                int height = getHeight();
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height); // paint background
            }
        };

        backgroundPanel.setForeground(new Color(115, 179, 189, 150));
        backgroundPanel.setOpaque(false);
        backgroundPanel.setBounds(0, 0, 1100, 800);
        backgroundPanel.add(dialogPanel);
        MainPane.add(backgroundPanel, 5, 0);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setBounds(50, 240, 400, 40);

        JTextField input = new JTextField(20);
        JFormattedTextField inputTime = new JFormattedTextField(new MaskFormatter("##:##"));

        inputTime.setBounds(50, 150, 80, 40);
        inputTime.setHorizontalAlignment(SwingConstants.CENTER);
        inputTime.setFont(f.MontserratSemiBold.deriveFont(14f));

        dialogPanel.add(inputTime);

        RoundedButton btnCreateCard = new RoundedButton("Add card", Color.WHITE);
        btnCreateCard.setBackground(f.LightGreen);
        btnCreateCard.addActionListener(e -> {
            if (!input.getText().equals("") && !inputTime.getText().equals("")) {
                TodoCard t = new TodoCard(input.getText(), inputTime.getText(), false,
                        (Priority) comboBox.getSelectedItem(), this);
                CardsArray.add(t);
                MainPane.remove(backgroundPanel);
                this.revalidate();
                this.repaint();
                UpdateBodyPanel();
            }
        });

        RoundedButton btnCancel = new RoundedButton("Cancel", Color.WHITE);
        btnCancel.setBackground(f.Red);
        btnCancel.addActionListener(e -> {
            MainPane.remove(backgroundPanel);
            this.revalidate();
            this.repaint();
            UpdateBodyPanel();
        });

        buttonsPanel.add(btnCreateCard);
        buttonsPanel.add(btnCancel);

        dialogPanel.add(buttonsPanel);
        dialogPanel.setFocusable(true);

        JLabel TimeAndPriorityLabel = new JLabel("Estimated end time and Priority");
        TimeAndPriorityLabel.setBounds(50, 110, 300, 40);
        TimeAndPriorityLabel.setHorizontalAlignment(JLabel.LEFT);
        TimeAndPriorityLabel.setFont(f.MontserratMedium.deriveFont(14f));
        dialogPanel.add(TimeAndPriorityLabel);

        Action enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCreateCard.doClick();
            }
        };

        input.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "Enter");
        input.getActionMap().put("Enter", enterAction);
        input.setBounds(50, 60, 400, 40);
        input.setEnabled(true);
        input.setEditable(true);
        input.setFocusable(true);
        input.setForeground(Color.BLACK);
        dialogPanel.add(input);
        input.setFocusable(true);
        input.requestFocus();
        input.setFont(f.MontserratMedium.deriveFont(14f));

        this.revalidate();
        this.repaint();
    }

    public void RemoveCard(TodoCard card) {
        CardsArray.remove(card);
    }
}
