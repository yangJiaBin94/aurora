package com.aurora.risk.strategy;

import com.aurora.risk.base.model.RiskRule;
import com.aurora.risk.base.model.RiskStrategy;
import com.aurora.risk.rule.AbstractRule;
import com.aurora.risk.rule.RuleFactory;
import com.aurora.risk.service.RuleService;
import com.aurora.risk.util.BeanUtil;
import lombok.Data;

import java.util.List;

/**
 * @author: Nick
 * @create: 2022-03-28 10:00
 **/
@Data
public class StrategyLink {

    /**
     * 策略id
     */
    private Long strategyId;

    /**
     * 策略名称
     */
    private String strategyName;

    /**
     * 策略唯一编号
     */
    private String strategyNo;

    /**
     * 策略类型：result决策，score评分
     */
    private String strategyType;

    /**
     * 策略版本号
     */
    private Integer strategyVersion;

    /**
     * 规则集
     */
    private AbstractRule ruleLink;

    /**
     * 下一个节点策略
     */
    private StrategyLink next;

    /**
     * 策略结果
     */
    private String result;

    /**
     * 耗时
     */
    private Long useTime;

    /**
     * 有参构造
     *
     * @param riskStrategyList : 策略集
     * @param index            : 索引
     * @author Nick
     * @date 2022/03/29
     */
    public StrategyLink(List<RiskStrategy> riskStrategyList, int index) {
        RiskStrategy riskStrategy = riskStrategyList.get(index);
        this.strategyId = riskStrategy.getId();
        this.strategyName = riskStrategy.getStrategyName();
        this.strategyNo = riskStrategy.getStrategyNo();
        this.strategyType = riskStrategy.getStrategyType();
        initRuleList();
        if (index + 1 < riskStrategyList.size()) {
            this.next = new StrategyLink(riskStrategyList, index + 1);
        }
    }

    /**
     * 初始化当前策略下规则集
     */
    void initRuleList() {
        RuleService ruleService = BeanUtil.getBean(RuleService.class);
        List<RiskRule> rules = ruleService.getRuleListByStrategyNo(this.strategyNo);
        this.ruleLink = RuleFactory.getRule(rules, 0);
        addStrategyNoToRuleLink(this.ruleLink);
    }

    /**
     * 设置规则所属策略编号
     *
     * @param ruleLink : ruleLink
     * @return void
     * @author Nick
     * @date 2022/04/06
     */
    void addStrategyNoToRuleLink(AbstractRule ruleLink) {
        if (ruleLink != null) {
            ruleLink.setStrategyNo(this.getStrategyNo());
            addStrategyNoToRuleLink(ruleLink.getNext());
        }
    }


}
