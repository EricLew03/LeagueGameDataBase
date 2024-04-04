package ui;

import delegates.LeagueDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DivisionPanel extends ActionPanel {

    @Override
    public JPanel renderActionPanel(LeagueDelegate delegate) {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());

        JLabel descriptorLabel = new JLabel("Query finds all items that are owned by every player");
        JButton divisionButton = new JButton("Find");
        divisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = delegate.division();
                resultLabel.setText(result);
            }
        });

        actionPanel.add(descriptorLabel);
        actionPanel.add(divisionButton);

        return actionPanel;
    }
}
