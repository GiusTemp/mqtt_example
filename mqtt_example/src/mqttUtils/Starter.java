package mqttUtils;

public class Starter {

	public static void main(String[] args) {
		MqttUtils.init();
		
		MqttUtils.publish("ma s vene staser");

	}

}
