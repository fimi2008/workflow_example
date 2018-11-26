package top.lionxxw.flowabledemo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * TODO

 * created on 2018/2/26 10:38
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Getter
@Setter
public class DeployWorkFlowVo implements Serializable {
    private static final long serialVersionUID = -2083977698705337599L;
    private String id;
    private String name;
    private Integer version;
    private String key;
    private String deploymentTime;
    private String deploymentId;
    private String diagramResourceName;
}