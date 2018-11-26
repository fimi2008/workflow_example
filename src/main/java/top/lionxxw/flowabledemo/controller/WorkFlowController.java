package top.lionxxw.flowabledemo.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.hibernate.validator.internal.engine.ValueContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.lionxxw.flowabledemo.constant.AppConfig;
import top.lionxxw.flowabledemo.service.WorkFlowService;
import top.lionxxw.flowabledemo.util.DateFormat;
import top.lionxxw.flowabledemo.util.DateUtil;
import top.lionxxw.flowabledemo.vo.DeployWorkFlowVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * TODO

 * created on 2018/2/25 14:50
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Controller
@RequestMapping
public class WorkFlowController {
    @Autowired
    private WorkFlowService workFlowService;

    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("deploy")
    public String deploy(Model model) {
        List<Deployment> deploymentList = workFlowService.findDeploymentList();
        if (!CollectionUtils.isEmpty(deploymentList)) {
            List<ProcessDefinition> processDefinitionList = workFlowService.findProcessDefinitionList();
            List<DeployWorkFlowVo> vos = new ArrayList<>(deploymentList.size());
            DeployWorkFlowVo vo;
            for (int i = 0, len = deploymentList.size(); i < len; i++) {
                vo = new DeployWorkFlowVo();
                Deployment deployment = deploymentList.get(i);
                ProcessDefinition processDefinition = null;
                inner: for (ProcessDefinition pro : processDefinitionList){
                    if (deployment.getId().equals(pro.getDeploymentId())){
                        processDefinition = pro;
                        break inner;
                    }
                }
                vo.setDeploymentId(deployment.getId());
                vo.setDeploymentTime(DateUtil.formatDate(deployment.getDeploymentTime(), DateFormat.SETTLE_PATTERN));
                vo.setName(deployment.getName());
                vo.setId(processDefinition.getId());
                vo.setKey(processDefinition.getKey());
                vo.setVersion(processDefinition.getVersion());
                vo.setDiagramResourceName(processDefinition.getDiagramResourceName());
                vos.add(vo);
            }
            model.addAttribute("list", vos);
        }
        return "deploy";
    }

    @PostMapping("deploy")
    @ResponseBody
    public String deploy(MultipartFile file, String fileName) {
        try {
            workFlowService.saveNewDeploye(file.getInputStream(), fileName);
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping("viewImage")
    public String viewImage(HttpServletResponse response, String deploymentId, String imageName) throws IOException {
        //2：获取资源文件表（act_ge_bytearray）中资源图片输入流InputStream
        InputStream in = workFlowService.findImageInputStream(deploymentId, imageName);
        //3：从response对象获取输出流
        OutputStream out = response.getOutputStream();
        //4：将输入流中的数据读取出来，写到输出流中
        for (int b = -1; (b = in.read()) != -1; ) {
            out.write(b);
        }
        out.close();
        in.close();
        //将图写到页面上，用输出流写
        return null;
    }

    @RequestMapping("viewCurrentImage")
    public String viewCurrentImage(String taskId, Model model){
        /**一：查看流程图*/
        //1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
        ProcessDefinition pd = workFlowService.findProcessDefinitionByTaskId(taskId);
        //workflowAction_viewImage?deploymentId=<s:property value='#deploymentId'/>&imageName=<s:property value='#imageName'/>
        model.addAttribute("deploymentId", pd.getDeploymentId());
        model.addAttribute("imageName", pd.getDiagramResourceName());
        /**二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中*/
        Map<String, Object> map = workFlowService.findCoordingByTask(taskId);
        model.addAttribute("acs", map);
        return "image";
    }

    @RequestMapping("delDeployment")
    public void delDeployment(HttpServletRequest request, HttpServletResponse response, String deploymentId) throws IOException {
        workFlowService.deleteProcessDefinitionByDeploymentId(deploymentId);
        response.sendRedirect("/deploy");
    }
}
