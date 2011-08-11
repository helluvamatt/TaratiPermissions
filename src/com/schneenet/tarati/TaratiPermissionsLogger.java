package com.schneenet.tarati;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TaratiPermissionsLogger {

	private static Logger logger = Logger.getLogger("Minecraft");
	
	public static void info(String message) {
		logger.info("[TaratiPermissions] " + message);
	}
	
	public static void warn(String message) {
		logger.warning("[TaratiPermissions] " + message);
	}
	
	public static void severe(String message) {
		logger.severe("[TaratiPermissions] " + message);
	}
	
	public static void severe(String message, Throwable th) {
		logger.log(Level.SEVERE, message, th);
	}
	
}
