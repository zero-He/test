package cn.strong.leke.scs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.scs.dao.master.SpecialDateDao;
import cn.strong.leke.scs.model.SpecialDate;
import cn.strong.leke.scs.service.SpecialDateService;

@Service
public class SpecialDateServiceImpl implements SpecialDateService{
	
	@Resource
	private SpecialDateDao specialDateDao;
	
	@Override
	public boolean insert(List<SpecialDate> specialDateList) {
		return specialDateDao.insert(specialDateList) > 0;
	}

	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return specialDateDao.delete() > 0;
	}


	@Override
	public List<String> queryList(Integer type) {
		return specialDateDao.queryList(type);
	}


	
}
