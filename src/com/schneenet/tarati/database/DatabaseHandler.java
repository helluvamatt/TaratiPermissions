package com.schneenet.tarati.database;

public interface DatabaseHandler {

	/**
	 * Prepare this DatabaseHandler to start looking up forum users
	 * @param uri Database connection URI
	 * @param user Database user
	 * @param password Database password
	 * @param userTable Table containing user data (Example for SMF: smf_members)
	 * @param userIdCol Field for the user id
	 * @param userNameCol Field for the user name
	 * @param ignCol Field for the Minecraft user name
	 * @param groupIdCol Field for the group ID which will be mapped by this plugin to the Permissions group
	 */
	public void initialize(String uri, String user, String password, String userTable, String userIdCol, String userNameCol, String ignCol, String groupIdCol) throws DatabaseException;
	
	/**
	 * Set the lookup query to a custom query. The query can contain macros which take the form of &<macro_name> where <macro_name> is either either userIdCol, userNameCol, ignCol, groupIdCol, or ignValue
	 * The query must contain either the macro &ignValue or a single question mark ('?') to work properly.  
	 * @param query The SQL query string
	 */
	public void setLookupQuery(String query);
	
	/**
	 * Perform the actual lookup
	 * @param ign Minecraft player username
	 * @return ForumUser object representing the Minecraft player
	 */
	public ForumUser lookupForumUser(String ign) throws DatabaseException;
	
}
