package ui;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    public HomePage() {
        super("HomePage");
    }

    public void showFrame() {
        JLabel welcomeLabel = new JLabel("Welcome to the Homepage");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.add(welcomeLabel, BorderLayout.CENTER);

        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}