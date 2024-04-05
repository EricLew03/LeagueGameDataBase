package ui;

import delegates.LeagueDelegate;
import models.OwnsItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowOwnsItemPanel extends ActionPanel {
    @Override
    public JPanel renderActionPanel(LeagueDelegate delegate) {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());

        actionPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resultLabel.setText(formatOwnsItem(delegate.showOwnsItem()));
            }
        });

        return actionPanel;
    }

    public String formatOwnsItem(OwnsItem[] ownsItemsArray) {
        StringBuilder result = new StringBuilder();

        result.append("<html><h3 style='text-align: center;'>Owns Item relation:</h3>");
        result.append("<table style='margin: 0 auto; border-collapse: collapse; text-align: center; font-size: 16px;' border='1' cellspacing='0' cellpadding='5'>");
        result.append("<tr>");
        result.append("<th>Player ID</th>");
        result.append("<th>Item Name</th>");
        result.append("<th>mr</th>");
        result.append("<th>ad</th>");
        result.append("<th>ap</th>");
        result.append("<th>armor</th>");
        result.append("<th>cost</th>");
        result.append("</tr>");

        for (OwnsItem ownsItem : ownsItemsArray) {
            result.append("<tr>");
            result.append("<td>").append(ownsItem.getPlayerID()).append("</td>");
            result.append("<td>").append(ownsItem.getName()).append("</td>");
            result.append("<td>").append(ownsItem.getMr()).append("</td>");
            result.append("<td>").append(ownsItem.getAd()).append("</td>");
            result.append("<td>").append(ownsItem.getAp()).append("</td>");
            result.append("<td>").append(ownsItem.getArmor()).append("</td>");
            result.append("<td>").append(ownsItem.getCost()).append("</td>");
            result.append("</tr>");
        }

        result.append("</table></html>");

        return result.toString();
    }

}
