/**
 * 
 */
package cn.strong.leke.question.controller.devops;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.strong.leke.framework.web.JsonResult;
import cn.strong.leke.question.service.KnowledgeService;
import cn.strong.leke.question.service.MaterialService;

/**
 * 运维人员重建树索引API
 * 
 * @author liulongbiao
 *
 */
@Controller
@RequestMapping("/auth/devops")
public class RebuildTreeIndexController {
	private static final Logger LOG = LoggerFactory.getLogger(RebuildTreeIndexController.class);
	@Resource
	private KnowledgeService knowledgeService;
	@Resource
	private MaterialService materialService;
	@Resource
	private JdbcTemplate jdbcTemplate;

	@RequestMapping("knowledge/rebuildTreeIndex")
	@ResponseBody
	public JsonResult rebuildKlgTreeIndex(Long schoolStageId, Long subjectId) {
		knowledgeService.rebuildTreeIndexWithTx(schoolStageId, subjectId);
		return new JsonResult();
	}

	@RequestMapping("knowledge/rebuildTreeIndexes")
	@ResponseBody
	public JsonResult rebuildKlgTreeIndexes() {
		jdbcTemplate.query("SELECT schoolStageId, subjectId FROM que_knowledge GROUP BY schoolStageId, subjectId",
				rs -> {
					Long schoolStageId = rs.getLong("schoolStageId");
					Long subjectId = rs.getLong("subjectId");
					if (schoolStageId != null && subjectId != null) {
						CompletableFuture.runAsync(() -> {
							try {
								knowledgeService.rebuildTreeIndexWithTx(schoolStageId, subjectId);
							} catch (Exception e) {
								LOG.error("rebuild index for klgree {}/{} error", schoolStageId, subjectId, e);
							}
						});
					}
				});
		return new JsonResult();
	}

	@RequestMapping("material/rebuildTreeIndex")
	@ResponseBody
	public JsonResult rebuildMatTreeIndex(Long materialId) {
		materialService.rebuildTreeIndexWithTx(materialId);
		return new JsonResult();
	}

	@RequestMapping("material/rebuildTreeIndexes")
	@ResponseBody
	public JsonResult rebuildMatTreeIndexes() {
		jdbcTemplate.query("SELECT materialId FROM que_material WHERE isDeleted = 0", rs -> {
			Long materialId = rs.getLong("materialId");
			if (materialId != null) {
				CompletableFuture.runAsync(() -> {
					try {
						materialService.rebuildTreeIndexWithTx(materialId);
					} catch (Exception e) {
						LOG.error("rebuild index for matree {} error", materialId, e);
					}
				});
			}
		});
		return new JsonResult();
	}
}
