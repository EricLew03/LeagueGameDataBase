package controller;


import database.DatabaseConnectionHandler;
import delegates.LeagueDelegate;
import delegates.LoginWindowDelegate;
import models.OwnsItem;
import models.PlayerEcon;
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


	@Override
	public void deleteOwnsItem(int playerId, String itemName) {
		dbHandler.deleteOwnsItem(playerId, itemName);
	}

	@Override
	public void insertOwnsItem(OwnsItem model) {
		dbHandler.insertOwnsItem(model);
	}

	@Override
	public void showOwnsItem() {
		OwnsItem[] models = dbHandler.getOwnsItem();

		for (int i = 0; i < models.length; i++) {
			OwnsItem model = models[i];
			System.out.printf("1. ");
			System.out.printf("%-4.10s", model.getPlayerID());
			System.out.printf("%-20.20s", model.getName());
			System.out.printf("%-20.20s", model.getAd());
			System.out.printf("%-15.15s", model.getArmor());
			System.out.println();
		}

	}

	@Override
	public void insertPlayerEcon(PlayerEcon model) {
		dbHandler.insertPlayerEcon(model);
	}

	@Override
	public void deletePlayerEcon(int creepScore, int kills) {
		dbHandler.deletePlayerEcon(creepScore,kills);

	}
	@Override
	public void showPlayerEcon() {
		PlayerEcon[] models = dbHandler.getPlayerEcon();

		for (int i = 0; i < models.length; i++) {
			PlayerEcon model = models[i];
			System.out.printf("1. ");
			System.out.printf("%-4.10s", model.getCreepscore());
			System.out.printf("%-20.20s", model.getKills());
			System.out.printf("%-20.20s", model.getGold());
			System.out.printf("%-15.15s", model.getLevel());
			System.out.println();
		}

	}




	@Override
	public void insertPlayerStats(PlayerStats model) {
		dbHandler.insertPlayerStats(model);
	}


	@Override
	public void deletePlayerStats(int playerId) {

		dbHandler.deletePlayerStats(playerId);
	}

	@Override
	public void updatePlayerStats(int playerId, String name) {
		dbHandler.updatePlayerStats(playerId, name);
    }

	@Override
	public void playerSelection() {
		PlayerStats[] models = dbHandler.playerSelection();

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

	@Override
	public void playerProjection() {
		dbHandler.showTables();

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

	public void joinPlayerTurret(int mapID) {
		PlayerStats[] models = dbHandler.joinPlayerTurret(mapID);
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

	@Override
	public void aggregateHaving() {
		dbHandler.aggregateHaving();
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

	@Override
	public void aggregate() {
		dbHandler.aggregate();
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
