package database;

import models.OwnsItem;
import models.PlayerEcon;
import models.PlayerStats;
import util.PrintablePreparedStatement;
import util.StringFormatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
    // Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    private static final int INVALID_INPUT = Integer.MIN_VALUE;
    private static final int EMPTY_INPUT = 0;

    private BufferedReader bufferedReader = null;


    //=================================================================================
    // connecting to the oracle server
    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }


    //===========================================================================================================
    //============================================================================================================
    // functions for ownItem relation

    // delete one tuple of the ownItem given playerID and itemName
    public void deleteOwnsItem(int playerId, String name) {
        try {
            String query = "DELETE FROM ownsItem WHERE playerID = ? AND itemName = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, playerId);
            ps.setString(2, name);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Player " + playerId + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // insert one tuple into the ownItem relation
    public void insertOwnsItem(OwnsItem model) {
        try {
            String query = "INSERT INTO ownsItem VALUES (?,?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, model.getPlayerID());
            ps.setString(2, model.getName());
            ps.setInt(3, model.getMr());
            ps.setInt(4, model.getAd());
            ps.setInt(5, model.getAp());
            ps.setInt(6, model.getArmor());
            ps.setInt(7, model.getCost());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

    }

    // returns the ownItem relation
    public OwnsItem[] getOwnsItem() {
        ArrayList<OwnsItem> result = new ArrayList<OwnsItem>();

        try {
            String query = "SELECT * FROM ownsItem";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OwnsItem model = new OwnsItem(rs.getInt("playerID"),
                        rs.getString("itemName"),
                        rs.getInt("mr"),
                        rs.getInt("ad"),
                        rs.getInt("ap"),
                        rs.getInt("armor"),
                        rs.getInt("cost")
                );
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new OwnsItem[result.size()]);
    }


    public String aggregate() {
        String result = "";

        try {
            String query = "SELECT itemName, COUNT(*) AS item_count FROM ownsItem GROUP BY itemName";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String itemName = rs.getString("itemName");
                int count = rs.getInt("item_count");
//                System.out.println("Item Name: " + itemName + ", Count: " + count);
                result += "Item Name: " + itemName + ", Count: " + count + "<br>";
            }
            if (result == "") {
                result = "No items in the table";
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            result = EXCEPTION_TAG + " " + e.getMessage();
            rollbackConnection();
        }
        return result;
    }

    public String aggregateHaving() {
        String result = "";

        try {
            String query = "SELECT itemName, MAX(cost) AS max_price " +
                    "FROM ownsItem " +
                    "GROUP BY itemName " +
                    "HAVING COUNT(*) >= 2";

            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String itemName = rs.getString("itemName");
                double maxPrice = rs.getDouble("max_price");
//                System.out.println("Item Name: " + itemName + ", Max Price: " + maxPrice);
                result += "Item Name: " + itemName + ", Max Price: " + maxPrice + "<br>";
            }
            if (result == "") {
                result = "No items in the table";
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            result = EXCEPTION_TAG + " " + e.getMessage();
            rollbackConnection();
        }
        return result;
    }

    public String nestedAggregate() {
        String result = "";

        try {
            String query =
                    "WITH PlayerAverageCost AS (" +
                            "   SELECT item.playerID, AVG(item.cost) AS average_cost " +
                            "   FROM ownsItem item " +
                            "   GROUP BY item.playerID" +
                            ") " +
                            "SELECT player.playerName, average.average_cost " +
                            "FROM PlayerAverageCost average " +
                            "JOIN playerStats player ON average.playerID = player.playerID " +
                            "WHERE average.average_cost = (SELECT MAX(average_cost) FROM PlayerAverageCost)";

            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String playerName = rs.getString("playerName");
                double highestAvgCost = rs.getDouble("average_cost");
//                System.out.println("Player Name: " + playerName + ", Highest Average Cost: " + highestAvgCost);
                result = "Player Name: " + playerName + ", Highest Average Cost: " + highestAvgCost;
            }
            if (result == "") {
                result = "No items in the table";
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            result = EXCEPTION_TAG + " " + e.getMessage();
            rollbackConnection();
        }
        return result;
    }


// ==================================================================================================================================
// ===============================================================================================================================
//  functions for the playerStats relation

    // delete one tuple in the playerStats relation given a playerID
    public String deletePlayerStats(int playerID) {
        String result = "";

        try {
            String query = "DELETE FROM playerStats WHERE playerID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, playerID);

            int rowsAffected = ps.executeUpdate();
            result = String.valueOf(rowsAffected);
            if (rowsAffected == 0) {
//                System.out.println(WARNING_TAG + " Player " + playerID + " does not exist!");
                result = (WARNING_TAG + " Player " + playerID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            result = EXCEPTION_TAG + " " + e.getMessage();
            rollbackConnection();
        }
        return result;
    }

    // Insert a new tuple in the playerStats relation
    public String insertPlayerStats(PlayerStats model) {
        String result = "";

        try {
            String query = "INSERT INTO playerStats VALUES (?,?,?,?,?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, model.getPlayerID());
            ps.setString(2, model.getPlayerName());
            ps.setInt(3, model.getChampID());
            ps.setString(4, model.getChampionName());
            ps.setInt(5, model.getManaPoints());
            ps.setInt(6, model.getHealthPoints());
            ps.setInt(7, model.getCreepScore());
            ps.setInt(8, model.getKills());
            ps.setString(9, model.getRank());
            ps.setInt(10, model.getMapID());


            int rowsChanged = ps.executeUpdate();

            if (rowsChanged == 0) {
                result = "Insertion Failed, playerID is already in use";
            } else {
                result = String.valueOf("Rows successfully added: " + rowsChanged);
            }
//            System.out.println("Insertion: " + rowsChanged + " changed");

            connection.commit();

            ps.close();
        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            result = EXCEPTION_TAG + " " + e.getMessage();
            rollbackConnection();
        }

        return result;
    }


    // returns the tuples in playerStats relation
    public PlayerStats[] getPlayerStats() {
        ArrayList<PlayerStats> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM playerStats";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PlayerStats model = new PlayerStats(rs.getInt("playerID"),
                        rs.getString("playerName"),
                        rs.getInt("champID"),
                        rs.getString("championName"),
                        rs.getInt("manaPoints"),
                        rs.getInt("healthPoints"),
                        rs.getInt("creepScore"),
                        rs.getInt("kills"),
                        rs.getString("rank"),
                        rs.getInt("mapID")
                );
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new PlayerStats[result.size()]);
    }

    public String joinPlayerTurret(int mapID) {
//        ArrayList<PlayerStats> result = new ArrayList<>();
        String result = "";

        try {
            String query = "SELECT p.playerName, p.championName, p.creepScore, p.kills," +
                    " p.rank FROM playerStats p INNER JOIN turret ON p.playerID = turret.playerID WHERE turret.mapID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, mapID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
//                PlayerStats model = new PlayerStats(0,
//                        rs.getString("playerName"),
//                        0,
//                        rs.getString("championName"),
//                        0,
//                        0,
//                        rs.getInt("creepScore"),
//                        rs.getInt("kills"),
//                        rs.getString("rank"),
//                        0
//                );
                result += "Player name: " + rs.getString("playerName")
                        + ", Champion: " + rs.getString("championName")
                        + ", Creep Score: " + rs.getInt("creepScore")
                        + ", Kills: " + rs.getInt("kills")
                        + ", Rank: " + rs.getString("rank") + "<br>";

//                result.add(model);
            }
            if (result == "") {
                result = "No player has destroyed any turrets on this map";
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            result = EXCEPTION_TAG + " " + e.getMessage();
        }

//        return result.toArray(new PlayerStats[result.size()]);
        return result;
    }


    // returns tuples based on the query condition
    public String playerSelection(String query) {
        ArrayList<PlayerStats> rawResult = new ArrayList<PlayerStats>();
        StringFormatting formatter = new StringFormatting();
        String result;

        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM playerStats");
            if (query != "") {
                queryBuilder.append(" WHERE ");
                queryBuilder.append(query);
            }

            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(queryBuilder.toString()), queryBuilder.toString(), false);
//            int index = 1;
//            for (Object value : conditions.values()) {
//                if (value instanceof Integer) {
//                    ps.setInt(index++, (Integer) value);
//                } else if (value instanceof String) {
//                    ps.setString(index++, (String) value);
//                } // Add additional cases for other data types if needed
//            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PlayerStats model = new PlayerStats(rs.getInt("playerID"),
                        rs.getString("playerName"),
                        rs.getInt("champID"),
                        rs.getString("championName"),
                        rs.getInt("manaPoints"),
                        rs.getInt("healthPoints"),
                        rs.getInt("creepScore"),
                        rs.getInt("kills"),
                        rs.getString("rank"),
                        rs.getInt("mapID")
                );
                rawResult.add(model);
            }
            rs.close();
            ps.close();

            PlayerStats[] typedResult = rawResult.toArray(new PlayerStats[rawResult.size()]);
            if (typedResult.length == 0) {
                result = "No players found that satisfy the criteria";
            } else {
                result = formatter.formatPlayerStats(typedResult);
            }
        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            result = EXCEPTION_TAG + " " + e.getMessage();
        }
        return result;
    }


    // helper function to get user input for the selection conditions
    // have to improve to get more conditions
    public Map<String, Object> getUserInput() {
        Scanner scanner = new Scanner(System.in);
        Map<String, Object> conditions = new HashMap<>();

        System.out.println("Enter conditions for filtering (press Enter to skip):");
        System.out.print("Player ID: ");
        int playerId = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Player Name: ");
        String playerName = scanner.nextLine().trim();

        System.out.print("Champion ID: ");
        int champId = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Champion Name: ");
        String championName = scanner.nextLine().trim();

        if (playerId != -1) {
            conditions.put("playerID", playerId);
        }
        if (!playerName.isEmpty()) {
            conditions.put("playerName", playerName);
        }
        if (champId != -1) {
            conditions.put("champID", champId);
        }
        if (!championName.isEmpty()) {
            conditions.put("championName", championName);
        }


        return conditions;
    }


    // updating one tuple given a playerID
    // got to change it to update more attributes

    public void updatePlayerStats(int id, String name) {
        try {
            String query = "UPDATE playerStats SET playerName = ? WHERE playerID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, name);
            ps.setInt(2, id);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Player " + id + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public String updatePlayerStatsWithID(PlayerStats model) {
        String result = "";
//            ps.setString(1, name);
//            ps.setInt(2, id);

        // Check if valid ID
        if (model.getPlayerID() < 0) {
            result = WARNING_TAG + " Player " + model.getPlayerID() + " is invalid";
            return result;
        }

        try {
            StringBuilder queryBuilder = new StringBuilder("UPDATE playerStats SET ");
            List<Object> values = new ArrayList<>(); // holds the values to inject into prepared statement later

            // Add fields to update dynamically
            if (model.getPlayerName() != null) {
                queryBuilder.append("playerName = ?, ");
                values.add(model.getPlayerName());
            }

            if (model.getChampID() != -1) {
                queryBuilder.append("champID = ?, ");
                values.add(model.getChampID());
            }

            if (model.getChampionName() != null) {
                queryBuilder.append("championName = ?, ");
                values.add(model.getChampionName());
            }

            if (model.getManaPoints() != -1) {
                queryBuilder.append("manaPoints = ?, ");
                values.add(model.getManaPoints());
            }

            if (model.getHealthPoints() != -1) {
                queryBuilder.append("healthPoints = ?, ");
                values.add(model.getHealthPoints());
            }

            if (model.getCreepScore() != -1) {
                queryBuilder.append("creepScore = ?, ");
                values.add(model.getCreepScore());
            }

            if (model.getKills() != -1) {
                queryBuilder.append("kills = ?, ");
                values.add(model.getKills());
            }

            if (model.getRank() != null) {
                queryBuilder.append("rank = ?, ");
                values.add(model.getRank());
            }

            if (model.getMapID() != -1) {
                queryBuilder.append("mapID = ?, ");
                values.add(model.getMapID());
            }

            queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length()); // removes artifacts fron query building, the extra ", "
            queryBuilder.append(" WHERE playerID = ?");
            values.add(model.getPlayerID());

            String query = queryBuilder.toString();
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

            // set parameter values
            if (values.size() == 1) {
                // If there was only 1 value, meaning just player ID, return
                result = "Error: Please enter a field to be updated";
            } else {
                for (int i = 0; i < values.size(); i++) {
                    // General obkct but need to check if int or str so query doesnt replace it as string
                    Object value = values.get(i);
                    if (value instanceof Integer) {
                        ps.setInt(i + 1, (Integer) value);
                    } else if (value instanceof String) {
                        ps.setString(i + 1, (String) value);
                    }
                }

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    result = WARNING_TAG + "No rows changed";
                } else {
                    result = "Rows updated: " + rowsAffected;
                }

                connection.commit();

                ps.close();
            }
        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            result = EXCEPTION_TAG + " " + e.getMessage();
            rollbackConnection();
        }

        return result;
    }

    // show all the tables we have in the database
    public List<String> showTables() {
        List<String> tableNames = new ArrayList<>();

        try {
            String query = "SELECT table_name FROM user_tables";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and add table names to the list
            while (rs.next()) {
                String tableName = rs.getString("table_name");
                tableNames.add(tableName);
            }

            // Close resources
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return tableNames;
    }

    // method to make show selected columns
    public List<List<String>> playerProjection(List<String> nameList) {
        List<List<String>> result = new ArrayList<>();

        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT ");

            // Append selected columns to the query
            queryBuilder.append(String.join(", ", nameList));
            queryBuilder.append(" FROM playerStats");

            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(queryBuilder.toString()), queryBuilder.toString(), false);
            ResultSet rs = ps.executeQuery();

            // Iterate over the ResultSet and extract values
            while (rs.next()) {
                List<String> row = new ArrayList<>();
                // Extract values for each selected column
                for (String columnName : nameList) {
                    String value = rs.getString(columnName); // Assuming all columns are strings
                    row.add(value);
                }
                result.add(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }

    // Method to prompt the user to select columns
    public List<String> getColumnSelection() {
        Scanner scanner = new Scanner(System.in);
        List<String> selectedColumns = new ArrayList<>();

        System.out.println("Enter columns to select (comma-separated, e.g., playerID, playerName, ...): ");
        String columnsInput = scanner.nextLine().trim();
        selectedColumns.addAll(Arrays.asList(columnsInput.split("\\s*,\\s*")));

        return selectedColumns;
    }


    //====================================================================================================================================
    //====================================================================================================================================

    //functions for the player econ relation

    public void insertPlayerEcon(PlayerEcon model) {
        try {
            String query = "INSERT INTO playerEcon VALUES (?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, model.getCreepscore());
            ps.setInt(2, model.getKills());
            ps.setInt(3, model.getGold());
            ps.setInt(4, model.getLevel());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public PlayerEcon[] getPlayerEcon() {
        ArrayList<PlayerEcon> result = new ArrayList<PlayerEcon>();

        try {
            String query = "SELECT * FROM playerEcon";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PlayerEcon model = new PlayerEcon(rs.getInt("creepScore"),
                        rs.getInt("kills"),
                        rs.getInt("gold"),
                        rs.getInt("playerLevel")
                );
                result.add(model);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new PlayerEcon[result.size()]);
    }

    public void deletePlayerEcon(int creepScore, int kills) {
        try {
            String query = "DELETE FROM playerEcon WHERE creepScore = ? AND kills = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, creepScore);
            ps.setInt(2, kills);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Player " + creepScore + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }


    //===================================================================================================================================
    // ============================================================================================================================
    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    private void rollbackConnection() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }


    public String division() {
        List<String> ownedItemNames = new ArrayList<>();
        String result;

        try {
            String query = "SELECT UNIQUE oi.itemname " +
                    "FROM ownsItem oi " +
                    "WHERE NOT EXISTS " +
                    "(SELECT playerID " +
                    " FROM playerStats " +
                    " MINUS " +
                    "(SELECT playerID " +
                    " FROM ownsItem " +
                    " WHERE ownsItem.itemname = oi.itemname))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String itemName = rs.getString(1);
                ownedItemNames.add(itemName);
                System.out.println(itemName);
                System.out.println("<br>");
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        if (ownedItemNames.size() == 0) {
            result = "No items are owned by every player";
        } else {
            String flattenedResult = String.join(", ", ownedItemNames);
            result = flattenedResult;
        }

        return result;
    }


    public void databaseSetup() {
        dropOwnsItemTableIfExists();
        dropPlayerEconTableIfExists();
        dropPlayerStatsTableIfExists();


//        dropDragonJungleTableIfExists();
//        dropDragonTypeTableIfExists();
//        dropBaronJungleObjectiveTableIfExists();
//        dropInhibitorTableIfExists();
//        dropNexusTableIfExists();
//        dropTurretTableIfExists();
//        dropTurretDamageTableIfExists();
//        dropTurretStatsTableIfExists();
//        dropMapDeterminesTableIfExists();
//        dropGameModeTableIfExists();
//
        try {
            String query = "CREATE TABLE playerStats ( playerID INTEGER PRIMARY KEY,playerName VARCHAR(20), champID INTEGER UNIQUE, championName VARCHAR(20), manaPoints INTEGER, healthPoints INTEGER, creepScore INTEGER , kills INTEGER , rank VARCHAR(20), mapID INTEGER)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            ps.close();

            query = "CREATE TABLE ownsItem ( playerID INTEGER, itemName VARCHAR(20), mr INTEGER, ad INTEGER, ap INTEGER, armor INTEGER, cost INTEGER, PRIMARY KEY (playerID, itemName), FOREIGN KEY (playerID) REFERENCES playerStats(playerID) ON DELETE CASCADE)";
            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();

            query = "CREATE TABLE playerEcon ( creepScore INTEGER, kills INTEGER, gold INTEGER, playerLevel INTEGER, PRIMARY KEY (creepScore, kills))";
            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();


        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        PlayerStats player1 = new PlayerStats(1, "CyberReaper666", 123, "Ahri", 500, 800, 150, 5, "Iron", 1);
        insertPlayerStats(player1);

        OwnsItem item1 = new OwnsItem(1, "lol", 1, 2, 3, 4, 5);
        insertOwnsItem(item1);

        PlayerEcon player2 = new PlayerEcon(150, 5, 23, 22);
        insertPlayerEcon(player2);
    }

//        String query = "CREATE TABLE gameMode ( gamemodeName VARCHAR(20) PRIMARY KEY, maxPartySize INTEGER, canBan INTEGER)";
//        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//        ps.executeUpdate();

//        query = "CREATE TABLE turretStats ( structureLocation VARCHAR(20) PRIMARY KEY, healthPoints INTEGER)";
//        ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.executeUpdate();
//
//            query = "CREATE TABLE turretDamage ( structureLocation VARCHAR(20) PRIMARY KEY, damage INTEGER, FOREIGN KEY(structureLocation) REFERENCES turretStats(structureLocation) ON DELETE CASCADE)";
//            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.executeUpdate();
//
//            query = "CREATE TABLE turret ( structureID INTEGER PRIMARY KEY, structureLocation VARCHAR(20), mapID INTEGER, playerID INTEGER, FOREIGN KEY (mapID) REFERENCES mapDetermines(mapID) ON DELETE CASCADE, FOREIGN KEY (playerID) REFERENCES playerStats(playerID) ON DELETE CASCADE, FOREIGN KEY(structureLocation) REFERENCES turretStats(structureLocation) ON DELETE CASCADE)";
//            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.executeUpdate();
//
//            query = "CREATE TABLE nexus ( structureID INTEGER PRIMARY KEY, healthPoints INTEGER, structureLocation VARCHAR(20), vulnerable INTEGER, mapID INTEGER, playerID INTEGER, FOREIGN KEY (mapID) REFERENCES mapDetermines(mapID) ON DELETE CASCADE, FOREIGN KEY (playerID) REFERENCES playerStats(playerID) ON DELETE CASCADE)";
//            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.executeUpdate();
//
//            query = "CREATE TABLE inhibitor ( structureID INTEGER PRIMARY KEY, healthPoints INTEGER, structureLocation VARCHAR(20), respawnTime INTEGER, mapID INTEGER, playerID INTEGER, FOREIGN KEY (mapID) REFERENCES mapDetermines(mapID) ON DELETE CASCADE, FOREIGN KEY (playerID) REFERENCES playerStats(playerID) ON DELETE CASCADE)";
//            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.executeUpdate();
//
//            query = "CREATE TABLE baronJungleObjective ( jungleObjectiveID INTEGER PRIMARY KEY, healthPoints INTEGER, effectTime INTEGER, mapID INTEGER, FOREIGN KEY (mapID) REFERENCES mapDetermines(mapID) ON DELETE CASCADE)";
//            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.executeUpdate();
//
//            query = "CREATE TABLE dragonType ( dragonType VARCHAR(20) PRIMARY KEY, healthPoints INTEGER)";
//            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.executeUpdate();
//
//            query = "CREATE TABLE dragonJungle ( jungleObjectiveID INTEGER PRIMARY KEY, dragonType VARCHAR(20), mapID INTEGER, FOREIGN KEY (mapID) REFERENCES mapDetermines(mapID) ON DELETE CASCADE, FOREIGN KEY (dragonType) REFERENCES dragonType(dragonType) ON DELETE CASCADE)";
//            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.executeUpdate();
//


    // we can drop all tables in a single function i think
    private void dropPlayerStatsTableIfExists() {
        try {
            String query = "select table_name from user_tables";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString(1).toLowerCase().equals("playerstats")) {
                    ps.execute("DROP TABLE playerStats");
                    break;
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private void dropOwnsItemTableIfExists() {
        try {
            String query = "select table_name from user_tables";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString(1).toLowerCase().equals("ownsitem")) {
                    ps.execute("DROP TABLE ownsItem");
                    break;
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private int readInteger(boolean allowEmpty) {
        String line = null;
        int input = INVALID_INPUT;
        try {
            line = bufferedReader.readLine();
            input = Integer.parseInt(line);
        } catch (IOException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        } catch (NumberFormatException e) {
            if (allowEmpty && line.length() == 0) {
                input = EMPTY_INPUT;
            } else {
                System.out.println(WARNING_TAG + " Your input was not an integer");
            }
        }
        return input;
    }

    private void dropPlayerEconTableIfExists() {
        try {
            String query = "select table_name from user_tables";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getString(1).toLowerCase().equals("playerecon")) {
                    ps.execute("DROP TABLE playerEcon");
                    break;
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }


    private String readLine() {
        String result = null;
        try {
            result = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result;
    }


}


//
//
//
//private void dropMapDeterminesTableIfExists() {
//    try {
//        String query = "select table_name from user_tables";
//        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//        ResultSet rs = ps.executeQuery();
//
//        while(rs.next()) {
//            if(rs.getString(1).toLowerCase().equals("mapdetermines")) {
//                ps.execute("DROP TABLE mapDetermines");
//                break;
//            }
//        }
//        rs.close();
//        ps.close();
//    } catch (SQLException e) {
//        System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//    }
//}
//
//private void dropGameModeTableIfExists() {
//    try {
//        String query = "select table_name from user_tables";
//        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//        ResultSet rs = ps.executeQuery();
//
//        while(rs.next()) {
//            if (rs.getString(1).toLowerCase().equals("gamemode")) {
//                ps.execute("DROP TABLE gameMode");
//                break;
//            }
//        }
//        rs.close();
//        ps.close();
//    } catch (SQLException e) {
//        System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//    }
//}


//    private void dropDragonJungleTableIfExists() {
//        try {
//            String query = "select table_name from user_tables";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                if(rs.getString(1).toLowerCase().equals("dragonjungle")) {
//                    ps.execute("DROP TABLE dragonjungle");
//                    break;
//                }
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    private void dropDragonTypeTableIfExists() {
//        try {
//            String query = "select table_name from user_tables";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                System.out.println(rs.getString(1).toLowerCase());
//                if(rs.getString(1).toLowerCase().equals("dragontype")) {
//                    ps.execute("DROP TABLE dragontype");
//                    break;
//                }
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    private void dropBaronJungleObjectiveTableIfExists() {
//        try {
//            String query = "select table_name from user_tables";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                if(rs.getString(1).toLowerCase().equals("baronjungleobjective")) {
//                    ps.execute("DROP TABLE baronJungleObjective");
//                    break;
//                }
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    private void dropInhibitorTableIfExists() {
//        try {
//            String query = "select table_name from user_tables";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                if(rs.getString(1).toLowerCase().equals("inhibitor")) {
//                    ps.execute("DROP TABLE inhibitor");
//                    break;
//                }
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    private void dropNexusTableIfExists() {
//        try {
//            String query = "select table_name from user_tables";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                if(rs.getString(1).toLowerCase().equals("nexus")) {
//                    ps.execute("DROP TABLE nexus");
//                    break;
//                }
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }

//    private void dropTurretTableIfExists() {
//        try {
//            String query = "select table_name from user_tables";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                if(rs.getString(1).toLowerCase().equals("turret")) {
//                    ps.execute("DROP TABLE turret");
//                    break;
//                }
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//
//    private void dropTurretDamageTableIfExists() {
//        try {
//            String query = "select table_name from user_tables";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                if(rs.getString(1).toLowerCase().equals("turretdamage")) {
//                    ps.execute("DROP TABLE turretDamage");
//                    break;
//                }
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }

//    private void dropTurretStatsTableIfExists() {
//        try {
//            String query = "select table_name from user_tables";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                if(rs.getString(1).toLowerCase().equals("turretstats")) {
//                    ps.execute("DROP TABLE turretStats");
//                    break;
//                }
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }



