package com.schneenet.tarati;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.schneenet.tarati.database.DatabaseException;
import com.schneenet.tarati.database.DatabaseHandler;
import com.schneenet.tarati.database.TaratiMySQLHandler;

public class TaratiPermissionsPlugin extends JavaPlugin {
	
	private PermissionManager permissionManager;
	private HashMap<Integer, TaratiPermissionGroup> groupMapping = new HashMap<Integer, TaratiPermissionGroup>();
	private DatabaseHandler databaseHandler;
	
	private String notRegisteredKickMessage;
	private String groupNotAllowedKickMessage;
	private boolean useWhitelist;
	
	private String pluginName;
	private String pluginVersion;
	
	@Override
	public void onDisable() {
		TaratiPermissionsLogger.info("Disabling " + pluginName + " v" + pluginVersion);
		TaratiPermissionsLogger.info("Disabled " + pluginName + " v" + pluginVersion);
	}

	@Override
	public void onEnable() {
		
		// Basic Plugin Setup
		pluginName = this.getDescription().getName();
		pluginVersion = this.getDescription().getVersion();
		TaratiPermissionsLogger.info("Enabling " + pluginName + " v" + pluginVersion);
		
		// Hook into PermissionsEx 
		Plugin permissionsEx = this.getServer().getPluginManager().getPlugin("PermissionsEx");
		if (permissionsEx != null) {
			permissionManager = PermissionsEx.getPermissionManager();
			TaratiPermissionsLogger.info("Hooked into " + permissionsEx.getDescription().getName() + " (v" + permissionsEx.getDescription().getVersion() + ").");
		} else {
			TaratiPermissionsLogger.warn("PermissionsEx plugin was not found or enabled.");
		}
		
		// Load Configuration
		Configuration config = this.getConfiguration();
		
		// Database configs
		databaseHandler = new TaratiMySQLHandler();
		
		try {
			databaseHandler.initialize(
					config.getString("bridge.database.uri"),
					config.getString("bridge.database.user"),
					config.getString("bridge.database.password"),
					config.getString("bridge.database.userTable"),
					config.getString("bridge.database.userIdCol"),
					config.getString("bridge.database.userNameCol"),
					config.getString("bridge.database.ignCol"),
					config.getString("bridge.database.groupIdCol"));
			String customLookupQuery = config.getString("bridge.database.customlookupquery", null);
			if (customLookupQuery != null) {
				databaseHandler.setLookupQuery(customLookupQuery);
			}
		} catch (DatabaseException e) {
			TaratiPermissionsLogger.severe(e.getMessage(), e);
			// The plugin is now useless, shut it down
			this.getPluginLoader().disablePlugin(this);
		}
		
		// Group mapping
		// Iterate over all specified groups from PermissionsEx looking for the configuration node:
		//   bridge.groups.<group>.gid
		// Example config portion:
		// bridge:
		//     groups:
		//         Admins:
		//             gid: 0
		//             whitelist: true
		//         Moderators:
		//             gid: 1
		//             whitelist: true
		//         Members:
		//             gid: 2
		//             whitelist: true
		PermissionGroup[] groups = permissionManager.getGroups();
		int size = groups.length;
		for (int i = 0; i < size; i++) {
			int gid = config.getInt("bridge.groups." + groups[i].getName() + ".gid", -1);
			boolean whitelisted = config.getBoolean("bridge.groups." + groups[i].getName() + ".whitelist", false);
			if (gid < 0) {
				TaratiPermissionsLogger.warn("No forum mapping for group '" + groups[i].getName() + "'!");
			} else {
				this.groupMapping.put(gid, new TaratiPermissionGroup(gid, groups[i].getName(), whitelisted));
			}
		}
		
		// Kick messages
		this.notRegisteredKickMessage = config.getString("bridge.kickmessage.notregistered", "Please register on our forum to play on our server!");
		this.groupNotAllowedKickMessage = config.getString("bridge.kickmessage.groupnotallowed", "You are not allowed on our server at this time!");
		
		// Whitelisting
		this.useWhitelist = config.getBoolean("bridge.whitelist.enable", false);
		
		// TODO Other configuration options
		
		// Register playerListener
		this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, new TaratiPermissionsPlayerListener(this), Priority.Lowest, this);
		
		TaratiPermissionsLogger.info("Enabled " + pluginName + " v" + pluginVersion);
		
	}
	
	public PermissionManager getPermissionManager() {
		return this.permissionManager;
	}
	
	public Map<Integer, TaratiPermissionGroup> getGroupMapping() {
		return this.groupMapping;
	}
	
	public String getNotRegisteredKickMessage() {
		return this.notRegisteredKickMessage;
	}
	
	public String getGroupNotAllowedKickMessage() {
		return this.groupNotAllowedKickMessage;
	}
	
	public DatabaseHandler getDatabaseHandler() {
		return this.databaseHandler;
	}
	
	public boolean getUseWhitelist() {
		return this.useWhitelist;
	}

}
