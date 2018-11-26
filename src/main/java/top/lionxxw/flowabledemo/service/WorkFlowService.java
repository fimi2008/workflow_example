package top.lionxxw.flowabledemo.service;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import top.lionxxw.flowabledemo.dto.SubmitTaskDto;
import top.lionxxw.flowabledemo.entity.ApplyWinBill;
import top.lionxxw.flowabledemo.enums.BusinessKey;
import top.lionxxw.flowabledemo.vo.DeployWorkFlowVo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * TODO

 * created on 2018/2/26 10:31
 *
 * @author lionxxw
 * @version 1.0.0
 */
public interface WorkFlowService {
    /**
     * 查询部署信息
     *
     * @return
     */
    List<Deployment> findDeploymentList();

    /**
     * 查询流程定义信息
     *
     * @return
     */
    List<ProcessDefinition> findProcessDefinitionList();

    /**
     * 部署流程
     *
     * @param file
     * @param fileName
     */
    boolean saveNewDeploye(InputStream file, String fileName);

    /**
     * 根据部署id和资源图片名称,获取图片的输入流
     *
     * @param deploymentId
     * @param imageName
     * @return
     */
    InputStream findImageInputStream(String deploymentId, String imageName);

    /**
     * 删除流程
     *
     * @param deploymentId
     */
    void deleteProcessDefinitionByDeploymentId(String deploymentId);

    /**
     * 开启流程
     *
     * @param id  业务id
     * @param key 流程定义key
     */
    void startProcess(Long id, BusinessKey key);

    /**
     * 查询指定用户的任务
     *
     * @param userId
     * @return
     */
    List<Task> findTaskListByUserId(Long userId);

    /**
     * 根据任务id查看流程定义
     *
     * @param taskId
     * @return
     */
    ProcessDefinition findProcessDefinitionByTaskId(String taskId);

    Map<String, Object> findCoordingByTask(String taskId);

    ApplyWinBill findApplyWinBillByTaskId(String taskId);

    List<String> findOutComeListByTaskId(String taskId);

    List<Comment> findCommentByTaskId(String taskId);

    String findTaskFormKeyByTaskId(String taskId);

    void submitTask(SubmitTaskDto dto);

    List<Comment> findCommentByApplyWinId(Long id);

    String getTaskIdByApplyWinId(Long id);
}
