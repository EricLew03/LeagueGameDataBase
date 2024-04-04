package ui;

import delegates.LeagueDelegate;

import javax.swing.*;

// Abstract class to represent default action panel that defines its own render behavior
public abstract class ActionPanel extends JPanel {
    protected JLabel resultLabel;

    public void setResultLabel(JLabel resultLabel) {
        this.resultLabel = resultLabel;
    }
    
    public abstract JPanel renderActionPanel(LeagueDelegate delegate);
}
