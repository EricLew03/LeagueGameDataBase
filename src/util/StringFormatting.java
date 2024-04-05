package util;

import models.PlayerStats;

public class StringFormatting {
    public String formatPlayerStats(PlayerStats[] playerStatsArray) {
        StringBuilder result = new StringBuilder();

        result.append("<html>" +
                "<p style=\"width:400px; height:500px\">");

        for (PlayerStats playerStats : playerStatsArray) {
            result.append("Player ID: ").append(playerStats.getPlayerID()).append(", ");
            result.append("Player Name: ").append(playerStats.getPlayerName()).append(", ");
            result.append("Champion ID: ").append(playerStats.getChampID()).append(", ");
            result.append("Champion Name: ").append(playerStats.getChampionName()).append(", ");
            result.append("Mana Points: ").append(playerStats.getManaPoints()).append(", ");
            result.append("Health Points: ").append(playerStats.getHealthPoints()).append(", ");
            result.append("Creep Score: ").append(playerStats.getCreepScore()).append(", ");
            result.append("Kills: ").append(playerStats.getKills()).append(", ");
            result.append("Rank: ").append(playerStats.getRank()).append(", ");
            result.append("Map ID: ").append(playerStats.getMapID()).append("<br>");
        }

        result.append("</p></html>");

        return result.toString();
    }
}
