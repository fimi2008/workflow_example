package top.lionxxw.flowabledemo.constant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.lionxxw.flowabledemo.interceptor.LoginInterceptor;

import java.util.ArrayList;
import java.util.List;

/**

 * created on 2017/11/14 11:00
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    public static void main(String[] args) {
        List<JSONObject> arr = new ArrayList<>();
        JSONObject json = new JSONObject();
        json.put("chanceId", "10001");
        json.put("chanceName", "网关，快捷");
        arr.add(json);

        System.out.println(JSON.toJSONString(arr));
    }
}
