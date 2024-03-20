package models;

public class Inhibitor {
    private int structureID;
    private int healthPoints;
    private String location;
    private int respawnTime;
    private int mapID;
    private int playerID;

    public Inhibitor(int structureID, int healthPoints, String location, int respawnTime, int mapID, int playerID) {
        this.structureID = structureID;
        this.healthPoints = healthPoints;
        this.location = location;
        this.respawnTime = respawnTime;
        this.mapID = mapID;
        this.playerID = playerID;
    }

    public int getStructureID() {
        return structureID;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public String getLocation() {
        return location;
    }

    public int getRespawnTime() {
        return respawnTime;
    }

    public int getMapID() {
        return mapID;
    }

    public int getPlayerID() {
        return playerID;
    }
}
