package main.java.command;

import test.java.command.PlaintextTestDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.*;

public class CommandLineInterface implements Runnable {
	
	// the logged in user (null if logged out)
	private static User user;
	// the reference to the database being interacted with
	private static Database database;
	
	public static String readPassword() {
		// TODO: read password in hidden style
		// return hashcode of password
		return null;
	}
	
	// list existing users/entries
	private static final String LIST = "list";
	// anything related to a user
	private static final String USER = "user";
	// request an entry for a user
	private static final String ENTRY = "entry";
	// generate a new password
	private static final String GEN = "gen";
	private static final String GENERATE = "generate";
	// help
	private static final String HELP = "help";
	// exit
	private static final String EXIT = "exit";
	
	public static boolean readInput(Database database) throws IOException, SQLException {
		BufferedReader reader =
				new BufferedReader(new InputStreamReader(System.in));
		String[] input = reader.readLine().toLowerCase().split(" ");
		switch(input[0]) {
			case LIST:
				list(input);
				break;
				
			case USER:
				user(input);
				break;
				
			case ENTRY:
				entry(input);
				break;
				
			case HELP:
				help(input);
				break;

			case GEN:
			case GENERATE:
				generate(input);
				break;

			case EXIT:
				System.out.println(EXIT_MSG);
				database.closeInterface();
				return false;
				
			default:
				throw new IOException("Invalid argument " + input[0]);
		}
		return true;
	}
	
	// users of this applications
	private static final String USERS = "users";
	// accounts entered for this user (else total?)
	private static final String ACCOUNTS = "accounts";
	// user count outputs
	private static final String USERS_HEADER = "Existing users:";
	private static final String USER_ENTRY = " - %s\n";
	private static final String USER_ENTRY_LOGGED_IN = " - %s (logged in)\n";
	// account counts output
	private static final String ACCTS_ALL_HEADER = "%d accounts currently exist: please log in to continue.\n";
	private static final String ACCTS_HEADER = "%s has %d accounts registered:\n";
	private static final String ACCTS_ENTRY = " - %s\n";

	public static void list(String[] input) throws IOException, SQLException {
		System.out.println("Read list command");
		// list user/accounts
		if(input.length != 2) {
			throw new IOException("Invalid number of arguments\nTODO: print usage");
		} else {
			switch(input[1]) {
				case USERS:
					System.out.println(USERS_HEADER);
					for(String s : database.getUsers()) {
						if(user.getUsername().equals(s)) {
							System.out.printf(USER_ENTRY_LOGGED_IN, s);
						} else {
							System.out.printf(USER_ENTRY);
						}
					}
					
				case ACCOUNTS:
					if(user != null) {
						String hashPassword = readPassword();
						System.out.printf(ACCTS_HEADER, user.getUsername(), user.getAccountNames(hashPassword).size());
						for(String acct : user.getAccountNames(hashPassword)) {
							System.out.printf(ACCTS_ENTRY, acct);
						}
					} else {
						System.out.printf(ACCTS_ALL_HEADER);
					}
				
				default:
					throw new IOException("Invalid argument " + input[1]);
			}
		}
	}
	
	// passwords are entered/changed on a new line in hidden text
	// log in as user input[2]
	private static final String LOGIN = "login";
	// create user with name input[2]
	private static final String CREATE = "create";
	// edit user with name input[2]
	private static final String UPDATE = "edit";
	
	public static void user(String[] input) throws IOException {
		System.out.println("Read user command");
		if(input.length < 3) {
			throw new IOException("Invalid number of arguments\nTODO: print usage");
		} else {
			switch(input[1]) {
				case LOGIN:
					System.out.println("TODO: log user in");
					break;
					
				case CREATE:
					System.out.println("TODO: create new account");
					break;
					
				case UPDATE:
					System.out.println("TODO: edit existing account");
					break;
					
				default:
					throw new IOException("Invalid argument " + input[1]);
			}
		}
	}
	
	// insert new entry
	private static final String ADD = "user";
	// get specific entry
	private static final String GET = "entries";
	
	public static void entry(String[] input) throws IOException {
		System.out.println("Read entry command (specific entry for user)");
		// TODO
		// if(!user.loggedIn())
		if(user == null) {
			throw new IOException("User must be logged in");
		} else if(input.length != 3) {
			throw new IOException("Invalid number of arguments\nTODO: print usage");
		} else {
			switch(input[1]) {
				case ADD:
					System.out.println("TODO: add user-specific entry");
					break;
					
				case GET:
					System.out.println("TODO: get user-specific entry");
					break;
					
				case UPDATE:
					System.out.println("TODO: update user-specific entry");
					break;
					
				default:
					throw new IOException("Invalid argument " + input[1]);
			}
		}
	}

//	public static final String

