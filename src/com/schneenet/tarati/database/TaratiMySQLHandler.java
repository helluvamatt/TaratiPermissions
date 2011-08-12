package com.schneenet.tarati.database;

public class TaratiMySQLHandler implements DatabaseHandler {

	private String dbUri;
	private String dbUser;
	private String dbPass;
	private String lookupQuery;
	
	@Override
	public void initialize(String uri, String user, String password, String userTable, String profileTable, String userIdCol, String ignCol, String groupIdCol) {
		dbUri = uri;
		dbUser = user;
		dbPass = password;
		
		// TODO Connect to database
		
		// TODO Make the query based on the passed in values
	}

	@Override
	public void setLookupQuery(String query) {
		this.lookupQuery = query;
	}

	@Override
	public ForumUser lookupForumUser(String ign) {
		// TODO Perform the lookup on the database
		return null;
	}

}
