package ui;

import delegates.LeagueDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame implements ActionListener {
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private static final int INVALID_INPUT = Integer.MIN_VALUE;
    private static final int EMPTY_INPUT = 0;

    private LeagueDelegate delegate;

    // UI components
    private String result;
    private JLabel resultLabel;
    JButton divisionButton;

    public HomePage() {
        super("HomePage");
    }

    public void showFrame(LeagueDelegate delegate) {
        this.delegate = delegate;
        this.result = "Welcome";

        JLabel welcomeLabel = new JLabel("Welcome to the Homepage");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        resultLabel = new JLabel(result);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        divisionButton = new JButton("Division");

        // Set up button action listeners
        divisionButton.addActionListener(this);

        // content adding
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.add(welcomeLabel, BorderLayout.CENTER);
        contentPane.add(resultLabel, BorderLayout.AFTER_LAST_LINE);
        contentPane.add(divisionButton, BorderLayout.BEFORE_FIRST_LINE);
        setContentPane(contentPane);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // template from LoginWindow.java
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == divisionButton) {
            result = delegate.division();
            resultLabel.setText(result);
        } else {
            // different button
        }
    }

}