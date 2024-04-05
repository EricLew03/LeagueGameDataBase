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


        List<String> tableNames = delegate.showTable();


        JList<String> tableList = new JList<>(tableNames.toArray(new String[0]));


        tableList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedTableName = tableList.getSelectedValue();
                    if (selectedTableName != null && selectedTableName.equals("PLAYERSTATS")) {
                        List<String> names = performActionForPlayerStats(panel);


                        List<List<String>> result = delegate.playerProjection(names);



                        StringBuilder htmlContent = new StringBuilder("<html><table style=\"width:500px; border: 1px solid black; font-size: 14px;\">");


                        htmlContent.append("<tr>");
                        for (int i = 0; i < names.size(); i++) {
                            htmlContent.append("<th>").append(names.get(i)).append("</th>");
                        }
                        htmlContent.append("</tr>");


                        for (List<String> row : result) {
                            htmlContent.append("<tr>");
                            for (String value : row) {
                                htmlContent.append("<td>").append(value).append("</td>");
                                System.out.print(value + "\t");
                            }
                            htmlContent.append("</tr>");
                            System.out.println();
                        }

                        htmlContent.append("</table></html>");
                        resultLabel.setText(htmlContent.toString());

                    }
                }
            }

        });


        JScrollPane scrollPane = new JScrollPane(tableList);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }


    private List<String> performActionForPlayerStats(JPanel parentPanel) {
        JDialog dialog = new JDialog((Frame)null, "Select Names", true);
        JPanel selectNamesPanel = new JPanel(new GridLayout(0, 1));

        String[] names = {
                "playerID", "playerName", "champID", "championName",
                "manaPoints", "healthPoints", "creepScore", "kills",
                "rank", "mapID"
        };

        List<String> selectedNames = new ArrayList<>();


        for (String name : names) {
            JCheckBox checkBox = new JCheckBox(name);
            selectNamesPanel.add(checkBox);
        }

        JButton doneButton = new JButton("Done");

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Find selected checkboxes and get their text (names)
                Component[] components = selectNamesPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) component;
                        if (checkBox.isSelected()) {
                            selectedNames.add(checkBox.getText());
                        }
                    }
                }

                dialog.dispose();
            }
        });
        selectNamesPanel.add(doneButton);

        dialog.add(selectNamesPanel);


        dialog.setPreferredSize(new Dimension(300, 400));

        dialog.pack();
        dialog.setLocationRelativeTo(parentPanel);
        dialog.setVisible(true);


        return selectedNames;
    }
}
