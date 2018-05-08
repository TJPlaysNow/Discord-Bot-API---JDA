package com.tjplaysnow.discord.object.logger;

/**
 * <b>The Log Level of a Message</b>
 * <p>Used to determine how to log
 * a message to console.</p>
 * 
 * @author TJPlaysNow
 */
public enum LogLevel {
	/**
	 * <b>Log as spam</b>
	 * <p>Not visible unless
	 * in debug mode</p>
	 */
	SPAM(0),
	
	/**
	 * <b>Log as info</b>
	 * <p>Visible in
	 * deffault mode</p>
	 */
	INFO(1),
	
	/**
	 * <b>Log warnings</b>
	 * <p>Will normally
	 * show up.</p>
	 */
	WARN(2),
	
	/**
	 * <b>Log errors</b>
	 * <p>Will almost
	 * always log.</p>
	 */
	ERROR(3),
	
	/**
	 * <b>Log nothing</b>
	 * <p>This mode won't
	 * log anything to the
	 * console.</p>
	 */
	NONE(4);
	
	private int level;
	
	private LogLevel(int level) {
		this.level = level;
	}
	
	/**
	 * <b>Get the log level</b>
	 * <p>The higher the log level
	 * the more important.</p>
	 * 
	 * @return The log level in form of int.
	 */
	public int getLevel() {
		return level;
	}
}