package cn.strong.leke.question.remote;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.model.question.Outline;
import cn.strong.leke.question.service.OutlineService;
import cn.strong.leke.remote.service.question.IOutlineRemoteService;

@Service
public class OutlineRemoteService implements IOutlineRemoteService {
	@Resource
	private OutlineService outlineService;

	@Override
	public List<Outline> findOutlines() {
		Outline outline = new Outline();
		outline.setStatus(1);
		return outlineService.queryOutline(outline, null);
	}

}
