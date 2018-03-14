/**
 * 
 */
package cn.strong.leke.question.model.question.query;


/**
 * 代录学校习题查询
 * 
 * @author liulongbiao
 *
 */
public class AgencySchoolQuestionQuery extends BaseAgencyQuestionQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3953286223304139103L;
	private Long schoolId; // 学校ID

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

}
