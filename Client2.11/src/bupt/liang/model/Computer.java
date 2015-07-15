package bupt.liang.model;

public class Computer {
	String ID;
	String ip;
	String port;
	String speed;
	public Computer(String s1, String s2, String s3, String s4) {
		ID = s1;
		ip = s2;
		port = s3;
		speed = s4;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
}
