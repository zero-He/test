/**
 * 
 */
package cn.strong.leke.question.model.question.query;

/**
 * 名师习题我的代录查询
 * 
 * @author liulongbiao
 *
 */
public class AgencyFmsTchQuestionQuery extends BaseAgencyQuestionQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1375660323647705253L;
	private Long famousTeacherId; // 名师ID

	public Long getFamousTeacherId() {
		return famousTeacherId;
	}

	public void setFamousTeacherId(Long famousTeacherId) {
		this.famousTeacherId = famousTeacherId;
	}

}
