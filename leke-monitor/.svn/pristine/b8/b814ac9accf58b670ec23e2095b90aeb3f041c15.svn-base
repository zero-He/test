/**
 * 
 */
package cn.strong.leke.monitor.core.service.stat.impl;

import static cn.strong.leke.model.base.SchoolCst.NATURE_BASIC;
import static cn.strong.leke.model.base.SchoolCst.NATURE_CRAM;
import static cn.strong.leke.model.base.SchoolCst.NATURE_UNIT;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.LocalDate;

import cn.strong.leke.monitor.core.service.stat.ISchoolStatService;
import cn.strong.leke.monitor.db.tutor.mapper.ISchoolMapper;
import cn.strong.leke.monitor.db.tutor.model.NatureSchoolCount;
import cn.strong.leke.monitor.mongo.stat.dao.ISchoolStatDailyDao;
import cn.strong.leke.monitor.mongo.stat.model.SchoolStat;
import cn.strong.leke.monitor.mongo.stat.model.SchoolStatDaily;
import cn.strong.leke.monitor.util.StatUtils;

/**
 * @author liulongbiao
 *
 */
@Resource
public class SchoolStatService implements ISchoolStatService {

	@Resource
	private ISchoolMapper schoolMapper;
	@Resource
	private ISchoolStatDailyDao schoolStatDailyDao;

	@Override
	public void updateDaily() {
		Date yestoday = LocalDate.now().minusDays(1).toDate();
		Date today = LocalDate.now().toDate();

		SchoolStat sum = getSum();
		SchoolStat added = getAdded(yestoday, today);

		SchoolStatDaily stat = new SchoolStatDaily();
		stat.setDay(StatUtils.ofDay(yestoday));
		stat.setSum(sum);
		stat.setAdded(added);
		schoolStatDailyDao.save(stat);
	}

	private SchoolStat getSum() {
		int total = schoolMapper.count();
		List<NatureSchoolCount> ncs = schoolMapper.countByNature();
		return toSchoolStat(total, ncs);
	}

	private SchoolStat getAdded(Date yestoday, Date today) {
		int total = schoolMapper.countAdded(yestoday, today);
		List<NatureSchoolCount> ncs = schoolMapper.countAddedByNature(yestoday, today);
		return toSchoolStat(total, ncs);
	}

	private static SchoolStat toSchoolStat(int total, List<NatureSchoolCount> ncs) {
		SchoolStat result = new SchoolStat();
		result.setTotal(total);
		if (ncs != null && !ncs.isEmpty()) {
			for (NatureSchoolCount c : ncs) {
				Integer nature = c.getSchoolNature();
				Integer cnt = c.getCnt();
				if (nature != null) {
					switch (nature.intValue()) {
					case NATURE_BASIC:
						result.setBasic(cnt);
						break;
					case NATURE_CRAM:
						result.setSocial(cnt);
						break;
					case NATURE_UNIT:
						result.setUnit(cnt);
						break;
					default:
						break;
					}
				}
			}
		}
		return result;
	}

}
