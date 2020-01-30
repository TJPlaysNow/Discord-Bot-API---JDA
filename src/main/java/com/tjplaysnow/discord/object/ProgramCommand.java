package com.tjplaysnow.discord.object;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * A Discord ProgramCommand.
 * @version 1.0
 * @author TJPlaysNow
 */
public abstract class ProgramCommand {
	
	/**
	 * This is used to tell what the command's label is.
	 * @return The command's label.
	 */
	public abstract String getLabel();
	
	/**
	 * This is used for simple help commands.
	 * @return The command's help information.
	 */
	public String getDescription() {
		return "";
	}
	
	/**
	 * The permission level needed to run this command.
	 * @return <b>Permission</b> to run the command.
	 */
	public Permission getPermissionNeeded() {
		return Permission.MESSAGE_WRITE;
	}
	
	/**
	 * Called when the command is ran.
	 * @param user The user that ran the command.
	 * @param channel The channel the command was ran in.
	 * @param guild The guild the command was ran in.
	 * @param label The label of the command, (used for exact message).
	 * @param args The args sent with the command.
	 * @return True or False whether you want to delete the command message.
	 */
	protected abstract boolean run(User user, MessageChannel channel, Guild guild, String label, List<String> args);
	
	/**
	 * Get's the time to wait before calling <b>delete()</b>
	 * @return <b>int</b> Time to wait before deleting.
	 */
	protected int getDeleteTime() {
		return 5;
	}
	
	/**
	 * Use to delete messages after a period of time.<br>
	 * Default time is 5 seconds, use <b>getDeleteTime()</b>
	 * to specify how long it should wait.
	 */
	protected Consumer<Message> delete() {
		return (message) -> message.delete().queueAfter(getDeleteTime(), TimeUnit.SECONDS);
	}
}