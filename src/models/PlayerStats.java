package models;

public class PlayerStats {
    private final int playerID;
    private final int champID;
    private final String championName;
    private final int manaPoints;
    private final int healthPoints;
    private final int creepScore;
    private final int kills;
    private final String rank;
    private final int mapID;

    public PlayerStats(int playerID, int champID, String championName, int manaPoints, int healthPoints,
                            int creepScore, int kills, String rank, int mapID) {
        this.playerID = playerID;
        this.champID = champID;
        this.championName = championName;
        this.manaPoints = manaPoints;
        this.healthPoints = healthPoints;
        this.creepScore = creepScore;
        this.kills = kills;
        this.rank = rank;
        this.mapID = mapID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getChampID() {
        return champID;
    }

    public String getChampionName() {
        return championName;
    }

    public int getManaPoints() {
        return manaPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getCreepScore() {
        return creepScore;
    }

    public int getKills() {
        return kills;
    }

    public String getRank() {
        return rank;
    }

    public int getMapID() {
        return mapID;
    }
}
