package main.java.command;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import main.java.Cryptograph;
import main.java.encoder.Encoder;
import main.java.encoder.UTFEncoder;

public class User {

	private String username;
	private Encoder utfEncoder;
	private DatabaseInterface dbInterface;
	
	// TODO: this one for if we're logging in as a user from the start
	public User(String username, String password, String[] dbInfo) {
		this(dbInfo);
		
		// TODO: store encoded password locally, when requesting password again,
		// simply check equality against private variable here
		
		// takes nth random primes of range primes{50:150}
		int p = (int) Cryptograph.getNthPrime((int)(Math.random() * 100 + 50));
		int q = p;
		while(p == q) {
			// ensure p != q
			q = (int) Cryptograph.getNthPrime((int)(Math.random() * 100 + 50));
		}
		
		this.utfEncoder = new UTFEncoder(password, p, q);
		this.username = username;
		
	}

	// Takes dbInfo = {path, username, password}
	// TODO: else this one if we're just logging into database
	public User(String[] dbInfo) {
		this.dbInterface = connectToDatabase(dbInfo);
	}
	
	private DatabaseInterface connectToDatabase(String[] dbInfo) {
		
		// TODO: raise database username and password to the next tier?
		// TODO: figure out when/where to determine database location and login
		// details
		
		DatabaseInterface dbInterface = null;
		try {
			dbInterface = new DatabaseInterface(dbInfo[0], dbInfo[1], dbInfo[2]);
			System.out.println("DEBUG: successfully connected to database.");
		} catch (SQLException e) {
			System.err.println("DEBUG: failed to connect to database.");
			e.printStackTrace();
		}
		return dbInterface;
	}
	
	private boolean verifyPassword(String username, String password) {
		
		try {
			List<String> encodedUsernames = this.dbInterface.getUsers();
			for(String user : encodedUsernames) {
				if(username.equals(utfEncoder.decryptCiphertext(user))) {
					this.username = user;
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// TODO: encode username with password
		
		// TODO: request all usernames from database, if it matches an encoded
		// username, then it is correct
		
		// shouldn't have got here!
		return false;
	}
	
	public String getAcctPassword(String password, String acctName) {
		// TODO: verify password locally
		
		// TODO: return the password for requested account, or null
		return null;
	}
	
	public List<String> getAccountNames(String password) {
		// TODO: get all accounts as Map<Accounts.Location, Accounts.Username>
		
		return null;
	}
	
	public Map<String, String[]> getAcctData(String password) {
		// TODO: get all account data (excluding passwords) for general display
		
		return null;
	}
	
	public String getUsername() {
		return this.username;
	}
}
