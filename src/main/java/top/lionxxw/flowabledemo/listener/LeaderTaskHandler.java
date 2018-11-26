package top.lionxxw.flowabledemo.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.lionxxw.flowabledemo.bean.LoginUser;
import top.lionxxw.flowabledemo.constant.AppConfig;
import top.lionxxw.flowabledemo.entity.Depart;
import top.lionxxw.flowabledemo.entity.User;
import top.lionxxw.flowabledemo.service.UserService;
import top.lionxxw.flowabledemo.util.SpringUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 员工经理任务分配
 */
@Component
public class LeaderTaskHandler implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        /**懒加载异常*/
//		Employee employee = SessionContext.get();
//		//设置个人任务的办理人
//		delegateTask.setAssignee(employee.getManager().getName());
        /**从新查询当前用户，再获取当前用户对应的领导*/
        //获取到当前线程绑定的请求对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//已经拿到session,就可以拿到session中保存的用户信息了。
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(AppConfig.login_user);
        //当前用户
        String name = loginUser.getUserName();
        //使用当前用户名查询用户的详细信息
        //从web中获取spring容器
        UserService userService = (UserService) SpringUtil.getBean("userService");
        LoginUser user = userService.findByUserId(loginUser.getUserId());

        List<Depart> departs = user.getDeparts();
        if (!CollectionUtils.isEmpty(departs)) {
            Depart depart = departs.get(0);
            User leader = userService.findDepartLeader(depart.getId());
            //设置个人任务的办理人
            delegateTask.setAssignee(String.valueOf(leader.getId()));
        }
    }

}
