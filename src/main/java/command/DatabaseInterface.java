package main.java.command;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Skeleton notes for the moment:
 * 
 * A collection of database queries that can be called upon by any part of the
 * program.
 * 
 * AT NO POINT should any other section be capable of communicating with the
 * database, in an effort to restrict all database debugging to this single class.
 * 
 * TODO: create a test class that builds a fresh database for the test case, creates
 * and executes queries on it and then destroys the database at completion.
 * Probably have it as a separate test set to avoid building a million databases
 * when running tests on this stuff.
 * 
 * @author Eamon O'Brien
 *
 */
public class DatabaseInterface implements Database {
	
	// The internal connection to the database, initialised at creation, and
	// must be collapsed at the end of the program.
	private Connection conn;
	
	/**
	 * The primary interface for the database: this class holds all SQL clauses
	 * internally and can return "Java-fied" versions of it for user by the main
	 * program. Since this class has broken a lot of supplied code into fragments
	 * for my own learning purposes, its creation must be ensured to maintain
	 * the ability of the program, and on exit it must again be called upon to
	 * close that same connection.
	 * 
	 * @require	databasePath is an appropriate path to an existing database, or
	 * 			to a location that one can be created, and it not null.
	 * 			login is an existing user for the same database, and not null
	 * 			password is the associated login password for the account and
	 * 			is also not null, except in the case where the user's password
	 * 			is also null.
	 * 
	 * @ensure	the correct database has been linked to, and that all data is of
	 * 			the correct format when initialising from scratch.
	 * 
	 * @throws SQLException
	 * 			when any SQL related clauses fail to be enacted on initialising
	 * 			or connecting to the database.
	 */
	public DatabaseInterface(String databasePath, String login, String password) throws SQLException {
		// https://www.ntu.edu.sg/home/ehchua/programming/java/JDBC_Basic.html
		// https://stackoverflow.com/questions/41233/java-and-sqlite
		// http://www.kfu.com/~nsayer/Java/dyn-jdbc.html
		// https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html
		
		// basic setup
		this.conn = DriverManager.getConnection(databasePath, login, password);
		
		// setup tables
		if(!this.setupUsersTable()) {
			throw new SQLException();
		}
		if(!this.setupAccountsTable()) {
			throw new SQLException();
		}
		
		// https://www.javatpoint.com/java-jdbc
		
		// https://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html
		// https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
		
	}
	
	// https://stackoverflow.com/a/2225275
	@Override
	public void closeInterface() {
		// TODO: add commands to close/exit database
		System.out.println("TODO: ensure exit successful");
		try { this.conn.close(); } catch (Exception e) { /* ignored */ }
	}
	
	// New tables format:
	
//	  Statement stat = conn.createStatement();
//    stat.executeUpdate("drop table if exists people;");
//    stat.executeUpdate("create table people (name, occupation);");
	
	@Override
	public boolean setupUsersTable() {
		Statement stat = null;
		try {
			stat = conn.createStatement();
			stat.executeUpdate(USERS_CREATE_TABLE_STATEMENT);
			stat.executeUpdate(USERS_ALTER_KEY_STATEMENT);
		} catch(SQLException e) {
			System.err.println("DEBUG: caught SQL error on Users table setup");
			System.err.println(e.getMessage());
			return false;
		} finally {
			try { stat.close(); } catch(Exception e) { /* ignore */ }
		}
		return true;
	}

	@Override
	public boolean setupAccountsTable() {
		Statement stat = null;
		try {
			stat = conn.createStatement();
			stat.executeUpdate(ACCOUNTS_CREATE_TABLE_STATEMENT);
			stat.executeUpdate(ACCOUNTS_ALTER_KEY_STATEMENT);
		} catch(SQLException e) {
			System.err.println("DEBUG: caught SQL error on Accounts table setup");
			System.err.println(e.getMessage());
			return false;
		} finally {
			try { stat.close(); } catch(Exception e) { /* ignore */ }
		}
		return true;
	}
	
	// Insert format:
	
//	  PreparedStatement prep = conn.prepareStatement(
//            "insert into people values (?, ?);");
//
//    prep.setString(1, "Gandhi");
//    prep.setString(2, "politics");
//    prep.addBatch();

