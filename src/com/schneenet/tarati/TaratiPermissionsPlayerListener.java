package com.schneenet.tarati;

import java.util.Map;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import com.schneenet.tarati.database.DatabaseException;
import com.schneenet.tarati.database.ForumUser;

public class TaratiPermissionsPlayerListener extends PlayerListener {

	private TaratiPermissionsPlugin plugin;
	
	public TaratiPermissionsPlayerListener(TaratiPermissionsPlugin instance) {
		this.plugin = instance;
	}
	
	public void onPlayerJoin(PlayerJoinEvent e) {
		try {
			// Perform that actual database lookup ASAP
			ForumUser forumUser = plugin.getDatabaseHandler().lookupForumUser(e.getPlayer().getName());
			if (forumUser != null) {
				// The user was registered on the forum!
				Map<Integer, TaratiPermissionGroup> groupMapping = plugin.getGroupMapping();
				if (groupMapping.containsKey(forumUser.getGroupId())) {
					// The user is in a forum group that is specified in the configuration
					// Get that group:
					TaratiPermissionGroup tGroup = groupMapping.get(forumUser.getGroupId());
					// Tell PEX they should be in that group:
					String[] newGroups = {tGroup.getPexName()};
					plugin.getPermissionManager().getUser(e.getPlayer()).setGroups(newGroups);
					// If we are using a white list, and the group is not white listed, kick them
					if (plugin.getUseWhitelist() && !tGroup.isWhitelisted()) {
						e.getPlayer().kickPlayer(plugin.getGroupNotAllowedKickMessage());
					}
				} else if (plugin.getUseWhitelist()) {
					// The user is in a forum group that is not specified in the configuration
					// If we are using a white list, kick them
					e.getPlayer().kickPlayer(plugin.getGroupNotAllowedKickMessage());
				}
			} else if (plugin.getUseWhitelist()) {
				// The user is not registered, if we are using a white list, kick them.
				e.getPlayer().kickPlayer(plugin.getNotRegisteredKickMessage());
			}
		} catch (DatabaseException e1) {
			// Should the database error out, they will be set to the default group by PEX. (Hopefully, that group has no permissions)
			TaratiPermissionsLogger.severe(e1.getMessage(), e1);
		}
	}
}
