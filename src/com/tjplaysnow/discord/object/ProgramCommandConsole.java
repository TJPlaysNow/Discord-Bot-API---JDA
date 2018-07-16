package com.tjplaysnow.discord.object;

/**
 * A Console ProgramCommand.
 * @version 1.0
 * @author TJPlaysNow
 */
public abstract class ProgramCommandConsole {
	
	/**
	 * This is used to tell what the command's label is.
	 * @return The command's label.
	 */
	public abstract String getLabel();
	
	/**
	 * This is used to tell how to use the command.
	 * @return The command's usage.
	 */
	public String getUsage() {
		return "";
	}
	
	/**
	 * This is used for simple help commands.
	 * @return The command's help information.
	 */
	public String getDescription() {
		return "";
	}
	
	/**
	 * Called when the command is ran.
	 * @param args The args sent with the command.
	 */
	public abstract void run(String[] args);
}