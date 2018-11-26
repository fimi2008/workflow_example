package top.lionxxw.flowabledemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.lionxxw.flowabledemo.bean.LoginUser;
import top.lionxxw.flowabledemo.constant.AppConfig;
import top.lionxxw.flowabledemo.entity.ApplyWinBill;
import top.lionxxw.flowabledemo.entity.Depart;
import top.lionxxw.flowabledemo.entity.User;
import top.lionxxw.flowabledemo.enums.BusinessKey;
import top.lionxxw.flowabledemo.service.ApplyWinService;
import top.lionxxw.flowabledemo.service.UserService;
import top.lionxxw.flowabledemo.service.WorkFlowService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO

 * created on 2018/2/27 9:44
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Controller
@RequestMapping("apply")
public class ApplyController {
    @Autowired
    private ApplyWinService applyWinService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private UserService userService;
    @Autowired
    private RuntimeService runtimeService;

    @GetMapping("list")
    public String list(HttpServletRequest request, Model model) {
        String chanceDatas = AppConfig.CHANCE_DATAS;
        JSONArray chances = JSON.parseArray(chanceDatas);
        model.addAttribute("chances", chances);
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(AppConfig.login_user);
        List<ApplyWinBill> applies = applyWinService.findByUserId(loginUser.getUserId());
        model.addAttribute("applies", applies);
        return "apply/list";
    }

    @PostMapping("add")
    @ResponseBody
    public String add(HttpServletRequest request, ApplyWinBill bill) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(AppConfig.login_user);
        bill.setApplyUserId(loginUser.getUserId());
        bill.setApplyUserName(loginUser.getUserName());
        applyWinService.createOrUpdate(bill);
        return "success";
    }

    @PostMapping("leave")
    @ResponseBody
    public String leave(HttpServletRequest request, Integer days) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(AppConfig.login_user);
        if (!CollectionUtils.isEmpty(loginUser.getDeparts())) {
            Depart depart = loginUser.getDeparts().get(0);
            User leader = userService.findDepartLeader(depart.getId());
            Map<String, Object> variables = new HashMap<>();
            variables.put("userId", leader.getId());
            variables.put("applyUserId", loginUser.getUserId());
            variables.put("days", days);
            //设置个人任务的办理人
            runtimeService.startProcessInstanceByKey(BusinessKey.TEST_OUT.getCode(), variables);
        }
        return "success";
    }

    @GetMapping("detail")
    public String detail(Long id, Model model) {
        ApplyWinBill bill = applyWinService.getById(id);
        model.addAttribute("bill", bill);
        //2：使用请假单ID，查询历史的批注信息
        List<Comment> commentList = workFlowService.findCommentByApplyWinId(id);
        model.addAttribute("commentList", commentList);
        return "apply/detail";
    }

    @RequestMapping("viewCurrentImage")
    public String viewCurrentImage(Long id) {
        String taskId = workFlowService.getTaskIdByApplyWinId(id);
        return "redirect:/viewCurrentImage?taskId=" + taskId;
    }

    @GetMapping("all")
    public String list(Model model) {
        String chanceDatas = AppConfig.CHANCE_DATAS;
        JSONArray chances = JSON.parseArray(chanceDatas);
        model.addAttribute("chances", chances);
        List<ApplyWinBill> applies = applyWinService.findAll();
        model.addAttribute("applies", applies);
        return "apply/all";
    }
}
