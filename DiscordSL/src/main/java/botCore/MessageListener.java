package botCore;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import variableStores.Properties;

public class MessageListener implements IListener<MessageReceivedEvent> {

	@Override
	public void handle(MessageReceivedEvent arg0) {
		if(arg0.getAuthor().getLongID()!=Engine.botClient.getOurUser().getLongID()) {
		String tempContent = arg0.getMessage().getContent();
		if (tempContent.substring(0, 1).equals(Properties.getCommandPrefix())) {
			if (arg0.getAuthor().isBot() != true) {
				IChannel sentChannel = arg0.getChannel();
				System.out.println(arg0.getMessage());
				String[] tempContentSplit = tempContent.substring(1).split(Properties.getEntryDelimiter());
				String Command = tempContentSplit[0];
				System.out.println("Command: " + tempContent.substring(1));
				switch (Command.toLowerCase()) {
				case "test":
					sentChannel.sendMessage(
							"Well, thanks for the test message, I hope you enjoy the bot. to see other commmands, type "
									+ Properties.getCommandPrefix() + "help");
					break;
				case "help":

					break;
				case "add":
					
					break;
				case "source":
					sentChannel
							.sendMessage("You can find my source code at https://www.github.com/Arkevorkhat/DiscordSL");
					break;
				case "relog":
					Engine.botClient.logout();
					Engine.botClient.login();
				case "exit":
					sentChannel.sendMessage("Okay.");
					Engine.botClient.logout();
					System.exit(0);
				}
			}
		}
	}
}
	}
