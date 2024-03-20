package models;

public class DragonJungle {
    private int jungleObjectiveID;
    private String type;
    private int mapID;

    // Constructor
    public DragonJungle(int jungleObjectiveID, String type, int mapID) {
        this.jungleObjectiveID = jungleObjectiveID;
        this.type = type;
        this.mapID = mapID;
    }

    // Getters
    public int getJungleObjectiveID() {
        return jungleObjectiveID;
    }

    public String getType() {
        return type;
    }

    public int getMapID() {
        return mapID;
    }
}
