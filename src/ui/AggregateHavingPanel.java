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
                "name if they are owned by at least 2 players, showing their name and price" + "</p></html>");

        JButton aggregateHavingButton = new JButton("Find");
        aggregateHavingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = delegate.aggregateHaving();
                resultLabel.setText("<html><p style=\"width:300px\">" + result + "</p></html>");
            }
        });

        actionPanel.add(descriptorLabel);
        actionPanel.add(aggregateHavingButton);

        return actionPanel;
    }
}
