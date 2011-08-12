package com.schneenet.tarati;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class TaratiPermissionsPlayerListener extends PlayerListener {

	private TaratiPermissionsPlugin plugin;
	
	public TaratiPermissionsPlayerListener(TaratiPermissionsPlugin instance) {
		this.plugin = instance;
	}
	
	public void onPlayerJoin(PlayerJoinEvent e) {
		// TODO If the user has not been assigned to a group (inGroup('default'))
		// plugin.getPermissionManager().getUser(e.getPlayer());
		// TODO    Look him up in the forum database
		// TODO    If the user has signed up for the forum
		// TODO       Assign his group from the group mapping
		// TODO    Else
		// TODO       Deny connection to server
		// e.getPlayer().kickPlayer(kickMessage)
	}
}
