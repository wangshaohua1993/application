package com.uqierp.service;

import java.util.List;

import com.uqierp.bean.Customer;
import com.uqierp.dao.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;

	public int addCustomer(Customer record){
		return customerDao.insertSelective(record);
	}

	public int updateCustomer(Customer record){
		return customerDao.updateByPrimaryKeySelective(record);
	}

	public Customer queryCustomerById(Long customerId){
		return customerDao.selectByPrimaryKey(customerId);
	}

	public int deleteCustomerByIds(List<Long> customerIds){
		int result = 0;
		if (CollectionUtils.isEmpty(customerIds)){
			return result;
		}
		for (Long customerId : customerIds){
			customerDao.deleteByPrimaryKey(customerId);
			result++;
		}
		return result;
	}
}
