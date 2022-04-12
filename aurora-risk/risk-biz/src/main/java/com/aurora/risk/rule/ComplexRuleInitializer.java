package com.aurora.risk.rule;

import com.aurora.common.util.CommonUtil;
import com.aurora.risk.base.model.RiskRule;
import com.aurora.risk.service.RuleService;
import com.aurora.risk.util.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 复杂规则初始化器
 *
 * @author: Nick
 * @create: 2022-03-28 15:05
 **/
@Component
public class ComplexRuleInitializer implements RuleInitializer<ComplexRule> {

    @Override
    public ComplexRule initializer(RiskRule rule) {
        RuleService ruleService = BeanUtil.getBean(RuleService.class);
        ComplexRule complexRule = CommonUtil.transferDate(rule, ComplexRule.class);
        List<RiskRule> simpleRuleList = ruleService.getSimpleRuleListByComplexRuleId(complexRule.getId());
        if (CollectionUtils.isNotEmpty(simpleRuleList)) {
            complexRule.setSimpleRuleLink((SimpleRule) RuleFactory.getRule(simpleRuleList, 0));
            complexRule.setSubRulePolicyType(simpleRuleList.get(0).getPolicyType());
        }
        return complexRule;

    }
}
