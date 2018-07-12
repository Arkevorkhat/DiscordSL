package botCore;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.obj.Guild;
import variableStores.ObjProperties;

public class GuildCreateListener implements IListener<GuildCreateEvent> {

	@Override
	public void handle(GuildCreateEvent event) {
		ServerPref<String> serverpref = Engine.mainFileHandler.importPrefByID(event.getGuild().getLongID());
		Engine.Servers.put(new Long(event.getGuild().getLongID()),
				(serverpref != null) ? serverpref : new ServerPref<String>((Guild) event.getGuild(), new ObjProperties()));
	}
}
