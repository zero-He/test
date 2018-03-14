package cn.strong.leke.monitor.core.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.core.dao.mybatis.IIndividualsInDao;
import cn.strong.leke.monitor.core.model.IndiClassStatisticsDTO;
import cn.strong.leke.monitor.core.model.IndividualsInDto;
import cn.strong.leke.monitor.core.service.IIndividualsInService;


@Service
public class IndividualsInServiceImpl implements IIndividualsInService{
	@Resource
	private IIndividualsInDao iIndividualsInDao;
	
	@Override
	public IndividualsInDto getIndividualsInDetail(IndividualsInDto query) {
		IndividualsInDto individualsInDto = new IndividualsInDto();
		
		int customerCount = iIndividualsInDao.getCustomerCount(query);
		
		int newCustomerCount = iIndividualsInDao.getNewCustomerCount(query);
		
		int newInTeacherNum = iIndividualsInDao.getNewInTeacherNum(query);
		
		IndividualsInDto commission = iIndividualsInDao.getCommission(query);
		
		if (commission != null) {
			individualsInDto = commission;
		}
		
		individualsInDto.setTeacherNum(customerCount);
		
		individualsInDto.setNewTeacherNum(newCustomerCount);
		
		individualsInDto.setNewInTeacherNum(newInTeacherNum);
		
		individualsInDto.setRoleId(query.getRoleId());
		
		return individualsInDto;
	}

	@Override
	public List<IndividualsInDto> getCustomerDetail(IndividualsInDto query,Page page) {
		List<IndividualsInDto> result = new ArrayList<>();
		
		Long roleId = query.getRoleId();
		if (roleId.equals(112L)) {
			
			result = iIndividualsInDao.getCustomerDetail(query,page);
			
		}else if (roleId.equals(603L)) {
			
			result = getSellerInDetail(query,page);
			
		}else {
			
			List<IndividualsInDto> list = iIndividualsInDao.getCustomerGroupByMarketId(query, page);
			
			result = setIndividualksInDto(query, list);
			
		}
		
		return result;
	}
	
	
	public List<IndividualsInDto> setIndividualksInDto(IndividualsInDto query,List<IndividualsInDto> result) {
		for (IndividualsInDto individualsInDto : result) {
			Long marketId = individualsInDto.getMarketId();
			
			List<Long> listSeller = iIndividualsInDao.getSeller(marketId);
			
			if (listSeller.size() > 0) {
				query.setListSeller(listSeller);
			}
			
			int newCustomerCount = iIndividualsInDao.getNewCustomerCount(query);
			
			individualsInDto.setNewTeacherNum(newCustomerCount);
			
			if (query.getRoleId().equals(604L)) {
				
				String departName = iIndividualsInDao.getDepartName(marketId);
				
				individualsInDto.setDepartName(departName);
			}
		}
		
		return result;
	}

	@Override
	public List<IndividualsInDto> getTeacherDetail(IndividualsInDto query, Page page) {
		return iIndividualsInDao.getCustomerDetail(query, page);
	}

	@Override
	public List<IndividualsInDto> getSellerInDetail(IndividualsInDto query, Page page) {
		List<IndividualsInDto> list = iIndividualsInDao.getCustomerGroupBySeller(query, page);
		
		for (IndividualsInDto individualsInDto : list) {
			query.setSellerId(individualsInDto.getSellerId());
			
			int newCustomerCount = iIndividualsInDao.getNewCustomerCount(query);
			
			individualsInDto.setNewTeacherNum(newCustomerCount);
		}
		return  list;
	}

	//课时统计
	@Override
	public IndiClassStatisticsDTO getClassStatisticsList(IndividualsInDto query) {
		
		Long finishedClassNum = iIndividualsInDao.getFinishedClassNums(query);
		Long attendedClassNum = iIndividualsInDao.getAttendedClassNums(query);
		IndiClassStatisticsDTO  resultDTO= iIndividualsInDao.getMustClassTimesAndActualClassTimes(query);
		Long mustClassNum = iIndividualsInDao.getMustClassNums(query);
		Long actualClassNum = iIndividualsInDao.getActualClassNums(query);
		//上课率
		String classRate = percent(attendedClassNum, finishedClassNum);
		//到课率
		Long mustClassTimes = resultDTO.getMustClassTimes();
		Long actualClassTimes = resultDTO.getActualClassTimes();
		String attendanceRate = percent(actualClassTimes, mustClassTimes);
				
		resultDTO.setFinishedClassNum(finishedClassNum);
		resultDTO.setAttendedClassNum(attendedClassNum);
		resultDTO.setMustClassNum(mustClassNum);
		resultDTO.setActualClassNum(actualClassNum);
		resultDTO.setClassRate(classRate);
		resultDTO.setAttendanceRate(attendanceRate);
		return resultDTO;
	}
	
	/**
	 * 求a.b的百分比
	 * @param a
	 * @param b
	 * @return
	 */
	public static String percent(Long a, Long b) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
		String probability = "";
		if (b != 0) {
			// 创建一个数值格式化对象
			NumberFormat numberFormat = NumberFormat.getInstance();
			// 设置精确到小数点后2位
			numberFormat.setMaximumFractionDigits(2);
			probability = numberFormat.format((float) a / (float) b * 100) + "%";
		}else {
			probability = "0%";
		}
		return probability;
	}
	
}
