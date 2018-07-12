package botCore;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.shard.ReconnectFailureEvent;

public class ShardEventListener {
	@EventSubscriber
	public void handle(ReconnectFailureEvent event) {
		if (event.isShardAbandoned()==false) {
			return;
		} else {
			try {
				Thread x = new Thread(new ClientReconnectThread());
				x.wait(36000000);
				x.start();
			} catch (InterruptedException e) {
				new Thread(new ClientReconnectThread()).start();
			}
		}
	}
}
