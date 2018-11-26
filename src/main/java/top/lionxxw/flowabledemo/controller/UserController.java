package top.lionxxw.flowabledemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.lionxxw.flowabledemo.entity.User;
import top.lionxxw.flowabledemo.enums.ProEnums;
import top.lionxxw.flowabledemo.service.UserService;
import top.lionxxw.flowabledemo.vo.UserProVo;
import top.lionxxw.flowabledemo.vo.UserVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO

 * created on 2018/3/1 9:44
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("list")
    public String list(Model model) {
        List<UserVo> users = userService.findVoAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    /**
     * 用户行业审批配置查看
     *
     * @param model
     * @return
     */
    @RequestMapping("pro/list")
    public String proList(Model model) {
        List<UserProVo> userProList = userService.findUserProList();
        model.addAttribute("userProList", userProList);
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("pros", ProEnums.values());
        return "user/pro/list";
    }

    /**
     * 添加用户行业审批关系
     *
     * @param userId
     * @param proCode
     * @return
     */
    @PostMapping("pro/add")
    @ResponseBody
    public Map<String, Object> proAdd(Long userId, String proCode) {
        try {
            userService.bindUserPro(userId, proCode);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                return failed("该用户已经添加过该行业");
            }
            return failed("系统异常!");
        }
        return success();
    }

    /**
     * 移除用户行业关系
     *
     * @param id
     * @return
     */
    @RequestMapping("pro/del")
    public String delPro(Long id) {
        userService.delUserPro(id);
        return "redirect:/user/pro/list";
    }

    private Map<String, Object> success() {
        Map<String, Object> map = new HashMap();
        map.put("status", true);
        map.put("reason", "操作成功");
        return map;
    }

    private Map<String, Object> failed(String reason) {
        Map<String, Object> map = new HashMap();
        map.put("status", false);
        map.put("reason", "操作失败：" + reason);
        return map;
    }
}
