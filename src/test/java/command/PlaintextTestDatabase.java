package test.java.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.java.command.Database;
import main.java.command.DatabaseInterface;
import main.java.encoder.Encoder;
import main.java.encoder.UTFEncoder;

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
	public boolean setupUsersTable() {
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
//			String user = UTFEncoder.encryptText(users[i], passwords[i]);
		}
		
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
		return this.users;
	}

	@Override
	public Map<String, Integer> getAllAccounts() {
		Map<String, Integer> accounts = new HashMap<>();
		for(Entry<String, Map<String, List<String>>> entry : this.accounts.entrySet()) {
			if(!accounts.containsKey(entry.getKey())) {
				accounts.put(entry.getKey(), 1);
			} else {
				accounts.put(entry.getKey(), accounts.get(entry.getKey()) + 1);
			}
		}
		return accounts;
	}

	@Override
	public int getNoUserAccounts(String user, String password) {
		return getUserAccounts(user, password).size();
	}

	@Override
	public List<List<String>> getUserAccounts(String user, String password) {
		List<List<String>> users = new ArrayList<>();
		for(Entry<String, Map<String, List<String>>> entry : this.accounts.entrySet()) {
			if(entry.getKey().equals(user)) {
				users.addAll(entry.getValue().values());
			}
		}
		return users;
	}

	@Override
	public String getPassword(String user, String password, String accountName) {
		List<String> data = getUserAccountDetails(user, password, accountName);
		return data.get(DatabaseInterface.ENTRIES.get("PASSWORD"));
	}

	@Override
	public List<String> getUserAccountDetails(String user, String password,
			String acctName) {
		List<String> data = new ArrayList<>();
		// find user
		for(Entry<String, Map<String, List<String>>> entry : this.accounts.entrySet()) {
			if(entry.getKey().equals(user)) {
				// got user, now for acct
				for(Entry<String, List<String>> e : entry.getValue().entrySet()) {
					if(e.getKey().equals(acctName)) {
						return e.getValue();
					}
				}
			} else {
				continue;
			}
		}
		// failed
		return null;
	}

}
