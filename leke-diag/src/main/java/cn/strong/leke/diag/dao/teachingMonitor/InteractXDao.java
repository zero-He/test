package cn.strong.leke.diag.dao.teachingMonitor;

import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.interact.InteractRankBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-19 16:01:56
 */
public interface InteractXDao {

	/**
	 * 根据schoolId、gradeId查courseSingleIds
	 * @param vo
	 * @return
	 */
	List<Long> selectSingleIdsBySchoolIdGradeId(RequestVo vo);

}
