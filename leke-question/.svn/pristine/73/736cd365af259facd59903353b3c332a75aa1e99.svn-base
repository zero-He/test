/**
 * 
 */
package cn.strong.leke.question.analysis.core.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.strong.leke.question.analysis.mongo.dao.IMatNodeKlgCounterDao;

/**
 * 章节和知识点关联计数任务
 * 
 * @author liulongbiao
 *
 */
public class MatNodeKlgCountTask {
	private static final Logger LOG = LoggerFactory.getLogger(MatNodeKlgCountTask.class);

	private static final int BATCH_SIZE = 5000;
	private static final String SQL_MAX_QID = "SELECT MAX(questionId) FROM que_question";
	private static final String SQL_ASSOC = "SELECT s.materialNodeId, k.knowledgeId "
			+ "FROM que_question_section s JOIN que_question q ON s.questionId = q.questionId JOIN que_question_knowledge k ON q.questionId = k.questionId "
			+ "WHERE s.isDeleted = 0 AND q.isDeleted = 0 AND q.isDisabled = 0 AND k.isDeleted = 0 "
			+ "AND s.questionId > ? AND s.questionId <= ?";

	final JdbcTemplate jdbcTemplate;
	final IMatNodeKlgCounterDao matNodeKlgCountDao;

	public MatNodeKlgCountTask(JdbcTemplate jdbcTemplate, IMatNodeKlgCounterDao matNodeKlgCountDao) {
		this.jdbcTemplate = jdbcTemplate;
		this.matNodeKlgCountDao = matNodeKlgCountDao;
	}

	public void start() {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		try {
			long maxQid = jdbcTemplate.queryForObject(SQL_MAX_QID, Long.class);
			int batches = (int) (maxQid / BATCH_SIZE) + 1;
			CountDownLatch latch = new CountDownLatch(batches);

			matNodeKlgCountDao.clear();

			for (int i = 0; i < batches; i++) {
				long min = i * BATCH_SIZE;
				long max = min + BATCH_SIZE;
				executor.submit(() -> {
					LOG.info("开始习题ID {} - {} 子任务...", min, max);
					try {
						process(min, max);
					} catch (Exception e) {
						LOG.error("处理习题ID {} - {} 子任务失败", min, max, e);
					} finally {
						latch.countDown();
					}
				});
			}
			latch.await();
			LOG.info("执行章节知识点关联计数任务完毕");
		} catch (Exception e) {
			LOG.error("执行章节知识点关联计数任务失败", e);
		} finally {
			executor.shutdown();
		}
	}

	private void process(long min, long max) {
		jdbcTemplate.query(SQL_ASSOC, new Object[] { min, max }, rs -> {
			Long materialNodeId = rs.getLong("materialNodeId");
			Long knowledgeId = rs.getLong("knowledgeId");
			if (materialNodeId != null && knowledgeId != null) {
				matNodeKlgCountDao.add(materialNodeId, knowledgeId);
			}
		});
	}

}
