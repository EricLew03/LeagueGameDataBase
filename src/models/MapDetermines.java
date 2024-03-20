package models;

public class MapDetermines {
    private int mapID;
    private int numberOfLanes;
    private String gameModeName;

    public MapDetermines(int mapID, int numberOfLanes, String gameModeName) {
        this.mapID = mapID;
        this.numberOfLanes = numberOfLanes;
        this.gameModeName = gameModeName;
    }

    public int getMapID() {
        return mapID;
    }

    public int getNumberOfLanes() {
        return numberOfLanes;
    }

    public String getGameModeName() {
        return gameModeName;
    }
}
