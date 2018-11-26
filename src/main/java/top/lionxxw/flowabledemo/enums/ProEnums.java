package top.lionxxw.flowabledemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * TODO

 * created on 2018/3/6 14:56
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ProEnums {
    MRHY("MRHY","默认行业"),
    DZHY("DZHY","大宗"),
    XFJRH("XFJRH","消费金融"),
    HJHY("HJHY","互金"),
    ZXHY("ZXHY","征信"),
    HLHY("HLHY","航旅"),
    JJHY("JJHY","基金"),
    BXHY("BXHY","保险"),
    DSHY("DSHY","电商"),
    SYHY("SYHY","数娱");
    private String code;
    private String name;

    public static ProEnums obtain(String code){
        if (StringUtils.isNotBlank(code)){
            for (ProEnums pro : ProEnums.values()){
                if (StringUtils.equals(pro.getCode(), code)){
                    return pro;
                }
            }
        }
        return null;
    }
}
