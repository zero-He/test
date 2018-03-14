package cn.strong.leke.scs.remote;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.strong.leke.remote.service.crm.ICustomerServiceRemoteService;
import cn.strong.leke.scs.dao.slave.CustomerDao;


@Service
public class CustomerServiceRemoteService implements ICustomerServiceRemoteService {

	
	@Resource
	private CustomerDao customerDao;
	@Override
	public Long getSellerByLekeIdCustomerType(Long lekeId, Integer customerType) {
		return customerDao.getSellerByLekeIdCustomerType(lekeId, customerType);
	}
}
