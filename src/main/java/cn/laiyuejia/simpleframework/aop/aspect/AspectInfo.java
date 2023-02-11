package cn.laiyuejia.simpleframework.aop.aspect;

import cn.laiyuejia.simpleframework.aop.PointcutLocator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AspectInfo {

    private int orderIndex;

    private DefaultAspect defaultAspect;

    private PointcutLocator pointcutLocator;

}
