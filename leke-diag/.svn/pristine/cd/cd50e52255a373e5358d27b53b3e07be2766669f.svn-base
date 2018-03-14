package cn.strong.leke.diag.service.teachingMonitor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.NumberUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.diag.dao.teachingMonitor.LessonBeikeInfoDao;
import cn.strong.leke.diag.model.teachingMonitor.BeikeRate;
import cn.strong.leke.diag.model.teachingMonitor.BeikeUnitCommit;
import cn.strong.leke.diag.model.teachingMonitor.CommProp;
import cn.strong.leke.diag.model.teachingMonitor.LessonBeikeInfo;
import cn.strong.leke.diag.model.teachingMonitor.LessonBeikePkg;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.mongo.teachingMonitor.LessonBeikeMongoDao;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;

@Service
public class LessonBeikeInfoService {
	
	@Resource
	private LessonBeikeInfoDao lessonBeikeInfoDao;
	
	@Resource
	private LessonBeikeMongoDao lessonBeikeMongoDao;
	
	@Resource
	private IKlassRemoteService klassRemoteService;
	
	@Resource
	private LessonBeikeInfoService lessonBeikeInfoService;
	
	@Resource
	private CommService commService;
	
	/**
	 * 查询上课备课明细信息
	 * @param vo
	 * @return
	 */
	public List<LessonBeikeInfo> findLessonBeikeInfoByGradeSubject(RequestVo vo){
		List<LessonBeikeInfo> lessonBeikeList = lessonBeikeInfoDao.findLessonBeikeInfoByGradeSubject(vo);
		List<LessonBeikePkg> lessonBeikePkgList = lessonBeikeMongoDao.getLessonBeikeMinDateByLessonIds(lessonBeikeList.stream().map(LessonBeikeInfo::getCourseSingleId).collect(Collectors.toList()));
		lessonBeikeList.stream().forEach(v->{
			Long courseSingleId = v.getCourseSingleId();
			Date lessonStartTime = v.getStartTime();
			if(v.getIsPrepared()){
				List<LessonBeikePkg> bekePkgList = lessonBeikePkgList.stream().filter(b->{
					return b.getLessonId().equals(courseSingleId);
				}).collect(Collectors.toList());
				if(!bekePkgList.isEmpty()){
					LessonBeikePkg beikePkg = bekePkgList.get(0);
					if(null != beikePkg.getCreatedOn() && null != lessonStartTime && DateUtils.addMinutes(beikePkg.getCreatedOn(), 60).getTime() < lessonStartTime.getTime()){
						v.setIsEarlyPrepared(true);
						v.setBeikeTime(beikePkg.getCreatedOn());
					}
				}
			}
		});
		
		return lessonBeikeList;
	}
	
	/**
	 * 查询当前登录用户所属学校的指定年级的班级
	 * @return
	 */
	public List<Clazz> findClazzOfGradeBySchool(RequestVo vo){
		ClazzQuery clazzQuery = new ClazzQuery();
		clazzQuery.setSchoolId(vo.getSchoolId());
		clazzQuery.setGradeId(vo.getGradeId());
		
		return klassRemoteService.findClazzByQuery(clazzQuery);
	}
	
	/**
	 * 查询老师备课率排行
	 * @param vo
	 * @return
	 */
	public List<BeikeRate> findTeacherBeikeRateRank(RequestVo vo){
		return lessonBeikeInfoDao.findTeacherBeikeRateRank(vo);
	}
	
	/**
	 * 查询老师上课明细数据
	 * @param vo
	 * @return
	 */
	public List<LessonBeikeInfo> findLessonDtlOfTeacher(RequestVo vo, Page page){
		return lessonBeikeInfoDao.findLessonDtlOfTeacher(vo, page);
	}
	
	public List<LessonBeikeInfo> findLessonDtlOfTeacher(RequestVo vo){
		return lessonBeikeInfoDao.findLessonDtlOfTeacher(vo);
	}
	
