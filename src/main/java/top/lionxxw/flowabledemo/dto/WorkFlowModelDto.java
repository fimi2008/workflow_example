package top.lionxxw.flowabledemo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;

/**
 * TODO

 * created on 2018/3/1 14:18
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Getter
@Setter
public class WorkFlowModelDto implements Serializable {
    private static final long serialVersionUID = 2125664067151353680L;
    public String id;
    private String name;
    private String description;
    private Integer version;
    private String key;
}
