package controller;


import database.DatabaseConnectionHandler;
import delegates.LeagueDelegate;
import delegates.LoginWindowDelegate;
import models.PlayerStats;
import ui.LoginWindow;
import ui.TerminalTransactions;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class Main implements LoginWindowDelegate, LeagueDelegate {
	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;

	public Main() {
		dbHandler = new DatabaseConnectionHandler();
	}
	
	private void start() {
		loginWindow = new LoginWindow();
		loginWindow.showFrame(this);
	}
	
	/**
	 * LoginWindowDelegate Implementation
	 * 
     * connects to Oracle database with supplied username and password
     */ 
	public void login(String username, String password) {
		boolean didConnect = dbHandler.login(username, password);

		if (didConnect) {
			// Once connected, remove login window and start text transaction flow
			loginWindow.dispose();

			TerminalTransactions transaction = new TerminalTransactions();
			transaction.setupDatabase(this);
			transaction.showMainMenu(this);
		} else {
			loginWindow.handleLoginFailed();

			if (loginWindow.hasReachedMaxLoginAttempts()) {
				loginWindow.dispose();
				System.out.println("You have exceeded your number of allowed attempts");
				System.exit(-1);
			}
		}
	}
	
	/**
	 * TerminalTransactionsDelegate Implementation
	 * 
	 * Insert a branch with the given info
	 */
	@Override
	public void insertPlayerStats(PlayerStats model) {
		dbHandler.insertPlayerStats(model);
	}

    /**
	 * TerminalTransactionsDelegate Implementation
	 * 
	 * Delete branch with given branch ID.
	 */

	@Override
	public void deletePlayerStats(int playerId) {

		dbHandler.deletePlayerStats(playerId);
	}
    /**
	 * TerminalTransactionsDelegate Implementation
	 * 
	 * Update the branch name for a specific ID
	 */

	@Override
	public void updatePlayerStats(int playerId, String name) {
		dbHandler.updatePlayerStats(playerId, name);
    }

    /**
	 * TerminalTransactionsDelegate Implementation
	 * 
	 * Displays information about varies bank branches.
	 */
	@Override
	public void showPlayerStats() {
    	PlayerStats[] models = dbHandler.getPlayerStats();
    	
    	for (int i = 0; i < models.length; i++) {
    		PlayerStats model = models[i];
			System.out.printf("1. ");
    		// simplified output formatting; truncation may occur
    		System.out.printf("%-4.10s", model.getPlayerID());
    		System.out.printf("%-20.20s", model.getPlayerName());
    		if (model.getChampionName() == null) {
    			System.out.printf("%-20.20s", " ");
    		} else {
    			System.out.printf("%-20.20s", model.getChampionName());
    		}
			System.out.printf("%-20.20s", model.getRank());
    		System.out.printf("%-15.15s", model.getKills());
    		System.out.println();
    	}
    }
	
    /**
	 * TerminalTransactionsDelegate Implementation
	 * 
     * The TerminalTransaction instance tells us that it is done with what it's 
     * doing, so we are cleaning up the connection since it's no longer needed.
     */


	@Override
	public void LeagueFinished() {
    	dbHandler.close();
    	dbHandler = null;
    	System.exit(0);
    }
    
    /**
	 * TerminalTransactionsDelegate Implementation
	 * 
     * The TerminalTransaction instance tells us that the user is fine with dropping any existing table
     * called branch and creating a new one for this project to use
     */ 
	public void databaseSetup() {
		dbHandler.databaseSetup();

	}
	/**
	 * Main method called at launch time
	 */
	public static void main(String args[]) {
		Main main = new Main();
		main.start();
	}
}
