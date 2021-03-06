/* 
 * 包名: cn.strong.leke.question.service.impl
 *
 * 文件名：QuestionLogService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: lavender
 *
 * 日期：2014-4-24
 */
package cn.strong.leke.question.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.common.utils.function.Function;
import cn.strong.leke.common.utils.function.Functions;
import cn.strong.leke.context.base.SchoolStageContext;
import cn.strong.leke.context.question.MaterialContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.model.base.SchoolStageSubject;
import cn.strong.leke.model.question.Material;
import cn.strong.leke.model.question.QuestionLog;
import cn.strong.leke.model.question.querys.MaterialFilter;
import cn.strong.leke.question.core.question.query.IQuestionQueryService;
import cn.strong.leke.question.dao.mybatis.IQuestionLogDao;
import cn.strong.leke.question.model.InputStatisDTO;
import cn.strong.leke.question.model.InputStatisQuery;
import cn.strong.leke.question.model.MaterialAmount;
import cn.strong.leke.question.model.MaterialQuestionAmount;
import cn.strong.leke.question.model.SubjectMaterial;
import cn.strong.leke.question.model.VerifyStatisDTO;
import cn.strong.leke.question.service.IQuestionLogService;
import cn.strong.leke.question.service.IQuestionTaskService;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.model.tukor.UserRemote;
import cn.strong.leke.remote.service.tutor.base.IGradeRemoteService;
import cn.strong.leke.remote.service.tutor.base.ISchoolStageRemoteService;
import cn.strong.leke.remote.service.tutor.base.ISubjectRemoteService;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;

/**
 * 题目统计服务层实现
 * @author    lavender
 * @version   Avatar 
 */
@Service
public class QuestionLogService implements IQuestionLogService {
	@Resource
	private IQuestionLogDao questionLogDao;
	@Resource
	private IUserRemoteService userRemoteService;
	@Resource
	private IGradeRemoteService gradeRemoteService;
	@Resource
	private ISubjectRemoteService subjectRemoteService;
	@Resource
	private ISchoolStageRemoteService schoolStageRemoteService;
	@Resource
	private IQuestionTaskService questionTaskService;
	@Resource
	private IQuestionQueryService questionQueryService;
	
	//--------------------------------------admin---------------------------------------//
	@Override
	public List<InputStatisDTO> findInputAmountList(InputStatisQuery query, String userName) {
		//远程接口调用角色用户列表
		List<UserRemote> userList = userRemoteService.findUserListForQuestion(userName, RoleCst.INPUTER);
		if (null != userList && userList.size() > 0) {
			//用户id赋值
			query.setUserIds(findUserIdList(userList));
			List<InputStatisDTO> inputStatisList = questionLogDao.queryInputAmountList(query);
			List<InputStatisDTO> effectiveList = questionLogDao.queryInputAmountEffectiveList(query);

			for (int i = 0; i < inputStatisList.size(); i++) {
				for (UserRemote userRemote : userList) {
					if (inputStatisList.get(i).getUserId().equals(userRemote.getId())) {
						inputStatisList.get(i).setUserName(
								userRemote.getUserName());
						break;
					}
				}

				for (InputStatisDTO e : effectiveList) {
					if (inputStatisList.get(i).getUserId().equals(e.getUserId())) {
						inputStatisList.get(i).setEffectiveAmount(e.getEffectiveAmount());
					}
				}

			}
			
			return inputStatisList;
		}
		return null;
	}

	@Override
	public List<InputStatisDTO> findInputAmountExecuteList(InputStatisQuery query, String userName) {
		//远程接口调用角色用户列表
		List<UserRemote> userList = userRemoteService.findUserListForQuestion(userName, RoleCst.INPUTER);
		List<UserRemote> researcherList = userRemoteService.findUserListForQuestion(userName, RoleCst.RESEARCHER);
		for (UserRemote userRemote : researcherList){
			if (findUserIdList(userList).contains(userRemote.getId()) == false){
				userList.add(userRemote);
			}
		}
		
		if (null != userList && userList.size() > 0) {
			InputStatisDTO inputStatisDTO = new InputStatisDTO();
			inputStatisDTO.setStartDate(query.getStartDate());
			inputStatisDTO.setEndDate(query.getEndDate());
			inputStatisDTO.setUserIds(findUserIdList(userList));
				
			List<InputStatisDTO> inputStatisList = questionQueryService.queryInputStatisDTO(inputStatisDTO);
			for (int i = 0; i < inputStatisList.size(); i++) {
				for (UserRemote userRemote : userList){
					if ((userRemote.getId() - inputStatisList.get(i).getUserId()) == 0){
						if (inputStatisList.get(i).getInputAmount() > 0){
							inputStatisList.get(i).setUserName(userRemote.getUserName());
						}else{
							inputStatisList.remove(i);
						}
					}
				}
			}
			return inputStatisList;
		}
		return null;
	}

