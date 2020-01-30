package com.tjplaysnow.discord.object;

import com.tjplaysnow.discord.config.Config;
import com.tjplaysnow.discord.config.File;
import com.tjplaysnow.discord.object.logger.LogLevel;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.internal.utils.PermissionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Bot extends ProgramBot {
	
	private String prefix;
	
	private List<String> badWords;
	private List<User> mutedUsers;
	private List<ProgramCommand> commands;
	
	private Runnable loadAction;
	private Consumer<MessageReceivedEvent> mutedUserRun;
	private Consumer<MessageReceivedEvent> badWordRun;
	private BiConsumer<MessageReceivedEvent, ProgramCommand> commandRun;
	
	private List<Predicate<GenericEvent>> events;
	private Config config;
	
	private ProgramThread botThread;
	private ProgramConsoleCommandManager consoleCommandManager;
	
	public Bot(String token, String prefix) {
		super(token);
		
		this.prefix = prefix;
		
		badWords = new ArrayList<>();
		mutedUsers = new ArrayList<>();
		commands = new ArrayList<>();
		
		loadAction = () -> {};
		events = new ArrayList<>();
		
		mutedUserRun = (event) -> {
			if (PermissionUtil.checkPermission(Objects.requireNonNull(event.getMember()), Permission.ADMINISTRATOR)) {
				event.getChannel().sendMessage("You are muted, but you're an admin so oh well.").complete();
			} else {
				event.getMessage().delete().complete();
				Message message = event.getChannel().sendMessage("You are muted " + event.getAuthor().getAsMention() + ".").complete();
				botThread.addAction(() -> message.delete().queue(), 5);
			}
		};
		
		badWordRun = (event) -> {
			event.getMessage().delete().complete();
			Message message = event.getChannel().sendMessage("Watch your language " + event.getAuthor().getAsMention() + "!!").complete();
			botThread.addAction(() -> message.delete().queue(), 5);
		};
		
		commandRun = (event, command) -> {
			if (event.getMessage().getContentRaw().startsWith(getPrefix())) {
				User sender = event.getAuthor();
				MessageChannel channel = event.getChannel();
				Guild guild = event.getGuild();
				String[] commandArgs = event.getMessage().getContentRaw().substring(getPrefix().length()).split(" ");
				List<String> args = new ArrayList<>();
				Collections.addAll(args, commandArgs);
				args.remove(0);
				if (commandArgs[0].equalsIgnoreCase(command.getLabel())) {
					if (command.getPermissionNeeded().equals(Permission.MESSAGE_WRITE)) {
						if (event.getChannel().getType().equals(ChannelType.PRIVATE)) {
							boolean delete = command.run(sender, channel, guild, commandArgs[0], args);
							if (delete) {
								event.getMessage().delete().complete();
							}
							return;
						}
					}
					if (PermissionUtil.checkPermission(Objects.requireNonNull(event.getMember()), command.getPermissionNeeded())) {
						logger().info("Running command " + command.getLabel());
						boolean delete = command.run(sender, channel, guild, commandArgs[0], args);
						if (delete) {
							event.getMessage().delete().complete();
						}
					}
				}
			}
		};
		
		build();
	}
	
	public Bot(String token, String prefix, LogLevel logLevel) {
		super(token, logLevel);
		
		this.prefix = prefix;
		
		badWords = new ArrayList<>();
		mutedUsers = new ArrayList<>();
		
		loadAction = () -> {};
		events = new ArrayList<>();
		commands = new ArrayList<>();
		
		mutedUserRun = (event) -> {
			if (PermissionUtil.checkPermission(Objects.requireNonNull(event.getMember()), Permission.ADMINISTRATOR)) {
				event.getChannel().sendMessage("You are muted, but you're an admin so oh well.").complete();
			} else {
				event.getMessage().delete().complete();
				Message message = event.getChannel().sendMessage("You are muted " + event.getAuthor().getAsMention() + ".").complete();
				botThread.addAction(() -> message.delete().queue(), 5);
			}
		};
		
		badWordRun = (event) -> {
			event.getMessage().delete().complete();
			Message message = event.getChannel().sendMessage("Watch your language " + event.getAuthor().getAsMention() + "!!").complete();
			botThread.addAction(() -> message.delete().queue(), 5);
		};
		
		commandRun = (event, command) -> {
			if (event.getMessage().getContentRaw().startsWith(getPrefix())) {
				User sender = event.getAuthor();
				MessageChannel channel = event.getChannel();
				Guild guild = event.getGuild();
				String[] commandArgs = event.getMessage().getContentRaw().substring(getPrefix().length()).split(" ");
				List<String> args = new ArrayList<>();
				Collections.addAll(args, commandArgs);
				args.remove(0);
				if (commandArgs[0].equalsIgnoreCase(command.getLabel())) {
					if (command.getPermissionNeeded().equals(Permission.MESSAGE_WRITE)) {
						if (event.getChannel().getType().equals(ChannelType.PRIVATE)) {
							command.run(sender, channel, guild, commandArgs[0], args);
							return;
						}
					}
					if (PermissionUtil.checkPermission(Objects.requireNonNull(event.getMember()), command.getPermissionNeeded())) {
						logger().info("Running command " + command.getLabel());
						boolean delete = command.run(sender, channel, guild, commandArgs[0], args);
						if (delete) {
							event.getMessage().delete().complete();
						}
					}
				}
			}
		};
		
		build();
	}
	
	public void setBotThread(ProgramThread botThread) {
		this.botThread = botThread;
	}
	
	public void setConsoleCommandManager(ProgramConsoleCommandManager consoleCommandManager) {
		this.consoleCommandManager = consoleCommandManager;
	}
	
	public void setMutedUserRun(Consumer<MessageReceivedEvent> mutedUserRun) {
		this.mutedUserRun = mutedUserRun;
	}
	
	public void setBadWordRun(Consumer<MessageReceivedEvent> badWordRun) {
		this.badWordRun = badWordRun;
	}
	
	public void setCommandRun(BiConsumer<MessageReceivedEvent, ProgramCommand> commandRun) {
		this.commandRun = commandRun;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * Action to use on ProgramBot loadup.
	 * @param loadAction The action to be run.
	 */
	public void setLoadAction(Runnable loadAction) {
		this.loadAction = loadAction;
	}
	
	/**
	 * Add a command to the bot.
	 * @param command A new command.
	 */
	public void addCommand(ProgramCommand command) {
		commands.add(command);
	}
	
	/**
	 * Setup the config before using get config because the config isn't created until this is called.
	 */
	public void setupConfig() {
		config = new Config(new File("Config.txt"));
	}
	
	/**
	 * Get the bot's default config.
	 * @return Config
	 */
	public Config getConfig() {
		return config;
	}
	
	/**
	 * Add a command to the bot.
	 * @param command A new command.
	 */
	@Deprecated
	public void addCommand(Command command) {
		commands.add(new ProgramCommand() {
			@Override
			public String getLabel() {
				return command.getLabel();
			}
			@Override
			public Permission getPermissionNeeded() {
				return command.getPermissionNeeded();
			}
			@Override
			public String getDescription() {
				return command.getDescription();
			}
			@Override
			protected int getDeleteTime() {
				return command.getDeleteTime();
			}
			@Override
			protected boolean run(User user, MessageChannel channel, Guild guild, String label, List<String> args) {
				return command.run(user, channel, guild, label, args);
			}
		});
	}
	
	@Override
	public void logout() {
		botThread.stop();
		consoleCommandManager.stop();
		super.logout();
	}
	
	@Deprecated
	public ProgramThread getBotThread() {
		return botThread;
	}
	
	public List<ProgramCommand> getCommands() {
		return commands;
	}
	
	/**
	 * Add a new console command to the bot.<br>
	 * <i>Console commands mainly used for non-spigot.</i>
	 * @param command The new command to add.
	 */
	public void addConsoleCommand(ProgramCommandConsole command) {
		consoleCommandManager.addCommand(command);
	}
	
	/**
	 * Add a new console command to the bot.<br>
	 * <i>Console commands mainly used for non-spigot.</i>
	 * @param command The new command to add.
	 */
	@Deprecated
	public void addConsoleCommand(ConsoleCommand command) {
		consoleCommandManager.addCommand(new ProgramCommandConsole() {
			@Override
			public String getLabel() {
				return command.getLabel();
			}
			@Override
			public String getDescription() {
				return command.getDescrition();
			}
			@Override
			public String getUsage() {
				return command.getUsage();
			}
			@Override
			public void run(String[] args) {
				command.run(args);
			}
		});
	}
	
	public List<ProgramCommandConsole> getConsoleCommands() {
		return consoleCommandManager.getCommands();
	}
	
	/**
	 * Add a bad word, and when a user uses the word it will be deleted and they will be warned.
	 * @param badWord The bad word to add.
	 */
	public void addBadWord(String badWord) {
		badWords.add(badWord);
	}
	
	/**
	 * Add a bad word, and when a user uses the word it will be deleted and they will be warned.
	 * @param badWords The bad words to add.
	 */
	public void addBadWords(String... badWords) {
		this.badWords.addAll(Arrays.asList(badWords));
	}
	
	/**
	 * Remove a bad word.
	 * @param badWord The bad word to remove.
	 */
	public void removeadWord(String badWord) {
		badWords.remove(badWord);
	}
	
	/**
	 * Mute a user easily by adding them to the muted users list.
	 * @param mutedUser Adds the user to a blacklist of users to mute.
	 */
	public void addMutedUser(User mutedUser) {
		mutedUsers.add(mutedUser);
	}
	
	/**
	 * Unmute a user by removing them from the muted users list.
	 * @param unmutedUser Removes the user from the muted blacklist.
	 */
	public void removeMutedUser(User unmutedUser) {
		mutedUsers.remove(unmutedUser);
	}
	
	/**
	 * Add an event to the listener.
	 *
	 * @param event The event.
	 */
	public void addEvent(Predicate<GenericEvent> event) {
		events.add(event);
	}
	
	/**
	 * Remove an event from the listener.
	 * @param event The event.
	 */
	public void removeEvent(Predicate<Event> event) {
		events.remove(event);
	}
	
	public void addAction(Runnable action, int seconds) {
		botThread.addAction(action, seconds);
	}
	
	@Override
	public void onEvent(@NotNull GenericEvent e) {
//		Bot login event.
		if (e instanceof ReadyEvent) {
			loadAction.run();
			return;
		}

//		Bot custom added events.
		boolean cancelled = false;
		for (Predicate<GenericEvent> event : events) {
			if (event.test(e)) {
				cancelled = true;
			}
		}
		if (cancelled) {
			return;
		}

//		Bot receive message events for - Bad words, Muted users, and Commands.
		if (e instanceof MessageReceivedEvent) {
			MessageReceivedEvent event = (MessageReceivedEvent) e;
			
//			Bad word check.
			for (String badWord : badWords) {
				if (event.getMessage().getContentRaw().toLowerCase().contains(badWord.toLowerCase()) || event.getMessage().getContentDisplay().toLowerCase().contains(badWord.toLowerCase()) || event.getMessage().getContentStripped().toLowerCase().contains(badWord.toLowerCase())) {
					badWordRun.accept(event);
				}
			}
			
//			Muted user check.
			for (User user : mutedUsers) {
				if (user.getId().equals(event.getAuthor().getId())) {
					mutedUserRun.accept(event);
				}
			}
			
//			ProgramCommand check.
			for (ProgramCommand command : commands) {
				commandRun.accept(event, command);
			}
		}
	}
}