	@Override
	public boolean insertUser(String user) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(String.format(INSERT_USER, user));
		} catch(SQLException e) {
			System.err.println("DEBUG: caught SQL error on insert user");
			System.err.println(e.getMessage());
			return false;
		} finally {
			try { stmt.close(); } catch(Exception e) { /* ignore */ }
		}
		return true;
	}

	@Override
	public boolean insertEntry(String user, String password, String acctName, String acctPassword) {
		// ensure get password is correct
		
		// insert to Accounts table
		
		return false;
	}
	
	// Query format:
	
//	  ResultSet rs = stat.executeQuery("select * from people;");
//    while (rs.next()) {
//        System.out.println("name = " + rs.getString("name"));
//        System.out.println("job = " + rs.getString("occupation"));
//    }
//    rs.close();

	@Override
	public ResultSet newQuery(String user, String password, String query)
			throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// check for results
			stmt = conn.createStatement();
			rs = stmt.executeQuery(USERS_GET_NAMES_QUERY);
		} catch(SQLException e) {
			// TODO: handle exception
			System.err.println("DEBUG: SQL Exception raised");
			System.err.println(e.getLocalizedMessage());
			return null;
		} finally {
			try { stmt.close(); } catch(Exception e) { /* ignore */ }
			// close connection at termination
		}
		return rs;
	}

	@Override
	public void closeQuery(ResultSet results) throws SQLException {
		try { results.close(); } catch(Exception e) { /* ignore */ }
	}

	@Override
	public int getNoUsers(String user, String password) throws SQLException {
		List<String> users = getUsers();
		return (users == null ? null : users.size());
	}

	@Override
	public List<String> getUsers() throws SQLException {
		ResultSet rs = newQuery(null, null, USERS_GET_NAMES_QUERY);
		List<String> results = new ArrayList<String>();
		while(rs.next()) {
			// TODO: check what column it should be from
			results.add(rs.getString(0));
			System.out.println("Col 0: " + rs.getString(0));
			System.out.println("Col 1: " + rs.getString(1));
		}
		closeQuery(rs);
		return results;
	}

	@Override
	public Map<String, Integer> getAllAccounts() throws SQLException {
		// TODO: get me a list like:
		// User A: N accounts
		// User B: M accounts
		// ...
		return null;
	}

	@Override
	public int getNoUserAccounts(String user, String password) throws SQLException {
		List<List<String>> userAccounts = getUserAccounts(user, password);
		return (userAccounts == null ? null : userAccounts.size());
	}

	@Override
	public List<List<String>> getUserAccounts(String user, String password)
			throws SQLException {
		ResultSet rs = newQuery(user, password, ACCOUNTS_GET_USER_ACCOUNTS_QUERY);
		List<List<String>> results = new ArrayList<List<String>>();
		while(rs.next()) {
			// TODO: check what column it should be from
			results.add(Arrays.asList(rs.getString(0), rs.getString(1)));
			System.out.println("Col 0: " + rs.getString(0));
			System.out.println("Col 1: " + rs.getString(1));
		}
		closeQuery(rs);
		return results;
	}

	@Override
	public List<String> getUserAccountDetails(String user, String password,
			String acctName) throws SQLException {
		ResultSet rs = newQuery(user, password, ACCOUNTS_GET_SINGLE_USER_ACCOUNT_QUERY);
		List<String> results = new ArrayList<String>();
		while(rs.next()) {
			// TODO: check what column it should be from
			results.add(rs.getString(0));
			System.out.println("Col 0: " + rs.getString(0));
			results.add(rs.getString(1));
			System.out.println("Col 1: " + rs.getString(1));
			results.add(rs.getString(2));
			System.out.println("Col 0: " + rs.getString(2));
			results.add(rs.getString(3));
			System.out.println("Col 1: " + rs.getString(3));
		}
		closeQuery(rs);
		return results;
	}

	@Override
	public String getPassword(String user, String password, String accountName)
			throws SQLException {
		ResultSet rs = newQuery(user, password, ACCOUNTS_GET_PASSWORD_QUERY);
		List<String> results = new ArrayList<String>();
		while(rs.next()) {
			// TODO: check what column it should be from
			results.add(rs.getString(0));
			System.out.println("Col 0: " + rs.getString(0));
			results.add(rs.getString(1));
			System.out.println("Col 1: " + rs.getString(1));
			results.add(rs.getString(2));
			System.out.println("Col 0: " + rs.getString(2));
			results.add(rs.getString(3));
			System.out.println("Col 1: " + rs.getString(3));
		}
		closeQuery(rs);
		// TODO: ensure only 1 result
		// TODO: decode result and give back to user
		return results.get(0);
	}
	
}
