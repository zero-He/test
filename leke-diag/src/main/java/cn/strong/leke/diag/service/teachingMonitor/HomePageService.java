package cn.strong.leke.diag.service.teachingMonitor;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.diag.dao.teachingMonitor.HomePageDao;
import cn.strong.leke.diag.model.teachingMonitor.HomePageDynamic;
import cn.strong.leke.diag.model.teachingMonitor.HomePageLessonSchedule;
import cn.strong.leke.diag.model.teachingMonitor.HomePageSchedule;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.mongo.teachingMonitor.MonitorMongoDao;

@Service
public class HomePageService {
	
	@Resource
	private HomePageDao homePageDao;
	
	@Resource
	private MonitorMongoDao monitorMongoDao; 
	
	public Long findOnlineUserCount(Long schoolId, List<Long> userIdList, Date date, Long roleId){
		return Long.valueOf(monitorMongoDao.getOnlineUserCount(schoolId, userIdList, date, roleId));
	}
	
	/**
	 * 查询老师动态-上课、备课
	 * @param vo
	 * @return
	 */
	public HomePageDynamic findTeacherLessonAndBeike(RequestVo vo){
		return homePageDao.findTeacherLessonAndBeike(vo);
	}
	
	/**
	 * 查询老师动态-布置作业
	 * @param vo
	 * @return
	 */
	public HomePageDynamic findTeacherAssignHW(RequestVo vo){
		return homePageDao.findTeacherAssignHW(vo);
	}
	
	/**
	 * 查询老师动态-已批改作业
	 * @param vo
	 * @return
	 */
	public HomePageDynamic findTeacherCorrectHW(RequestVo vo){
		return homePageDao.findTeacherCorrectHW(vo);
	}
	
	/**
	 * 查询老师动态-待批改作业
	 * @param vo
	 * @return
	 */
	public HomePageDynamic findTeacherAwaitingCorrectHW(RequestVo vo){
		return homePageDao.findTeacherAwaitingCorrectHW(vo);
	}
	
	/**
	 * 查询老师动态-答疑
	 * @param vo
	 * @return
	 */
	public HomePageDynamic findTeacherDoubt(RequestVo vo){
		return homePageDao.findTeacherDoubt(vo);
	}
	
	/**
	 * 查询学生动态-考勤
	 * @param vo
	 * @return
	 */
	public HomePageDynamic findStudentAttendent(RequestVo vo){
		return homePageDao.findStudentAttendent(vo);
	}
	
	/**
	 * 查询学生动态-提问
	 * @param vo
	 * @return
	 */
	public HomePageDynamic findStudentDoubt(RequestVo vo){
		return homePageDao.findStudentDoubt(vo);
	}
	
	/**
	 * 查询日课表
	 * @param vo
	 * @return
	 */
	public List<HomePageLessonSchedule> findHomePageSchedule(RequestVo vo){
		return homePageDao.findHomePageSchedule(vo);
	}
}
