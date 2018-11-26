package top.lionxxw.flowabledemo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 申请赢单 工作流

 * created on 2018/2/26 16:21
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Getter
@Setter
public class ApplyWinBill {
    private Long id;
    private Long chanceId;
    private String chanceName;
    /**
     * 申请人
     */
    private Long applyUserId;
    private String applyUserName;
    /**
     * 状态
     * 0-初始状态,1-审核中,2-审核成功,-1-拒绝
     */
    private Integer status;
    private String description;
    private Date createTime;

    public String getStatusName(){
        switch (this.status){
            case 1 : return "审核中";
            case 2 : return "审核通过";
            case -1: return "审批失败";
            default: return "初始状态";
        }
    }
}
