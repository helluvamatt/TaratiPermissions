package com.schneenet.tarati;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class TaratiPermissionsPlayerListener extends PlayerListener {

	private TaratiPermissionsPlugin plugin;

	public TaratiPermissionsPlayerListener(TaratiPermissionsPlugin instance) {
		this.plugin = instance;
	}

	public void onPlayerJoin(PlayerJoinEvent e) {
		this.plugin.processPlayer(e.getPlayer());
	}
}
