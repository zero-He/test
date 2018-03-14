/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.model;

/**
 * 在线用户统计
 * 
 * @author liulongbiao
 *
 */
public class OnlineUserStat {
	private int registered; // 累计注册用户数
	private int platform; // 平台在线用户数
	private int lesson; // 实时课堂在线用户数
	private int web; // 网站在线用户数
	private int device; // 设备在线人数

	public OnlineUserStat() {
	}

	public OnlineUserStat(OnlineUserStat stat) {
		if (stat != null) {
			setRegistered(stat.getRegistered());
			setPlatform(stat.getPlatform());
			setLesson(stat.getLesson());
			setWeb(stat.getWeb());
			setDevice(stat.getDevice());
		}
	}

	public int getRegistered() {
		return registered;
	}

	public void setRegistered(int registered) {
		this.registered = registered;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public int getLesson() {
		return lesson;
	}

	public void setLesson(int lesson) {
		this.lesson = lesson;
	}

	public int getWeb() {
		return web;
	}

	public void setWeb(int web) {
		this.web = web;
	}

	public int getDevice() {
		return device;
	}

	public void setDevice(int device) {
		this.device = device;
	}

}
