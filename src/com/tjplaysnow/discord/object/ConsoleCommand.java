package com.tjplaysnow.discord.object;

/**
 * A Console ProgramCommand.
 * @version 1.0
 * @author TJPlaysNow
 */
public interface ConsoleCommand {
	/**
	 * This is used to tell what the command's label is.
	 * @return The command's label.
	 */
	public String getLabel();
	
	/**
	 * This is used to tell how to use the command.
	 * @return The command's usage.
	 */
	public String getUsage();
	
	/**
	 * This is used for simple help commands.
	 * @return The command's help information.
	 */
	public String getDescrition();
	
	/**
	 * Called when the command is ran.
	 * @param args The args sent with the command.
	 */
	public void run(String[] args);
}