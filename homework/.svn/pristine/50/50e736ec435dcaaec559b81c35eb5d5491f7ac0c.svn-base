package cn.strong.leke.homework.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.incentive.UserExtraContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.framework.spring.SpringContextHolder;
import cn.strong.leke.homework.dao.mongo.ExerciseDao;
import cn.strong.leke.homework.dao.mongo.activities.AccessRecordDao;
import cn.strong.leke.homework.dao.mongo.activities.UserActivitiesRecordDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDao;
import cn.strong.leke.homework.dao.mybatis.HomeworkDtlDao;
import cn.strong.leke.homework.model.activities.AccessRecord;
import cn.strong.leke.homework.model.activities.MissionStatus;
import cn.strong.leke.homework.model.activities.UserActivitiesRecord;
import cn.strong.leke.homework.model.activity.BugFixHwInfo;
import cn.strong.leke.homework.model.activity.MonthHwInfo;
import cn.strong.leke.homework.model.activity.SimpleExercise;
import cn.strong.leke.homework.service.ActivityService;
import cn.strong.leke.homework.service.impl.MissionManager.Mission;
import cn.strong.leke.homework.service.impl.MissionManager.MissionEnum;
import cn.strong.leke.model.incentive.DynamicInfo;
import cn.strong.leke.model.incentive.ExtraInfo;

@Service
public class ActivityServiceImpl implements ActivityService {

	@Resource
	private HomeworkDao homeworkDao;
	@Resource
	private HomeworkDtlDao homeworkDtlDao;
	@Resource
	private ExerciseDao exerciseDao ;
	@Resource
	private UserActivitiesRecordDao userActivitiesRecordDao;
	@Resource
	private AccessRecordDao accessRecordDao;
	@Resource
	private MessageSender userDynamicSender;
	
	@Override
	public BugFixHwInfo getBugFixInfo(Long userId, Integer month) {
		Date startDate = getOneDayMonth(month);
		return this.homeworkDao.getBugFixInfo(userId, startDate);
	}

