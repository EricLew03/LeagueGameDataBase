package ui;

import delegates.LeagueDelegate;
import util.InputSanitizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectPlayerStatsPanel extends ActionPanel {
    private JTextField queryField;
    private String query;


    @Override
    public JPanel renderActionPanel(LeagueDelegate delegate) {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(0, 1));


        queryField = new JTextField(12);

        actionPanel.add(new JLabel("<html>Enter Player Selection Criteria; Leave blank for no criteria<br>" +
                "Attributes: playerID, playerName, champID, championName, manaPoints, healthPoints <br>" +
                "Example: rank = 'Diamond' AND healthPoints = 100 OR manaPoints = 0" +
                "</html>"));
        actionPanel.add(queryField);

        JButton deletePlayerStatsButton = new JButton("Run Query");
        deletePlayerStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (queryField.getText().isEmpty()) {
                    query = "";
                } else {
                    query = InputSanitizer.sanitizeInput(queryField.getText());
                }

                String result = delegate.playerSelection(query);
                resultLabel.setText(result);
            }

        });

        actionPanel.add(deletePlayerStatsButton);

        return actionPanel;
    }
}
