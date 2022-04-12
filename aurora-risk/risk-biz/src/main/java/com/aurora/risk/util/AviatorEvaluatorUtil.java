package com.aurora.risk.util;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

/**
 * @author: Nick
 * @create: 2022-03-10 13:59
 **/
public class AviatorEvaluatorUtil {



    static class EqualsFunction extends AbstractFunction {
        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
            String left = FunctionUtils.getStringValue(arg1, env);
            return AviatorBoolean.valueOf(left.equals(arg2.stringValue(null)));
        }
        @Override
        public String getName() {
            return "equals";
        }
    }

    public static Boolean execute(String expression, Map<String, Object> env) {
        AviatorEvaluator.addFunction(new EqualsFunction());
        try {
            return (Boolean) AviatorEvaluator.execute(expression, env);
        } catch (Exception e) {
            // ignore
        }
        return null;
    }

    public static boolean execute(String expression, boolean defaultVal, Map<String, Object> env) {
        try {
            return (boolean) AviatorEvaluator.execute(expression, env);
        } catch (Exception e) {
            // ignore
        }
        return defaultVal;
    }
}
