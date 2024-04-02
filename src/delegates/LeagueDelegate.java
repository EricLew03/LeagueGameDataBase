package delegates;

import models.OwnsItem;
import models.PlayerStats;

public interface LeagueDelegate {

    public void databaseSetup();

    public void deletePlayerStats(int playerId);
    public void insertPlayerStats(PlayerStats model);
    public void showPlayerStats();
    public void updatePlayerStats(int playerId, String name);

    public void playerSelection();

    public void deleteOwnsItem(int playerId, String itemName);

    public void insertOwnsItem(OwnsItem model);


    public void showOwnsItem();






    public void LeagueFinished();
}
