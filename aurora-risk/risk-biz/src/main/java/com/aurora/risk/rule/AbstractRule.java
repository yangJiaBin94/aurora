package com.aurora.risk.rule;

import com.aurora.risk.base.model.RiskRule;
import com.aurora.risk.constant.LogPrefixConstant;
import com.aurora.risk.context.RiskApplicationContext;
import com.aurora.risk.context.RiskContextInfo;
import com.aurora.risk.enums.RuleExecuteStatusEnum;
import com.aurora.risk.enums.RuleHitResultEnum;
import com.aurora.risk.util.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Nick
 * @create: 2022-03-28 10:07
 **/
@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractRule extends RiskRule implements RuleInterface {

    private static final String LOG_PREFIX = LogPrefixConstant.RISK_RULE + "[AbstractRule] - ";

    /**
     * 指标值
     */
    private String value;

    /**
     * 所属策略编号
     */
    private String strategyNo;

    /**
     * 是否命中
     */
    private Boolean isHit;

    /**
     * 规则最终结果
     */
    private String result;

    /**
     * 耗时
     */
    private Long useTime;

    /**
     * 执行状态
     */
    private RuleExecuteStatusEnum executeStatus = RuleExecuteStatusEnum.UN_EXECUTED;

    /**
     * 下一个节点规则
     */
    private AbstractRule next;

    @Override
    public void execute(RiskContextInfo riskContextInfo) {
        //只有规则执行状态为UN_EXECUTED 的规则才进入执行逻辑
        if (RuleExecuteStatusEnum.UN_EXECUTED.equals(this.getExecuteStatus())) {
            try {
                verify();
                getIndexValue(riskContextInfo);
                //当前指标执行状态变更为GETTING时
                if (RuleExecuteStatusEnum.GETTING.equals(this.getExecuteStatus())) {
                    //暂存riskContextInfo 上下文 等待回调继续执行
                    RiskApplicationContext riskApplicationContext = BeanUtil.getBean(RiskApplicationContext.class);
                    riskApplicationContext.saveRiskContextInfoIntoRedis(riskContextInfo);
                    return;
                }
                getRuleResult();
                //如果规则结果为复审则不继续执行直接返回
                if (RuleHitResultEnum.REVIEW.hitStatus.equals(this.getResult())) {
                    return;
                }
            } catch (Exception e) {
                log.warn(LOG_PREFIX + e.getMessage() + "当前规则设为默认结果");
                this.setResult(this.getRuleDefultResult());
                this.setExecuteStatus(RuleExecuteStatusEnum.DONE);
            }
            //执行下一个节点
            if (this.next != null) {
                this.next.execute(riskContextInfo);
            }
        }
    }

    /**
     * 校验
     *
     * @return void
     * @author Nick
     * @date 2022/03/28
     */
    abstract void verify();


    /**
     * 获取指标值
     *
     * @param riskContextInfo : riskContextInfo
     * @return void
     * @author Nick
     * @date 2022/04/02
     */
    abstract void getIndexValue(RiskContextInfo riskContextInfo);

    /**
     * 获取规则结果
     *
     * @return void
     * @author Nick
     * @date 2022/03/30
     */
    abstract void getRuleResult();


}
