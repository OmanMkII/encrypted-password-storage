package main.java.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Database {

	// Users(Name[key], N, E)
	// Accounts(Location[key], Username[key], Owner[f-key], Password ... )
	
	// The name is encoded, thus we decode it with the private key and if the 
	// resulting string matches the username given, it's correct!
	
	// Location & Username are already distinct set since they're part of a
	// database somewhere else.

	// TODO: documentation
	public void closeInterface();

	// TODO: documentation
	public ResultSet newQuery(String user, String password, String query)
			throws SQLException;

	// TODO: documentation
	public void closeQuery(ResultSet results) throws SQLException;

	public static final String USERS_CREATE_TABLE_STATEMENT =
		"CREATE TABLE Users ("
			+ "Name varchar(255), "
			+ "N int, "
			+ "E int"
		+ ");";
	
	public static final String USERS_ALTER_KEY_STATEMENT =
		"ALTER TABLE User ("
			+ "ADD PRIMARY KEY (Name)"
		+ ");";

	// TODO: documentation
	public boolean setupUsersTable() throws SQLException;
	
	public static final String ACCOUNTS_CREATE_TABLE_STATEMENT =
		"CREATE TABLE Accounts ("
			+ "Location varchar(255),"
			+ "Username varchar(255),"
			+ "Owner varchar(255),"
			+ "Password varchar(255),"
			// ...
			+ "FOREIGN KEY (Owner) REFERENCES Users(Name)"
		+ ");";
	
	public static final String ACCOUNTS_ALTER_KEY_STATEMENT =
			  "ALTER TABLE Accounts"
			+ "ADD CONSTRAINT PK_ACCT_OWNER PRIMARY KEY (Location, Username);";

	// TODO: documentation
	public boolean setupAccountsTable() throws SQLException;
	
	public static final String INSERT_USER = "INSERT INTO Users (Name) "
			+ "VALUES (%s)";

	// TODO: documentation
	public boolean insertUser(String user) throws SQLException;
	
	public static final String INSERT_ACCOUNT = null;

	// TODO: documentation
	public boolean insertEntry(String user, String password, String acctName,
			String acctPassword) throws SQLException;

	public static final String USERS_GET_NAMES_QUERY =
			  "SELECT * "
			+ "FROM Users "
			+ "WHERE NOT Name = \"root\";";

	// TODO: documentation
	public int getNoUsers(String user, String password) throws SQLException;

	// TODO: documentation
	public List<String> getUsers() throws SQLException;
	
	public static final String USERS_GET_ALL_ACCOUNTS = null;
	
	// SELECT Users.Name, Count(Accounts.Username)
	// FROM Users, Accounts
	// WHERE Users.Name = Accounts.Owner
	// TODO: check if this is valid
	// GROUP BY Users.Owner

	// TODO: documentation
	public Map<String, Integer> getAllAccounts() throws SQLException;
	
	public static final String ACCOUNTS_GET_USER_ACCOUNTS_QUERY =
			  "SELECT Accounts.Location, Accounts.Username "
			+ "FROM Users, Accounts "
			+ "WHERE Users.Name = Accounts.Owner "
				+ "AND Users.Name = user "
				+ "AND Users.Password = password;";

	// TODO: documentation
	public int getNoUserAccounts(String user, String password) throws SQLException;

	// TODO: documentation
	public List<List<String>> getUserAccounts(String user, String password)
			throws SQLException;

	public static final String ACCOUNTS_GET_SINGLE_USER_ACCOUNT_QUERY =
			  "SELECT Accounts.* "
			+ "FROM Users, Accounts "
			+ "WHERE Users.Name = Accounts.Owner "
				+ "AND Users.Name = user "
				+ "AND Users.Password = password "
				+ "AND Accounts.Location = acctName;";

	// TODO: documentation
	public List<String> getUserAccountDetails(String user, String password,
			String acctName) throws SQLException;
	
	public static final String ACCOUNTS_GET_PASSWORD_QUERY =
			  "SELECT Accounts.Location, Accounts.Username, Accounts.Password"
			+ "FROM Users, Accounts"
			+ "WHERE Users.Name = Accounts.Owner"
				+ "AND Users.Name = user"
				+ "AND Users.Password = password"
				+ "AND Accounts.Username = accountName;";

	// TODO: documentation
	public String getPassword(String user, String password, String accountName)
			throws SQLException;
	
}
