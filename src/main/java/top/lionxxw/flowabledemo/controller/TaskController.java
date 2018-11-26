package top.lionxxw.flowabledemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.lionxxw.flowabledemo.bean.LoginUser;
import top.lionxxw.flowabledemo.constant.AppConfig;
import top.lionxxw.flowabledemo.dto.SubmitTaskDto;
import top.lionxxw.flowabledemo.entity.ApplyWinBill;
import top.lionxxw.flowabledemo.entity.User;
import top.lionxxw.flowabledemo.enums.BusinessKey;
import top.lionxxw.flowabledemo.service.ApplyWinService;
import top.lionxxw.flowabledemo.service.UserService;
import top.lionxxw.flowabledemo.service.WorkFlowService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.function.Consumer;

/**
 * TODO

 * created on 2018/2/26 11:41
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Controller
@RequestMapping("task")
public class TaskController {
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @RequestMapping("list")
    public String list(HttpServletRequest request, Model model) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(AppConfig.login_user);
        List<Task> tasks = workFlowService.findTaskListByUserId(loginUser.getUserId());
        tasks.forEach(task -> {
            if (StringUtils.isNotBlank(task.getAssignee())) {
                User user = userService.getById(Long.valueOf(task.getAssignee()));
                task.setAssignee(user.getUserName());
            }
            if (StringUtils.isNotBlank(task.getProcessDefinitionId())){
                String[] arr = task.getProcessDefinitionId().split(":");
                BusinessKey obtain = BusinessKey.obtain(arr[0]);
                task.setDescription(obtain.getName());
            }
        });
        model.addAttribute("tasks", tasks);
        return "task/list";
    }

    @RequestMapping("detail")
    public String detail(String taskId){
        String url = workFlowService.findTaskFormKeyByTaskId(taskId);
        url += "?taskId="+taskId;
        return "redirect:/"+url;
    }

    @RequestMapping("submit")
    public String submit(HttpServletRequest request, SubmitTaskDto dto){
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(AppConfig.login_user);
        dto.setUserId(loginUser.getUserId());
        dto.setUserName(loginUser.getUserName());
        workFlowService.submitTask(dto);
        return "redirect:/task/list";
    }

    /**
     * 审批通过
     * @param taskId
     * @return
     */
    @RequestMapping("pass")
    public String pass(String taskId){
        taskService.complete(taskId);
        return "redirect:/task/list";
    }
}
