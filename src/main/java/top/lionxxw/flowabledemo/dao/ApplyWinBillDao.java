package top.lionxxw.flowabledemo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.lionxxw.flowabledemo.entity.ApplyWinBill;

import java.util.List;

/**
 * TODO

 * created on 2018/2/27 9:14
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Repository
@Mapper
public interface ApplyWinBillDao {

    void insert(ApplyWinBill bill);

    void update(ApplyWinBill bill);

    List<ApplyWinBill> findByUserId(Long userId);

    ApplyWinBill getById(Long id);

    List<ApplyWinBill> findAll();
}
