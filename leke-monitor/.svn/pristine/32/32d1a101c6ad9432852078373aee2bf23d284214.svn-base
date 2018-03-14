/**
 * 
 */
package cn.strong.leke.monitor.mongo.online.model.query;

import static cn.strong.leke.common.utils.StringUtils.isNotEmpty;

import java.util.Date;
import java.util.regex.Pattern;

import org.bson.Document;
import org.springframework.util.Assert;

/**
 * 在线用户查询
 * 
 * @author liulongbiao
 *
 */
public class OnlineUserQuery {

	private Date ts;
	private String loginName;
	private String oldLoginName;
	private String userName;
	private String schoolName;

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getOldLoginName() {
		return oldLoginName;
	}

	public void setOldLoginName(String oldLoginName) {
		this.oldLoginName = oldLoginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Document toBSON() {
		Assert.notNull(ts, "the min timestamp is null");
		Document filter = new Document("ts", new Document("$gte", ts));
		if (isNotEmpty(loginName)) {
			filter.append("loginName", loginName);
		}
		if (isNotEmpty(oldLoginName)) {
			filter.append("oldLoginName", oldLoginName);
		}
		if (isNotEmpty(userName)) {
			filter.append("userName", userName);
		}
		if (isNotEmpty(schoolName)) {
			filter.append("schoolName", Pattern.compile(schoolName));
		}
		return filter;
	}
}
