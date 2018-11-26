package top.lionxxw.flowabledemo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.lionxxw.flowabledemo.entity.User;
import top.lionxxw.flowabledemo.vo.UserProVo;
import top.lionxxw.flowabledemo.vo.UserVo;

import java.util.List;

/**
 * TODO

 * created on 2018/2/26 15:26
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Repository
@Mapper
public interface UserDao{
    List<User> findAll();

    User findById(Long id);

    List<UserVo> findVoAll();

    void bindUserPro(@Param("userId") Long userId, @Param("proCode")String proCode);

    List<UserProVo> findUserProList();

    void delUserPro(Long id);
}
