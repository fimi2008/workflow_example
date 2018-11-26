package top.lionxxw.flowabledemo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.lionxxw.flowabledemo.entity.Customer;

import java.util.List;

/**
 * TODO

 * created on 2018/3/6 15:22
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Repository
@Mapper
public interface CustomerDao {
    List<Customer> findPubSea();

    List<Customer> findCustomer();

    void update(Customer customer);
}
