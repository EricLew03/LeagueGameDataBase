package ui;

import delegates.LeagueDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AggregateNestedPanel extends ActionPanel {

    @Override
    public JPanel renderActionPanel(LeagueDelegate delegate) {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());


        JLabel descriptorLabel = new JLabel("<html><p style=\"width:300px\">" + "Finds the player name " +
                "who has the highest average cost of items owned" + "</p></html>");

        JButton aggregateNestedButton = new JButton("Find");
        aggregateNestedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = delegate.nestedAggregate();
                resultLabel.setText("<html><p style=\"width:300px\">" + result + "</p></html>");
            }
        });

        actionPanel.add(descriptorLabel);
        actionPanel.add(aggregateNestedButton);

        return actionPanel;
    }
}
