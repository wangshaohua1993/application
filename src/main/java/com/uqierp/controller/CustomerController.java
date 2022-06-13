package com.uqierp.controller;

import com.uqierp.bean.Customer;
import com.uqierp.bean.Mail;
import com.uqierp.rabbitmq.MQSender;
import com.uqierp.result.Result;
import com.uqierp.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

    @Autowired
    private MQSender sender;


    @ApiOperation("添加用户接口")
    @PostMapping(value="/addCustomer")
    public Result<Integer> addCustomer(Customer customer) {
        Integer result = customerService.addCustomer(customer);
        // mq异步发送邮件
        Mail mail = this.buildMail(customer);
        sender.send(mail);
        return Result.success(result);
    }

    @ApiOperation("修改用户接口")
    @PostMapping(value="/updateCustomer")
    public Result<Integer> updateCustomer(Customer customer) {
        Integer result = customerService.updateCustomer(customer);
        return Result.success(result);
    }

    @ApiOperation("通过id查询用户接口")
    @PostMapping(value="/queryCustomerById")
    public Result<Customer> queryCustomerById(Long customerId) {
        Customer customer = customerService.queryCustomerById(customerId);
        return Result.success(customer);
    }

    @ApiOperation("删除用户接口")
    @PostMapping(value="/deleteCustomerByIds")
    public Result<Integer> deleteCustomerByIds(List<Long> customerIds) {
        Integer result = customerService.deleteCustomerByIds(customerIds);
        return Result.success(result);
    }

    private Mail buildMail(Customer customer){
        Mail mail = new Mail();
        mail.setTo("xxx@163.com");
        mail.setTitle("测试邮件标题");
        mail.setContent(customer.getCustomerName());
        return mail;
    }
}
