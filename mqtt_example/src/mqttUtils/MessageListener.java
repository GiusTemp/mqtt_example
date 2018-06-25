package mqttUtils;

public interface MessageListener {
	public void messageReceived(String message);
}
