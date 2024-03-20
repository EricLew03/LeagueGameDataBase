package models;

public class Nexus {
    private int structureID;
    private int healthPoints;
    private String location;
    private int vulnerable;
    private int mapID;
    private int playerID;

    public Nexus(int structureID, int healthPoints, String location, int vulnerable, int mapID, int playerID) {
        this.structureID = structureID;
        this.healthPoints = healthPoints;
        this.location = location;
        this.vulnerable = vulnerable;
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

    public int getVulnerable() {
        return vulnerable;
    }

    public int getMapID() {
        return mapID;
    }

    public int getPlayerID() {
        return playerID;
    }
}