	public static void generate(String[] input) {

	}
	
	// collections of help data
	private static final Map<String, String[]> HELP_DATA;
	static {
		Map<String, String[]> baseMap = new HashMap<String, String[]>();
		// base help
		baseMap.put("help", new String[] {
				"List all existing users or, when logged in, all account entries for that user",
				"(otherwise all entries in table).",
				" list  users",
				"       accounts",
				"",
				"Log in as an existing user, create a new one, or update/delete an existing one.",
				" user  login <name>",
				"       create <name>",
				"       update <name>",
				"       delete <name>",
				"",
				"Add new entries to for a logged in user, as well as retrieve or update existing",
				"ones.",
				" entry add <title>",
				"       get <title>",
				"       update <title> <password>",
				"",
				" help [command]",
				" exit"
		});
		
		// list	users
		//		accounts
		baseMap.put("list", new String[] {
				"Lists all requested entries for database queries, use \'users\' to display all",
				"existing users, or \'accounts\' to display all existing accounts of the current",
				"user (otherwise all accounts if not yet logged in)."
		});
		
		// user login <name>
		//		create <name>
		//		update <name> (TODO)
		//		delete <name> (TODO)
		baseMap.put("user", new String[] {
				"Allows the user to create new accounts with \'create <name>\', log into an existing",
				"account with \'login <name>\', change an existing account's password with \'update",
				"<name>\', or delete an existing account and all associated entries with \'delete",
				"<name>\'."
		});
		
		// entry add <title>
		//		 get <title>
		//		 update <title> <password> (TODO)
		baseMap.put("entry", new String[] {
				"TODO"
		});
		
		// Help/Exit commands
		baseMap.put("help2", new String[] {
				"Lists all of the existing commands for this program, user help <command> for more",
				"information on a specific command."
		});
		baseMap.put("exit", new String[] {
				"Exits this program and returns to the main shell."
		});
		
		// init static map
		HELP_DATA = Collections.unmodifiableMap(baseMap);
	}
	
	public static void help(String[] input) {
		if(input.length > 1) {
			switch(input[1]) {
				case LIST:
					Arrays.asList(HELP_DATA.get("list")).forEach(System.out::println);
					break;
			
				case USER:
					Arrays.asList(HELP_DATA.get("user")).forEach(System.out::println);
					break;
					
				case ENTRY:
					Arrays.asList(HELP_DATA.get("entry")).forEach(System.out::println);
					break;
					
				case HELP:
					Arrays.asList(HELP_DATA.get("help2")).forEach(System.out::println);
					break;
					
				case EXIT:
					Arrays.asList(HELP_DATA.get("exit")).forEach(System.out::println);
					break;
					
				default:
					Arrays.asList(HELP_DATA.get("help")).forEach(System.out::println);
			}
		} else {
			Arrays.asList(HELP_DATA.get("help")).forEach(System.out::println);
		}
	}
	
	private static final String[] INTRODUCTION = {
			"TODO: welcome message/essay",
			"Version 0.1"
	};
	
	private static final String EXIT_MSG = "TODO: exit message";

	@Override
	public void run() {
		
		// connect to database
		final Database database;
		try {
			// TODO: make a proper database
//			database = new DatabaseInterface(null, null, null);
			database = new PlaintextTestDatabase();
//		} catch(SQLException e) {
		} catch(Exception e) {
//			System.out.println(e.getSQLState());
			System.out.println("DEBUG: exiting...");
			return;
		}
		
		// TODO: fix errors thrown to work like this:
		// list usr blah
		//       ^ invalid argument
		
		// TODO: set up interrupts [what did I need them for again? lost the train of thought]
		// -> something to do with verifying passwords
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				database.closeInterface();
			}
		});
		
		// print welcome
		Arrays.asList(INTRODUCTION).forEach(System.out::println);
		
		// main
		boolean active = true;
		while(active) {
			try {
				active = readInput(database);
			} catch(IOException e) {
				System.err.println("DEBUG: IO Exception");
				System.err.println(e.getMessage());
			} catch(SQLException e) {
				System.err.println("DEBUG: SQL Exception");
				System.err.println(e.getMessage());
			}
		}
	}
}
