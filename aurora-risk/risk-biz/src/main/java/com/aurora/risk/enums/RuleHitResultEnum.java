package com.aurora.risk.enums;

import lombok.AllArgsConstructor;

/**
 * @author: Nick
 * @create: 2022-03-30 18:01
 **/
@AllArgsConstructor
public enum RuleHitResultEnum {

    //命中通过 未命中拒绝
    PASS("pass", "reject"),
    //命中拒绝 未命中通过
    REJECT("reject", "pass"),
    //命中复审 未命中通过
    REVIEW("review", "pass"),
    ;

    /**
     * 命中结果
     */
    public final String hitStatus;

    /**
     * 未命中结果
     */
    public final String notHitStatus;


    public static String getNotHitStatus(String hitStatus) {
        for (RuleHitResultEnum ruleHitResultEnum : RuleHitResultEnum.values()) {
            if (ruleHitResultEnum.hitStatus.equals(hitStatus)) {
                return ruleHitResultEnum.notHitStatus;
            }
        }
        return null;
    }

}
