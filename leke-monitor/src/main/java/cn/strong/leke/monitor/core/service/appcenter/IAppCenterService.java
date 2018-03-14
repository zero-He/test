/**
 * 
 */
package cn.strong.leke.monitor.core.service.appcenter;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.model.appcenter.PadSystemReport;
import cn.strong.leke.monitor.core.service.appcenter.impl.AppCenterService.PadSystemExtend;
import cn.strong.leke.monitor.mongo.appcenter.model.UserImei;

/**
 * 应用中心服务接口
 * 
 * @author liulongbiao
 *
 */
public interface IAppCenterService {

	/**
	 * 保存PAD系统报告
	 * 
	 * @param report
	 */
	void savePadSystemReport(PadSystemReport report);

	/**
	 * 查询
	 * @param page
	 * @param loginName
	 * @param userName
	 * @param root
	 * @param lastTime
	 * @param sys
	 * @param mac
	 * @return
	 */
	public List<PadSystemExtend> findPadSystem(Page page,String loginName,String userName,Boolean root,String version,String mac,String imei,Long schoolId);
	
	/**
	 * 获取平板数据
	 * @param imei
	 * @param schoolId
	 * @param loginName
	 * @return
	 */
	public PadSystemExtend getPadSystemExtend(String imei,Long schoolId,String loginName);
	
	
	/**
	 * 获取该平板的用户
	 * @param imei
	 * @return
	 */
	public List<UserImei> findUserList(String imei);
}
