/**
 * 
 */
package cn.strong.leke.question.remote;

import static cn.strong.leke.core.business.remote.PageRemotes.queryInPage;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepoDayGroup;
import cn.strong.leke.model.repository.RepositoryRecord;
import cn.strong.leke.model.user.User;
import cn.strong.leke.question.core.workbook.query.ILekeWorkbookQueryService;
import cn.strong.leke.question.service.IWorkbookService;
import cn.strong.leke.remote.model.common.PageRemote;
import cn.strong.leke.remote.service.question.IWorkbookRemoteService;

/**
 * @author liulongbiao
 *
 */
@Service
public class WorkbookRemoteService implements IWorkbookRemoteService {

	@Autowired
	private IWorkbookService workbookService;
	@Autowired
	private ILekeWorkbookQueryService lekeWorkbookQueryService;

	@Override
	public Workbook getWorkbookById(Long workbookId) {
		return workbookService.getWorkbook(workbookId);
	}

	@Override
	public List<Workbook> findWorkbookByIds(Long[] ids) {
		return workbookService.findWorkbookByIds(ids);
	}

	@Override
	public PageRemote<RepositoryRecord> findLekeWorkbooks(final RepositoryWorkbookQuery query,
			PageRemote<RepositoryRecord> pageRemote) {
		return queryInPage(pageRemote, page -> {
			return workbookService.queryLekeBoutiqueWorkbooks(query, page);
		});
	}
	
	@Override
	public void updatePhoto(Long workbookId, User user, String photoUrl) {
		workbookService.workbookPhotoUpload(workbookId, user, photoUrl);
	}

	@Override
	public int countLekeWorkbook(RepositoryWorkbookQuery query) {
		if (query.getEndDataTime() != null) {
			query.setEndDataTime(ofEndDateTime(query.getEndDataTime()));
		}
		return lekeWorkbookQueryService.countLekeWorkbook(query);
	}

	@Override
	public List<RepoDayGroup> groupCreatedOnLekeWorkbook(RepositoryWorkbookQuery query) {
		if (query.getEndDataTime() != null) {
			query.setEndDataTime(ofEndDateTime(query.getEndDataTime()));
		}
		return lekeWorkbookQueryService.groupCreatedOnLekeWorkbook(query);
	}

	private Date ofEndDateTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
}
