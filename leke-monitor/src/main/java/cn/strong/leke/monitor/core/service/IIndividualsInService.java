package cn.strong.leke.monitor.core.service;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.model.IndiClassStatisticsDTO;
import cn.strong.leke.monitor.core.model.IndividualsInDto;

/**
 * 
 * @author hedan
 *
 */
public interface IIndividualsInService {
	
	IndividualsInDto getIndividualsInDetail(IndividualsInDto query);
	
	List<IndividualsInDto> getCustomerDetail(IndividualsInDto query,Page page);
	
	List<IndividualsInDto> getTeacherDetail(IndividualsInDto query,Page page);
	
	List<IndividualsInDto> getSellerInDetail(IndividualsInDto query,Page page);

	IndiClassStatisticsDTO getClassStatisticsList(IndividualsInDto query);
}
