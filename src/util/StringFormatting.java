package util;

import models.PlayerStats;

public class StringFormatting {
    public String formatPlayerStats(PlayerStats[] playerStatsArray) {
            StringBuilder result = new StringBuilder();

            result.append("<html><h3>All Available tuples to choose:</h3>");
            result.append("<table border=\"1\">");
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

            result.append("</table>");
            result.append("</html>");

            return result.toString();
        }

    }

