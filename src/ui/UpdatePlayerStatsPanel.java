package ui;

import delegates.LeagueDelegate;
import models.PlayerStats;
import util.StringFormatting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePlayerStatsPanel extends ActionPanel {
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
        StringFormatting formatter = new StringFormatting();

        // Show all available tuples
        PlayerStats[] allRawPlayerTuples = delegate.showPlayerStats();
        actionPanel.add(new JLabel("Available  tuples:"));
        JLabel players = new JLabel(formatter.formatPlayerStats(allRawPlayerTuples));
        actionPanel.add(players);

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));

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

        inputPanel.add(new JLabel("Select Player ID to update:"));
        inputPanel.add(playerIDField);
        inputPanel.add(new JLabel("Updated Player Name:"));
        inputPanel.add(playerNameField);
        inputPanel.add(new JLabel("Updated Champion ID:"));
        inputPanel.add(champIDField);
        inputPanel.add(new JLabel("Updated Champion Name:"));
        inputPanel.add(championNameField);
        inputPanel.add(new JLabel("Updated Mana Points:"));
        inputPanel.add(manaPointsField);
        inputPanel.add(new JLabel("Updated Health Points:"));
        inputPanel.add(healthPointsField);
        inputPanel.add(new JLabel("Updated Creep Score:"));
        inputPanel.add(creepScoreField);
        inputPanel.add(new JLabel("Updated Kills:"));
        inputPanel.add(killsField);
        inputPanel.add(new JLabel("Updated Rank:"));
        inputPanel.add(rankField);
        inputPanel.add(new JLabel("Updated Map ID:"));
        inputPanel.add(mapIDField);


        JButton updatePlayerStatsButton = new JButton("Update Player Stats");
        updatePlayerStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if field is empty first
                if (playerIDField.getText().isEmpty()) {
                    resultLabel.setText("Error: Please select a PlayerID to update");
                    return;
                }

                // Check formatting dynamically, in case some fields are empty
//                try {
                int playerID = Integer.parseInt(playerIDField.getText());
                String playerName = playerNameField.getText();
                if (playerNameField.getText().isEmpty()) {
                    playerName = null;
                }
                int champID = -1;
                if (!champIDField.getText().isEmpty()) {
                    champID = Integer.parseInt(champIDField.getText());
                }
                String championName = championNameField.getText();
                if (championNameField.getText().isEmpty()) {
                    championName = null;
                }
                int manaPoints = -1;
                if (!manaPointsField.getText().isEmpty()) {
                    manaPoints = Integer.parseInt(manaPointsField.getText());
                }
                int healthPoints = -1;
                if (!healthPointsField.getText().isEmpty()) {
                    healthPoints = Integer.parseInt(healthPointsField.getText());
                }
                int creepScore = -1;
                if (!creepScoreField.getText().isEmpty()) {
                    creepScore = Integer.parseInt(creepScoreField.getText());
                }
                int kills = -1;
                if (!killsField.getText().isEmpty()) {
                    kills = Integer.parseInt(killsField.getText());
                }
                String rank = rankField.getText();
                if (rankField.getText().isEmpty()) {
                    rank = null;
                }
                int mapID = -1;
                if (!mapIDField.getText().isEmpty()) {
                    mapID = Integer.parseInt(mapIDField.getText());
                }

                PlayerStats playerStats = new PlayerStats(playerID, playerName, champID, championName,
                        manaPoints, healthPoints, creepScore, kills, rank, mapID);
                String result = delegate.updatePlayerStatsWithID(playerStats);
                resultLabel.setText(result);
//                } catch (NumberFormatException error) {
//                    resultLabel.setText("Error: Invalid format in one or more fields");
//                }
                PlayerStats[] allRawPlayerTuples = delegate.showPlayerStats();
                players.setText(formatter.formatPlayerStats(allRawPlayerTuples));
            }

        });

        inputPanel.add(updatePlayerStatsButton);
        actionPanel.add(inputPanel);

        return actionPanel;
    }


}
