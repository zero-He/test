/**
 * 
 */
package cn.strong.leke.question.model.question.query;


/**
 * 名校习题我的代录查询
 * 
 * @author liulongbiao
 *
 */
public class AgencyFmsSchQuestionQuery extends BaseAgencyQuestionQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6108877652077193560L;
	private Long famousSchoolId; // 名校ID

	public Long getFamousSchoolId() {
		return famousSchoolId;
	}

	public void setFamousSchoolId(Long famousSchoolId) {
		this.famousSchoolId = famousSchoolId;
	}

}
