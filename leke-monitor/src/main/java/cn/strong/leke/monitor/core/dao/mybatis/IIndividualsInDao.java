package cn.strong.leke.monitor.core.dao.mybatis;

import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.model.IndiClassStatisticsDTO;
import cn.strong.leke.monitor.core.model.IndividualsInDto;

/**
 * @Description: 个人入驻数据层接口
 * @author hedan
 *
 */
public interface IIndividualsInDao {
	
	//查询累计和新增入驻老师数  
	int getCustomerCount(IndividualsInDto query);
	
	int getNewCustomerCount(IndividualsInDto query);
	
	//查询月累计充值金额和提成金额
	IndividualsInDto getCommission(IndividualsInDto query);
	
	//查询客户经理下每个入驻老师详情
	List<IndividualsInDto> getCustomerDetail(IndividualsInDto query,Page page);
	
	//查询营销处经理下每个客户经理入驻详情
	List<IndividualsInDto> getCustomerGroupBySeller(IndividualsInDto query,Page page);
	
	//查询营销处经理下每个客户经理入驻详情
	List<IndividualsInDto> getCustomerGroupByMarketId(IndividualsInDto query,Page page);
	
	List<Long> getSeller( Long marketId);

	
	String getDepartName(Long marketId);
	
	int getNewInTeacherNum(IndividualsInDto query);
	
	//查询已结束课堂数
	Long getFinishedClassNums(IndividualsInDto query);
	//查询上课课堂数
	Long getAttendedClassNums(IndividualsInDto query);
	//查询应上课人次、实上课人次
	IndiClassStatisticsDTO getMustClassTimesAndActualClassTimes(IndividualsInDto query);
	//查询应上课学生数
	Long getMustClassNums(IndividualsInDto query);
	//查询实上课学生数
	Long getActualClassNums(IndividualsInDto query);
	
}


















