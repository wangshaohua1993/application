package com.uqierp.dao;

import com.uqierp.bean.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerDao {

    int deleteByPrimaryKey(Long customerId);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Long customerId);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
}