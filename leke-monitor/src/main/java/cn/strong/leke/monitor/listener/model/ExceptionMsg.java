/**
 * 
 */
package cn.strong.leke.monitor.listener.model;

/**
 * 异常消息
 * 
 * @author liulongbiao
 *
 */
public class ExceptionMsg {
	private String type; // 异常类型
	private Long createdTime; // 时间戳
	private String message; // 异常信息
	private String stack; // 异常堆栈
	private String ip; // 服务器IP

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
