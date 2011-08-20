package com.schneenet.tarati;

public class TaratiPermissionGroup {

	private int forumGid;
	private String pexName;
	private boolean whitelisted;

	public TaratiPermissionGroup(int fgid, String pName, boolean wl) {
		this.forumGid = fgid;
		this.pexName = pName;
		this.whitelisted = wl;
	}

	public boolean isWhitelisted() {
		return this.whitelisted;
	}

	public String getPexName() {
		return this.pexName;
	}

	public int getForumGroupId() {
		return this.forumGid;
	}

}
