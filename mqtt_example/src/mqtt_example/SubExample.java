package mqtt_example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SubExample implements MqttCallback {
	
	private String connectionString;
	private String topic;
	private Queue<String> queue = new LinkedList<>();
	private MqttClient mqttClient;
	private MessageListener listener;

	public SubExample() {
		this.setTopic("MQTT Examples");
	}

	public void connect(MQTTBrokerConfig config) throws MqttException {
		this.connectionString = config.getConnectionString();
		this.mqttClient = new MqttClient(this.connectionString, "client1");
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		this.mqttClient.connect(connOpts);
		this.mqttClient.setCallback(this);
		this.mqttClient.subscribe(getTopic(), 0);
	}
	
	public void setMessageListener(MessageListener listener) {
		this.listener = listener;
	}

	@Override
	public void connectionLost(Throwable cause) {
		System.err.println("Lost connection" + cause);
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		synchronized (this.queue) {
			System.out.println("Received message: " +  message.toString());
			if (this.queue.size() >= 100) {
				this.queue.remove();
			}
			this.queue.add(message.toString());
			if (this.listener != null) {
				this.listener.messageReceived(message.toString());
			}
		}
	}

	public List<String> messages() {
		return new ArrayList<String>(this.queue);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.err.println("delivery completed");
	}

	public String getConnectionString() {
		return this.connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public void disconnect() {
		if (this.mqttClient != null) {
			try {
				this.mqttClient.disconnect();
				this.mqttClient.close();
			} catch (MqttException e) {
				System.err.println("Error when closing connection " + e);
			}
		}
	}

}