package top.lionxxw.flowabledemo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.lionxxw.flowabledemo.entity.Depart;
import top.lionxxw.flowabledemo.entity.User;

import java.util.List;

/**
 * TODO

 * created on 2018/2/26 16:07
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Repository
@Mapper
public interface DepartDao {

    List<Depart> findByUserId(Long userId);

    User findLeaderByDepartId(Long departId);
}
