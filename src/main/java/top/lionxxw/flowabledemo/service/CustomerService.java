package top.lionxxw.flowabledemo.service;

import top.lionxxw.flowabledemo.bean.LoginUser;
import top.lionxxw.flowabledemo.entity.Customer;

import java.util.List;

/**
 * TODO

 * created on 2018/3/6 15:31
 *
 * @author lionxxw
 * @version 1.0.0
 */
public interface CustomerService {
    List<Customer> findPubsea();

    List<Customer> findCustomer();

    void update(Customer customer);

    /**
     * 公海客户认领
     * @param customerId
     * @param loginUser
     */
    void claim(Long customerId, LoginUser loginUser);
}
