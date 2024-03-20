package models;

public class OwnsItem {
    private int playerID;
    private String name;
    private int mr;
    private int ad;
    private int ap;
    private int armor;
    private int cost;

    public OwnsItem(int playerID, String name, int mr, int ad, int ap, int armor, int cost) {
        this.playerID = playerID;
        this.name = name;
        this.mr = mr;
        this.ad = ad;
        this.ap = ap;
        this.armor = armor;
        this.cost = cost;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getName() {
        return name;
    }

    public int getMr() {
        return mr;
    }

    public int getAd() {
        return ad;
    }

    public int getAp() {
        return ap;
    }

    public int getArmor() {
        return armor;
    }

    public int getCost() {
        return cost;
    }
}

