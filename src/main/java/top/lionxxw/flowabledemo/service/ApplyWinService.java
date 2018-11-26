package top.lionxxw.flowabledemo.service;

import top.lionxxw.flowabledemo.entity.ApplyWinBill;

import java.util.List;

/**
 * TODO

 * created on 2018/2/27 9:10
 *
 * @author lionxxw
 * @version 1.0.0
 */
public interface ApplyWinService {
    void createOrUpdate(ApplyWinBill bill);

    List<ApplyWinBill> findByUserId(Long userId);

    ApplyWinBill getById(Long id);

    List<ApplyWinBill> findAll();
}
