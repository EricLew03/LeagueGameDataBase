package ui;

import delegates.LeagueDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinPanel extends ActionPanel {
    private JTextField mapIDField;

    @Override
    public JPanel renderActionPanel(LeagueDelegate delegate) {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        mapIDField = new JTextField(20); // Increased size of text field

        // Set font size for labels
        Font labelFont = new Font("Arial", Font.PLAIN, 16); // Adjust font size as needed

        JLabel descriptionLabel = new JLabel("<html><p style=\"width:300px\">Enter Map ID to find all player's names, champions, creepscores, kills and ranks who" +
                " have destroyed at least one turret structure</p></html>");
        descriptionLabel.setFont(labelFont);
        actionPanel.add(descriptionLabel);

        JLabel mapIDLabel = new JLabel("Map ID:");
        mapIDLabel.setFont(labelFont);
        actionPanel.add(mapIDLabel);

        actionPanel.add(mapIDField);

        JButton joinButton = new JButton("Find Players");
        joinButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Adjust font size for button
        joinButton.setPreferredSize(new Dimension(150, 40)); // Increase button size
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if field is empty first
                if (mapIDField.getText().isEmpty()) {
                    resultLabel.setText("Error: Please fill in all fields");
                } else {
                    try {
                        int mapID = Integer.parseInt(mapIDField.getText());
                        String result = delegate.joinPlayerTurret(mapID);
                        resultLabel.setText("<html><p style=\"width:300px\">" + result + "</p></html>");
                    } catch (NumberFormatException ex) {
                        resultLabel.setText("Error: Map ID must be a valid integer");
                    }
                }
            }
        });

        actionPanel.add(joinButton);

        return actionPanel;
    }
}
