package delegates;

import models.OwnsItem;
import models.PlayerEcon;
import models.PlayerStats;

import java.util.List;

public interface LeagueDelegate {

    public void databaseSetup();

    public String deletePlayerStats(int playerId);

    public String insertPlayerStats(PlayerStats model);

    public PlayerStats[] showPlayerStats();


    public void updatePlayerStats(int playerId, String name);

    public String updatePlayerStatsWithID(PlayerStats model);

    public String playerSelection(String query);

    public List<List<String>> playerProjection(List<String> nameList);

    public void deleteOwnsItem(int playerId, String itemName);

    public void insertOwnsItem(OwnsItem model);

    public OwnsItem[] showOwnsItem();

    public void insertPlayerEcon(PlayerEcon model);

    public void deletePlayerEcon(int creepScore, int kills);

    public void showPlayerEcon();

    public String joinPlayerTurret(int mapID);

    public String nestedAggregate();

    public String aggregateHaving();

    public void LeagueFinished();

    public String aggregate();

    public String division();

    public List<String> showTable();


}
