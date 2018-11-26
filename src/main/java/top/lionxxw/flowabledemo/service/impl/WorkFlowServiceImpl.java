package top.lionxxw.flowabledemo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lionxxw.flowabledemo.dto.SubmitTaskDto;
import top.lionxxw.flowabledemo.entity.ApplyWinBill;
import top.lionxxw.flowabledemo.enums.BusinessKey;
import top.lionxxw.flowabledemo.service.ApplyWinService;
import top.lionxxw.flowabledemo.service.WorkFlowService;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * TODO

 * created on 2018/2/26 10:35
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Service
@Slf4j
public class WorkFlowServiceImpl implements WorkFlowService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private ApplyWinService applyWinService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;
    @Autowired
    private HistoryService historyService;

    @Override
    public List<Deployment> findDeploymentList() {
        List<Deployment> list = repositoryService.createDeploymentQuery()//创建部署对象查询
                .orderByDeploymenTime().asc()//
                .list();
        return list;
    }

    @Override
    public List<ProcessDefinition> findProcessDefinitionList() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//创建流程定义查询
                .orderByProcessDefinitionVersion().asc()//
                .list();
        return list;
    }

    @Override
    public boolean saveNewDeploye(InputStream file, String fileName) {
        try {
            //2：将File类型的文件转化成ZipInputStream流
            ZipInputStream zipInputStream = new ZipInputStream(file);
            repositoryService.createDeployment()//创建部署对象
                    .name(fileName)//添加部署名称
                    .addZipInputStream(zipInputStream)//
                    .deploy();//完成部署
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public InputStream findImageInputStream(String deploymentId, String imageName) {
        return repositoryService.getResourceAsStream(deploymentId, imageName);
    }

    /**
     * 使用部署对象ID，删除流程定义
     *
     * @param deploymentId
     */
    @Override
    public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
    }

    @Override
    public void startProcess(Long id, BusinessKey key) {
        Map<String, Object> variables = new HashMap<String, Object>();
        if (key == BusinessKey.APPLY_WIN_BILL) {
            ApplyWinBill bill = applyWinService.getById(id);
            bill.setStatus(1);
            applyWinService.createOrUpdate(bill);
            // 表示惟一用户
            variables.put("userId", bill.getApplyUserId());
        }
        String objId = key.getCode() + "." + id;
        // 格式：LeaveBill.id的形式（使用流程变量）
        variables.put("objId", objId);
        runtimeService.startProcessInstanceByKey(key.getCode(), objId, variables);
        log.info("key:{} 流程已开启 objId:{}", key.getCode(), objId);
    }

    @Override
    public List<Task> findTaskListByUserId(Long userId) {
        List<Task> list = taskService.createTaskQuery()//
                .taskAssignee(String.valueOf(userId))//指定个人任务查询
                .orderByTaskCreateTime().asc()//
                .list();
        return list;
    }

    @Override
    public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
        //使用任务ID，查询任务对象
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)//使用任务ID查询
                .singleResult();
        //获取流程定义ID
        String processDefinitionId = task.getProcessDefinitionId();
        //查询流程定义的对象
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//创建流程定义查询对象，对应表act_re_procdef
                .processDefinitionId(processDefinitionId)//使用流程定义ID查询
                .singleResult();
        return pd;
    }

    @Override
    public Map<String, Object> findCoordingByTask(String taskId) {
        //存放坐标
        Map<String, Object> map = new HashMap<String, Object>();
        //使用任务ID，查询任务对象
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)//使用任务ID查询
                .singleResult();
        //获取流程定义的ID
        String processDefinitionId = task.getProcessDefinitionId();
        //获取流程定义的实体对象（对应.bpmn文件中的数据）
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        //流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //使用流程实例ID，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//创建流程实例查询
                .processInstanceId(processInstanceId)//使用流程实例ID查询
                .singleResult();
        //获取当前活动的ID
        String activityId = pi.getActivityId();
        //获取当前活动对象
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);//活动ID
        //获取坐标
        map.put("x", activityImpl.getX());
        map.put("y", activityImpl.getY());
        map.put("width", activityImpl.getWidth());
        map.put("height", activityImpl.getHeight());
        return map;
    }

    @Override
    public ApplyWinBill findApplyWinBillByTaskId(String taskId) {
        //1：使用任务ID，查询任务对象Task
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)//使用任务ID查询
                .singleResult();
        //2：使用任务对象Task获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                .processInstanceId(processInstanceId)//使用流程实例ID查询
                .singleResult();
        //4：使用流程实例对象获取BUSINESS_KEY
        String buniness_key = pi.getBusinessKey();
        //5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
        String id = "";
        if (StringUtils.isNotBlank(buniness_key)) {
            //截取字符串，取buniness_key小数点的第2个值
            id = buniness_key.split("\\.")[1];
        }
        //查询请假单对象
        //使用hql语句：from LeaveBill o where o.id=1
        ApplyWinBill bill = applyWinService.getById(Long.parseLong(id));
        return bill;
    }

    @Override
    public List<String> findOutComeListByTaskId(String taskId) {
        //返回存放连线的名称集合
        List<String> list = new ArrayList<String>();
        //1:使用任务ID，查询任务对象
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)//使用任务ID查询
                .singleResult();
        //2：获取流程定义ID
        String processDefinitionId = task.getProcessDefinitionId();
        //3：查询ProcessDefinitionEntiy对象
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        //使用任务对象Task获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                .processInstanceId(processInstanceId)//使用流程实例ID查询
                .singleResult();
        //获取当前活动的id
        String activityId = pi.getActivityId();
        //4：获取当前的活动
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
        //5：获取当前活动完成之后连线的名称
        List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
        if (pvmList != null && pvmList.size() > 0) {
            for (PvmTransition pvm : pvmList) {
                String name = (String) pvm.getProperty("name");
                if (StringUtils.isNotBlank(name)) {
                    list.add(name);
                } else {
                    list.add("默认提交");
                }
            }
        }
        return list;
    }

    @Override
    public List<Comment> findCommentByTaskId(String taskId) {
        List<Comment> list = new ArrayList<Comment>();
        //使用当前的任务ID，查询当前流程对应的历史任务ID
        //使用当前任务ID，获取当前任务对象
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)//使用任务ID查询
                .singleResult();
        //获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
