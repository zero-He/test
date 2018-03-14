/**
 * 
 */
package cn.strong.leke.question.analysis.core.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.strong.leke.common.utils.CollectionUtils;
import cn.strong.leke.common.utils.ListUtils;
import cn.strong.leke.model.question.Knowledge;
import cn.strong.leke.model.question.MaterialNode;
import cn.strong.leke.question.analysis.core.service.IMatNodeKlgService;
import cn.strong.leke.question.analysis.core.service.MatNodeKlgCountTask;
import cn.strong.leke.question.analysis.mongo.dao.IMatNodeKlgCounterDao;
import cn.strong.leke.question.analysis.mongo.dao.IMatNodeKlgsDao;
import cn.strong.leke.question.analysis.mongo.model.MatNodeKlgCounter;
import cn.strong.leke.question.analysis.mongo.model.MatNodeKlgs;
import cn.strong.leke.question.analysis.mongo.model.MatNodeKlgs.Klg;
import cn.strong.leke.question.dao.mybatis.KnowledgeDao;
import cn.strong.leke.question.dao.mybatis.MaterialNodeDao;

/**
 * @author liulongbiao
 *
 */
@Service
public class MatNodeKlgService implements IMatNodeKlgService {

	@Resource
	private IMatNodeKlgsDao matNodeKlgsDao;
	@Resource
	private MaterialNodeDao materialNodeDao;
	@Resource
	private KnowledgeDao knowledgeDao;

	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private IMatNodeKlgCounterDao matNodeKlgCountDao;

	@Override
	public MatNodeKlgs getMatNodeKlgs(Long materialNodeId) {
		if (materialNodeId == null) {
			return null;
		}
		MatNodeKlgs result = matNodeKlgsDao.getById(materialNodeId);
		if (result == null) {
			result = getMatNodeKlgsFromCounter(materialNodeId);
			matNodeKlgsDao.save(result);
		}
		return result;
	}

	private MatNodeKlgs getMatNodeKlgsFromCounter(Long materialNodeId) {
		MatNodeKlgs result = new MatNodeKlgs();
		result.setMaterialNodeId(materialNodeId);
		MaterialNode matnode = materialNodeDao.getMaterialNodeById(materialNodeId);
		if (matnode == null) {
			result.setKnowledges(Collections.emptyList());
			return result;
		}

		result.setMaterialNodeName(matnode.getMaterialNodeName());
		List<MatNodeKlgCounter> counters = matNodeKlgCountDao.findAssocKlgIds(materialNodeId);
		result.setKnowledges(toKnowledges(counters));
		return result;
	}

	private List<Klg> toKnowledges(List<MatNodeKlgCounter> counters) {
		if (CollectionUtils.isEmpty(counters)) {
			return Collections.emptyList();
		}
		List<Long> knowledgeIds = ListUtils.map(counters, MatNodeKlgCounter::getKid);
		List<Knowledge> knowledges = knowledgeDao.findByIds(knowledgeIds);
		Map<Long, Knowledge> map = ListUtils.toMap(knowledges, Knowledge::getKnowledgeId);
		return ListUtils.map(counters, counter -> {
			return toKlg(counter, map);
		});
	}

	private Klg toKlg(MatNodeKlgCounter counter, Map<Long, Knowledge> map) {
		Knowledge klg = map.get(counter.getKid());
		if (klg == null) {
			return null;
		}
		Klg result = new Klg();
		result.setKnowledgeId(klg.getKnowledgeId());
		result.setKnowledgeName(klg.getKnowledgeName());
		result.setSchoolStageId(klg.getSchoolStageId());
		result.setSubjectId(klg.getSubjectId());
		result.setParentId(klg.getParentId());
		result.setWeight(counter.getCount());
		return result;
	}

	@Override
	public void startCount() {
		new MatNodeKlgCountTask(jdbcTemplate, matNodeKlgCountDao).start();
	}

}
