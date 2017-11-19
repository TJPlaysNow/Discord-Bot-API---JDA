package com.pzg.www.discord.object;

/**
 * A Console Command.
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