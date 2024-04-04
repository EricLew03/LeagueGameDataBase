package ui;

import delegates.LeagueDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame implements ActionListener {
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private static final int INVALID_INPUT = Integer.MIN_VALUE;
    private static final int EMPTY_INPUT = 0;

    private LeagueDelegate delegate;

    // UI components
    private JMenuBar menuBar;
    private JMenu menu;
    private String currentMenu;
    private JLabel menuLabel;
    private JPanel contentPane;
    private JPanel actionPanel;
    private JPanel resultPanel;

    private String result;
    private JLabel resultLabel;
    JButton divisionButton;

    JMenuItem[] menuItems = {
            new JMenuItem("Insert playerStats"),
            new JMenuItem("Delete playerStats"),
            new JMenuItem("Update playerStats"),
            new JMenuItem("Show playerStats"),
            new JMenuItem("Insert item"),
            new JMenuItem("Delete item"),
            new JMenuItem("Show item"),
            new JMenuItem("PlayerStats selection"),
            new JMenuItem("PlayerStats projection"),
            new JMenuItem("Insert playerEcon"),
            new JMenuItem("Delete playerEcon"),
            new JMenuItem("Show playerEcon"),
            new JMenuItem("Join"),
            new JMenuItem("Aggregate"),
            new JMenuItem("AggregateHaving"),
            new JMenuItem("NestedAggregated"),
            new JMenuItem("Division")
    };

    public HomePage() {
        super("League of Legends Query");
    }

    public void showFrame(LeagueDelegate delegate) {
        // General Instantiation
        this.delegate = delegate;

        this.result = "Result will display here";
        resultLabel = new JLabel(result);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.currentMenu = menuItems[0].getText(); // set default chosen menu location
        menuLabel = new JLabel("Selected Action: " + this.currentMenu);

        // Menubar instantiation
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menu = new JMenu("Actions");
        menuBar.add(menu);

        // Listener for menu location. Updates the text label and currentMenu location
        ActionListener menuActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosenMenu = e.getActionCommand();
                menuLabel.setText("Selected Action: " + chosenMenu);
                currentMenu = chosenMenu;
                updateUI(); // update action content based on what was selected
            }
        };
        for (JMenuItem menuItem : menuItems) {
            menuItem.addActionListener(menuActionListener);
            menu.add(menuItem);
        }

        // Action-specific UI instantiation
//        divisionButton = new JButton("Division");
//        divisionButton.addActionListener(this);

        // content adding and packing
        setSize(400, 300);
        contentPane = new JPanel(); // Main overall content pane
        contentPane.setLayout(new GridLayout(1, 2));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // render default action content based on menu selection
        this.actionPanel = renderActionPanel(currentMenu);
        this.contentPane.add(this.actionPanel);

        resultPanel = new JPanel(); // Panel only for displaying results
        resultPanel.setLayout(new BorderLayout());
        resultPanel.add(menuLabel, BorderLayout.NORTH);
        resultPanel.add(resultLabel, BorderLayout.CENTER);

        contentPane.add(actionPanel);
        contentPane.add(resultPanel);
        setContentPane(contentPane);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void updateUI() {
        this.contentPane.removeAll();
        JPanel newActionPanel = renderActionPanel(this.currentMenu);
        this.actionPanel = newActionPanel;
        this.contentPane.add(this.actionPanel);
        contentPane.add(resultPanel);
    }

    private JPanel renderActionPanel(String currentMenu) {
        JPanel newActionPanel = new JPanel();
        newActionPanel.setLayout(new FlowLayout());

        if (currentMenu.equals("Division")) {
            divisionButton = new JButton("Division");
            divisionButton.addActionListener(this);
            newActionPanel.add(divisionButton);
        } else {
            divisionButton = new JButton("not division");
            newActionPanel.add(divisionButton);
        }

        return newActionPanel;
    }

    // template from LoginWindow.java
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == divisionButton) {
            result = delegate.division();
            resultLabel.setText(result);
        } else {
            // different button
        }
    }



}