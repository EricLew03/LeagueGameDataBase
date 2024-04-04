package ui;

import delegates.LeagueDelegate;

import javax.swing.*;

// Class to represent default action panel that defines its own render behaviour
public interface ActionPanel {
    void setResultLabel(JLabel resultLabel);

    JPanel renderActionPanel(LeagueDelegate delegate);
}
