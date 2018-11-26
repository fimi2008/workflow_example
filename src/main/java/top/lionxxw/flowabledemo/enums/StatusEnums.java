package top.lionxxw.flowabledemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * TODO

 * created on 2018/3/6 15:03
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
public enum StatusEnums {
    DEL("DEL", "删除"),
    DELAY("DELAY", "延迟"),
    UNPICK("UNPICK", "未领取"),
    PICK("PICK", "已领取"),
    PICKING("PICKING", "领取中"),
    BACKING("BACKING", "退回中"),
    DELAYING("DELAYING", "延迟中"),
    BACK("BACK", "已退回"),
    SIGN("SIGN", "已签约");

    private String code;
    private String name;

    public static StatusEnums obtain(String code){
        if (StringUtils.isNotBlank(code)){
            for (StatusEnums st : StatusEnums.values()){
                if (StringUtils.equals(st.getCode(), code)){
                    return st;
                }
            }
        }
        return null;
    }
}
