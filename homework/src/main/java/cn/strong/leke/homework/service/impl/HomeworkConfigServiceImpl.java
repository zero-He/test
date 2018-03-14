package cn.strong.leke.homework.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.homework.dao.mybatis.HomeworkConfigDao;
import cn.strong.leke.homework.model.HomeworkConfig;
import cn.strong.leke.homework.service.HomeworkConfigService;

@Service
public class HomeworkConfigServiceImpl implements HomeworkConfigService {

	

	@Resource
	private HomeworkConfigDao homeworkConfigDao;
	
	@Override
	public List<HomeworkConfig> GetHomeworkConfigList(
			HomeworkConfig homeworkConfig,Page page) {
		
		return homeworkConfigDao.GetHomeworkConfigList(homeworkConfig,page);
		
	}

	@Override
	public void AddHomeworkConfig(HomeworkConfig homeworkConfig) {
					
		homeworkConfigDao.addHomeworkConfig(homeworkConfig);
	}

	@Override
	public Boolean HasConfig() {

	    Page page=new Page();
	    page.setCurPage(1);
	    page.setPageSize(1);
		List<HomeworkConfig> lst = GetHomeworkConfigList(null,page);
		Boolean  flag=(lst!=null&&lst.size()>0)?true:false;
	    return	flag;
	}
	
}
