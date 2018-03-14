/**
 * 
 */
package cn.strong.leke.question.core.workbook.cmd.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.Workbook;
import cn.strong.leke.question.core.workbook.cmd.store.IWorkbookGetHandler;
import cn.strong.leke.question.core.workbook.query.IWorkbookQueryService;

/**
 * @author liulongbiao
 *
 */
@Service
public class WorkbookGetHandler implements IWorkbookGetHandler {
	@Resource
	private IWorkbookQueryService workbookQueryService;

	@Override
	public Workbook getById(Long workbookId) {
		return workbookQueryService.getById(workbookId);
	}

}
