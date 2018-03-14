package cn.strong.leke.scs.remote;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.remote.service.crm.ISpecialDateRemoteService;
import cn.strong.leke.scs.service.SpecialDateService;

@Service
public class SpecialDateRemoteService implements ISpecialDateRemoteService {

	@Resource
	private SpecialDateService specialDateService;
	
	@Override
	public List<String> queryList(Integer type){
		return specialDateService.queryList(type);
	}
	
}
