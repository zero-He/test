package cn.strong.leke.homework.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.homework.model.activities.UserActivitiesRecord;
import cn.strong.leke.homework.model.activity.BugFixHwInfo;
import cn.strong.leke.homework.model.activity.MonthHwInfo;
import cn.strong.leke.homework.model.activity.SimpleExercise;
import cn.strong.leke.homework.service.ActivityService;
import cn.strong.leke.homework.util.ActivityCst;

@Component
public class MissionManager {
	
	@Resource
	private ActivityService activityService;
	
	private static final Integer month = ActivityCst.month;
	private static final double good = ActivityCst.good;//良好
	private static final double excellent = ActivityCst.excellent;//优秀
	
	public enum MissionEnum{
		Mission_3("Mission_3","exerciseMission",null),
		Mission_2("Mission_2","wrongTopicMission",Mission_3),
		Mission_1("Mission_1","correctMission",Mission_2);
		MissionEnum(String missionKey,String type,MissionEnum next){
			this.missionKey = missionKey;
			this.type = type;
			this.next = next;
		}
		private String missionKey;
		private String type;
		private MissionEnum next;
		public String getMissionKey() {
			return missionKey;
		}
		public void setMissionKey(String missionKey) {
			this.missionKey = missionKey;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public MissionEnum getNext() {
			return next == null?this:next;
		}
		public void setNext(MissionEnum next) {
			this.next = next;
		}
		public static MissionEnum getMissionEnum(String missionKey){
			MissionEnum[] list = MissionEnum.values();
			for(MissionEnum me : list){
				if(me.getMissionKey().equals(missionKey)){
					return me;
				}
			}
			return list[0];
		}
	}
	
	public interface Mission{
		/**
		 * 计算乐币奖励
		 * @param map
		 * @return
		 */
		Integer getLekeVal(Map<String,Object> map);
		/**
		 * 任务是否完成
		 * @param status
		 * @return
		 */
		Boolean isDown(Map<String,Object> map);
		
		/**
		 * 任务数据
		 * @param record
		 * @return
		 */
		Map<String,Object> getData(UserActivitiesRecord record);
		
		Boolean isActive(UserActivitiesRecord record);
		
	}
	
	/**
	 * 订正
	 * @return
	 */
	@Bean
	Mission correctMission(){
		return new Mission() {
			
			@Override
			public Integer getLekeVal(Map<String,Object> map){
				return 80;
			}
			
			@Override
			public Boolean isDown(Map<String,Object> map) {
				long bugfixTotal = (Long)map.get("bugfixTotal");
				long finishTotal = (Long)map.get("finishTotal");
				return finishTotal>=bugfixTotal;
			}
			
			@Override
			public Map<String, Object> getData(UserActivitiesRecord record) {
				BugFixHwInfo info = activityService.getBugFixInfo(record.getUserId(), month);
				Map<String, Object> map = new HashMap<>();
				map.put("bugfixTotal", info.getBugfixTotal());
				map.put("finishTotal", info.getFinishTotal());//个人作业订正总数
				return map;
			}
			
			@Override
			public Boolean isActive(UserActivitiesRecord record){
				Map<String, Object> map= getData(record);
				long bugfixTotal = (long)map.get("bugfixTotal");
				return bugfixTotal>0;
			}
		};
	};
	
