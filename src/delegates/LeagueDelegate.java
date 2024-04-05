package delegates;

import models.OwnsItem;
import models.PlayerEcon;
import models.PlayerStats;

public interface LeagueDelegate {

    public void databaseSetup();

    public String deletePlayerStats(int playerId);

    public String insertPlayerStats(PlayerStats model);

    public PlayerStats[] showPlayerStats();


    public void updatePlayerStats(int playerId, String name);

    public String updatePlayerStatsWithID(PlayerStats model);

    public void playerSelection();

    public void playerProjection();

    public void deleteOwnsItem(int playerId, String itemName);

    public void insertOwnsItem(OwnsItem model);

    public void showOwnsItem();

    public void insertPlayerEcon(PlayerEcon model);

    public void deletePlayerEcon(int creepScore, int kills);

    public void showPlayerEcon();

    public String joinPlayerTurret(int mapID);

    public String nestedAggregate();

    public String aggregateHaving();


    public void LeagueFinished();

    public String aggregate();

    public String division();


}
