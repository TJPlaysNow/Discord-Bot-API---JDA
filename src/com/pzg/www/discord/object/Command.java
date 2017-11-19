package com.pzg.www.discord.object;

import java.util.List;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * A Discord Command.
 * @version 1.0
 * @author TJPlaysNow
 */
public interface Command {
	/**
	 * This is used to tell what the command's label is.
	 * @return The command's label.
	 */
	public String getLabel();
	
	/**
	 * This is used for simple help commands.
	 * @return The command's help information.
	 */
	public String getDescription();
	
	/**
	 * The permission level needed to run this command.
	 * @return <b>Permission</b> to run the command.
	 */
	public Permission getPermissionNeeded();
	
	/**
	 * Called when the command is ran.
	 * @param user The user that ran the command.
	 * @param channel The channel the command was ran in.
	 * @param guild The guild the command was ran in.
	 * @param label The label of the command, (used for exact message).
	 * @param args The args sent with the command.
	 * @return True or False whether the command worked or not.
	 */
	public boolean run(User user, MessageChannel channel, Guild guild, String label, List<String> args);
}