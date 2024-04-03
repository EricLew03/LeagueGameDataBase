package database;

import models.OwnsItem;
import models.PlayerEcon;
import models.PlayerStats;
import util.PrintablePreparedStatement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public void deleteOwnsItem(int playerId, String name){
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
    public void insertOwnsItem(OwnsItem model){
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
    public OwnsItem[] getOwnsItem(){
        ArrayList<OwnsItem> result = new ArrayList<OwnsItem>();

        try {
            String query = "SELECT * FROM ownsItem";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
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

// ==================================================================================================================================
// ===============================================================================================================================
//  functions for the playerStats relation

    // delete one tuple in the playerStats relation given a playerID
    public void deletePlayerStats(int playerID) {
        try {
            String query = "DELETE FROM playerStats WHERE playerID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, playerID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Player " + playerID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // Insert a new tuple in the playerStats relation
    public void insertPlayerStats(PlayerStats model) {
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


            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }


    // returns the tuples in playerStats relation
    public PlayerStats[] getPlayerStats() {
        ArrayList<PlayerStats> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM playerStats";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
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

    public PlayerStats[] joinPlayerTurret(int mapID) {
        ArrayList<PlayerStats> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM playerStats INNER JOIN turret ON playerStats.playerID = turret.playerID WHERE turret.mapID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, mapID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
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



    // returns tuples based on the selection condition
    // have to improve to accept or arguments as well
    public PlayerStats[] playerSelection() {
        ArrayList<PlayerStats> result = new ArrayList<PlayerStats>();
        Map<String, Object> conditions = getUserInput();

        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM playerStats");
            if (!conditions.isEmpty()) {
                queryBuilder.append(" WHERE ");
                List<String> conditionsList = new ArrayList<>();
                for (Map.Entry<String, Object> entry : conditions.entrySet()) {
                    conditionsList.add(entry.getKey() + " = ?");
                }
                queryBuilder.append(String.join(" AND ", conditionsList));
            }

            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(queryBuilder.toString()), queryBuilder.toString(), false);
            int index = 1;
            for (Object value : conditions.values()) {
                if (value instanceof Integer) {
                    ps.setInt(index++, (Integer) value);
                } else if (value instanceof String) {
                    ps.setString(index++, (String) value);
                } // Add additional cases for other data types if needed
            }
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
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

    // show all the tables we have in the database
    public void showTables() {
        try {
            String query = "SELECT table_name FROM user_tables";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and print table names
            System.out.println("Table Names:");
            while (rs.next()) {
                String tableName = rs.getString("table_name");
                System.out.println(tableName);
            }

            // Close resources
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 5) {
            choice = readInteger(false);

            System.out.println(" ");

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        playerProjection();
                        break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                        break;
                }
            }
        }
    }

    // method to make show selected columns
    public void playerProjection() {
        List<String> selectedColumns = getColumnSelection(); // Get user-selected columns


        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT ");

            // Append selected columns to the query
            queryBuilder.append(String.join(", ", selectedColumns));
            queryBuilder.append(" FROM playerStats");

            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(queryBuilder.toString()), queryBuilder.toString(), false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Iterate through each row of the result set
                for (String columnName : selectedColumns) {
                    // For each selected column, retrieve its value and process it as needed
                    String columnValue = rs.getString(columnName);
                    System.out.println(columnName + ": " + columnValue);
                    // Or do whatever processing you need with the column value
                }
                // Optionally, you can add a separator between rows
                System.out.println("-------------------------------------");
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
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
            while(rs.next()) {
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

    public void deletePlayerEcon(int creepScore, int kills){
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
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
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

        OwnsItem item1 = new OwnsItem(1, "lol",1,2,3,4,5);
        insertOwnsItem(item1);

        PlayerEcon player2 = new PlayerEcon(150,5,23,22);
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

            while(rs.next()) {
                if(rs.getString(1).toLowerCase().equals("playerstats")) {
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

            while(rs.next()) {
                if(rs.getString(1).toLowerCase().equals("ownsitem")) {
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

            while(rs.next()) {
                if(rs.getString(1).toLowerCase().equals("playerecon")) {
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



