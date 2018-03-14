/**
 * 
 */
package cn.strong.leke.question.core.workbook.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.question.core.workbook.cmd.WorkbookSaveHandler;
import cn.strong.leke.question.core.workbook.cmd.store.IFamousSchoolWorkbookHandler;
import cn.strong.leke.question.core.workbook.cmd.store.IFamousTeacherWorkbookHandler;
import cn.strong.leke.question.core.workbook.cmd.store.ILekeBoutiqueWorkbookHandler;
import cn.strong.leke.question.core.workbook.cmd.store.ILekeShareWorkbookHandler;
import cn.strong.leke.question.core.workbook.cmd.store.IPersonalWorkbookHandler;
import cn.strong.leke.question.core.workbook.cmd.store.ISchoolWorkbookHandler;
import cn.strong.leke.question.core.workbook.cmd.store.IWorkbookAddHandler;
import cn.strong.leke.question.core.workbook.cmd.store.IWorkbookGetHandler;
import cn.strong.leke.question.core.workbook.cmd.store.IWorkbookUserResGroupHandler;

/**
 * @author liulongbiao
 *
 */
@Configuration
public class WorkbookCoreConfig {
	@Resource(name = "repoStatDiffSender")
	private MessageSender repoStatDiffSender;

	@Bean
	public WorkbookSaveHandler workbookSaveHandler(IWorkbookAddHandler workbookAddHandler,
			IWorkbookGetHandler workbookGetHandler,
			IPersonalWorkbookHandler personalWorkbookHandler,
			ISchoolWorkbookHandler schoolWorkbookHandler,
			ILekeShareWorkbookHandler lekeShareWorkbookHandler,
			ILekeBoutiqueWorkbookHandler lekeBoutiqueWorkbookHandler,
			IFamousSchoolWorkbookHandler famousSchoolWorkbookHandler,
			IFamousTeacherWorkbookHandler famousTeacherWorkbookHandler,
			IWorkbookUserResGroupHandler workbookUserResGroupHandler) {
		WorkbookSaveHandler result = new WorkbookSaveHandler();
		result.setRepoRecordAddHandler(workbookAddHandler);
		result.setRepoRecordGetHandler(workbookGetHandler);
		result.setPersonalRepoHandler(personalWorkbookHandler);
		result.setSchoolRepoHandler(schoolWorkbookHandler);
		result.setLekeShareRepoHandler(lekeShareWorkbookHandler);
		result.setLekeBoutiqueRepoHandler(lekeBoutiqueWorkbookHandler);
		result.setFamousSchoolRepoHandler(famousSchoolWorkbookHandler);
		result.setFamousTeacherRepoHandler(famousTeacherWorkbookHandler);
		result.setRepoUserResGroupHandler(workbookUserResGroupHandler);
		result.setRepoStatDiffSender(repoStatDiffSender);
		return result;
	}
}
