package top.lionxxw.flowabledemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lionxxw.flowabledemo.bean.LoginUser;
import top.lionxxw.flowabledemo.dao.DepartDao;
import top.lionxxw.flowabledemo.dao.UserDao;
import top.lionxxw.flowabledemo.entity.Depart;
import top.lionxxw.flowabledemo.entity.User;
import top.lionxxw.flowabledemo.service.UserService;
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
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private DepartDao departDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public LoginUser findByUserId(Long userId) {
        User user = userDao.findById(userId);
        List<Depart> departs = departDao.findByUserId(userId);
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getId());
        loginUser.setDeparts(departs);
        loginUser.setEmail(user.getEmail());
        loginUser.setUserName(user.getUserName());
        return loginUser;
    }

    @Override
    public User findDepartLeader(Long departId) {
        return departDao.findLeaderByDepartId(departId);
    }

    @Override
    public User getById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<UserVo> findVoAll() {
        return userDao.findVoAll();
    }

    @Override
    public void bindUserPro(Long userId, String proCode) {
        userDao.bindUserPro(userId, proCode);
    }

    @Override
    public List<UserProVo> findUserProList() {
        return userDao.findUserProList();
    }

    @Override
    public void delUserPro(Long id) {
        userDao.delUserPro(id);
    }
}
