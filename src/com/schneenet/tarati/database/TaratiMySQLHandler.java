package com.schneenet.tarati.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class TaratiMySQLHandler implements DatabaseHandler {

	private String dbUri;
	private String dbUser;
	private String dbPass;
	private String lookupQuery;

	private String lookupQueryUserNameCol;
	private String lookupQueryUserIdCol;
	private String lookupQueryGroupIdCol;
	private String lookupQueryIgnCol;

	private MysqlConnectionPoolDataSource mysqlDataSource;

	@Override
	public void initialize(String uri, String user, String password, String userTable, String userIdCol, String userNameCol, String ignCol, String groupIdCol) throws DatabaseException {
		dbUri = uri;
		dbUser = user;
		dbPass = password;

		// Connect to database
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mysqlDataSource = new MysqlConnectionPoolDataSource();
			mysqlDataSource.setUrl(dbUri);
			mysqlDataSource.setUser(dbUser);
			mysqlDataSource.setPassword(dbPass);
		} catch (ClassNotFoundException e) {
			throw new DatabaseException(e);
		}

		this.lookupQueryGroupIdCol = groupIdCol;
		this.lookupQueryIgnCol = ignCol;
		this.lookupQueryUserNameCol = userNameCol;
		this.lookupQueryUserIdCol = userIdCol;
		String query = "SELECT `&userIdCol`,`&userNameCol`,`&groupIdCol`,`&ignCol` FROM `" + userTable + "` WHERE `&ignCol` = '&ignValue'";
		this.setLookupQuery(query);

	}

	@Override
	public void setLookupQuery(String query) {
		// Process macros here
		this.lookupQuery = query.replace("&groupIdCol", this.lookupQueryGroupIdCol).replace("&ignCol", this.lookupQueryIgnCol).replace("&userIdCol", this.lookupQueryUserIdCol).replace("&userNameCol", this.lookupQueryUserNameCol).replace("&ignValue", "?");
	}

	@Override
	public ForumUser lookupForumUser(String ign) throws DatabaseException {
		try {
			Connection conn = null;
			try {
				conn = this.mysqlDataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(this.lookupQuery);
				stmt.setString(1, ign);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					return new ForumUser(rs.getInt(this.lookupQueryUserIdCol), rs.getInt(this.lookupQueryGroupIdCol), rs.getString(this.lookupQueryUserNameCol), rs.getString(this.lookupQueryIgnCol));
				} else {
					return null;
				}
			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
}
