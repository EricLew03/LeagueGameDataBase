package ui;

import delegates.LeagueDelegate;
import models.PlayerStats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertPlayerStatsPanel extends ActionPanel {
    private JTextField playerIDField;
    private JTextField playerNameField;
    private JTextField champIDField;
    private JTextField championNameField;
    private JTextField manaPointsField;
    private JTextField healthPointsField;
    private JTextField creepScoreField;
    private JTextField killsField;
    private JTextField rankField;
    private JTextField mapIDField;

    @Override
    public JPanel renderActionPanel(LeagueDelegate delegate) {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(0, 1));

        playerIDField = new JTextField(12);
        playerNameField = new JTextField(12);
        champIDField = new JTextField(12);
        championNameField = new JTextField(12);
        manaPointsField = new JTextField(12);
        healthPointsField = new JTextField(12);
        creepScoreField = new JTextField(12);
        killsField = new JTextField(12);
        rankField = new JTextField(12);
        mapIDField = new JTextField(12);

        actionPanel.add(new JLabel("Player ID:"));
        actionPanel.add(playerIDField);
        actionPanel.add(new JLabel("Player Name:"));
        actionPanel.add(playerNameField);
        actionPanel.add(new JLabel("Champion ID:"));
        actionPanel.add(champIDField);
        actionPanel.add(new JLabel("Champion Name:"));
        actionPanel.add(championNameField);
        actionPanel.add(new JLabel("Mana Points:"));
        actionPanel.add(manaPointsField);
        actionPanel.add(new JLabel("Health Points:"));
        actionPanel.add(healthPointsField);
        actionPanel.add(new JLabel("Creep Score:"));
        actionPanel.add(creepScoreField);
        actionPanel.add(new JLabel("Kills:"));
        actionPanel.add(killsField);
        actionPanel.add(new JLabel("Rank:"));
        actionPanel.add(rankField);
        actionPanel.add(new JLabel("Map ID:"));
        actionPanel.add(mapIDField);


        JButton insertPLayerStatsButton = new JButton("Insert Player Stats");
        insertPLayerStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if field is empty first
                if (playerIDField.getText().isEmpty() || playerNameField.getText().isEmpty() ||
                        champIDField.getText().isEmpty() || championNameField.getText().isEmpty() ||
                        manaPointsField.getText().isEmpty() || healthPointsField.getText().isEmpty() ||
                        creepScoreField.getText().isEmpty() || killsField.getText().isEmpty() ||
                        rankField.getText().isEmpty() || mapIDField.getText().isEmpty()) {
                    resultLabel.setText("Error: Please fill in all fields");
                }

                int playerID = Integer.parseInt(playerIDField.getText());
                String playerName = playerNameField.getText();
                int champID = Integer.parseInt(champIDField.getText());
                String championName = championNameField.getText();
                int manaPoints = Integer.parseInt(manaPointsField.getText());
                int healthPoints = Integer.parseInt(healthPointsField.getText());
                int creepScore = Integer.parseInt(creepScoreField.getText());
                int kills = Integer.parseInt(killsField.getText());
                String rank = rankField.getText();
                int mapID = Integer.parseInt(mapIDField.getText());

                PlayerStats playerStats = new PlayerStats(playerID, playerName, champID, championName,
                        manaPoints, healthPoints, creepScore, kills, rank, mapID);

                String rowsChanged = delegate.insertPlayerStats(playerStats);
                resultLabel.setText("Rows changed: " + rowsChanged);
//                resultLabel.setText("PlayerStats{" +
//                        "playerID=" + playerID +
//                        ", playerName='" + playerName + '\'' +
//                        ", champID=" + champID +
//                        ", championName='" + championName + '\'' +
//                        ", manaPoints=" + manaPoints +
//                        ", healthPoints=" + healthPoints +
//                        ", creepScore=" + creepScore +
//                        ", kills=" + kills +
//                        ", rank='" + rank + '\'' +
//                        ", mapID=" + mapID +
//                        '}');
            }

        });

        actionPanel.add(insertPLayerStatsButton);

        return actionPanel;
    }
}
