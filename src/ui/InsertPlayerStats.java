package ui;

import delegates.LeagueDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertPlayerStats extends ActionPanel {

    @Override
    public JPanel renderActionPanel(LeagueDelegate delegate) {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());

        JButton divisionButton = new JButton("InsertPlayerStats");
        divisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                delegate.insertPlayerStats();
                resultLabel.setText("InsertPlayerStats");
            }
        });

        actionPanel.add(divisionButton);

        return actionPanel;
    }
}