//		//使用流程实例ID，查询历史任务，获取历史任务对应的每个任务ID
//		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()//历史任务表查询
//						.processInstanceId(processInstanceId)//使用流程实例ID查询
//						.list();
//		//遍历集合，获取每个任务ID
//		if(htiList!=null && htiList.size()>0){
//			for(HistoricTaskInstance hti:htiList){
//				//任务ID
//				String htaskId = hti.getId();
//				//获取批注信息
//				List<Comment> taskList = taskService.getTaskComments(htaskId);//对用历史完成后的任务ID
//				list.addAll(taskList);
//			}
//		}
        list = taskService.getProcessInstanceComments(processInstanceId);
        return list;
    }

    @Override
    public String findTaskFormKeyByTaskId(String taskId) {
        TaskFormData formData = formService.getTaskFormData(taskId);
        //获取Form key的值
        String url = formData.getFormKey();
        return url;
    }

    @Override
    public void submitTask(SubmitTaskDto dto) {
        /**
         * 1：在完成之前，添加一个批注信息，向act_hi_comment表中添加数据，用于记录对当前申请人的一些审核信息
         */
        //使用任务ID，查询任务对象，获取流程流程实例ID
        Task task = taskService.createTaskQuery()//
                .taskId(dto.getTaskId())//使用任务ID查询
                .singleResult();
        //获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        /**
         * 注意：添加批注的时候，由于Activiti底层代码是使用：
         * 		String userId = Authentication.getAuthenticatedUserId();
         CommentEntity comment = new CommentEntity();
         comment.setUserId(userId);
         所有需要从Session中获取当前登录人，作为该任务的办理人（审核人），对应act_hi_comment表中的User_ID的字段，不过不添加审核人，该字段为null
         所以要求，添加配置执行使用Authentication.setAuthenticatedUserId();添加当前任务的审核人
         * */
        Authentication.setAuthenticatedUserId(dto.getUserName());
        taskService.addComment(dto.getTaskId(), processInstanceId, dto.getComment());
        /**
         * 2：如果连线的名称是“默认提交”，那么就不需要设置，如果不是，就需要设置流程变量
         * 在完成任务之前，设置流程变量，按照连线的名称，去完成任务
         流程变量的名称：outcome
         流程变量的值：连线的名称
         */
        Map<String, Object> variables = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(dto.getOutcome()) && !StringUtils.equals("默认提交", dto.getOutcome())) {
            variables.put("outcome", dto.getOutcome());
        }
        //3：使用任务ID，完成当前人的个人任务，同时流程变量
        taskService.complete(dto.getTaskId(), variables);
        //4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成

        /**
         * 5：在完成任务之后，判断流程是否结束
         如果流程结束了，更新请假单表的状态从1变成2（审核中-->审核完成）
         */
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                .processInstanceId(processInstanceId)//使用流程实例ID查询
                .singleResult();
        //流程结束了
        if (pi == null) {
            //更新请假单表的状态从1变成2（审核中-->审核完成）
            ApplyWinBill applyWinBill = applyWinService.getById(dto.getId());
            if (StringUtils.equals("审批失败", dto.getOutcome())) {
                applyWinBill.setStatus(-1);
            } else {
                applyWinBill.setStatus(2);
            }
            applyWinService.createOrUpdate(applyWinBill);
        }
    }

    @Override
    public List<Comment> findCommentByApplyWinId(Long id) {
        //使用请假单ID，查询请假单对象
        //获取对象的名称
        String objectName = ApplyWinBill.class.getSimpleName();
        //组织流程表中的字段中的值
        String objId = objectName + "." + id;

        /**1:使用历史的流程实例查询，返回历史的流程实例对象，获取流程实例ID*/
//		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()//对应历史的流程实例表
//						.processInstanceBusinessKey(objId)//使用BusinessKey字段查询
//						.singleResult();
//		//流程实例ID
//		String processInstanceId = hpi.getId();
        /**2:使用历史的流程变量查询，返回历史的流程变量的对象，获取流程实例ID*/
        HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()//对应历史的流程变量表
                .variableValueEquals("objId", objId)//使用流程变量的名称和流程变量的值查询
                .singleResult();
        //流程实例ID
        String processInstanceId = hvi.getProcessInstanceId();
        List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
        return list;
    }

    @Override
    public String getTaskIdByApplyWinId(Long id) {
        String objectName = ApplyWinBill.class.getSimpleName();
        //组织流程表中的字段中的值
        String objId = objectName + "." + id;
        Task task = taskService.createTaskQuery().processVariableValueEquals("objId", objId).singleResult();
        return task.getId();
    }
}
