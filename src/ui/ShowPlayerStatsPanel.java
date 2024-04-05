package ui;

import delegates.LeagueDelegate;
import models.PlayerStats;

import javax.swing.*;
import java.awt.*;

public class ShowPlayerStatsPanel extends ActionPanel {
    @Override
    public JPanel renderActionPanel(LeagueDelegate delegate) {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());


        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(new JLabel(formatPlayerStats(delegate.showPlayerStats())));


        actionPanel.add(centerPanel);

        return actionPanel;
    }

    public String formatPlayerStats(PlayerStats[] playerStatsArray) {
        StringBuilder result = new StringBuilder();


        result.append("<html><h3 style='text-align: center;'>Player Stats Relation:</h3>");
        result.append("<table style='margin: 0 auto; border-collapse: collapse; text-align: center;' border='1' cellspacing='0' cellpadding='5'>");
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
