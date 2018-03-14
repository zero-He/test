package cn.strong.leke.diag.controller.teachingMonitor;

import cn.strong.leke.context.base.GradeContext;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.tutor.base.IGradeRemoteService;
import cn.strong.leke.remote.service.tutor.base.ISubjectRemoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth/provost/teachingMonitor/comm/")
public class CommQueryParamController {
	
	@Resource
	private IGradeRemoteService gradeRemoteService;
	
	@Resource
	private ISubjectRemoteService subjectRemoteService;
	
	@Resource
	private CommService commService;
	
	@RequestMapping("loadQueryParam")
	@ResponseBody
	public JsonResult loadQueryParam(){
		JsonResult json = new JsonResult();
		
		List<GradeRemote> gradeList = new ArrayList<>();
		List<SubjectRemote> subjectList = new ArrayList<>();
		List<Clazz> classList = new ArrayList<>();
		
		GradeRemote allGrade = new GradeRemote();
		allGrade.setGradeId(null);
		allGrade.setGradeName("全校");
		allGrade.setIsSelected(true);
		gradeList.add(allGrade);
		
		SubjectRemote allSubject = new SubjectRemote();
		allSubject.setSubjectId(null);
		allSubject.setSubjectName("全部学科");
		subjectList.add(allSubject);
		
		Clazz clazz = new Clazz();
		clazz.setClassId(null);
		clazz.setClassName("全部班级");
		classList.add(clazz);
		
		gradeList.addAll(commService.findGradeOfSchool());
		subjectList.addAll(commService.findSubjectOfSchool());
		classList.addAll(commService.findClazzOfSchool());
		
		json.addDatas("gradeList", gradeList);
		json.addDatas("subjectList", subjectList);
		json.addDatas("classList", classList);
		
		return json;
	}
	
	@RequestMapping("loadClassByGrade")
	@ResponseBody
	public JsonResult loadClassByGrade(Long gradeId){
		JsonResult json = new JsonResult();
		List<Clazz> classList = new ArrayList<>();
		Clazz clazz = new Clazz();
		clazz.setClassId(null);
		clazz.setClassName("全部班级");
		if(gradeId == null){
			classList.add(clazz);
			classList.addAll(commService.findClazzOfSchool());
		}else{
			classList.add(clazz);
			classList.addAll(commService.findClazzOfSchoolByGrade(gradeId));
		}
		json.addDatas("classList", classList);
		return json;
	}

	/**
	 * 学生端查询
	 * @param gradeId
	 * @param classType true:行政班，false：非行政班
	 * @return
	 */
	@RequestMapping("loadQueryParamPro")
	@ResponseBody
	public JsonResult loadQueryParamPro(Long gradeId, boolean classType) {
		JsonResult json = new JsonResult();

		List<GradeRemote> gradeList = new ArrayList<>();
		List<SubjectRemote> subjectList = new ArrayList<>();
		List<Clazz> classList = new ArrayList<>();

		List<GradeRemote> gradeOfSchool = commService.findGradeOfSchool();
		gradeOfSchool.forEach(g -> {
			g.setGradeName(g.getGradeName());
		});

		if (gradeId == null && gradeOfSchool.size() > 0) {
			gradeId = gradeOfSchool.get(0).getGradeId();
		}
		GradeRemote allGrade = new GradeRemote();
		allGrade.setGradeId(gradeId);
		allGrade.setGradeName(GradeContext.getGrade(gradeId).getGradeName());
		allGrade.setIsSelected(true);
		gradeList.add(allGrade);

		SubjectRemote allSubject = new SubjectRemote();
		allSubject.setSubjectId(null);
		allSubject.setSubjectName("全部学科");
		subjectList.add(allSubject);

		Clazz clazz = new Clazz();
		clazz.setClassId(null);
		clazz.setClassName("全部班级");
		classList.add(clazz);

		for (int i = 0; i < gradeOfSchool.size(); i++) {
			GradeRemote gradeRemote = gradeOfSchool.get(i);
			if (gradeRemote.getGradeId().equals(gradeId)) {
				gradeOfSchool.remove(gradeRemote);
			}
		}

		gradeList.addAll(gradeOfSchool);
		subjectList.addAll(commService.findSubjectOfGradeBySchool(gradeId));
		classList.addAll(classType ? commService.queryClassByGradeId(gradeId) : commService.findClazzOfSchoolByGrade(gradeId));

		json.addDatas("gradeList", gradeList.stream().sorted(Comparator.comparing(GradeRemote::getGradeId)).collect(Collectors.toList()));
		json.addDatas("subjectList", subjectList);
		json.addDatas("classList", classList);

		return json;
	}
}
