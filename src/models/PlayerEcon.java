package models;

public class PlayerEcon {
    private int creepscore;
    private int kills;
    private int gold;
    private int level;

    public PlayerEcon(int creepscore, int kills, int gold, int level) {
        this.creepscore = creepscore;
        this.kills = kills;
        this.gold = gold;
        this.level = level;
    }

    public int getCreepscore() {
        return creepscore;
    }

    public int getKills() {
        return kills;
    }

    public int getGold() {
        return gold;
    }

    public int getLevel() {
        return level;
    }
}
