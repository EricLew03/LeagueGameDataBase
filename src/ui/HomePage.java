package ui;

import delegates.LeagueDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
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

    private JLabel resultLabel;

    final String INSERT_PLAYER_STATS_TEXT = "Insert playerStats";
    final String DELETE_PLAYER_STATS_TEXT = "Delete playerStats";
    final String UPDATE_PLAYER_STATS_TEXT = "Update playerStats";
    final String SHOW_PLAYER_STATS_TEXT = "Show playerStats";
    final String INSERT_ITEM_TEXT = "Insert item";
    final String DELETE_ITEM_TEXT = "Delete item";
    final String SHOW_ITEM_TEXT = "Show item";
    final String PLAYERSTATS_SELECTION_TEXT = "PlayerStats selection";
    final String PLAYERSTATS_PROJECTION_TEXT = "PlayerStats projection";
    final String INSERT_PLAYER_ECON_TEXT = "Insert playerEcon";
    final String DELETE_PLAYER_ECON_TEXT = "Delete playerEcon";
    final String SHOW_PLAYER_ECON_TEXT = "Show playerEcon";
    final String JOIN_TEXT = "Join";
    final String AGGREGATE_TEXT = "Aggregate";
    final String AGGREGATE_HAVING_TEXT = "AggregateHaving";
    final String NESTED_AGGREGATED_TEXT = "NestedAggregated";
    final String DIVISION_ACTION_TEXT = "Division";

    JMenuItem[] menuItems = {
            new JMenuItem(INSERT_PLAYER_STATS_TEXT),
            new JMenuItem(DELETE_PLAYER_STATS_TEXT),
            new JMenuItem(UPDATE_PLAYER_STATS_TEXT),
            new JMenuItem(SHOW_PLAYER_STATS_TEXT),
            new JMenuItem(INSERT_ITEM_TEXT),
            new JMenuItem(DELETE_ITEM_TEXT),
            new JMenuItem(SHOW_ITEM_TEXT),
            new JMenuItem(PLAYERSTATS_SELECTION_TEXT),
            new JMenuItem(PLAYERSTATS_PROJECTION_TEXT),
            new JMenuItem(INSERT_PLAYER_ECON_TEXT),
            new JMenuItem(DELETE_PLAYER_ECON_TEXT),
            new JMenuItem(SHOW_PLAYER_ECON_TEXT),
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
//            case UPDATE_PLAYER_STATS_TEXT:
//                newActionPanel = new UpdatePlayerStats();
//                break;
//            case SHOW_PLAYER_STATS_TEXT:
//                newActionPanel = new ShowPlayerStats();
//                break;
//            case INSERT_ITEM_TEXT:
//                newActionPanel = new InsertItem();
//                break;
//            case DELETE_ITEM_TEXT:
//                newActionPanel = new DeleteItem();
//                break;
//            case SHOW_ITEM_TEXT:
//                newActionPanel = new ShowItem();
//                break;
//            case PLAYERSTATS_SELECTION_TEXT:
//                newActionPanel = new PlayerStatsSelection();
//                break;
//            case PLAYERSTATS_PROJECTION_TEXT:
//                newActionPanel = new PlayerStatsProjection();
//                break;
//            case INSERT_PLAYER_ECON_TEXT:
//                newActionPanel = new InsertPlayerEcon();
//                break;
//            case DELETE_PLAYER_ECON_TEXT:
//                newActionPanel = new DeletePlayerEcon();
//                break;
//            case SHOW_PLAYER_ECON_TEXT:
//                newActionPanel = new ShowPlayerEcon();
//                break;
//            case JOIN_TEXT:
//                newActionPanel = new Join();
//                break;
            case AGGREGATE_TEXT:
                newActionPanel = new AggregateGroupByPanel();
                break;
//            case AGGREGATE_HAVING_TEXT:
//                newActionPanel = new AggregateHaving();
//                break;
//            case NESTED_AGGREGATED_TEXT:
//                newActionPanel = new NestedAggregated();
//                break;
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