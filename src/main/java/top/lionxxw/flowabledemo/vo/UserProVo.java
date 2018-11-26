package top.lionxxw.flowabledemo.vo;

import lombok.Getter;
import lombok.Setter;
import top.lionxxw.flowabledemo.enums.ProEnums;

/**
 * TODO

 * created on 2018/3/6 16:13
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Getter
@Setter
public class UserProVo {
    private Long id;
    private Long userId;
    private String userName;
    private String proCode;

    public String getProName(){
        ProEnums obtain = ProEnums.obtain(this.proCode);
        if (null != obtain){
            return obtain.getName();
        }
        return "未定义";
    }
}
