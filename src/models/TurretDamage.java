package models;

public class TurretDamage {
    private String location;
    private int damage;

    public TurretDamage(String location, int damage) {
        this.location = location;
        this.damage = damage;
    }

    public String getLocation() {
        return location;
    }

    public int getDamage() {
        return damage;
    }
}
