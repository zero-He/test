/* 
 * 包名: cn.strong.leke.homework.service.impl
 *
 * 文件名：DoubtService.java
 *
 * 版权所有：Copyright www.leke.cn Corporation 2014 
 * 
 * 创建者: luf
 *
 * 日期：2014-6-18
 */
package cn.strong.leke.homework.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.context.question.MaterialContext;
import cn.strong.leke.context.question.QuestionContext;
import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.core.business.notice.NoticeHelper;
import cn.strong.leke.core.dfs.FileUtils;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.dao.mybatis.DoubtDao;
import cn.strong.leke.homework.dao.mybatis.ExplainDao;
import cn.strong.leke.homework.manage.WorkDetailService;
import cn.strong.leke.homework.model.Doubt;
import cn.strong.leke.homework.model.DoubtDtl;
import cn.strong.leke.homework.model.Explain;
import cn.strong.leke.homework.model.WorkDetail;
import cn.strong.leke.homework.service.DoubtService;
import cn.strong.leke.homework.util.PhotoUtils;
import cn.strong.leke.model.question.Material;
import cn.strong.leke.model.question.MaterialNode;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.model.question.QuestionResult;
import cn.strong.leke.model.question.QuestionSection;
import cn.strong.leke.model.user.UserBase;
import cn.strong.leke.notice.model.todo.DoubtStuEvent;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.question.IMaterialNodeRemoteService;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    luf
 * @version   Avatar 
 */
@Service("doubtServiceImpl")
public class DoubtServiceImpl implements DoubtService {

	@Resource
	private DoubtDao doubtDao;
	@Resource
	private ExplainDao explainDao;
	@Resource
	private IMaterialNodeRemoteService materialNodeService;
	@Resource
	private WorkDetailService workDetailService;

	@Override
	public boolean saveDoubt(Doubt doubt) {
		doubt.setTargetType(1L);
		doubt.setDeleted(false);
		doubt.setResolved(false);
		doubt.setDoubtTitle(StringUtils.replaceUtf8mb4(doubt.getDoubtTitle()));
		doubt.setDoubtContent(StringUtils.replaceUtf8mb4(doubt.getDoubtContent()));
		if(doubt.getQuestionId()!=null){
			doubt = getQuestionDtail(doubt,doubt.getQuestionId());
		}
		doubtDao.saveDoubt(doubt);
		//发送提问待办 
		this.todo(doubt.getDoubtId(), doubt.getTeacherId(), doubt.getDoubtTitle(), doubt.getCreatedBy(),
				doubt.getUserName());
		return doubt.getDoubtId() > 0;
	}

	@Override
	public List<DoubtDtl> doubtList(Doubt doubt, Page page,Boolean filtration) {
		List<DoubtDtl> list = doubtDao.doubtList(doubt, page);
		if(CollectionUtils.isNotEmpty(list)){
			list.forEach(v->{
				UserBase user= UserBaseContext.getUserBaseByUserId(v.getCreatedBy());
				if(v.getSubjectId()!=null){
					v.setSubjectName(SubjectContext.getSubject(v.getSubjectId()).getSubjectName());
				}
				if(v.getTeacherId()!=null){
					UserBase teacher = UserBaseContext.getUserBaseByUserId(v.getTeacherId());
					if(user!=null){
						v.setTeacherName(teacher.getUserName());
					}
				}
				v.setPhoto(user.getPhoto());
				if(filtration){
					v.setDoubtContent(delHTMLTag(v.getDoubtContent()));
				}
			});
		}
		
		return list;
	}
	
	@Override
	public List<DoubtDtl> getTeacherDoubtl(Doubt doubt, Page page) {
		doubt.setTeacherId(UserContext.user.getUserId());
		List<DoubtDtl> doubts = doubtDao.getTeacherDoubtl(doubt, page);
		/* 老师答疑界面，解答列表数据 */
		if(CollectionUtils.isNotEmpty(doubts)){
			doubts.forEach(v->{
				Explain explain = new Explain();
				explain.setDoubtId(v.getDoubtId());
				explain.setCreatedBy(UserContext.user.getUserId());
				UserBase user= UserBaseContext.getUserBaseByUserId(v.getCreatedBy());
				v.setPhoto(user.getPhoto());
				v.setExplains(explainDao.getExplainList(explain));
				if(v.getSubjectId()!=null){
					v.setSubjectName(SubjectContext.getSubject(v.getSubjectId()).getSubjectName());
				}
				v.setDoubtContent(delHTMLTag(v.getDoubtContent()));
			});
		}

		return doubts;
	}
	
	@Override
	public DoubtDtl getDetail(DoubtDtl doubt) {
		DoubtDtl v = doubtDao.getDetail(doubt);
		if(v.getSubjectId()!=null){
			SubjectRemote subject = SubjectContext.getSubject(v.getSubjectId());
			if(subject!=null){
				v.setSubjectName(subject.getSubjectName());
			}
		}
		if(v.getTeacherId()!=null){
			UserBase user = UserBaseContext.getUserBaseByUserId(v.getTeacherId());
			if(user!=null){
				v.setTeacherName(user.getUserName());
			}
		}
		return v;
	}
	
