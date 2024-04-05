package ui;

import delegates.LeagueDelegate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * The class is only responsible for handling terminal text inputs.
 */
public class TerminalTransactions {
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private static final int INVALID_INPUT = Integer.MIN_VALUE;
    private static final int EMPTY_INPUT = 0;

    private BufferedReader bufferedReader = null;
    private LeagueDelegate delegate = null;

    public TerminalTransactions() {
    }


    /**
     * Displays simple text interface
     */
    public void showMainMenu(LeagueDelegate delegate) {
        this.delegate = delegate;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 5) {
            System.out.println();
            System.out.println("1. Insert playerStats");
            System.out.println("2. Delete playerStats");
            System.out.println("3. Update playerStats");
            System.out.println("4. Show playerStats");
            System.out.println("5. Quit");
            System.out.println("6. insert item");
            System.out.println("7. delete item");
            System.out.println("8. show item");
            System.out.println("9. playerStats selection");
            System.out.println("10. playerStats projection");
            System.out.println("11. insert playerEcon");
            System.out.println("12. delete playerEcon");
            System.out.println("13. show playerEcon");
            System.out.println("14. join");
            System.out.println("15. aggregate");
            System.out.println("16. aggregateHaving");
            System.out.println("18. nestedAggregated");
            System.out.println("17. division");
        }
    }
}
