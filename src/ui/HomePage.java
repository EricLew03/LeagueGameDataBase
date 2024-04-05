package ui;

import delegates.LeagueDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private LeagueDelegate delegate;

    // UI components
    private JMenuBar menuBar;
    private JMenu menu;
    private String currentMenu;
    private JLabel menuLabel;
    private JPanel contentPane;
    private JPanel actionPanel;
    private JPanel resultPanel;

    private JLabel resultLabel;

    final String INSERT_PLAYER_STATS_TEXT = "Insert playerStats";
    final String DELETE_PLAYER_STATS_TEXT = "Delete playerStats";
    final String UPDATE_PLAYER_STATS_TEXT = "Update playerStats";
    final String SELECT_PLAYER_STATS_TEXT = "Select playerStats";
    final String PROJECT_TEXT = "Project";
    final String JOIN_TEXT = "Join";
    final String AGGREGATE_TEXT = "Aggregate";
    final String AGGREGATE_HAVING_TEXT = "Aggregate with Having";
    final String NESTED_AGGREGATED_TEXT = "Nested Aggregate";
    final String DIVISION_ACTION_TEXT = "Division";

    JMenuItem[] menuItems = {
            new JMenuItem(INSERT_PLAYER_STATS_TEXT),
            new JMenuItem(DELETE_PLAYER_STATS_TEXT),
            new JMenuItem(UPDATE_PLAYER_STATS_TEXT),
            new JMenuItem(SELECT_PLAYER_STATS_TEXT),
            new JMenuItem(PROJECT_TEXT),
            new JMenuItem(JOIN_TEXT),
            new JMenuItem(AGGREGATE_TEXT),
            new JMenuItem(AGGREGATE_HAVING_TEXT),
            new JMenuItem(NESTED_AGGREGATED_TEXT),
            new JMenuItem(DIVISION_ACTION_TEXT)
    };

    public HomePage() {
        super("League of Legends Query");
    }

    public void showFrame(LeagueDelegate delegate) {
        // General Instantiation
        this.delegate = delegate;

        resultLabel = new JLabel("Result will display here");
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
                resultLabel.setText("Result will display here");
            }
        };
        for (JMenuItem menuItem : menuItems) {
            menuItem.addActionListener(menuActionListener);
            menu.add(menuItem);
        }

        // content adding and packing
        contentPane = new JPanel(); // Main overall content pane
        contentPane.setSize(400, 300);
        contentPane.setLayout(new GridLayout(1, 2));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resultPanel = new JPanel(); // Panel only for displaying results
        resultPanel.setLayout(new BorderLayout());
        resultPanel.add(menuLabel, BorderLayout.NORTH);
        resultPanel.add(resultLabel, BorderLayout.CENTER);

        updateUI();

        contentPane.add(actionPanel);
        contentPane.add(resultPanel);
        setContentPane(contentPane);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void updateUI() {
        this.contentPane.removeAll();
        this.actionPanel = renderActionPanel(this.currentMenu);
        this.contentPane.add(this.actionPanel);
        contentPane.add(resultPanel);
    }

    private JPanel renderActionPanel(String currentMenu) {
        ActionPanel newActionPanel;

        switch (currentMenu) {
            case INSERT_PLAYER_STATS_TEXT:
                newActionPanel = new InsertPlayerStatsPanel();
                break;
            case DELETE_PLAYER_STATS_TEXT:
                newActionPanel = new DeletePlayerStatsPanel();
                break;
            case UPDATE_PLAYER_STATS_TEXT:
                newActionPanel = new UpdatePlayerStatsPanel();
                break;
//            case SELECT_PLAYER_STATS_TEXT:
//                newActionPanel = new SelectPlayerStatsPanel();
//                break;
//            case PROJECT_TEXT:
//                newActionPanel = new ProjectPanel();
//                break;
            case JOIN_TEXT:
                newActionPanel = new JoinPanel();
                break;
            case AGGREGATE_TEXT:
                newActionPanel = new AggregateGroupByPanel();
                break;
            case AGGREGATE_HAVING_TEXT:
                newActionPanel = new AggregateHavingPanel();
                break;
            case NESTED_AGGREGATED_TEXT:
                newActionPanel = new AggregateNestedPanel();
                break;
            case DIVISION_ACTION_TEXT:
                newActionPanel = new DivisionPanel();
                break;
            default:
                newActionPanel = new InsertPlayerStatsPanel();
                break;
        }

        newActionPanel.setResultLabel(this.resultLabel);
        return newActionPanel.renderActionPanel(this.delegate);
    }
}