package cn.laiyuejia.simpleframework.aop;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;

/**
 * 解析表达式，织入
 */
public class PointcutLocator {
    private PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(
            PointcutParser.getAllSupportedPointcutPrimitives()
    );

    private PointcutExpression pointcutExpression;

    public PointcutLocator(String expression){
        this.pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    public boolean roughMatches(Class<?> targetClass){
        return pointcutExpression.couldMatchJoinPointsInType(targetClass);
    }

    public boolean accurateMatches(Method method){
        ShadowMatch shadowMatch = pointcutExpression.matchesMethodExecution(method);
        return shadowMatch.alwaysMatches()==true?true:false;
    }
}
