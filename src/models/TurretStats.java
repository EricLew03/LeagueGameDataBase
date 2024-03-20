package models;

public class TurretStats {
    private String location;
    private int healthPoints;

    public TurretStats(String location, int healthPoints) {
        this.location = location;
        this.healthPoints = healthPoints;
    }

    public String getLocation() {
        return location;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
}
