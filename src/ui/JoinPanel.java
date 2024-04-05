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

        mapIDField = new JTextField(12);

        actionPanel.add(new JLabel("<html><p style=\"width:300px\">Enter Map ID to find all player's names, champions, creepscores, kills and ranks who" +
                " have destroyed at least one turret structure</p></html>"));
        actionPanel.add(new JLabel("Map ID:"));
        actionPanel.add(mapIDField);

        JButton joinBUtton = new JButton("Find Players");
        joinBUtton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if field is empty first
                if (mapIDField.getText().isEmpty()) {
                    resultLabel.setText("Error: Please fill in all fields");
                }

                int mapID = Integer.parseInt(mapIDField.getText());
                String result = delegate.joinPlayerTurret(mapID);

                resultLabel.setText("<html><p style=\"width:300px\">" + result + "</p></html>");


            }

        });

        actionPanel.add(joinBUtton);

        return actionPanel;
    }
}
