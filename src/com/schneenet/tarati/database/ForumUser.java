package com.schneenet.tarati.database;

public class ForumUser {

	private int userId;
	private int groupId;
	private String username;
	private String mcUsername;
	
	public ForumUser(int uid, int gid, String user, String ign) {
		this.userId = uid;
		this.groupId = gid;
		this.username = user;
		this.mcUsername = ign;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public int getGroupId() {
		return this.groupId;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getMinecraftUsername() {
		return this.mcUsername;
	}
	
}