	@Override
	public List<InputStatisDTO> findInputAmountExecuteListByUserIdOrderByDate(Long userId, InputStatisQuery query) {
		if (null != userId && userId > 0) {
			query.setUserId(userId);
		}
		List<InputStatisDTO> inputStatisList = questionLogDao.queryInputAmountListByUserIdOrderByDate(query);
		List<InputStatisDTO> effectiveList = questionLogDao.queryInputAmountEffectiveListByUserIdOrderByDate(query);
		for (int i = 0; i < inputStatisList.size(); i++) {
			for (InputStatisDTO e : effectiveList) {
				if(inputStatisList.get(i).getCreatedDate().equals(e.getCreatedDate())) {
					inputStatisList.get(i).setEffectiveAmount(e.getEffectiveAmount());
				}
			}
		}
		return inputStatisList;
	}
	
	@Override
	public List<InputStatisDTO> findInputAmountListByUserIdOrderByDate(Long userId, InputStatisQuery query) {
		if (null != userId && userId > 0) {
			query.setUserId(userId);
		}
		List<InputStatisDTO> inputStatisList = questionLogDao.queryInputAmountListByUserIdOrderByDate(query);
		List<InputStatisDTO> effectiveList = questionLogDao.queryInputAmountEffectiveListByUserIdOrderByDate(query);
		for (int i = 0; i < inputStatisList.size(); i++) {
			for (InputStatisDTO e : effectiveList) {
				if(inputStatisList.get(i).getCreatedDate().equals(e.getCreatedDate())) {
					inputStatisList.get(i).setEffectiveAmount(e.getEffectiveAmount());
				}
			}
		}
		return inputStatisList;
	}

