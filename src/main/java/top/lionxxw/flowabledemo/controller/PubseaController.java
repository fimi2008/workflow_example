package top.lionxxw.flowabledemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.lionxxw.flowabledemo.bean.LoginUser;
import top.lionxxw.flowabledemo.constant.AppConfig;
import top.lionxxw.flowabledemo.entity.Customer;
import top.lionxxw.flowabledemo.service.CustomerService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO

 * created on 2018/3/6 14:46
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Controller
@RequestMapping("pubsea")
@Slf4j
public class PubseaController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping("list")
    public String list(Model model) {
        List<Customer> pubsea = customerService.findPubsea();
        model.addAttribute("pubseas", pubsea);
        return "pubsea/list";
    }

    @RequestMapping("claim")
    public String claim(HttpServletRequest request, Long customerId) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(AppConfig.login_user);
        log.info("++++++++++++ user:{} 认领客户 customerId:{} ++++++++++++", loginUser.getUserName(), customerId);
        customerService.claim(customerId, loginUser);
        return "redirect:/pubsea/list";
    }
}
