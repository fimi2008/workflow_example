package top.lionxxw.flowabledemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * TODO

 * created on 2018/2/27 11:08
 *
 * @author lionxxw
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
public enum BusinessKey {
    APPLY_WIN_BILL("ApplyWinBill","申请赢单"),
    CLAIM_CUSTOMER("ClaimCustomer","认领客户"),
    TEST_OUT("test-out","输出领导审批");
    private String code;
    private String name;

    public static BusinessKey obtain(String code){
        if (StringUtils.isBlank(code)){
            return null;
        }
        for (BusinessKey item : BusinessKey.values()){
            if (StringUtils.equals(item.getCode(), code)){
                return item;
            }
        }
        return null;
    }
}
