package botCore;

import java.util.List;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public interface Command {
	/*
	 * functional interface that allows for lambdas to be used in handling command inputs.
	 */
	void runCommand(MessageReceivedEvent event, List<String> args);
}