	/**
	 * 错题本
	 * @return
	 */
	@Bean
	Mission wrongTopicMission(){
		return new Mission() {
			
			@Override
			public Integer getLekeVal(Map<String,Object> map){
				Integer state = (Integer)map.get("state");
				Integer lekeVal;
				switch (state) {
				case 1:
					lekeVal = 20+30;
					break;				
				case 2:
					lekeVal = 20+80;
					break;
				default:
					lekeVal = 20;
					break;
				}
				return lekeVal;
			}
			
			@Override
			public Boolean isDown(Map<String,Object> map) {
				return (Boolean)map.get("isDown");
			}
			
			@Override
			public Map<String, Object> getData(UserActivitiesRecord record) {
				List<MonthHwInfo> list = activityService.findMonthHw(record.getUserId(), month);
				Map<String, Object> map = new HashMap<>();
				long totalCount = 0L;
				long wipeOutCount = 0L;//消灭错题份数
				long goodCount = 0L;//良好
				long excellentCount = 0L;//优秀
				int state = 0;
				if(CollectionUtils.isNotEmpty(list)){
					totalCount = Long.parseLong(new Integer(list.size()).toString());
					wipeOutCount = list.stream().filter(v->{return v.getSubmitStatus()!=null&&v.getSubmitStatus()>=1;}).count();
					goodCount = list.stream().filter(v->{
						return v.getScoreRate()!=null&&v.getScoreRate().doubleValue()<excellent&&v.getScoreRate().doubleValue()>=good;
						}).count();
					excellentCount = list.stream().filter(v->{return v.getScoreRate()!=null&&v.getScoreRate().doubleValue()>=excellent;}).count();
					OptionalDouble avg = list.stream().mapToDouble(v->{
						if(v.getScoreRate()!=null){
							return v.getScoreRate().doubleValue();
						}
						return 0.0;
					}).average();
					if (avg.isPresent()) {
						double a = BigDecimal.valueOf(avg.getAsDouble()).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
						state = a>=excellent?2:a>=good?1:0;
					}
				}
				map.put("totalCount", totalCount);
				map.put("wipeOutCount", wipeOutCount);//、个人消灭错题份数
				map.put("goodCount", goodCount);//个人良好份数
				map.put("excellentCount", excellentCount);//个人优秀份数
				map.put("state", state);//个人 1.良好人数 、2优秀人数
				map.put("isDown", wipeOutCount>=totalCount);
				return map;
			}
			
			@Override
			public Boolean isActive(UserActivitiesRecord record){
				Map<String, Object> map= getData(record);
				long totalCount = (long)map.get("totalCount");
				return totalCount>0;
			}
		};
	};
	
	/**
	 * 自主练习
	 * @return
	 */
	@Bean
	Mission exerciseMission(){
		return new Mission() {
			
			@Override
			public Integer getLekeVal(Map<String,Object> map){
				double accuracy = (double)map.get("accuracy");
				return accuracy>=80?20:accuracy>=60?10:0;
			}
			
			@Override
			public Boolean isDown(Map<String,Object> map) {
				double accuracy = (double)map.get("accuracy");
				long exerciseCount = (long)map.get("exerciseCount");
				return exerciseCount>=3&&accuracy>=60;
			}
			
			@Override
			public Map<String, Object> getData(UserActivitiesRecord record) {
				List<SimpleExercise> list = activityService.findTodayExercis(record.getUserId());
				Map<String, Object> map = new HashMap<>();
				long exerciseCount = 0;
				long rightNum = 0;
				long totalNum = 0;
				if(CollectionUtils.isNotEmpty(list)){
					exerciseCount = list.size();
					for(SimpleExercise e: list){
						if(e.getRightNum()!=null){
							rightNum +=e.getRightNum(); 
						}
						if(e.getTotalNum()!=null){
							totalNum +=e.getTotalNum(); 
						}
					}
				}
				double accuracy = totalNum==0?0.0:BigDecimal.valueOf(rightNum*100).divide(BigDecimal.valueOf(totalNum),2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
				map.put("rightNum", rightNum);//个人 当天题目 正确数
				map.put("totalNum", totalNum);//个人 当天题目 数
				map.put("accuracy", accuracy);//个人 当天 平均正确率
				map.put("exerciseCount", exerciseCount);//个人 当天 自主练习完成份数
				return map;
			}
			
			@Override
			public Boolean isActive(UserActivitiesRecord record){
				return true;
			}
		};
	};
}
