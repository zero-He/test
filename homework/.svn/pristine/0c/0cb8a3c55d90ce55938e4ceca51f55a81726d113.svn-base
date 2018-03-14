package cn.strong.leke.homework.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.framework.exceptions.ValidateException;
import cn.strong.leke.framework.spring.MessageSource;
import cn.strong.leke.framework.spring.SpringContextHolder;
import cn.strong.leke.homework.model.HomeworkDtl;
import cn.strong.leke.homework.model.HomeworkDtlInfo;
import cn.strong.leke.homework.model.HomeworkPaper;
import cn.strong.leke.homework.model.HomeworkType;
import cn.strong.leke.homework.model.ResType;
import cn.strong.leke.homework.service.HomeworkDtlService;
import cn.strong.leke.model.paper.Paper;
import cn.strong.leke.model.paper.PaperDTO;

/**
 * @author Zhang Fujun
 * @date 2016年5月18日
 */
public class HomeworkUtils {

	/**
	 * 获取新学期开始日期 每年的 7.1号
	 * 如果是当前日期大于该日期，返回当年的7.1号，否则返回去年的7.1号
	 * @return
	 */
	public static Date getSchoolYearStartDate() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		if (month < 6) {
			calendar.set(year - 1, 6, 1);
		} else {
			calendar.set(year, 6, 1);
		}
		return DateUtils.truncate(calendar, Calendar.DATE).getTime();
	}

	/**
	 * 推算课时作业的开始时间。
	 * 计算方法：清空时分秒后向前三天
	 * @param lessonTime 开课时间
	 * @return
	 */
	public static Date reckonHomeworkStartTime(Date lessonTime) {
		return DateUtils.addDays(DateUtils.truncate(lessonTime, Calendar.DAY_OF_MONTH), -3);
	}

	/**
	 * 推算课时作业的截止时间。
	 * 计算方法：清空时分秒后向后四天
	 * 注意：因为排课可跨天，推算截止时间也用开课时间
	 * @param lessonTime 开课时间
	 * @return
	 */
	public static Date reckonHomeworkCloseTime(Date lessonTime) {
		return DateUtils.addDays(DateUtils.truncate(lessonTime, Calendar.DAY_OF_MONTH), 4);
	}

    /**
     * 格式化作业类型显示
     * @param homeworkType
     * @return
     */
	public static String fmtHomeworkTypeStr(Integer homeworkType) {
        if (homeworkType == null) {
            return "";
        } else if (homeworkType.equals(HomeworkType.HOLIDAY.value)) {
            return MessageSource.getMessage("homework.homework.homeworktype.holiday");
        } else if (homeworkType.equals(HomeworkType.CLASS_HOUR.value)) {
            return MessageSource.getMessage("homework.homework.homeworktype.classhour");
        } else if (homeworkType.equals(HomeworkType.VOD.value)) {
            return MessageSource.getMessage("homework.homework.homeworktype.vod");
        } else if (homeworkType.equals(HomeworkType.AUTO.value)) {
            return MessageSource.getMessage("homework.homework.homeworktype.auto");
        } else if (homeworkType.equals(HomeworkType.Exam.value)) {
	        return MessageSource.getMessage("homework.homework.homeworktype.exam");
        }
        return "";
    }
    
    public static String fmtPaperTypeStr(Integer paperType){
        return "";
    }

    public static String fmtResTypeStr(Integer resType) {
    	return ResType.getResTypeName(resType);
    }

    /**
     * 将 秒数 转化为 分钟数
     * 不到1分钟，显示1分钟
     * 大于1分钟的，取整数部分
     * @param second
     * @return
     */
    public static Integer convertUsedTime(Integer second) {
        if (second != null && second.intValue() > 0) {
            double times = new BigDecimal(second).divide(new BigDecimal(60), BigDecimal.ROUND_FLOOR).doubleValue();
            if (times <= 1) {
                return 1;
            } else {
                Math.floor(times);
                return (int) times;
            }
        }
        return 1;
    }

    public static PaperDTO buildPaperDTO(String paperId) {
        return buildPaperDTO(paperId, null, null, null);
    }

    public static PaperDTO buildPaperDTO(String paperId, String title, String subjectName, Long subjectId) {
        if (StringUtils.isEmpty(paperId)) {
            return null;
        }
        HomeworkDtlService homeworkDtlService = SpringContextHolder.getBean(HomeworkDtlService.class);
        HomeworkPaper hwPaper = homeworkDtlService.getHomeworkPaper(paperId);
        PaperDTO paperDto = new PaperDTO();
        paperDto.setIsDraft(false);
        paperDto.setPaperType(Paper.PAPER_TYPE_NORMAL);
        paperDto.setDetail(hwPaper.getPaperDetail());
        paperDto.setTitle(title);
        paperDto.setSubjectId(subjectId);
        paperDto.setSubjectName(subjectName);
        paperDto.setSubjective(false);
        paperDto.setSchoolStageId(1L);
        return paperDto;
    }

    /**
     * 计算距离当前时间差
     * {}天{}小时{}分钟
     * @param timeMillis
     * @return
     */
    public static String fmtDiffTime(Long timeMillis) {
        long timeGap = Math.abs((System.currentTimeMillis() - timeMillis) / 1000);//与现在时间相差秒数
        String timeStr = "";
        if (timeGap >= 86400L) {//天
            timeStr = timeGap / 86400L + "天";
            timeGap = timeGap % 86400L;
        }
        if (timeGap >= 3600L) { //小时
            timeStr += timeGap / 3600L + "小时";
            timeGap = timeGap % 3600L;
        }
        if (timeGap > 0) {
            Long minute = BigDecimal.valueOf((timeGap)).divide(BigDecimal.valueOf(60), 2, RoundingMode.CEILING).longValue();
            if (minute > 0) {
                timeStr += minute + "分钟";
            }
        }
        if (timeStr == "") {
            timeStr = "0分钟";
        }
        return timeStr;
    }
    

	/**
	 * 验证批改作业权限
	 * @param request
	 * @param homeworkDtlInfo
	 */
	public static void validateCorrectAccess(Long operator, HomeworkDtlInfo homeworkDtlInfo) {
		if(homeworkDtlInfo.getIsSelfCheck()){
			throw new ValidateException("作业已被老师设置不作批改，无法进行批改~");
		}
		else if (homeworkDtlInfo.getSubmitTime() == null) {
			throw new ValidateException("作业未提交，无法批改~");
		} else if (!homeworkDtlInfo.getTeacherId().equals(operator)) {
			if (!operator.equals(homeworkDtlInfo.getCorrectUserId())) {
				throw new ValidateException("您已无权批改该份作业~");
			} else if (homeworkDtlInfo.getCorrectTime() != null) {
				throw new ValidateException("该份作业已被批改，不能重复批改~");
			} else {
				HomeworkDtlService homeworkDtlService = SpringContextHolder.getBean(HomeworkDtlService.class);
				HomeworkDtl selfHw = homeworkDtlService.getHomeworkDtlByHomeworkIdAndStudentId(
						homeworkDtlInfo.getHomeworkId(), operator);
				if (selfHw != null && selfHw.getSubmitTime() == null) {
					throw new ValidateException("请先完成自己的作业后再进行批改~");
				}
			}

		}
	}
}
