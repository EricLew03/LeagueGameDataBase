package ui;

import delegates.LeagueDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AggregateHavingPanel extends ActionPanel {

    @Override
    public JPanel renderActionPanel(LeagueDelegate delegate) {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());


        JLabel descriptorLabel = new JLabel("<html><p style=\"width:300px\">" + "Query groups all items by " +
                "name and finds how many players own the item, if they are owned by at least 2 players" + "</p></html>");

        JButton aggregateGroupByButton = new JButton("Find");
        aggregateGroupByButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = delegate.aggregateHaving();
                resultLabel.setText("<html><p style=\"width:300px\">" + result + "</p></html>");
            }
        });

        actionPanel.add(descriptorLabel);
        actionPanel.add(aggregateGroupByButton);

        return actionPanel;
    }
}
