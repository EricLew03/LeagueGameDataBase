package database;

import models.OwnsItem;
import models.PlayerStats;
import util.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


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

    public PlayerStats[] getPlayerStats() {
        ArrayList<PlayerStats> result = new ArrayList<PlayerStats>();

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
        dropPlayerStatsTableIfExists();
//        dropPlayerEconTableIfExists();
//        dropOwnsItemTableIfExists();

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
            String query = "CREATE TABLE playerStats ( playerID INTEGER PRIMARY KEY,playerName VARCHAR(20), champID INTEGER UNIQUE, championName VARCHAR(20), manaPoints INTEGER, healthPoints INTEGER, creepScore INTEGER, kills INTEGER, rank VARCHAR(20), mapID INTEGER)";
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
                    ps.execute("DROP TABLE playerEcon");
                    ps.execute("DROP TABLE ownsItem");
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


}

//
//private void dropPlayerEconTableIfExists() {
//    try {
//        String query = "select table_name from user_tables";
//        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//        ResultSet rs = ps.executeQuery();
//
//        while(rs.next()) {
//            if(rs.getString(1).toLowerCase().equals("playerecon")) {
//                ps.execute("DROP TABLE playerEcon");
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

//private void dropOwnsItemTableIfExists() {
//    try {
//        String query = "select table_name from user_tables";
//        PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//        ResultSet rs = ps.executeQuery();
//
//        while(rs.next()) {
//            if(rs.getString(1).toLowerCase().equals("ownsitem")) {
//                ps.execute("DROP TABLE ownsItem");
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


