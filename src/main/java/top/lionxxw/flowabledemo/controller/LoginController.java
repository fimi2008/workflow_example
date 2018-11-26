package top.lionxxw.flowabledemo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.lionxxw.flowabledemo.bean.LoginUser;
import top.lionxxw.flowabledemo.constant.AppConfig;
import top.lionxxw.flowabledemo.entity.User;
import top.lionxxw.flowabledemo.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * TODO

 * created on 2018/2/25 15:07
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Controller
@RequestMapping
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String login(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "login";
    }

    @PostMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response, Long userId, Model model) {
        if (null != userId && userId > 0) {
            LoginUser user = userService.findByUserId(userId);
            request.getSession().setAttribute(AppConfig.login_user, user);
            try {
                request.getRequestDispatcher("/index").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("error", "对不起,请选择登陆用户");
        return "login";
    }


    @GetMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute(AppConfig.login_user);
        response.sendRedirect("/login");
    }
}
