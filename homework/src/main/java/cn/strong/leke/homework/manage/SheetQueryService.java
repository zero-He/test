package cn.strong.leke.homework.manage;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.dao.mongo.SheetBookDao;
import cn.strong.leke.homework.dao.mongo.SheetPageDao;
import cn.strong.leke.homework.dao.mongo.SheetTaskDao;
import cn.strong.leke.homework.model.SheetBook;
import cn.strong.leke.homework.model.SheetPage;
import cn.strong.leke.homework.model.SheetTask;
import cn.strong.leke.homework.model.SheetTask.SheetGroup;

@Component
public class SheetQueryService {

	@Resource
	private SheetTaskDao sheetTaskDao;
	@Resource
	private SheetPageDao sheetPageDao;
	@Resource
	private SheetBookDao sheetBookDao;

	/**
	 * 根据答题卡页ID，查询答题卡页详情
	 * @param pageId
	 * @return
	 */
	public SheetPage getSheetPageById(String pageId) {
		return this.sheetPageDao.getSheetPageById(pageId);
	}

	/**
	 * 根据任务ID查询详细信息
	 * @param taskId
	 * @return
	 */
	public SheetTask getSheetTaskById(String taskId) {
		return this.sheetTaskDao.getSheetTaskById(taskId);
	}

	/**
	 * 根据条件查询任务列表.
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<SheetTask> findSheetTaskByQuery(Long userId, Date startDate, Date endDate, Page page) {
		return this.sheetTaskDao.findSheetTaskByQuery(userId, startDate, endDate, page);
	}

	/**
	 * 统计解析正确的作业列表
	 * @param taskId
	 * @return
	 */
	public List<SheetGroup> findSheetGroupsByTaskId(String taskId) {
		return this.sheetBookDao.findSheetGroupsByTaskId(taskId);
	}

	/**
	 * 根据试卷ID，查询试卷详情
	 * @param bookId
	 * @return
	 */
	public SheetBook getSheetBookById(String bookId) {
		return this.sheetBookDao.getSheetBookById(bookId);
	}

	/**
	 * 获取下一个乐号异常
	 * @param taskId
	 * @param id
	 * @return
	 */
	public SheetPage getNextLekeNoSheetPageById(String id) {
		SheetPage sheetPage = this.getSheetPageById(id);
		return this.sheetPageDao.getNextLekeNoSheetPageById(sheetPage.getTaskId(), id);
	}

	/**
	 * 获取下一个重页异常
	 * @param id
	 * @return
	 */
	public SheetBook getNextRepeatSheetBookById(String id) {
		SheetBook sheetBook = this.sheetBookDao.getSheetBookById(id);
		return this.sheetBookDao.getNextRepeatSheetBookById(sheetBook.getTaskId(), id);
	}

	/**
	 * 获取下一个定位点异常或者填涂异常
	 * @param id
	 * @return
	 */
	public SheetBook getNextFillingSheetBookById(String id) {
		SheetBook sheetBook = this.sheetBookDao.getSheetBookById(id);
		return this.sheetBookDao.getNextFillingSheetBookById(sheetBook.getTaskId(), id);
	}

	/**
	 * 获取下一个定位点异常或者疑似异常
	 * @param id
	 * @return
	 */
	public SheetBook getNextSuspectSheetBookById(String id) {
		SheetBook sheetBook = this.sheetBookDao.getSheetBookById(id);
		return this.sheetBookDao.getNextSuspectSheetBookById(sheetBook.getTaskId(), id);
	}

	/**
	 * 更新答题卡卷
	 * @param book
	 */
	public void updateSheetBook(SheetBook book) {
		this.sheetBookDao.updateSheetBook(book);
	}

	/**
	 * 根据任务和作业ID查询作业中试卷列表.
	 * @param taskId
	 * @param homeworkId
	 * @return
	 */
	public List<SheetBook> findSheetPagesByTaskIdAndHomeworkId(String taskId, Long homeworkId) {
		return this.sheetBookDao.findSheetPagesByTaskIdAndHomeworkId(taskId, homeworkId);
	}

	/**
	 * 根据任务ID查询任务的错误试卷
	 * @param taskId
	 * @return
	 */
	public List<SheetBook> findErrorBooksByTaskId(String taskId) {
		return this.sheetBookDao.findErrorBooksByTaskId(taskId);
	}

	/**
	 * 根据页码ID查询页信息
	 * @param ids
	 * @return
	 */
	public List<SheetPage> findSheetPageByIds(List<String> ids) {
		return this.sheetPageDao.findSheetPageByIds(ids);
	}

	/**
	 * 根据任务ID查询任务中错误的页码
	 * @param taskId
	 * @return
	 */
	public List<SheetPage> findErrorPagesByTaskId(String taskId) {
		return this.sheetPageDao.findErrorPagesByTaskId(taskId);
	}
}
