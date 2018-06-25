package mqttUtils;

public class MQTTBrokerConfig {
	private String hostName;
	private Integer port;
	
	public MQTTBrokerConfig(String hostName, Integer port) {
		super();
		this.hostName = hostName;
		this.port = port;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getConnectionString() {
		return "tcp://" + this.hostName + ":" + this.port;
	}

	public String toString() {
		return this.hostName + ":" + this.port;
	}

}
