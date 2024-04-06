package ui;

import delegates.LeagueDelegate;
import util.InputSanitizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePlayerStatsPanel extends ActionPanel {
    private JTextField playerIDField;


    @Override
    public JPanel renderActionPanel(LeagueDelegate delegate) {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        playerIDField = new JTextField(12);

        actionPanel.add(new JLabel("Player ID to Delete:"));
        actionPanel.add(playerIDField);

        JButton deletePlayerStatsButton = new JButton("Delete Player Stat");
        deletePlayerStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if field is empty first
                if (playerIDField.getText().isEmpty()) {
                    resultLabel.setText("Error: Please fill in all fields");
                } else if (!InputSanitizer.checkNumbersOnly(playerIDField.getText())) {
                    resultLabel.setText("Error: Please only enter digits");
                } else {
                    int playerID = Integer.parseInt(playerIDField.getText());
                    String rowsChanged = delegate.deletePlayerStats(playerID);
                    resultLabel.setText("Rows deleted: " + rowsChanged);
                }
            }

        });

        actionPanel.add(deletePlayerStatsButton);

        return actionPanel;
    }
}
