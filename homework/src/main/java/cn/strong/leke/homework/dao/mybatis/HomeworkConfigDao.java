package cn.strong.leke.homework.dao.mybatis;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.model.HomeworkConfig;

public interface HomeworkConfigDao {
	
	
	/**
	 * 获取作业配置。<br>
	 * 
	 *
	 */
   public List<HomeworkConfig> GetHomeworkConfigList(HomeworkConfig homeworkConfig,Page page);
   
   
   /**
	 * 添加作业配置<br>
	 * 
	 * 
	 */
   public void addHomeworkConfig(HomeworkConfig homeworkConfig);

}
