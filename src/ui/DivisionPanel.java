package ui;

import delegates.LeagueDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DivisionPanel implements ActionPanel {
    @Override
    public JPanel renderActionPanel(LeagueDelegate delegate) {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());

        JButton divisionButton = new JButton("Division");
        divisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                delegate.division();
                System.out.println("division");
            }
        });

        actionPanel.add(divisionButton);

        return actionPanel;
    }
}
