package test.java.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.command.Database;

public class PlaintextTestDatabase implements Database {
	
	// Users(Name[key])
	// Accounts(Location[key], Username[key], Owner[f-key], Password ... )
	
	private List<String> users;
	// Tom:
	// 	Google: email@google.com, password (etc.)
	//	Facebook: ...
	private Map<String, Map<String, List<String>>> accounts;

	public PlaintextTestDatabase() {
		// local encoders to generate database

		// collections of test user
		String[] users = {
				"Tom",
				"Dave",
				"Frank"
		};
		String[] passwords = {
				"12345",
				"password",
				"random"
		};
		// TODO: encode user and enter into local list
		for(int i = 0; i < users.length; i++) {
//			String entry = Encoder.encode(user, password);
		}
		// Tom
		
	}

	@Override
	@Deprecated
	public void closeInterface() {
		// nothing required
	}

	@Override
	@Deprecated
	public ResultSet newQuery(String user, String password, String query) {
		// Don't use this here
		return null;
	}

	@Override
	@Deprecated
	public void closeQuery(ResultSet results) {
		// nothing required
	}

	@Override
	@Deprecated
	public boolean setupUsersTable() {
		// nothing required
		return false;
	}

	@Override
	@Deprecated
	public boolean setupAccountsTable() {
		// nothing required
		return false;
	}

	@Override
	public boolean insertUser(String user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertEntry(String user, String password, String acctName,
			String acctPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNoUsers(String user, String password) {
		return this.users.size();
	}

	@Override
	public List<String> getUsers() {
		return new ArrayList<String>(this.users);
	}

	@Override
	public Map<String, Integer> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNoUserAccounts(String user, String password) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<List<String>> getUserAccounts(String user, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUserAccountDetails(String user, String password,
			String acctName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword(String user, String password, String accountName) {
		// TODO Auto-generated method stub
		return null;
	}

}
