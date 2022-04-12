package com.aurora.risk.rule;

import com.aurora.common.util.CommonUtil;
import com.aurora.risk.base.model.RiskRule;
import com.aurora.risk.enums.RuleTypeEnum;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 规则工厂
 *
 * @author: Nick
 * @create: 2022-03-28 16:48
 **/
@Component
public class RuleFactory {

    /**
     * 获取规则链
     *
     * @param rules : rules
     * @param index : index
     * @return com.aurora.risk.rule.AbstractRule
     * @author Nick
     * @date 2022/04/01
     */
    public static AbstractRule getRule(List<RiskRule> rules, int index) {
        RiskRule rule = rules.get(index);
        if (RuleTypeEnum.SIMPLE.type.equals(rule.getRuleType())) {
            SimpleRule simpleRule = CommonUtil.transferDate(rule, SimpleRule.class);
            if (index + 1 < rules.size()) {
                simpleRule.setNext(getRule(rules, index + 1));
            }
            return simpleRule;
        } else if (RuleTypeEnum.COMPLEX.type.equals(rule.getRuleType())) {
            ComplexRule complexRule = getComplexRule(rule);
            if (index + 1 < rules.size()) {
                complexRule.setNext(getRule(rules, index + 1));
            }
            return complexRule;
        }
        return CommonUtil.transferDate(rule, SimpleRule.class);
    }

    /**
     * 获取复杂规则
     *
     * @param rule : rule
     * @return com.aurora.risk.rule.ComplexRule
     * @author Nick
     * @date 2022/04/01
    */
    private static ComplexRule getComplexRule(RiskRule rule) {
        RuleInitializer<ComplexRule> complexRuleInitializer = new ComplexRuleInitializer();
        return complexRuleInitializer.initializer(rule);
    }


}