	/**
	 * 根据单科ID判断课堂是否有教案
	 * @param courseSingleId
	 * @return
	 */
	public boolean hasLessonPlanForLesson(Long courseSingleId){
		List<LessonBeikePkg> beikePkgList = lessonBeikeMongoDao.findLessonBeikePkgListByLessonId(courseSingleId);
		BeikeUnitCommit unitCommit = null;
		for(LessonBeikePkg beikePkg : beikePkgList){
			if(null == beikePkg || null == beikePkg.getCommitId()){
				continue;
			}
			
			unitCommit = lessonBeikeMongoDao.findBeikeUnitCommitByCommitId(beikePkg.getCommitId());
			if(null == unitCommit){
				continue;
			}
			
			if((lessonBeikeMongoDao.hasContentForBeikeUnitText(unitCommit.getAttitude()) || 
			    lessonBeikeMongoDao.hasContentForBeikeUnitText(unitCommit.getKnowledge()) ||
			    lessonBeikeMongoDao.hasContentForBeikeUnitText(unitCommit.getMethod())
			    ) &&
			   lessonBeikeMongoDao.hasContentForBeikeUnitText(unitCommit.getProcess()) &&
			   lessonBeikeMongoDao.hasContentForBeikeUnitText(unitCommit.getDifficulty())
			  ){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 计算备课率
	 * @param beikeRate
	 * @param beikeInfoList
	 */
	public void handleBeikeRate(BeikeRate beikeRate, List<LessonBeikeInfo> beikeInfoList){
		
		//计算相关备课维度课堂数
		beikeRate.setActualLesson(Long.valueOf(beikeInfoList.size()));
		beikeRate.setPreparedLesson(beikeInfoList.stream().filter(LessonBeikeInfo::getIsPrepared).count());
		beikeRate.setNotPreparedLesson(beikeRate.getActualLesson() - beikeRate.getPreparedLesson());
		beikeRate.setEarlyPreparedLesson(beikeInfoList.stream().filter(v -> v.getIsPrepared() && v.getIsEarlyPrepared()).count());
		beikeRate.setTempPreparedLesson(beikeRate.getPreparedLesson() - beikeRate.getEarlyPreparedLesson());
		beikeRate.setCwLesson(beikeInfoList.stream().filter(v->v.getCwCount() > 0).count());
		beikeRate.setMcLesson(beikeInfoList.stream().filter(v->v.getMcCount() > 0).count());
		beikeRate.setHwLesson(beikeInfoList.stream().filter(v->v.getHwCount() > 0).count());
		beikeRate.setTeachPlanLesson(beikeInfoList.stream().filter(v->v.getTeachPlan() > 0).count());

		//计算相关备课维度备课率
		if (beikeRate.getActualLesson() != 0L) {
			beikeRate.setPreparedLessonRate(NumberUtils.formatScore(BigDecimal.valueOf(beikeRate.getPreparedLesson() * 100).divide(BigDecimal.valueOf(beikeRate.getActualLesson()), 1, RoundingMode.HALF_UP)));
			beikeRate.setNotPreparedLessonRate(NumberUtils.formatScore(BigDecimal.valueOf(beikeRate.getNotPreparedLesson() * 100).divide(BigDecimal.valueOf(beikeRate.getActualLesson()), 1, RoundingMode.HALF_UP)));
			beikeRate.setEarlyPreparedLessonRate(NumberUtils.formatScore(BigDecimal.valueOf(beikeRate.getEarlyPreparedLesson() * 100).divide(BigDecimal.valueOf(beikeRate.getActualLesson()), 1, RoundingMode.HALF_UP)));
			beikeRate.setTempPreparedLessonRate(NumberUtils.formatScore(BigDecimal.valueOf(beikeRate.getTempPreparedLesson() * 100).divide(BigDecimal.valueOf(beikeRate.getActualLesson()), 1, RoundingMode.HALF_UP)));
			beikeRate.setCwLessonRate(NumberUtils.formatScore(BigDecimal.valueOf(beikeRate.getCwLesson() * 100).divide(BigDecimal.valueOf(beikeRate.getActualLesson()), 1, RoundingMode.HALF_UP)));
			beikeRate.setMcLessonRate(NumberUtils.formatScore(BigDecimal.valueOf(beikeRate.getMcLesson() * 100).divide(BigDecimal.valueOf(beikeRate.getActualLesson()), 1, RoundingMode.HALF_UP)));
			beikeRate.setHwLessonRate(NumberUtils.formatScore(BigDecimal.valueOf(beikeRate.getHwLesson() * 100).divide(BigDecimal.valueOf(beikeRate.getActualLesson()), 1, RoundingMode.HALF_UP)));
			beikeRate.setTeachPlanLessonRate(NumberUtils.formatScore(BigDecimal.valueOf(beikeRate.getTeachPlanLesson() * 100).divide(BigDecimal.valueOf(beikeRate.getActualLesson()), 1, RoundingMode.HALF_UP)));
		}
	}
	
	/**
	 * 处理老师备课率统计详细信息
	 * @param vo
	 * @return
	 */
	public List<BeikeRate> handleBeikeRateStatOfTeacher(RequestVo vo, Page page){
		commService.setCommPropToRequestVo(vo);
		
		if(StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())){
			vo.setOrderAttr("preparedLessonRate");
			vo.setOrderType("desc");
		}
		
		List<LessonBeikeInfo> beikeInfoList = lessonBeikeInfoService.findLessonBeikeInfoByGradeSubject(vo);
		List<BeikeRate> beikeRateList = beikeInfoList.stream().distinct().map(v->{
											BeikeRate bk = new BeikeRate();
											bk.setTeacherId(v.getTeacherId());
											bk.setTeacherName(v.getTeacherName());
											bk.setSubjectId(v.getSubjectId());
											bk.setSubjectName(v.getSubjectName());
											return bk;
										}).collect(Collectors.toList());
	    
		//判断课堂是否有教案
		beikeInfoList.stream().forEach(v->{
			if(lessonBeikeInfoService.hasLessonPlanForLesson(v.getCourseSingleId())){
				v.setTeachPlan(1);
			}else{
				v.setTeachPlan(0);
			}
		});
		
		//循环老师计算老师在所选时间段内的备课率
		beikeRateList.stream().forEach(v->{
			v.setStartDate(vo.getStartDate());
			v.setEndDate(vo.getEndDate());
			handleBeikeRate(v, beikeInfoList.stream().filter(b->b.getTeacherId().equals(v.getTeacherId()) && b.getSubjectId().equals(v.getSubjectId())).collect(Collectors.toList()));
		});
		
		if(null == page){
			beikeRateList = commService.handleOrderData(vo, beikeRateList, BeikeRate.class);
		}else{
			page.setTotalSize(beikeRateList.size());
			beikeRateList = commService.handleOrderData(vo, beikeRateList, BeikeRate.class).stream().skip((page.getCurPage() - 1) * page.getPageSize()).limit(page.getPageSize()).collect(Collectors.toList());
		}
		
		return beikeRateList;
	}
}
