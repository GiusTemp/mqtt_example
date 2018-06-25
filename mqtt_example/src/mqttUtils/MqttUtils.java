package mqttUtils;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttUtils {
	private static String topic = "unibo/qasys";
	private static int qos = 2;
	private static String broker = "tcp://localhost:1883";
	// private static String broker = "tcp://iot.eclipse.org:1883";
	// private static String broker = "tcp://broker.hivemq.com:1883";
	private static String clientId = "JavaSample";
	private static MemoryPersistence persistence = new MemoryPersistence();
	private static MqttClient sampleClient;

	public static void init() {
		try {
			sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("MqttUtils connecting to broker: " + broker);
			sampleClient.connect(connOpts);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public static void publish(String content) {
		try {
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.publish(topic, message);
			sampleClient.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
