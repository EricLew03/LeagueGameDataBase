package delegates;

import models.PlayerStats;

public interface LeagueDelegate {

    public void databaseSetup();

    public void deletePlayerStats(int playerId);
    public void insertPlayerStats(PlayerStats model);
    public void showPlayerStats();
    public void updatePlayerStats(int playerId, String name);

    public void LeagueFinished();
}
