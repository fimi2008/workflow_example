package top.lionxxw.flowabledemo.service;

import top.lionxxw.flowabledemo.bean.LoginUser;
import top.lionxxw.flowabledemo.entity.User;
import top.lionxxw.flowabledemo.vo.UserProVo;
import top.lionxxw.flowabledemo.vo.UserVo;

import java.util.List;

/**
 * TODO

 * created on 2018/2/26 15:49
 *
 * @author lionxxw
 * @version 1.0.0
 */
public interface UserService {
    List<User> findAll();

    LoginUser findByUserId(Long userId);

    User findDepartLeader(Long departId);

    User getById(Long id);

    List<UserVo> findVoAll();

    void bindUserPro(Long userId, String proCode);

    List<UserProVo> findUserProList();

    void delUserPro(Long id);
}
