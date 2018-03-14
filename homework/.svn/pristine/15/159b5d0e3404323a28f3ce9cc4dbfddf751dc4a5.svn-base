package cn.strong.leke.homework.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import cn.strong.leke.homework.model.HomeworkType;
import cn.strong.leke.homework.model.ResRawType;
import cn.strong.leke.homework.model.ResType;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.core.cas.utils.TicketUtils;
import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.homework.model.ApiStudentHomeworkListDTO;
import cn.strong.leke.homework.model.Homework;
import cn.strong.leke.homework.model.query.ApiStuExamQuery;
import cn.strong.leke.homework.model.query.ApiTeaExamQuery;
import cn.strong.leke.homework.service.IExamService;

/**
 * 在线考试
 * @Author LIU.SHITING
 * @Version 2.7
 * @Date 2017-05-24 10:45:51
 */
@RestController
@RequestMapping("/api/w/*")
public class APIExamController {

	@Resource
	private IExamService examService;

	/**
	 * 老师在线考试列表
	 * 支持分页查询
	 * 调用者：APP老师端
	 */
	@RequestMapping("queryTeaOnlineExamList")
	public JsonResult queryTeaOnlineExamList(String data, String ticket) {
		APILogger.invoking("queryTeaOnlineExamList", data);
		ApiTeaExamQuery query = (data == null || data == "") ? new ApiTeaExamQuery() : JsonUtils.fromJSON(data, ApiTeaExamQuery.class);
		Long teacherId = Long.parseLong(TicketUtils.getUserId(ticket));
		query.setTeacherId(teacherId);
		if (query.getStart() == null) {
			query.setStart(0);
		}
		if (query.getLimit() == null) {
			query.setLimit(10);
		}
		List<Homework> teaList = examService.queryTeaOnlineExamList(query);
		List<Map<String, Object>> list = teaList.stream().map((Homework hw) -> {
			Map<String, Object> map = new HashMap<>();
			map.put("homeworkId", hw.getHomeworkId());
			map.put("homeworkName", hw.getHomeworkName());
			map.put("resType", hw.getResType());
			map.put("resTypeName", ResType.getResTypeName(hw.getResType()));
			map.put("rawType", hw.getRawType());
			map.put("rawTypeName", ResRawType.resolve(hw.getRawType()).name);
			map.put("homeworkType", hw.getHomeworkType());
			map.put("status", hw.getStatus());
			map.put("paperId", hw.getPaperId());
			map.put("paperType", hw.getPaperType());
			map.put("subjective", hw.getSubjective());
			map.put("className", hw.getClassName());
			map.put("startTime", hw.getStartTime());
			map.put("closeTime", hw.getCloseTime());
			map.put("examTime", (hw.getCloseTime().getTime() - hw.getStartTime().getTime()) / 1000 / 60);
			Integer examStatus = 0;//考试状态：0.无效状态、1.未开始、2.已结束、3.正在考试
			if ((new Date()).before(hw.getStartTime())) {
				examStatus = 1;
			} else if (hw.getCloseTime().before(new Date())) {
				examStatus = 2;
			} else if (hw.getStartTime().before(new Date()) && (new Date()).before(hw.getCloseTime())) {
				examStatus = 3;
			}
			map.put("examStatus", examStatus);
			map.put("totalNum", hw.getTotalNum());
			map.put("finishNum", hw.getFinishNum());
			map.put("correctNum", hw.getCorrectNum());
			map.put("bugFixNum", hw.getBugFixNum());
			map.put("totalFixNum", hw.getTotalFixNum());
			map.put("reviewNum", hw.getReviewNum());
			map.put("isOpenAnswer", hw.getIsOpenAnswer());
			map.put("openAnswerTime", hw.getOpenAnswerTime());
			map.put("isSelfCheck", hw.getIsSelfCheck());
			//新增字段
			map.put("avgScore", hw.getAvgScore());
			map.put("avgScoreRate", hw.getAvgScoreRate());
			return map;
		}).collect(Collectors.toList());
		Map<String, Long> totalsMap = examService.queryTeaOnlineExamTotal(teacherId);
		return new JsonResult().addDatas("total", totalsMap.get("total")).addDatas("list", list);
	}


	/**
	 * 学生在线考试列表
	 * 支持分页查询
	 * 调用者：APP学生端
	 */
	@RequestMapping("queryStuOnlineExamList")
	public JsonResult queryStuOnlineExamList(String data, String ticket) {
		APILogger.invoking("queryStuOnlineExamList", data);
		ApiStuExamQuery query = (data == null || data == "") ? new ApiStuExamQuery() : JsonUtils.fromJSON(data, ApiStuExamQuery.class);
		Long studentId = Long.parseLong(TicketUtils.getUserId(ticket));
		query.setStudentId(studentId);
		if (query.getStart() == null) {
			query.setStart(0);
		}
		if (query.getLimit() == null) {
			query.setLimit(10);
		}
		List<ApiStudentHomeworkListDTO> stuList = examService.queryStuOnlineExamList(query);
		List<Map<String, Object>> list = stuList.stream().map(x -> {
			Map<String, Object> map = new HashMap<>();
			map.put("homeworkDtlId", x.getHomeworkDtlId());
			map.put("homeworkId", x.getHomeworkId());
			map.put("homeworkName", x.getHomeworkName());
			map.put("homeworkType", HomeworkType.Exam.value);
			map.put("resType", x.getResType());
			map.put("resTypeName", ResType.getResTypeName(x.getResType()));
			map.put("rawType", x.getRawType());
			map.put("rawTypeName", ResRawType.resolve(x.getRawType()).name);
			map.put("usePhase", x.getUsePhase());
			map.put("status", x.getStatus());
			map.put("paperId", x.getPaperId());
			map.put("paperType", x.getPaperType());
			map.put("subjectId", x.getSubjectId());
			map.put("subjectName", x.getSubjectName());
			map.put("subjective", x.getSubjective());
			map.put("teacherId", x.getTeacherId());
			map.put("teacherName", x.getTeacherName());
			map.put("startTime", x.getStartTime());
			map.put("closeTime", x.getCloseTime());
			map.put("submitStatus", x.getSubmitStatus());
			map.put("submitTime", x.getSubmitTime());
			map.put("className", x.getClassName());
			map.put("correctTime", x.getCorrectTime());
			map.put("score", x.getScore());
			map.put("scoreRate", x.getScoreRate());
			map.put("bugFixStage", x.getBugFixStage());
			map.put("isOpenAnswer", x.getIsOpenAnswer());
			map.put("isSelfCheck", x.getIsSelfCheck());
			map.put("examTime", (x.getCloseTime().getTime() - x.getStartTime().getTime()) / 1000 / 60);
			Integer examStatus = 0;//考试状态：0.无效状态、1.未开始、2.已结束、3.正在考试
			if ((new Date()).before(x.getStartTime())) {
				examStatus = 1;
			} else if (x.getCloseTime().before(new Date())) {
				examStatus = 2;
			} else if (x.getStartTime().before(new Date()) && (new Date()).before(x.getCloseTime())) {
				examStatus = 3;
			}
			map.put("examStatus", examStatus);
			return map;
		}).collect(Collectors.toList());

		Map<String, Long> totalMap = examService.queryStuOnlineExamTotal(studentId);
		return new JsonResult().addDatas("total", totalMap.get("total")).addDatas("list", list);
	}
}
