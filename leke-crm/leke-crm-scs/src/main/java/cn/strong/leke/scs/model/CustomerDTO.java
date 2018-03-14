package cn.strong.leke.scs.model;

public class CustomerDTO extends Customer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7679022652299769415L;
	
	private String schoolName;
	private Long schoolId;
	
	
	public String getSchoolName()
	{
		return schoolName;
	}
	public void setSchoolName(String schoolName)
	{
		this.schoolName = schoolName;
	}
	public Long getSchoolId()
	{
		return schoolId;
	}
	public void setSchoolId(Long schoolId)
	{
		this.schoolId = schoolId;
	}
	
	public String getCreateOnStr()
	{
		return formatDate(this.getCreatedOn(), "yyyy-MM-dd HH:mm:ss");		
	}

}
