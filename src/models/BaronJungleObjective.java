package models;

public class BaronJungleObjective {
    private int jungleObjectiveID;
    private int healthPoints;
    private int effectTime;
    private int mapID;

    public BaronJungleObjective(int jungleObjectiveID, int healthPoints, int effectTime, int mapID) {
        this.jungleObjectiveID = jungleObjectiveID;
        this.healthPoints = healthPoints;
        this.effectTime = effectTime;
        this.mapID = mapID;
    }

    public int getJungleObjectiveID() {
        return jungleObjectiveID;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getEffectTime() {
        return effectTime;
    }

    public int getMapID() {
        return mapID;
    }
}
