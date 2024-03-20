package models;

public class Turret {
    private int structureID;
    private String location;
    private int mapID;
    private int playerID;

    public Turret(int structureID, String location, int mapID, int playerID) {
        this.structureID = structureID;
        this.location = location;
        this.mapID = mapID;
        this.playerID = playerID;
    }

    public int getStructureID() {
        return structureID;
    }

    public String getLocation() {
        return location;
    }

    public int getMapID() {
        return mapID;
    }

    public int getPlayerID() {
        return playerID;
    }
}
