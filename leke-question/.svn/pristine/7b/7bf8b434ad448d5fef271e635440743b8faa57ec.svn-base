/* 
 * 包名: cn.strong.leke.question.service.impl
 *
 * 文件名：QuestionTaskService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-5-7
 */
package cn.strong.leke.question.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.model.base.GradeSubject;
import cn.strong.leke.model.user.Checker;
import cn.strong.leke.question.dao.mybatis.IQuestionTaskDao;
import cn.strong.leke.question.model.InputStatisDTO;
import cn.strong.leke.question.model.InputStatisQuery;
import cn.strong.leke.question.model.QuestionTask;
import cn.strong.leke.question.model.QuestionTaskItem;
import cn.strong.leke.question.service.IQuestionTaskConfigService;
import cn.strong.leke.question.service.IQuestionTaskService;
import cn.strong.leke.remote.model.tukor.UserRemote;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

/**
 * 题目领取服务层实现
 * @author    lavender
 * @version   Avatar 
 */
@Service
public class QuestionTaskService implements IQuestionTaskService {
	@Resource
	private IQuestionTaskDao questionTaskDao;
	@Resource
	private IQuestionTaskConfigService questionTaskConfigService;
	@Resource
	private IUserRemoteService userRemoteService;

	@Override
	public Integer checkFinished(Long userId) {
		QuestionTask qt = new QuestionTask();
		if(userId != null && userId > 0) {
			qt.setUserId(userId);
		}
		return questionTaskDao.checkFinished(qt);
	}

	@Override
	public Integer addQuestionTask(QuestionTask questionTask) {
		if (questionTask == null || questionTask.getGradeId() == null || questionTask.getUserId() == null || questionTask.getSubjectId() == null || questionTask.getTaskCount() == null) {
			throw new ValidateException("que.questionTask.count.exceed");
		}
		int count = questionTask.getTaskCount();
		//获取最大可领取量
		int left = questionTaskDao.maxTaskCount(questionTask);
		if(left<count) {
			//实际领取量超过可领取最大量，抛异常
			throw new ValidateException("que.questionTask.count.exceed", left, count);
		}
		left = questionTaskConfigService.findTaskCount();
		if(left < count) {
			//实际领取量超过可习题领取设置量，抛异常
			throw new ValidateException("que.questionTask.count.config", left, count);
		}
		//保存任务领取量
		Integer saveQuestionTask = questionTaskDao.saveQuestionTask(questionTask);
		//保存任务领取量详情
		if(saveQuestionTask != null && saveQuestionTask > 0) {
			//获取未领取过的题目id列表
			List<Long> questionIdList = questionTaskDao.queryUnTaskedQuestionIdList(questionTask);
			//预计插入量
			int itemNumber =questionIdList.size();
			//实际插入量
			int resultNumber=0;
			if(itemNumber>0){
				for (Long questionId : questionIdList) {
					//数据库插入任务领取详情单条数据
					QuestionTaskItem qtItem = new QuestionTaskItem();
					qtItem.setCreatedBy(questionTask.getUserId());
					qtItem.setModifiedBy(questionTask.getUserId());
					qtItem.setTaskId(questionTask.getTaskId());
					qtItem.setQuestionId(questionId);
					qtItem.setUserId(questionTask.getUserId());
					if (questionTaskDao.saveQuestionTaskItem(qtItem)>0) {
						resultNumber +=1;
					}
				}
			}
			//判断插入量
			if (itemNumber == resultNumber) {
				return 1;
			} else {
				return 0;
			}
		}
		
		return null;
	}

