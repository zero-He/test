/**
 * 
 */
package cn.strong.leke.question.core.question.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.strong.leke.framework.mq.MessageSender;
import cn.strong.leke.model.question.QuestionDTO;
import cn.strong.leke.question.core.question.cmd.QuestionSaveHandler;
import cn.strong.leke.question.core.question.cmd.store.IFamousSchoolQuestionHandler;
import cn.strong.leke.question.core.question.cmd.store.IFamousTeacherQuestionHandler;
import cn.strong.leke.question.core.question.cmd.store.ILekeBoutiqueQuestionHandler;
import cn.strong.leke.question.core.question.cmd.store.ILekeShareQuestionHandler;
import cn.strong.leke.question.core.question.cmd.store.IPersonalQuestionHandler;
import cn.strong.leke.question.core.question.cmd.store.IQuestionAddHandler;
import cn.strong.leke.question.core.question.cmd.store.IQuestionFeedbackAwardHandler;
import cn.strong.leke.question.core.question.cmd.store.IQuestionGetHandler;
import cn.strong.leke.question.core.question.cmd.store.IQuestionUserResGroupHandler;
import cn.strong.leke.question.core.question.cmd.store.IQuestionWbnodeHandler;
import cn.strong.leke.question.core.question.cmd.store.ISchoolQuestionHandler;
import cn.strong.leke.repository.common.cmd.BaseSchoolRepoCheckHandler;
import cn.strong.leke.repository.common.cmd.ISchoolRepoCheckHandler;

/**
 * @author liulongbiao
 *
 */
@Configuration
public class QuestionCoreConfig {
	@Resource(name = "repoStatDiffSender")
	private MessageSender repoStatDiffSender;

	@Bean
	public QuestionSaveHandler questionSaveHandler(IQuestionAddHandler questionAddHandler,
			IQuestionGetHandler questionGetHandler,
			IPersonalQuestionHandler personalQuestionHandler,
			ISchoolQuestionHandler schoolQuestionHandler,
			ILekeShareQuestionHandler lekeShareQuestionHandler,
			ILekeBoutiqueQuestionHandler lekeBoutiqueQuestionHandler,
			IFamousSchoolQuestionHandler famousSchoolQuestionHandler,
			IFamousTeacherQuestionHandler famousTeacherQuestionHandler,
			IQuestionUserResGroupHandler questionUserResGroupHandler, IQuestionWbnodeHandler questionWbnodeHandler,
			IQuestionFeedbackAwardHandler questionFeedbackAwardHandler) {
		QuestionSaveHandler result = new QuestionSaveHandler();
		result.setRepoRecordAddHandler(questionAddHandler);
		result.setRepoRecordGetHandler(questionGetHandler);
		result.setPersonalRepoHandler(personalQuestionHandler);
		result.setSchoolRepoHandler(schoolQuestionHandler);
		result.setLekeShareRepoHandler(lekeShareQuestionHandler);
		result.setLekeBoutiqueRepoHandler(lekeBoutiqueQuestionHandler);
		result.setFamousSchoolRepoHandler(famousSchoolQuestionHandler);
		result.setFamousTeacherRepoHandler(famousTeacherQuestionHandler);
		result.setRepoUserResGroupHandler(questionUserResGroupHandler);
		result.setQuestionWbnodeHandler(questionWbnodeHandler);
		result.setQuestionFeedbackAwardHandler(questionFeedbackAwardHandler);
		result.setRepoStatDiffSender(repoStatDiffSender);
		return result;
	}

	@Bean
	public ISchoolRepoCheckHandler schoolQuestionCheckHandler(
			ISchoolQuestionHandler schoolQuestionHandler, IQuestionGetHandler questionGetHandler) {
		BaseSchoolRepoCheckHandler<QuestionDTO> result = new BaseSchoolRepoCheckHandler<>();
		result.setSchoolRepoHandler(schoolQuestionHandler);
		result.setRepoRecordGetHandler(questionGetHandler);
		result.setRepoStatDiffSender(repoStatDiffSender);
		return result;
	}
}
