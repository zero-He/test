package cn.strong.leke.diag.remote;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.strong.leke.diag.service.KnoGraspService;
import cn.strong.leke.remote.model.diag.KlgGrasp;
import cn.strong.leke.remote.service.diag.IDiagRemoteService;

@Component
public class DiagRemoteService implements IDiagRemoteService {

	@Resource
	private KnoGraspService knoGraspService;

	@Override
	public List<KlgGrasp> findStudentWeekUnGrasp(Long userId) {
		return this.knoGraspService.findStudentWeekUnGrasp(userId);
	}

}
