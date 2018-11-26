package top.lionxxw.flowabledemo.entity;

import lombok.Getter;
import lombok.Setter;
import top.lionxxw.flowabledemo.enums.ProEnums;
import top.lionxxw.flowabledemo.enums.StatusEnums;

import java.util.Date;

/**
 * TODO

 * created on 2018/3/6 14:47
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Getter
@Setter
public class Customer {
    private Long id;
    private String name;
    private String status;
    private String proCode;
    private Date createTime;
    private Date updateTime;
    private Long ownerId;
    private String ownerName;
    private Boolean pubsea;

    public String getStatusName(){
        StatusEnums obtain = StatusEnums.obtain(this.status);
        if (null != obtain){
            return obtain.getName();
        }
        return "未定义";
    }

    public String getProName(){
        ProEnums obtain = ProEnums.obtain(this.proCode);
        if (null != obtain){
            return obtain.getName();
        }
        return "未定义";
    }
}
