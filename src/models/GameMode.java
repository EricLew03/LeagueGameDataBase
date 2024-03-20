package models;

public class GameMode {
    private String gameModeName;
    private int maxPartySize;
    private int canBan;

    public GameMode(String gameModeName, int maxPartySize, int canBan) {
        this.gameModeName = gameModeName;
        this.maxPartySize = maxPartySize;
        this.canBan = canBan;
    }

    public String getGameModeName() {
        return gameModeName;
    }

    public void setGameModeName(String gameModeName) {
        this.gameModeName = gameModeName;
    }

    public int getMaxPartySize() {
        return maxPartySize;
    }

    public void setMaxPartySize(int maxPartySize) {
        this.maxPartySize = maxPartySize;
    }

    public int getCanBan() {
        return canBan;
    }

    public void setCanBan(int canBan) {
        this.canBan = canBan;
    }
}
