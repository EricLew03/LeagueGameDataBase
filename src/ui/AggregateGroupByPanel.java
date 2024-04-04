package ui;

import delegates.LeagueDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AggregateGroupByPanel extends ActionPanel {

    @Override
    public JPanel renderActionPanel(LeagueDelegate delegate) {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());


        JLabel descriptorLabel = new JLabel("Query groups all items by name and finds how many players own the item");
        JButton aggregateGroupByButton = new JButton("Find");
        aggregateGroupByButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                delegate.aggregate();
                String result = delegate.aggregate();
                resultLabel.setText("<html><p style=\"width:300px\">" + result + "</p></html>");
            }
        });

        actionPanel.add(descriptorLabel);
        actionPanel.add(aggregateGroupByButton);

        return actionPanel;
    }
}
