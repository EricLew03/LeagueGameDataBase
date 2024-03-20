package models;

public class DragonType {
    private String type;
    private int healthPoints;

    public DragonType(String type, int healthPoints) {
        this.type = type;
        this.healthPoints = healthPoints;
    }

    public String getType() {
        return type;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
}
