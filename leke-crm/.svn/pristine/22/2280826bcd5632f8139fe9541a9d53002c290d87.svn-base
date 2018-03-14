package cn.strong.leke.scs.remote;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.strong.leke.context.user.UserContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.base.School;
import cn.strong.leke.remote.model.common.PageRemote;
import cn.strong.leke.remote.model.crm.SellerSchoolRemote;
import cn.strong.leke.remote.service.crm.ISellerRemoteService;
import cn.strong.leke.scs.constant.CustomerCst;
import cn.strong.leke.scs.model.Customer;
import cn.strong.leke.scs.model.CustomerVO;
import cn.strong.leke.scs.model.query.CustomerQuery;
import cn.strong.leke.scs.service.CustomerService;
import cn.strong.leke.scs.service.SchoolService;

@Service
public class SellerRemoteService implements ISellerRemoteService {

	@Resource
	private SchoolService schoolService;
	
	@Resource
	private CustomerService customerService;

	@Override
	public SellerSchoolRemote getAssociatedSchools(Long sellerId) {
		SellerSchoolRemote sellerSchoolRemote = new SellerSchoolRemote();
		sellerSchoolRemote.setSellerId(sellerId);
		
		List<Long> schoolIds = schoolService.querySchoolIdsBySeller(sellerId);
		sellerSchoolRemote.setSchoolIds(schoolIds);
		
		return sellerSchoolRemote;
	}

	@Override
	public Long getSellerBySchoolIdForOrg(Long schoolId) {
		return customerService.getSellerByLekeIdCustomerType(schoolId, CustomerCst.CUSTOMERTYPE_SCHOOL);
	}
	
	@Override
	public Long getSellerByUserIdForPersonal(Long userId) {
		return customerService.getSellerByLekeIdCustomerType(userId, CustomerCst.CUSTOMERTYPE_PERSON);
	}
	
	@Override
	public PageRemote<School> findCustomerBySellerId(Long sellerId,String schoolName,PageRemote<School> page){
		CustomerQuery query = new CustomerQuery();
		query.setSellerId(sellerId);
		query.setCustomerType(CustomerCst.CUSTOMERTYPE_SCHOOL);//学校客户
		query.setCustomerName(schoolName);
		
		Page newPage = new Page();
		newPage.setCurPage(page.getCurPage());
		newPage.setPageSize(page.getPageSize());
		newPage.setPageCount(page.getPageCount());
		List<CustomerVO> customerList=customerService.findCustomerByQuery(query, newPage);
		List<School> schoolList = new ArrayList<School>();
		for(CustomerVO c : customerList){
			School school = new School();
			school.setId(c.getLekeId());//学校id
			school.setName(c.getCustomerName());
			schoolList.add(school);
		}
		page.setPageCount(newPage.getPageCount());
		page.setCurPage(newPage.getCurPage());
		page.setPageSize(newPage.getPageSize());
		page.setTotalSize(newPage.getTotalSize());
		page.setDataList(schoolList);
		return page;
		
	}
	
	
}
