package com.tjplaysnow.discord.object;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

/**
 * A Discord ProgramCommand.
 * @version 1.0
 * @author TJPlaysNow
 */
public abstract class ProgramCommand {
	
	/**
	 * This is used to tell what the command's label is.
	 *
	 * @return The command's label.
	 */
	public abstract String getLabel();
	
	/**
	 * This is for the new command system.
	 *
	 * @return The options the command should have.
	 */
	public abstract List<OptionData> getOptionData();
	
	/**
	 * This is used for simple help commands.
	 *
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
		return Permission.MESSAGE_SEND;
	}
	
	/**
	 * Called when the command is ran.
	 *
	 * @param event The slash command event triggered when the event is called.
	 */
	protected abstract boolean run(@NotNull SlashCommandInteractionEvent event);
	
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
	@Deprecated
	protected Consumer<Message> delete() {
		return (message) -> {/*message.delete().queueAfter(getDeleteTime(), TimeUnit.SECONDS)*/};
	}
}