package top.lionxxw.flowabledemo.constant;

import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import top.lionxxw.flowabledemo.interceptor.LoginInterceptor;

/**
 * TODO

 * created on 2018/2/25 23:02
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Configuration
@ImportResource(locations={"classpath:activiti.cfg.xml"})
public class ActivitiConfig {
}
