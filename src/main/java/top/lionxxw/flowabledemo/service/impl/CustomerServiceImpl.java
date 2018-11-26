package top.lionxxw.flowabledemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lionxxw.flowabledemo.bean.LoginUser;
import top.lionxxw.flowabledemo.dao.CustomerDao;
import top.lionxxw.flowabledemo.entity.Customer;
import top.lionxxw.flowabledemo.service.CustomerService;

import java.util.List;

/**
 * TODO

 * created on 2018/3/6 15:33
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<Customer> findPubsea() {
        return customerDao.findPubSea();
    }

    @Override
    public List<Customer> findCustomer() {
        return customerDao.findCustomer();
    }

    @Override
    public void update(Customer customer) {
        customerDao.update(customer);
    }

    @Override
    public void claim(Long customerId, LoginUser loginUser) {

    }
}
