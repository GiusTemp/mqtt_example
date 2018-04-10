package mqtt_example;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Starter {

	public static void main(String[] args) {

		String topic = "MQTT Examples";
		String content = "Message from MqttPublishSample";
		int qos = 2;
		String broker = "tcp://iot.eclipse.org:1883";
		// String broker = "tcp://broker.hivemq.com:1883";
		String clientId = "JavaSample";
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			// subscriber
			SubExample sub = new SubExample();
			sub.connect(new MQTTBrokerConfig("iot.eclipse.org", 1883));
			sub.setTopic(topic);
//			sub.setMessageListener(new MessageListener() {
//
//				@Override
//				public void messageReceived(String message) {
//					System.out.println("MessageReceived say: " + message);
//				}
//			});

			// publisher
			MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			System.out.println("Connected");
			System.out.println("Publishing message: " + content);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.publish(topic, message);
			//System.out.println("Message published");
			sampleClient.disconnect();
			//System.out.println("Disconnected");

		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}
}