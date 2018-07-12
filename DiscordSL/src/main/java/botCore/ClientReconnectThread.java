package botCore;

public class ClientReconnectThread implements Runnable {

	@Override
	public void run() {
		Engine.botClient.login();
	}
}
