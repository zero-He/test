/**
 * 
 */
package cn.strong.leke.monitor.core.service.appcenter.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.BeanUtils;
import cn.strong.leke.common.utils.Booleans;
import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.exceptions.Validation;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.monitor.core.model.appcenter.PadSystemReport;
import cn.strong.leke.monitor.core.service.appcenter.IAppCenterService;
import cn.strong.leke.monitor.mongo.appcenter.dao.IPadSystemDao;
import cn.strong.leke.monitor.mongo.appcenter.dao.IUserImeiDao;
import cn.strong.leke.monitor.mongo.appcenter.model.PadSystem;
import cn.strong.leke.monitor.mongo.appcenter.model.PadSystem.App;
import cn.strong.leke.monitor.mongo.appcenter.model.UserImei;
import cn.strong.leke.remote.model.tukor.UserRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

/**
 * @author liulongbiao
 *
 */
@Service
public class AppCenterService implements IAppCenterService {
	private static final Logger LOG = LoggerFactory.getLogger(AppCenterService.class);

	@Resource(name = "monitorExecutor")
	private ExecutorService monitorExecutor;
	@Resource
	private IPadSystemDao padSystemDao;
	@Resource
	private IUserImeiDao userImeiDao;
	@Resource
	private IUserRemoteService userRemoteService;
	@Resource
	private IKlassRemoteService klassRemoteService;

	@Override
	public void savePadSystemReport(PadSystemReport report) {
		Validation.notNull(report, "PadSystemReport is null");
		Validation.hasText(report.getImei(), "imei is required");

		CompletableFuture.runAsync(() -> {
			PadSystem sys = toPadSystem(report);
			padSystemDao.save(sys);

			String userId = report.getUserId();
			if (StringUtils.hasText(userId)) {
				userImeiDao.save(userId, report.getImei());
			}
		}, monitorExecutor).whenComplete((t, ex) -> {
			if (ex != null) {
				LOG.error("保存PAD系统应用报告失败", ex);
			}
		});
	}
	
	@Override
	public List<PadSystemExtend> findPadSystem(Page page,String loginName,
			String userName,Boolean root,String version,
			String mac,String imei,Long schoolId){
		List<Long> userIds = userRemoteService.findUserIdsBySchoolIdAndRoleId(schoolId, RoleCst.STUDENT);
		if(CollectionUtils.isNotEmpty(userIds)){
			List<Long> loginNameListId = new ArrayList<>(); 
			List<Long> userNameListId = new ArrayList<>();
			if(StringUtils.isNotEmpty(StringUtils.trim(loginName))){
				Long userId = userRemoteService.findUserIdByLoginName(loginName.trim());
				if(userIds.contains(userId)){
					loginNameListId = Arrays.asList(userId);
				}
				userIds.retainAll(loginNameListId);
			}
			if(StringUtils.isNotEmpty(StringUtils.trim(userName))){
				List<UserRemote> list = userRemoteService.queryUserByRoleIdSchoolId(RoleCst.STUDENT, userName.trim(), schoolId);
				if(CollectionUtils.isNotEmpty(list)){
					userNameListId = list.stream().map(UserRemote::getId).collect(Collectors.toList());
				}
				userIds.retainAll(userNameListId);
			}
			
		}
		List<UserImei> imeisList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(userIds)){
			List<String> strUsreIds = userIds.stream().map(v->{return v.toString();}).collect(Collectors.toList());
			imeisList = userImeiDao.findByUser(strUsreIds);
		}
		List<PadSystemExtend> l = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(imeisList)){
			List<String> imeis = imeisList.stream().map(UserImei::getImei).collect(Collectors.toList());
			Map<String,List<UserImei>> mapList = imeisList.stream().collect(Collectors.groupingBy(UserImei::getImei));
			if(StringUtils.isNotEmpty(imei)){
				imeis = Arrays.asList(imei.trim());
			}
			List<PadSystem> list = padSystemDao.findPadSystem(page, imeis, root, version, mac);
			if(CollectionUtils.isNotEmpty(list)){
				l = list.stream().map(v->{
					List<UserImei> userImeiList = mapList.get(v.getImei());
					Long userId = CollectionUtils.isNotEmpty(userImeiList)?Long.parseLong(userImeiList.get(0).getUserId()):null;
					return toPadSystemExtend(v,schoolId,userId);
				}).collect(Collectors.toList());
			}
			return l; 
		}else{
			return l;
		}
	}
	
	@Override
	public List<UserImei> findUserList(String imei){
		List<UserImei> imeisList = userImeiDao.findByImeis(imei);
		imeisList.stream().forEach(v->{
			UserBase user = UserBaseContext.getUserBaseByUserId(Long.parseLong(v.getUserId()));
			if(user!=null){
				v.setLoginName(user.getLoginName());
				v.setUserName(user.getUserName());
			}
			if(v.getTs()!=null){
				v.setTsStr(DateUtils.format(v.getTs(), DateUtils.CHINA_MINITE_DATE_PATTERN));
			}
		});
		return imeisList;
	}
	
	@Override
	public PadSystemExtend getPadSystemExtend(String imei,Long schoolId,String loginName){
		PadSystem v = padSystemDao.getByImei(imei);
		if(v==null){
			return new PadSystemExtend();
		}
		Long userId = userRemoteService.findUserIdByLoginName(loginName);
		return toPadSystemExtend(v,schoolId,userId);
	}
	
	private PadSystemExtend toPadSystemExtend(PadSystem v,Long schoolId,Long userId){
		PadSystemExtend e = new PadSystemExtend();
		BeanUtils.copyProperties(e, v);
		UserBase user = UserBaseContext.getUserBaseByUserId(userId);
		if(user!=null){
			e.setLoginName(user.getLoginName());
			e.setUserName(user.getUserName());
		}
		ClazzQuery query = new ClazzQuery();
		query.setType(Clazz.CLAZZ_ORGAN);
		query.setUserId(userId);
		query.setRoleId(RoleCst.STUDENT);
		query.setSchoolId(schoolId);
		List<Clazz> cList = klassRemoteService.findClazzByQuery(query);
		if(CollectionUtils.isNotEmpty(cList)){
			e.setClassName(cList.get(0).getClassName());
		}
		return e;
	}
	
	private PadSystem toPadSystem(PadSystemReport report) {
		PadSystem sys = new PadSystem();
		sys.setImei(report.getImei());
		sys.setVersion(report.getVersion());
		sys.setMac(report.getMac());
		sys.setLatitude(report.getLatitude());
		sys.setLongitude(report.getLongitude());
		sys.setRooted(Booleans.isTrue(report.getRooted()));
		sys.setApps(ListUtils.map(report.getApps(), inf -> {
			App app = new App();
			app.setPkgName(inf.getPkgName());
			app.setAppName(inf.getAppName());
			return app;
		}));
		sys.setTs(new Date());
		return sys;
	}

	public class PadSystemExtend extends PadSystem{
		private String loginName;
		private String userName;
		private String className;
		public String getLoginName() {
			return loginName;
		}
		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
	}
}