	@Override
	public List<QuestionTask> findQuestionTaskByUserId(Checker checker) {
		QuestionTask qt = new QuestionTask();
		Long userId = checker.getId();
		if (null != userId && userId > 0) {
			qt.setUserId(userId);
		}
		//获取该审核员对应的年级科目
		List<GradeSubject> gsList = checker.getGradeSubjects();
		//将GradeSubject列表转换为QuestionTask列表
		List<QuestionTask> allQuestionTasks = new ArrayList<QuestionTask>();
		for (GradeSubject gs : gsList) {
			QuestionTask q = new QuestionTask();
			q.setCreatedBy(userId);
			q.setGradeId(gs.getGradeId());
			q.setGradeName(gs.getGradeName());
			q.setSubjectId(gs.getSubjectId());
			q.setSubjectName(gs.getSubjectName());
			allQuestionTasks.add(q);
		}
		
		//查询用户各年级科目历史领取数量
		List<QuestionTask> qtList = questionTaskDao.queryQuestionTaskByUserId(qt);
		//将allQuestionTasks与qtList两个列表进行联合，成为最终的resultList存放所有数据
		Map<String, QuestionTask> map = new TreeMap<String, QuestionTask>();
		if(CollectionUtils.isNotEmpty(allQuestionTasks)) {
			for(QuestionTask task : allQuestionTasks) {
				String gsString = task.getGradeId() + "-" + task.getSubjectId();
				map.put(gsString, task);
			}
		}
		if(CollectionUtils.isNotEmpty(qtList)) {
			for(QuestionTask task2 : qtList) {
				String gsString = task2.getGradeId() + "-" + task2.getSubjectId();
				QuestionTask old = map.get(gsString);
				if(old != null) {//只在前台显示该用户可以领取的年级科目下的题量
					old.setTaskCount(task2.getTaskCount());
				}/* else {
					map.put(gsString, task2);
				}*/
			}
		}
		List<QuestionTask> resultList = new ArrayList<QuestionTask>();
		for(Map.Entry<String, QuestionTask> entry: map.entrySet()) {
			resultList.add(entry.getValue());
		}
		
		//查询用户各年级科目剩余量
		List<QuestionTask> surplusList = questionTaskDao.querySurplusQuestionTaskByUserId(qt);
		
		for (int i = 0; i < resultList.size(); i++) {
			//将各年级科目的剩余数量赋值
			for (QuestionTask surplus : surplusList) {
				if (resultList.get(i).getGradeId().equals(surplus.getGradeId()) && resultList.get(i).getSubjectId().equals(surplus.getSubjectId())) {
					resultList.get(i).setSurplusCount(surplus.getSurplusCount());
				}
			}
			if(resultList.get(i).getSurplusCount() == null) {
				resultList.get(i).setSurplusCount(0);
			}
			if(resultList.get(i).getTaskCount() == null) {
				resultList.get(i).setTaskCount(0);
			}
		}
		
		return resultList;
	}

	@Override
	public List<QuestionTask> findQuestionTaskSurplusGroupByUserId(String userName) {
		//根据角色查询用户列表并赋值
		List<UserRemote> userList = userRemoteService.findUserListForQuestion(userName, (long)401);
		List<Long> userIdList = new ArrayList<Long>();
		for (UserRemote user : userList) {
			userIdList.add(user.getId());
		}
		
		QuestionTask qTask = new QuestionTask();
		qTask.setUserIds(userIdList);
		//查询领取习题剩余数量
		List<QuestionTask> qtList = questionTaskDao.queryQuestionTaskSurplusGroupByUserId(qTask);
		//用户名赋值
		for (int i = 0; i < qtList.size(); i++) {
			qtList.get(i).setUserId(qtList.get(i).getCreatedBy());
			for (UserRemote userRemote : userList) {
				if(qtList.get(i).getUserId().equals(userRemote.getId())) {
					qtList.get(i).setUserName(userRemote.getUserName());
					break;
				}
			}
		}
		return qtList;
	}
	
	@Override
	public List<InputStatisDTO> findTaskAmountListByUserIdOrderByDate(InputStatisQuery query) {
		if (query != null) {
			return questionTaskDao.queryTaskAmountListByUserIdOrderByDate(query);
		}
		return null;
	}

	/* 
	(non-Javadoc)
	 * @see cn.strong.leke.question.service.IQuestionTaskService#findTaskAmountListOrderByUserId(cn.strong.leke.question.model.InputStatisDTO)
	 */
	@Override
	public List<InputStatisDTO> findTaskAmountListOrderByUserId(InputStatisQuery query) {
		if (query != null) {
			return questionTaskDao.queryTaskAmountListOrderByUserId(query);
		}
		return null;
	}

	@Override
	public Integer recycleQuestionTask(Long userId) {
		QuestionTask qt = new QuestionTask();
		if(userId != null && userId > 0) {
			qt.setUserId(userId);
		}
		
		return questionTaskDao.recycleQuestionTaskItem(qt);
	}

	@Override
	public void deleteQuestionTaskItemByQuestionId(QuestionTaskItem params) {
		questionTaskDao.deleteQuestionTaskItemByQuestionId(params);
	}

	

}
