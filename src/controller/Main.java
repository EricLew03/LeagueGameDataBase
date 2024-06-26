package controller;


import database.DatabaseConnectionHandler;
import delegates.LeagueDelegate;
import delegates.LoginWindowDelegate;
import models.OwnsItem;
import models.PlayerEcon;
import models.PlayerStats;
import ui.HomePage;
import ui.LoginWindow;

import java.util.List;

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
//        HomePage homePage = new HomePage();
//        homePage.showFrame(this);
    }

    /**
     * LoginWindowDelegate Implementation
     * <p>
     * connects to Oracle database with supplied username and password
     */
    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();

//			TerminalTransactions transaction = new TerminalTransactions();
//			transaction.showMainMenu(this);
//
            HomePage homePage = new HomePage();
            homePage.showFrame(this);

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
    public OwnsItem[] showOwnsItem() {
        OwnsItem[] models = dbHandler.getOwnsItem();
        return models;

//        for (int i = 0; i < models.length; i++) {
//            OwnsItem model = models[i];
//            System.out.printf("1. ");
//            System.out.printf("%-4.10s", model.getPlayerID());
//            System.out.printf("%-20.20s", model.getName());
//            System.out.printf("%-20.20s", model.getAd());
//            System.out.printf("%-15.15s", model.getArmor());
//            System.out.println();
//        }

    }

    @Override
    public void insertPlayerEcon(PlayerEcon model) {
        dbHandler.insertPlayerEcon(model);
    }

    @Override
    public void deletePlayerEcon(int creepScore, int kills) {
        dbHandler.deletePlayerEcon(creepScore, kills);

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
    public String insertPlayerStats(PlayerStats model) {
        return dbHandler.insertPlayerStats(model);
    }


    @Override
    public String deletePlayerStats(int playerId) {

        return dbHandler.deletePlayerStats(playerId);
    }

    @Override
    public void updatePlayerStats(int playerId, String name) {
        dbHandler.updatePlayerStats(playerId, name);
    }

    @Override
    public String updatePlayerStatsWithID(PlayerStats model) {
        return dbHandler.updatePlayerStatsWithID(model);
    }

    @Override
    public String playerSelection(String query) {
        String models = dbHandler.playerSelection(query);
        return models;

//        for (int i = 0; i < models.length; i++) {
//            PlayerStats model = models[i];
//            System.out.printf("1. ");
//            // simplified output formatting; truncation may occur
//            System.out.printf("%-4.10s", model.getPlayerID());
//            System.out.printf("%-20.20s", model.getPlayerName());
//            if (model.getChampionName() == null) {
//                System.out.printf("%-20.20s", " ");
//            } else {
//                System.out.printf("%-20.20s", model.getChampionName());
//            }
//            System.out.printf("%-20.20s", model.getRank());
//            System.out.printf("%-15.15s", model.getKills());
//            System.out.println();
//        }
    }

    @Override
    public List<List<String>> playerProjection(List<String> nameList) {
        List<List<String>> result = dbHandler.playerProjection(nameList);
        return result;

    }

    /**
     * TerminalTransactionsDelegate Implementation
     * <p>
     * Displays information about varies bank branches.
     */
    @Override
    public PlayerStats[] showPlayerStats() {
        PlayerStats[] models = dbHandler.getPlayerStats();
        return models;
//
//        for (int i = 0; i < models.length; i++) {
//            PlayerStats model = models[i];
//            System.out.printf("1. ");
//            // simplified output formatting; truncation may occur
//            System.out.printf("%-4.10s", model.getPlayerID());
//            System.out.printf("%-20.20s", model.getPlayerName());
//            if (model.getChampionName() == null) {
//                System.out.printf("%-20.20s", " ");
//            } else {
//                System.out.printf("%-20.20s", model.getChampionName());
//            }
//            System.out.printf("%-20.20s", model.getRank());
//            System.out.printf("%-15.15s", model.getKills());
//            System.out.println();
//        }
    }

    public String joinPlayerTurret(int mapID) {
        return dbHandler.joinPlayerTurret(mapID);
//        PlayerStats[] models = dbHandler.joinPlayerTurret(mapID);
//        for (int i = 0; i < models.length; i++) {
//            PlayerStats model = models[i];
//            System.out.printf("1. ");
//            // simplified output formatting; truncation may occur
//            System.out.printf("%-20.20s", model.getPlayerName());
//            if (model.getChampionName() == null) {
//                System.out.printf("%-20.20s", " ");
//            } else {
//                System.out.printf("%-20.20s", model.getChampionName());
//            }
//            System.out.printf("%-15.15s", model.getCreepScore());
//            System.out.printf("%-15.15s", model.getKills());
//            System.out.printf("%-20.20s", model.getRank());
//            System.out.println();
//        }
    }

    @Override
    public String nestedAggregate() {
        return dbHandler.nestedAggregate();
    }

    @Override
    public String aggregateHaving() {
        return dbHandler.aggregateHaving();
    }

    /**
     * TerminalTransactionsDelegate Implementation
     * <p>
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
    public String aggregate() {
        return dbHandler.aggregate();
    }

    @Override
    public String division() {
        return dbHandler.division();
    }

    @Override
    public List<String> showTable() {
        return dbHandler.showTables();
    }


    /**
     * TerminalTransactionsDelegate Implementation
     * <p>
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
