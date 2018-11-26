package top.lionxxw.flowabledemo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO

 * created on 2018/2/27 15:41
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Getter
@Setter
public class SubmitTaskDto {
    private Long id;
    private String taskId;
    private String comment;
    private String outcome;
    private Long userId;
    private String userName;
}
