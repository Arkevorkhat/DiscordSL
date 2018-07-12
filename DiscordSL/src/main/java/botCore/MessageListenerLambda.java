package botCore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.obj.Guild;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import variableStores.ObjProperties;
import variableStores.Properties;

public class MessageListenerLambda {
	private Map<String, Command> commandMap = new HashMap<>();
	private long GuildID;

	private List<MessageListenerCompleteListener> listeners = new ArrayList<>();

	public MessageListenerLambda() {
		commandMap.put("reply", (event, args) -> {
			activeGuild(event).setReplChannel(event.getChannel().getLongID());
		});
		commandMap.put("test", (event, args) -> {
			Engine.Servers.get(event.getGuild().getLongID()).getReplChannel().sendMessage("This is a test message.");
		});
		commandMap.put("pref", (event, args) -> {
			if (args.size() == 3 && activeGuild(event).administratorList.contains(event.getAuthor())) {
				activeGuild(event).getReplChannel()
						.sendMessage(activeGuild(event).properties.setPrefByName(args.get(0), args.get(1)));
			}
		});
		commandMap.put("help", (event, args)->{
			RequestBuffer.request(()-> activeGuild(event).getReplChannel().sendMessage(StringGenHelper.embedHelpFile()));
		});
		commandMap.put("info", (event, args) -> {
			StringBuffer SB = new StringBuffer();
			SB.append(String.format("Server (Guild) ID: %d \n", event.getGuild().getLongID()));
			SB.append(String.format("Channel ID: %d \n", event.getChannel().getLongID()));
			SB.append(String.format("Reply Channel ID: %d \n", activeGuild(event).getReplChannel().getLongID()));
			SB.append(String.format("Your ID: %d \n", event.getAuthor().getLongID()));
			activeGuild(event).getReplChannel().sendMessage(SB.toString());
		});
		commandMap.put("add", (event, args) -> {
			StringBuilder SB = new StringBuilder();
			for (String S : args) {
				SB.append(S+" ");
			}
			activeGuild(event).add(SB.toString());
		});
		commandMap.put("get", (event, args) -> {
			EmbedBuilder builder = new EmbedBuilder();
			for (String S : activeGuild(event)) {
				builder.appendField(Integer.toString(activeGuild(event).indexOf(S)), S, false);
			}
			RequestBuffer.request(() -> activeGuild(event).getReplChannel().sendMessage(builder.build()));
		});
		commandMap.put("remove", (event, args)->{
			if(args.size()==1) {
				try {
					int index = Integer.parseInt(args.get(0));
					activeGuild(event).remove(index);
					
				}catch(NumberFormatException e) {
					
				}
			}
		});
		for(MessageListenerCompleteListener MLCL : listeners) {
			MLCL.run();
		}
	}

	public void addListener(MessageListenerCompleteListener listener) {
		listeners.add(listener.addMLL(this));
	}

	public void addCommand(String command, Command lambda) {
		commandMap.put(command, lambda); // this takes a command tag, and a lambda expression implementing the
											// functional
		// interface "Command"
	}

	@EventSubscriber
	public void onMessageReceived(MessageReceivedEvent arg0) {
		if (arg0.getMessage().getContent().equals(Properties.getCommandPrefix() + "exit")) {
			Engine.mainFileHandler.exportPrefs();
			System.exit(0);
		}
		this.setGuildID(arg0.getGuild().getLongID());
		this.activeGuild(arg0).setReplChannel(arg0.getChannel().getLongID());
		System.out.println(arg0.getMessage().getContent());
		List<String> args = new ArrayList<>(
				Arrays.asList(arg0.getMessage().getContent().split(Properties.getEntryDelimiter())));
		for (String S : args)
			System.out.println(S);
		if (args.size() == 0 || args.get(0).startsWith(Properties.getCommandPrefix())) {
			// return;
		}
		String command = args.get(0).substring(1);
		args.remove(0);

		if (commandMap.containsKey(command)) {
			commandMap.get(command).runCommand(arg0, args);
		}
	}

	public long getGuildID() {
		return GuildID;
	}

	public void setGuildID(long guildID) {
		GuildID = guildID;
	}

	public ServerPref<String> activeGuild(MessageReceivedEvent arg0) {
		return (Engine.Servers.containsKey(arg0.getGuild().getLongID()))
				? Engine.Servers.get(arg0.getGuild().getLongID())
				: new ServerPref<String>((Guild) arg0.getGuild(), new ObjProperties());
	}
}
