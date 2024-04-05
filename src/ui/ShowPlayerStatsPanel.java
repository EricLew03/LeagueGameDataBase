package ui;

import delegates.LeagueDelegate;
import models.PlayerStats;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowPlayerStatsPanel extends ActionPanel {
    @Override
    public JPanel renderActionPanel(LeagueDelegate delegate) {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());


        actionPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resultLabel.setText(formatPlayerStats(delegate.showPlayerStats()));
            }
        });

        return actionPanel;
    }

    public String formatPlayerStats(PlayerStats[] playerStatsArray) {
        StringBuilder result = new StringBuilder();

        result.append("<html><h3 style='text-align: center;'>Owns Item relation:</h3>");
        result.append("<table style='margin: 0 auto; border-collapse: collapse; text-align: center; font-size: 12px;' border='1' cellspacing='0' cellpadding='2'>");
        result.append("<tr>");
        result.append("<th>Player ID</th>");
        result.append("<th>Player Name</th>");
        result.append("<th>Champion ID</th>");
        result.append("<th>Champion Name</th>");
        result.append("<th>Mana Points</th>");
        result.append("<th>Health Points</th>");
        result.append("<th>Creep Score</th>");
        result.append("<th>Kills</th>");
        result.append("<th>Rank</th>");
        result.append("<th>Map ID</th>");
        result.append("</tr>");

        for (PlayerStats playerStats : playerStatsArray) {
            result.append("<tr>");
            result.append("<td>").append(playerStats.getPlayerID()).append("</td>");
            result.append("<td>").append(playerStats.getPlayerName()).append("</td>");
            result.append("<td>").append(playerStats.getChampID()).append("</td>");
            result.append("<td>").append(playerStats.getChampionName()).append("</td>");
            result.append("<td>").append(playerStats.getManaPoints()).append("</td>");
            result.append("<td>").append(playerStats.getHealthPoints()).append("</td>");
            result.append("<td>").append(playerStats.getCreepScore()).append("</td>");
            result.append("<td>").append(playerStats.getKills()).append("</td>");
            result.append("<td>").append(playerStats.getRank()).append("</td>");
            result.append("<td>").append(playerStats.getMapID()).append("</td>");
            result.append("</tr>");
        }

        result.append("</table></html>");

        return result.toString();
    }

}

