package ui;

import delegates.LeagueDelegate;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProjectPanel extends ActionPanel {
    @Override
    public JPanel renderActionPanel(LeagueDelegate delegate) {
        JPanel panel = new JPanel(new BorderLayout());

        // Get the list of table names from the delegate
        List<String> tableNames = delegate.showTable();

        // Create a JList to display the table names
        JList<String> tableList = new JList<>(tableNames.toArray(new String[0]));

        // Add a list selection listener to handle selection events
        tableList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedTableName = tableList.getSelectedValue();
                    // Here you can check if the selected table name matches the specific one
                    if (selectedTableName != null && selectedTableName.equals("PLAYERSTATS")) {
                        // Perform your action only for the specific table name
                        performActionForSpecificTable(panel);
                    }
                }
            }
        });

        // Add the JList to a scroll pane for scrolling if needed
        JScrollPane scrollPane = new JScrollPane(tableList);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // Method to perform an action for the specific table name
    private void performActionForSpecificTable(JPanel parentPanel) {
        JPanel selectNamesPanel = new JPanel(new GridLayout(0, 1));

        String[] names = {
                "playerID", "playerName", "champID", "championName",
                "manaPoints", "healthPoints", "creepScore", "kills",
                "rank", "mapID"
        };

        // Create checkboxes for each name
        for (String name : names) {
            JCheckBox checkBox = new JCheckBox(name);
            selectNamesPanel.add(checkBox);
        }

        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Find selected checkboxes and get their text (names)
                List<String> selectedNames = new ArrayList<>();
                Component[] components = selectNamesPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) component;
                        if (checkBox.isSelected()) {
                            selectedNames.add(checkBox.getText());
                        }
                    }
                }
            }



        });
        selectNamesPanel.add(doneButton);

        parentPanel.removeAll();
        parentPanel.add(selectNamesPanel, BorderLayout.CENTER);
        parentPanel.revalidate();
        parentPanel.repaint();
    }


}
