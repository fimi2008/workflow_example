package top.lionxxw.flowabledemo.bean;

import lombok.Getter;
import lombok.Setter;
import top.lionxxw.flowabledemo.entity.Depart;
import top.lionxxw.flowabledemo.entity.Role;

import java.io.Serializable;
import java.util.List;

/**
 * TODO

 * created on 2018/2/26 16:03
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Getter
@Setter
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 7166747719241869421L;
    private Long userId;
    private String userName;
    private String email;
    private List<Depart> departs;
    private List<Role> roles;
}