package top.lionxxw.flowabledemo.controller;

import org.activiti.engine.task.Comment;
import org.hibernate.validator.internal.engine.ValueContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.lionxxw.flowabledemo.entity.ApplyWinBill;
import top.lionxxw.flowabledemo.service.WorkFlowService;

import java.util.List;

/**
 * TODO

 * created on 2018/2/27 14:10
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Controller
@RequestMapping("chance")
public class ChanceController {
    @Autowired
    private WorkFlowService workFlowService;

    @RequestMapping("audit")
    public String audit(String taskId, Model model){
        model.addAttribute("taskId", taskId);
        /**一：使用任务ID，查找请假单ID，从而获取请假单信息*/
        ApplyWinBill bill = workFlowService.findApplyWinBillByTaskId(taskId);
        model.addAttribute("bill", bill);
        /**二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
        List<String> outcomeList = workFlowService.findOutComeListByTaskId(taskId);
        model.addAttribute("outcomeList", outcomeList);
        /**三：查询所有历史审核人的审核信息，帮助当前人完成审核，返回List<Comment>*/
        List<Comment> commentList = workFlowService.findCommentByTaskId(taskId);
        model.addAttribute("commentList", commentList);
        return "apply/detail";
    }
}