	/**
	 * @param month
	 * @return
	 */
	private Date getOneDayMonth(Integer month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);
		Date startDate = DateUtils.parseDate(DateUtils.formatDate(calendar.getTime()));
		return startDate;
	}
	
	@Override
	public List<MonthHwInfo> findMonthHw(Long userId, Integer month) {
		month += 1;
		Date startDate = getOneDayMonth(month);
		return this.homeworkDtlDao.findMonthHw(userId, DateUtils.formatDate(startDate));
	}

	@Override
	public List<SimpleExercise> findTodayExercis(Long userId) {
		Date now = DateUtils.parseDate(DateUtils.formatDate(new Date()));
		return this.exerciseDao.findDayExercis(userId, now);
	}
	
	@Override
	public UserActivitiesRecord nextMission(Long userId) {
		UserActivitiesRecord record = userActivitiesRecordDao.getUserActivitiesRecord(userId);
		if(record==null){
			record = new UserActivitiesRecord();
			record.setUserId(userId);
			record.setUserName(UserBaseContext.getUserBaseByUserId(userId).getUserName());
		}
		if(CollectionUtils.isNotEmpty(record.getMissionChain())){
			List<MissionStatus> chain = record.getMissionChain();
			MissionStatus ms = chain.get(chain.size()-1);
			MissionEnum me = MissionEnum.getMissionEnum(ms.getMissionKey());
			if(ms.getState()==3||(me==MissionEnum.Mission_3&&DateUtils.getDay(new Date())>ms.getDay())){//如果任务已经领奖或者三类任务过期
				if(me==MissionEnum.Mission_3&&DateUtils.getDay(new Date())==ms.getDay()){
					return record;
				}
				chain.add(getMissionStatus(me.getNext(),record));
				userActivitiesRecordDao.updateUserActivitiesRecord(record);
			}
		}else{
			//生成第一个任务
			record.setMissionChain(Arrays.asList(getMissionStatus(MissionEnum.Mission_1,record)));
			userActivitiesRecordDao.updateUserActivitiesRecord(record);
		}
		return record;
	}
	
	@Override
	public UserActivitiesRecord getUserActivitiesRecord(Long userId){
		UserActivitiesRecord record = userActivitiesRecordDao.getUserActivitiesRecord(userId);
		if(record==null){
			record = nextMission(userId);
		}
		List<MissionStatus> list = record.getMissionChain();
		MissionStatus lastMs = list.get(list.size()-1);
		MissionEnum me = MissionEnum.getMissionEnum(lastMs.getMissionKey());
		if(lastMs.getState()==3||(me==MissionEnum.Mission_3&&DateUtils.getDay(new Date())>lastMs.getDay())){
			record = nextMission(userId);
			list = record.getMissionChain();
		}
		if(CollectionUtils.isNotEmpty(list)){
			for(MissionStatus ms : list){
				if(ms.getState()!=3){//任务未领奖，获取任务数据
					me = MissionEnum.getMissionEnum(ms.getMissionKey());
					if((me==MissionEnum.Mission_3&&DateUtils.getDay(new Date())!=ms.getDay())){
						continue;
					}
					Mission mission = getMission(ms.getType());
					Map<String,Object> data = mission.getData(record); 
					ms.setData(data);
					ms.setState(mission.isDown(data)?2:1);
				}
			}
		}
		return record;
	}
	
	@Override
	public Integer acceptPrize(Long userId,String userName,Long schoolId){
		UserActivitiesRecord record = getUserActivitiesRecord(userId);
		List<MissionStatus> list = record.getMissionChain();
		MissionStatus ms = list.get(list.size()-1);
		if(ms.getState()==2){
			Mission mission = getMission(ms.getType());
			ms.setLekeVal(mission.getLekeVal(ms.getData()));
			ms.setState(3);
			sendPrize(userId, userName, schoolId, ms);
			userActivitiesRecordDao.updateUserActivitiesRecord(record);//固化任务数据
		}else if(ms.getState()==3){
			throw new ValidateException("您已成功领取");
		}else{
			throw new ValidateException("您的任务还没有完成");
		}
		return ms.getLekeVal();
	}
	
	@Override
	public List<Map<String,Object>> findAllStatistics(){
		List<Map<String,Object>> list = new ArrayList<>();
		List<UserActivitiesRecord> l = userActivitiesRecordDao.findAll();
		long m_1_n = 0l;//任务一学生参与人数
		long m_1_n_f = 0l;//任务一学生完成人数
		long m_1_bt = 0l;//任务一学生作业订正总数
		
		long m_2_n = 0l;//任务二学生参与人数
		long m_2_n_f = 0l;//任务二学生完成人数
		long m_2_gc = 0l;//任务二学生良好人数
		long m_2_ec = 0l;//任务二学生优秀人数
		long m_2_woc = 0l;//任务二学生消灭错题份数
		long m_2_gn = 0l;//任务二学生消灭错题份数良好份数
		long m_2_en = 0l;//任务二学生消灭错题份数优秀份数
		
		long m_3_n = 0l;//任务三学生参与人数
		long m_3_n_f = 0l;//任务三学完成人数
		long m_3_ec = 0l;//任务三学生自主练习完成份数
		long m_3_tn = 0l;//任务三学生自主练习完成题目数
		long m_3_rn = 0l;//任务三学生自主练习完成题目正确数
		
		if(CollectionUtils.isNotEmpty(l)){
			for(UserActivitiesRecord record : l){
				List<MissionStatus> chain = record.getMissionChain();
				if(CollectionUtils.isNotEmpty(chain)){
					for(MissionStatus ms:chain){
						boolean is_m_3 = false;
						boolean is_m_3_f = false;
						MissionEnum me = MissionEnum.getMissionEnum(ms.getMissionKey());
						Map<String, Object> map = ms.getData();
						switch (me) {
						case Mission_1:
							m_1_n++;
							m_1_n_f += (ms.getState()==3?1:0);
							if(map!=null){
								m_1_bt = append(m_1_bt,(Long)map.get("finishTotal"));
							}
							break;
						case Mission_2:
							m_2_n++;
							m_2_n_f += (ms.getState()==3?1:0);
							if(map!=null){
								Long state = (Long)map.get("state");
								if(state!=null){
									if(state.equals(1L)){
										//良好
										m_2_gc += 1;
									}else if(state.equals(2L)){
										//优秀
										m_2_ec += 1;
									}
								}
								m_2_woc = append(m_2_woc,(Long)map.get("wipeOutCount"));
								m_2_gn = append(m_2_gn,(Long)map.get("goodCount"));
								m_2_en = append(m_2_en,(Long)map.get("excellentCount"));
							}
							break;
						case Mission_3:
							if(!is_m_3){
								is_m_3 = true;
								m_3_n++;
							}
							if(!is_m_3_f){
								is_m_3_f = true;
								m_3_n_f += (ms.getState()==3?1:0);
							}
							if(map!=null){
								m_3_ec = append(m_3_ec,(Long)map.get("exerciseCount"));
								m_3_tn = append(m_3_tn,(Long)map.get("totalNum"));
								m_3_rn = append(m_3_rn,(Long)map.get("rightNum"));
							}
							break;
						default:
							break;
						}
					}
				}
			}
		}
		double m_3_a = m_3_tn==0?0.0:BigDecimal.valueOf(m_3_rn*100).divide(BigDecimal.valueOf(m_3_tn)).setScale(2, RoundingMode.HALF_UP).doubleValue();
		Map<String,Object> map = new HashMap<>();
		map.put("m_1_n",m_1_n);
		map.put("m_1_n_f",m_1_n_f);
		map.put("m_1_bt",m_1_bt);
		map.put("m_2_n",m_2_n);
		map.put("m_2_n_f",m_2_n_f);
		map.put("m_2_gc",m_2_gc);
		map.put("m_2_ec",m_2_ec);
		map.put("m_2_woc",m_2_woc);
		map.put("m_2_gn",m_2_gn);
		map.put("m_2_en",m_2_en);
		map.put("m_3_n",m_3_n);
		map.put("m_3_n_f",m_3_n_f);
		map.put("m_3_ec",m_3_ec+"%");
		map.put("m_3_a",m_3_a);
		list.add(map);
		return list;
	}
	
	@Override
	public void addAccessRecord(Long userId,String userName){
		AccessRecord accessRecord = new AccessRecord();
		Date now = new Date();
		accessRecord.setDate(DateUtils.format(now, DateUtils.SHORT_DATE_PATTERN));
		accessRecord.setUserId(userId);
		accessRecord.setUserName(userName);
		accessRecord.setTime(now);
		accessRecordDao.insert(accessRecord);
	}
	
	private MissionStatus getMissionStatus(MissionEnum me,UserActivitiesRecord record){
		Mission mission = getMission(me.getType());
		if(mission.isActive(record)){
			MissionStatus ms = new MissionStatus();
			ms.setType(me.getType());
			ms.setMissionKey(me.getMissionKey());
			ms.setState(1);
			ms.setDay(DateUtils.getDay(new Date()));
			return ms;
		}else{
			return getMissionStatus(me.getNext(), record);
		}
	}

	private Mission getMission(String type) {
		return SpringContextHolder.getBean(type);
	}
	
	private long append(long a,Long b){
		if(b!=null){
			return a+b;
		}
		return a;
	}
	
	/** 活动奖励 */
	private Long ACTIVITY_REWARD = -10004L;

	private void sendPrize(Long userId,String userName,Long schoolId,MissionStatus ms){
		DynamicInfo dynamicInfo = new DynamicInfo();
		dynamicInfo.setUserId(userId);
		dynamicInfo.setUserName(userName);
		dynamicInfo.setRoleId(RoleCst.STUDENT);
		dynamicInfo.setSchoolId(schoolId);
		dynamicInfo.setTitle("活动奖励");
		dynamicInfo.setLekeVal(ms.getLekeVal());
		dynamicInfo.setExpVal(0);
		dynamicInfo.setTypeId(ACTIVITY_REWARD);
		dynamicInfo.setIsReward(true);
		ExtraInfo extraInfo = UserExtraContext.getUserExtraInfoByUserId(userId);
		extraInfo.setLekeVal(extraInfo.getLekeVal() + dynamicInfo.getLekeVal());
		extraInfo.setExpVal(extraInfo.getExpVal() + dynamicInfo.getExpVal());
		UserExtraContext.updateUserExtraInfo(extraInfo);
		userDynamicSender.send(dynamicInfo);
	}
	
}
