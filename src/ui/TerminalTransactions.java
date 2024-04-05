package ui;

import delegates.LeagueDelegate;
import models.OwnsItem;
import models.PlayerEcon;
import models.PlayerStats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The class is only responsible for handling terminal text inputs. 
 */
public class TerminalTransactions {
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	private static final int INVALID_INPUT = Integer.MIN_VALUE;
	private static final int EMPTY_INPUT = 0;
	
	private BufferedReader bufferedReader = null;
	private LeagueDelegate delegate = null;

	public TerminalTransactions() {
	}


	/**
	 * Displays simple text interface
	 */ 
	public void showMainMenu(LeagueDelegate delegate) {
		this.delegate = delegate;
		
	    bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;
		
		while (choice != 5) {
			System.out.println();
			System.out.println("1. Insert playerStats");
			System.out.println("2. Delete playerStats");
			System.out.println("3. Update playerStats");
			System.out.println("4. Show playerStats");
			System.out.println("5. Quit");
			System.out.println("6. insert item");
			System.out.println("7. delete item");
			System.out.println("8. show item");
			System.out.println("9. playerStats selection");
			System.out.println("10. playerStats projection");
			System.out.println("11. insert playerEcon");
			System.out.println("12. delete playerEcon");
			System.out.println("13. show playerEcon");
			System.out.println("14. join");
			System.out.println("15. aggregate");
			System.out.println("16. aggregateHaving");
			System.out.println("18. nestedAggregated");
            System.out.println("17. division");
			System.out.print("Please choose one of the above 5 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
				case 1:  
					handleInsertOption(); 
					break;
				case 2:  
					handleDeleteOption(); 
					break;
				case 3: 
					handleUpdateOption();
					break;
				case 4:  
					delegate.showPlayerStats();
					break;
				case 5:
					handleQuitOption();
					break;
					case 6:
						handleInsertItem();
						break;
					case 7:
						handleDeleteItem();
						break;
					case 8:
						delegate.showOwnsItem();
						break;
					case 9:
						delegate.playerSelection();
						break;
					case 10:
//						delegate.playerProjection();
						break;
					case 11:
						handleInsertEcon();
						break;
					case 12:
						handleDeleteEcon();
						break;
					case 13:
						delegate.showPlayerEcon();
						break;
					case 14:
						handleJoinOption();
						break;
					case 15:
						delegate.aggregate();
						break;
					case 16:
						delegate.aggregateHaving();
						break;
          case 17:
            delegate.nestedAggregate();
            break;
					case 18:
            delegate.division();
						break;
					default:
					System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
					break;
				}
			}
		}		
	}
	
	private void handleDeleteOption() {
		int playerId = INVALID_INPUT;
		while (playerId == INVALID_INPUT) {
			System.out.print("Please enter the player ID you wish to delete: ");
			playerId = readInteger(false);
			if (playerId != INVALID_INPUT) {
				delegate.deletePlayerStats(playerId);
			}
		}
	}
	
	private void handleInsertOption() {
		int pid = INVALID_INPUT;
		while (pid == INVALID_INPUT) {
			System.out.print("Please enter the player ID you wish to insert: ");
			pid = readInteger(false);
		}
		
		String name = null;
		while (name == null || name.length() <= 0) {
			System.out.print("Please enter the player name you wish to insert: ");
			name = readLine().trim();
		}

		int cid = INVALID_INPUT;
		while (cid == INVALID_INPUT) {
			System.out.print("Please enter the champion ID you wish to insert: ");
			cid = readInteger(false);
		}

		String cname = null;
		while (cname == null || name.length() <= 0) {
			System.out.print("Please enter the champion name you wish to insert: ");
			cname = readLine().trim();
		}

		System.out.print("Please enter the mana you wish to insert: ");
		int mana = readInteger(true);

		System.out.print("Please enter the kills you wish to insert: ");
		int kills = readInteger(true);


		System.out.print("Please enter the health you wish to insert: ");
		int health = readInteger(true);

		System.out.print("Please enter cs you wish to insert: ");
		int cs = readInteger(true);


		System.out.print("Please enter the branch city you wish to insert: ");
		String rank = readLine();


		System.out.print("Please enter the map you wish to insert: ");
		int map = readInteger(true);


		
		PlayerStats model = new PlayerStats(pid,
											name,
											cid,
											cname,
											mana,
											health,
											cs,
											 kills,
											rank,
											map );
		delegate.insertPlayerStats(model);
	}

	private void handleDeleteItem() {
		int playerId = INVALID_INPUT;
		String name = "";
		while (playerId == INVALID_INPUT && name == "") {
			System.out.print("Please enter the player ID you wish to delete: ");
			playerId = readInteger(false);
			System.out.print("Please enter the item name you wish to delete: ");
			name = readLine().trim();
			if (playerId != INVALID_INPUT && name != "") {
				delegate.deleteOwnsItem(playerId, name);
			}


		}
	}

	private void handleInsertItem() {
		int pid = INVALID_INPUT;
		while (pid == INVALID_INPUT) {
			System.out.print("Please enter the player ID you wish to insert: ");
			pid = readInteger(false);
		}

		String name = null;
		while (name == null || name.length() <= 0) {
			System.out.print("Please enter the item name you wish to insert: ");
			name = readLine().trim();
		}


		System.out.print("Please enter the mana you wish to insert: ");
		int mana = readInteger(true);

		System.out.print("Please enter the armor you wish to insert: ");
		int armor = readInteger(true);


		System.out.print("Please enter the ap you wish to insert: ");
		int ap = readInteger(true);

		System.out.print("Please enter ad you wish to insert: ");
		int ad = readInteger(true);

		System.out.print("Please enter cost you wish to insert: ");
		int cost = readInteger(true);


		OwnsItem model = new OwnsItem(pid,
				name,
				mana,
				armor,
				ad,
				ap,
				cost);
		delegate.insertOwnsItem(model);
	}

	private void handleInsertEcon() {
		int creepScore = INVALID_INPUT;
		while (creepScore == INVALID_INPUT) {
			System.out.print("Please enter the creep score you wish to insert: ");
			creepScore = readInteger(false);
		}

		int kills = INVALID_INPUT;
		while (kills == INVALID_INPUT) {
			System.out.print("Please enter the kills you wish to insert: ");
			kills = readInteger(false);
		}


		System.out.print("Please enter the gold you wish to insert: ");
		int gold = readInteger(true);

		System.out.print("Please enter the level you wish to insert: ");
		int level = readInteger(true);



		PlayerEcon model = new PlayerEcon(creepScore,
				kills,
				gold,
				level);
		delegate.insertPlayerEcon(model);
	}


	private void handleDeleteEcon() {
		int creepScore = INVALID_INPUT;
		int kills = INVALID_INPUT;
		while (creepScore == INVALID_INPUT && kills == INVALID_INPUT) {
			System.out.print("Please enter the player ID you wish to delete: ");
			creepScore = readInteger(false);
			System.out.print("Please enter the kills you wish to delete: ");
			kills = readInteger(false);
			if (creepScore != INVALID_INPUT && kills != INVALID_INPUT) {
				delegate.deletePlayerEcon(creepScore, kills);
			}


		}
	}


	private void handleQuitOption() {
		System.out.println("Good Bye!");
		
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				System.out.println("IOException!");
			}
		}
		
		delegate.LeagueFinished();
	}
	
	private void handleUpdateOption() {
		int id = INVALID_INPUT;
		while (id == INVALID_INPUT) {
			System.out.print("Please enter the player ID you wish to update: ");
			id = readInteger(false);
		}
		
		String name = null;
		while (name == null || name.length() <= 0) {
			System.out.print("Please enter the player name you wish to update: ");
			name = readLine().trim();
		}

		delegate.updatePlayerStats(id, name);
	}

	private void handleJoinOption() {
		int mapID = INVALID_INPUT;
		while (mapID == INVALID_INPUT) {
			System.out.print("Please enter the mapID you wish to look at: ");
			mapID = readInteger(false);
		}
		delegate.joinPlayerTurret(mapID);
	}
	
	private int readInteger(boolean allowEmpty) {
		String line = null;
		int input = INVALID_INPUT;
		try {
			line = bufferedReader.readLine();
			input = Integer.parseInt(line);
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		} catch (NumberFormatException e) {
			if (allowEmpty && line.length() == 0) {
				input = EMPTY_INPUT;
			} else {
				System.out.println(WARNING_TAG + " Your input was not an integer");
			}
		}
		return input;
	}
	
	private String readLine() {
		String result = null;
		try {
			result = bufferedReader.readLine();
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}
}
