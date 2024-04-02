package ui;

import delegates.LeagueDelegate;
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
	 * Sets up the database to have a branch table with two tuples so we can insert/update/delete from it.
	 * Refer to the databaseSetup.sql file to determine what tuples are going to be in the table.
	 */
	public void setupDatabase(LeagueDelegate delegate) {
		this.delegate = delegate;

		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;

		while(choice != 1 && choice != 2) {
			System.out.println("If you have a table called Branch in your database (capitialization of the name does not matter), it will be dropped and a new Branch table will be created.\nIf you want to proceed, enter 1; if you want to quit, enter 2.");

			choice = readInteger(false);

			if (choice != INVALID_INPUT) {
				switch (choice) {
				case 1:
					delegate.databaseSetup();
					break;
				case 2:
					handleQuitOption();
					break;
				default:
					System.out.println(WARNING_TAG + " The number that you entered was not a valid option.\n");
					break;
				}
			}
		}
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
			System.out.println("1. Insert ______");
			System.out.println("2. Delete ______");
			System.out.println("3. Update ______");
			System.out.println("4. Show playerStats");
			System.out.println("5. Quit");
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

		System.out.print("Please enter the mana you wish to insert: ");
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