	@Override
	public List<InputStatisDTO> findCheckAmountList(InputStatisQuery statisDTO, String userName) {
		
		//远程接口调用角色用户列表
		List<UserRemote> userList = userRemoteService.findUserListForQuestion(userName, RoleCst.CHECKER);
		if (null != userList && userList.size() > 0) {
			statisDTO.setUserIds(findUserIdList(userList));

			//map连接审核量列表和领取量列表，合并为同一列表
			Map<Long, InputStatisDTO> map = new TreeMap<Long, InputStatisDTO>();
			//审核量列表
			List<InputStatisDTO> checkAmountList = Collections.emptyList();
			//有效量列表
			List<InputStatisDTO> effectiveAmountList = questionLogDao.queryCheckerEffectiveAmountList(statisDTO);
			//领取量列表
			List<InputStatisDTO> taskAmountList = questionTaskService.findTaskAmountListOrderByUserId(statisDTO);
			if (CollectionUtils.isNotEmpty(checkAmountList)) {
				for (InputStatisDTO isd : checkAmountList) {
					Long userId = isd.getUserId();
					map.put(userId, isd);
				}
			}
			if (CollectionUtils.isNotEmpty(effectiveAmountList)) {
				for (InputStatisDTO isd3 : effectiveAmountList) {
					Long userId = isd3.getUserId();
					InputStatisDTO old = map.get(userId);
					if (old != null) {
						old.setEffectiveAmount(isd3.getEffectiveAmount());
					} else {
						map.put(userId, isd3);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(taskAmountList)) {
				for (InputStatisDTO isd2 : taskAmountList) {
					Long userId = isd2.getUserId();
					InputStatisDTO old = map.get(userId);
					if (old != null) {
						old.setTaskAmount(isd2.getTaskAmount());
					} else {
						map.put(userId, isd2);
					}
				}
			}

			List<InputStatisDTO> result = new ArrayList<InputStatisDTO>();
			for (Map.Entry<Long, InputStatisDTO> entry : map.entrySet()) {
				result.add(entry.getValue());
			}

			for (int i = 0; i < result.size(); i++) {
				for (UserRemote userRemote : userList) {
					if (result.get(i).getUserId().equals(userRemote.getId())) {
						result.get(i).setUserName(userRemote.getUserName());
						break;
					}
				}//用户名赋值
			}
			return result;
		}
		return null;
	}

	/* 
	(non-Javadoc)
	 * @see cn.strong.leke.question.service.IQuestionLogService#findCheckerCheckAmountListByUserIdOrderByDate(java.util.Date, java.util.Date, java.lang.Long, cn.strong.leke.question.model.InputStatisDTO)
	 */
	@Override
	public List<InputStatisDTO> findCheckerCheckAmountList(InputStatisQuery statisDTO, Long userId) {
		if (null != userId && userId > 0) {
			statisDTO.setUserId(userId);
		}
		//map连接审核量列表和领取量列表，合并为同一列表
		Map<Date, InputStatisDTO> map = new TreeMap<Date, InputStatisDTO>();
		//审核量列表
		List<InputStatisDTO> checkAmountList = questionLogDao.queryCheckAmountListByUserIdOrderByDate(statisDTO);
		//有效量列表
		List<InputStatisDTO> effectiveAmountList = questionLogDao.queryCheckerEffectiveAmountListByUserId(statisDTO);
		//领取量列表
		List<InputStatisDTO> taskAmountList = questionTaskService.findTaskAmountListByUserIdOrderByDate(statisDTO);
		if (CollectionUtils.isNotEmpty(checkAmountList)) {
			for (InputStatisDTO isd : checkAmountList) {
				Date dateString = isd.getCreatedDate();
				map.put(dateString, isd);
			}
		}
		if (CollectionUtils.isNotEmpty(effectiveAmountList)) {
			for (InputStatisDTO isd3 : effectiveAmountList) {
				Date date3 = isd3.getCreatedDate();
				InputStatisDTO old = map.get(date3);
				if (old != null) {
					old.setEffectiveAmount(isd3.getEffectiveAmount());
				} else {
					map.put(date3, isd3);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(taskAmountList)) {
			for (InputStatisDTO isd2 : taskAmountList) {
				Date date2 = isd2.getCreatedDate();
				InputStatisDTO old = map.get(date2);
				if (old != null) {
					old.setTaskAmount(isd2.getTaskAmount());
				} else {
					map.put(date2, isd2);
				}
			}
		}
		List<InputStatisDTO> result = new ArrayList<InputStatisDTO>();
		for (Map.Entry<Date, InputStatisDTO> entry : map.entrySet()) {
			result.add(entry.getValue());
		}

		return result;
	}

	@Override
	public List<InputStatisDTO> findVerifyAmountList(InputStatisQuery query, String userName) {
		return Collections.emptyList();
	}

	@Override
	public List<InputStatisDTO> findProofreadingAmountListByUserIdOrderByDate(Long userId, InputStatisQuery query) {
		return Collections.emptyList();
	}

	/* 
	(non-Javadoc)
	 * @see cn.strong.leke.question.service.IQuestionLogService#findUnVerifyAmountBySchoolStageSubjects(cn.strong.leke.question.model.InputStatisQuery)
	 */
	@Override
	public InputStatisDTO findUnVerifyAmountBySchoolStageSubjects(Long userId, InputStatisQuery query) {
		return null;
	}

	@Override
	public List<SubjectMaterial> findQuestionAmountByMaterial(InputStatisQuery query) {
		if (query == null || query.getSchoolStageId() == null) {
			return Collections.emptyList();
		}
		if (null == query.getStatus()) {
			query.setStatus(1);
		}

		List<SubjectMaterial> result = new ArrayList<SubjectMaterial>();
		SchoolStageRemote schoolStage = SchoolStageContext.getSchoolStage(query.getSchoolStageId());
		List<SubjectRemote> subjectList = schoolStage.getSubjects();
		for (SubjectRemote subjectRemote : subjectList) {
			SubjectMaterial subjectMaterial = findSubjectMaterial(query, subjectRemote);
			if (subjectMaterial != null) {
				result.add(subjectMaterial);
			}
		}
		return result;
	}

	private SubjectMaterial findSubjectMaterial(InputStatisQuery query, SubjectRemote subject) {
		if (subject == null || subject.getSubjectId() == null) {
			return null;
		}
		SubjectMaterial sm = new SubjectMaterial();
		sm.setSubjectId(subject.getSubjectId());
		sm.setSubjectName(subject.getSubjectName());

		query.setSubjectId(subject.getSubjectId());

		List<MaterialQuestionAmount> sources = null;
		if (query.getStatus() == 1) {
			sources = questionLogDao.queryQuestionAndMaterialChecked(query);
		} else {
			sources = questionLogDao.queryQuestionAndMaterialUnChecked(query);
		}
		Map<Long, Integer> matCounts = countByMaterialId(sources);
		
		List<Material> materials = new ArrayList<Material>();
		MaterialFilter filter = new MaterialFilter();
		filter.setPressId(query.getPressId());
		filter.setSchoolStageId(query.getSchoolStageId());
		filter.setSubjectId(query.getSubjectId());
		materials = MaterialContext.findMaterials(filter);
		List<MaterialAmount> mqas = new ArrayList<MaterialAmount>();
		for (Material material : materials) {
			MaterialAmount mqa = new MaterialAmount();
			Integer count = matCounts.get(material.getMaterialId());
			count = count == null ? 0 : count;
			mqa.setMaterialId(material.getMaterialId());
			mqa.setMaterialName(material.getMaterialName());
			mqa.setAmount(count);
			mqas.add(mqa);
		}
		sm.setMaterialQuestionAmounts(mqas);
		return sm;
	}

	private Map<Long, Integer> countByMaterialId(List<MaterialQuestionAmount> mqas) {
		if (CollectionUtils.isEmpty(mqas)) {
			return Collections.emptyMap();
		}
		Map<Long, Integer> result = new HashMap<Long, Integer>();
		for (MaterialQuestionAmount amount : mqas) {
			Long materialId = amount.getMaterialId();
			Integer old = result.get(materialId);
			int count = old == null ? 1 : old + 1;
			result.put(materialId, count);
		}
		return result;
	}

	@Override
	public List<InputStatisDTO> findStatisListGroupByKnowledge() {
		List<InputStatisDTO> inputStatisList = questionLogDao.queryStatisListGroupByKnowledge();
		
		return inputStatisList;
	}

	@Override
	public List<InputStatisDTO> findStatisGroupByOfficialTagId() {
		return questionLogDao.queryStatisGroupByOfficialTagId();
	}
	
	@Override
	public List<InputStatisDTO> findStatisByOfficialTagId(Long officialTagId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("officialTagId", officialTagId);
		List<InputStatisDTO> inputStatisList = questionLogDao.queryStatisByOfficialTagId(map);
		
		return inputStatisList;
	}

	@Override
	public InputStatisDTO findStatisByType(Integer type) {
		if (null != type && type > 0){
			return questionLogDao.queryStatisByType(type);
		}
		return null;
	}
	

	@Override
	public List<InputStatisDTO> findDraftAmountList() {
		List<InputStatisDTO> inputStatisList = questionLogDao.queryDraftAmountList();
		
		return inputStatisList;
	}
	

	@Override
	public InputStatisDTO findDraftAndFormalTotalAmount() {
		return questionLogDao.queryDraftAndFormalTotalAmount();
	}

	//--------------------------------------checker---------------------------------------//

	@Override
	public InputStatisDTO findCheckAmountByUserId(Long userId) {
		InputStatisQuery query = new InputStatisQuery();
		if (null != userId && userId > 0) {
			query.setUserId(userId);
		}
		//未审核量赋值
		InputStatisDTO unCheckDTO = questionLogDao.queryUnCheckAmountByUserId(query);
		
		return unCheckDTO;
	}

	@Override
	public List<InputStatisDTO> findCheckAmountListByUserId(Long userId, InputStatisQuery query) {
		
		if (null != userId && userId > 0) {
			query.setUserId(userId);
		}
		
		return questionLogDao.queryCheckAmountListByUserId(query);
	}

	//--------------------------------------inputer---------------------------------------//
	
	@Override
	public InputStatisDTO findInputAmountByUserId(Long userId) {
		InputStatisQuery query = new InputStatisQuery();
		if (null != userId && userId > 0) {
			query.setUserId(userId);
		}
		InputStatisDTO isDTO = questionLogDao.queryInputAmountByUserId(query);
		if(isDTO == null) {
			isDTO = new InputStatisDTO();
			isDTO.setInputAmount(0);
		}
		return isDTO;
	}

	@Override
	public List<InputStatisDTO> findInputAmountListByUserId(Long userId,
 InputStatisQuery query) {
		
		if (null != userId && userId > 0) {
			query.setUserId(userId);
		}
		
		return questionLogDao.queryInputAmountListByUserId(query);
	}
	
	//--------------------------------------researcher---------------------------------------//

	@Override
	public InputStatisDTO findProofreadingAmountByUserId(Long userId) {
		return null;
	}

	@Override
	public void addQuestionLog(QuestionLog questionLog) {
		questionLogDao.insertQuestionLog(questionLog);
	}

	@Override
	public List<QuestionLog> findQuestionLogByQuestionId(Long questionId) {
		return questionLogDao.findQuestionLogByQuestionId(questionId);
	}
	
	@Override
	public List<VerifyStatisDTO> findResearcherVerifyStatis(List<SchoolStageSubject> schoolStageSubjects) {
		if (CollectionUtils.isEmpty(schoolStageSubjects)) {
			return Collections.emptyList();
		}
		final Map<Long, String> grades = mapGrades(schoolStageSubjects);
		final Map<Long, String> subjects = mapSubjects(schoolStageSubjects);
		List<VerifyStatisDTO> result = questionLogDao.findResearcherVerifyStatis(schoolStageSubjects);
		if (CollectionUtils.isNotEmpty(result)) {
			for (VerifyStatisDTO vs : result) {
				vs.setGradeName(grades.get(vs.getGradeId()));
				vs.setSubjectName(subjects.get(vs.getSubjectId()));
			}
		}
		return result;
	}

	public Map<Long, String> mapGrades(List<SchoolStageSubject> schoolStageSubjects) {
		Function<SchoolStageSubject, Long> idFunc = Functions.prop("schoolStageId");
		Function<SchoolStageSubject, String> nameFunc = Functions.prop("schoolStageName");
		return ListUtils.toMap(schoolStageSubjects, idFunc, nameFunc);
	}

	public Map<Long, String> mapSubjects(List<SchoolStageSubject> schoolStageSubjects) {
		Function<SchoolStageSubject, Long> idFunc = Functions.prop("subjectId");
		Function<SchoolStageSubject, String> nameFunc = Functions.prop("subjectName");
		return ListUtils.toMap(schoolStageSubjects, idFunc, nameFunc);
	}

	/*
	(non-Javadoc)
	 * @see cn.strong.leke.question.service.IQuestionLogService#findResearcherCheckAmountList(cn.strong.leke.question.model.InputStatisDTO)
	 */
	@Override
	public List<InputStatisDTO> findResearcherCheckAmountList(InputStatisQuery query) {
		return Collections.emptyList();
	}

	/* 
	(non-Javadoc)
	 * @see cn.strong.leke.question.service.IQuestionLogService#findResearcherCheckAmountListByUserId(cn.strong.leke.question.model.InputStatisDTO)
	 */
	@Override
	public List<InputStatisDTO> findResearcherCheckAmountListByUserId(InputStatisQuery query) {
		if (null != query.getUserId() && query.getUserId() > 0) {
			List<InputStatisDTO> inputStatisList = questionLogDao.queryResearcherCheckAmountListByUserId(query);
			return inputStatisList;
		}
		return null;
	}

	//--------------------------------------private method---------------------------------------//

	/**
	 *	
	 * 描述:根据用户列表获取用户id列表
	 *
	 * @author  lavender
	 * @created 2014年11月3日 下午4:32:18
	 * @since   v1.0.0 
	 * @param userList
	 * @return
	 * @return  List<Long>
	 */
	private List<Long> findUserIdList(List<UserRemote> userList) {
		List<Long> userIdList = new ArrayList<Long>();
		for (UserRemote user : userList) {
			userIdList.add(user.getId());
		}
		return userIdList;
	}


	/* 
	(non-Javadoc)
	 * @see cn.strong.leke.question.service.IQuestionLogService#findResearcherImportedAmountList(cn.strong.leke.question.model.InputStatisDTO)
	 */
	@Override
	public List<InputStatisDTO> findResearcherImportedAmountList(InputStatisQuery query) {
		//远程接口调用角色用户列表
		List<UserRemote> userList = userRemoteService.findUserListForQuestion(query.getUserName(), RoleCst.RESEARCHER);
		List<InputStatisDTO> inputStatisList = new ArrayList<InputStatisDTO>();
		if (userList != null && userList.size() > 0) {
			//用户id赋值
			query.setUserIds(findUserIdList(userList));
			inputStatisList = questionLogDao.queryResearcherImportedAmountList(query);

			for (int i = 0; i < inputStatisList.size(); i++) {
				for (UserRemote userRemote : userList) {
					if (inputStatisList.get(i).getUserId().equals(userRemote.getId())) {
						inputStatisList.get(i).setUserName(
								userRemote.getUserName());
						break;
					}
				}

			}
		}
		return inputStatisList;
	}

	/* 
	(non-Javadoc)
	 * @see cn.strong.leke.question.service.IQuestionLogService#findResearcherImportedAmountListByUserId(cn.strong.leke.question.model.InputStatisDTO)
	 */
	@Override
	public List<InputStatisDTO> findResearcherImportedAmountListByUserId(InputStatisQuery query) {
		if (null != query.getUserId() && query.getUserId() > 0) {
			List<InputStatisDTO> inputStatisList = questionLogDao.queryResearcherImportedAmountListByUserId(query);
			return inputStatisList;
		}
		return null;
	}

}
