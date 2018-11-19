package top.xionghz.framework.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.xionghz.framework.annotation.Aspect;
import top.xionghz.framework.annotation.Controller;
import top.xionghz.framework.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * 拦截 Controller 方法
 * @author bj
 * @since 1.0.0
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    private Long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOGGER.debug("-------------------- begin --------------------");
        LOGGER.debug(String.format("class: %s", cls.getName()));
        LOGGER.debug(String.format("method: %s",method.getName()));
        begin=System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        LOGGER.debug(String.format("time: %dms", System.currentTimeMillis()-begin));
        LOGGER.debug("-------------------- end --------------------");
    }


}
