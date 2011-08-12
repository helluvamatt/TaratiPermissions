package com.schneenet.tarati;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TaratiPermissionsPlugin extends JavaPlugin {
	
	private PermissionManager permissionManager;
	private HashMap<Integer,String> groupMapping = new HashMap<Integer,String>();
	
	private String pluginName;
	private String pluginVersion;
	
	@Override
	public void onDisable() {
		TaratiPermissionsLogger.info("Disabling " + pluginName + " v" + pluginVersion);
		// TODO UnRegister playerListener?
		// TODO Close database connections?
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
		
		// TODO Test database connection (persistent connection?)
		// TODO Load groupMapping from configuration
		// TODO Register playerListener
		
		TaratiPermissionsLogger.info("Enabled " + pluginName + " v" + pluginVersion);
		
	}
	
	public PermissionManager getPermissionManager() {
		return this.permissionManager;
	}
	
	public Map<Integer,String> getGroupMapping() {
		return this.groupMapping;
	}
	
	

}