	@Override
	public void updateSection(Doubt doubt){
		doubtDao.updateSection(doubt);
	}
	
	/**
	 * 学生提问，发送待办
	 */
	private void todo(Long doubtId, Long teacherId, String title, Long studentId, String studentName) {
		DoubtStuEvent event = new DoubtStuEvent();
		event.setDoubtId(doubtId);
		event.setTitle(title);
		event.setTeacherId(teacherId);
		event.setStudentId(studentId);
		event.setStudentName(studentName);
		NoticeHelper.todo(event);
	}
	
	@Override
	public Integer getResolveDoubtTotal(Long teacherId){
		return this.doubtDao.getResolveDoubtTotal(teacherId);
	}

	@Override
	public void deleteMySelfDoubt(Long[] doubtIds,Long roleId) {
		doubtDao.deleteMySelfDoubt(Arrays.asList(doubtIds), roleId);
	}

	@Override
	public void markMySelfDoubt(Long doubtId,Long roleId) {
		doubtDao.markMySelfDoubt(doubtId, roleId);
	}
	
	@Override
	public Integer getTeacherResolveDoubt(Long teacherId){
		return doubtDao.getTeacherResolveDoubt(teacherId);
	}
	
	@Override
	public Integer getStudentResolveDoubt(Long createdBy){
		return null;//doubtDao.getStudentResolveDoubt(createdBy);
	}

	@Override
	public Doubt getQuestionDtail(Doubt doubt, Long questionId) {
		QuestionDTO question = QuestionContext.getQuestionDTO(questionId);
		if(question!=null){
			doubt.setSubjectId(question.getSubjectId());
			doubt.setSchoolStageId(question.getSchoolStageId());
			if(CollectionUtils.isNotEmpty(question.getSections())){
				QuestionSection section = question.getSections().get(0);
				if(section.getMaterialNodeId()!=null){
					MaterialNode materialNode = materialNodeService.getMaterialNodeById(section.getMaterialNodeId());
					if(materialNode!=null){
						Material material = MaterialContext.getMaterial(materialNode.getMaterialId());
						doubt.setMaterialNodeId(materialNode.getMaterialNodeId());
						doubt.setMaterialPathName(section.getPath());
						if(material!=null){
							doubt.setMaterialId(material.getMaterialId());
							doubt.setPressId(material.getPressId());
						}
					}
				}
			}
		}
		return doubt;
	}
	
	@Override
	public Map<String,Object> getDoubt_Explain_Question(DoubtDtl doubt,HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		DoubtDtl doubtDetail = getDetail(doubt);
		String doubtAudio = doubtDetail.getDoubtAudio();
		if (doubtAudio != null && !"".equals(doubtAudio)) {
			doubtDetail.setDoubtAudio(FileUtils.getDomain() + "/" + doubtDetail.getDoubtAudio());//重置录音路径
		}
		if (doubtDetail.getHomeworkDtlId() != null && doubtDetail.getDoubtType() != null
				&& doubtDetail.getDoubtType() == 2) {
			map.put("homeworkDtlId", doubtDetail.getHomeworkDtlId());
			WorkDetail workDetail = this.workDetailService.getWorkDetailByHomeworkDtlId(doubtDetail.getHomeworkDtlId());
			if (workDetail != null) {
				Optional<QuestionResult> optional = workDetail.getQuestions().stream()
						.filter(v -> v.getQuestionId().equals(doubtDetail.getQuestionId())).findFirst();
				optional.ifPresent(questionResult -> map.put("questionResult", questionResult));
			}
		}
		Explain explain = new Explain();
		explain.setDoubtId(doubtDetail.getDoubtId());
		explain.setCreatedBy(doubtDetail.getCreatedBy());
		List<Explain> explains = explainDao.getExplainList(explain);
		for (Explain e : explains) {
			String expainAudio = e.getExpainAudio();
			if (expainAudio != null && !"".equals(expainAudio)) {
				e.setExpainAudio(FileUtils.getDomain() + "/" + e.getExpainAudio());
			}
		}
		UserBase student = UserBaseContext.getUserBaseByUserId(doubtDetail.getCreatedBy());
		UserBase teacher = UserBaseContext.getUserBaseByUserId(doubtDetail.getTeacherId());
		String studentPhoto = PhotoUtils.getAbsolutePhoto(student,request);
		String teacherPhoto = PhotoUtils.getAbsolutePhoto(teacher,request);
		if(doubtDetail.getQuestionId()!=null&&doubtDetail.getQuestionId()==0L){
			doubtDetail.setQuestionId(null);
		}
		map.put("doubt", doubtDetail);
		map.put("explains", explains);
		map.put("studentPhoto", studentPhoto);
		map.put("teacherPhoto", teacherPhoto);
		map.put("teacherId", doubtDetail.getTeacherId());
		return map;
	}
	
	public static String delHTMLTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串 
    }
}
