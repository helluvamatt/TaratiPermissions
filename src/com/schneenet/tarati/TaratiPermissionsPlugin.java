package com.schneenet.tarati;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TaratiPermissionsPlugin extends JavaPlugin {
	
	private PermissionManager permissionManager;
	private String pluginName;
	private String pluginVersion;
	
	@Override
	public void onDisable() {
		
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
		
	}

}
