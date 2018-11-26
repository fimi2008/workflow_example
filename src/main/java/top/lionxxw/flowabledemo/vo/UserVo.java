package top.lionxxw.flowabledemo.vo;

import lombok.Getter;
import lombok.Setter;
import top.lionxxw.flowabledemo.entity.User;

import java.io.Serializable;

/**
 * TODO

 * created on 2018/3/1 9:48
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Getter
@Setter
public class UserVo extends User implements Serializable {
    private static final long serialVersionUID = 7917101219445372128L;
    private Boolean leader;
    private String departId;
    private String departName;
    private String roleCode;
    private String roleName;
}
