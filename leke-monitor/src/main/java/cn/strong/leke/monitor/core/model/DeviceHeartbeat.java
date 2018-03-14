/**
 * 
 */
package cn.strong.leke.monitor.core.model;

/**
 * 设备在线心跳
 * 
 * @author liulongbiao
 *
 */
public class DeviceHeartbeat {
	private String ticket; // 用户ticket
	private Integer d; // 设备端
	private String model; // 设备型号
	private String os; // 操作系统
	private String ip; // 登录IP

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Integer getD() {
		return d;
	}

	public void setD(Integer d) {
		this.d = d;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
