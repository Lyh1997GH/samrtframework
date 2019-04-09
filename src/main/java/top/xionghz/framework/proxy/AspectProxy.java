package top.xionghz.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 * @author bj
 * @version 1.0
 */
public abstract class AspectProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain chain) throws Throwable{
        Object result;
        Class<?> targetClass = chain.getTargetClass();
        Method targetMethod = chain.getTargetMethod();
        Object[] methodParams = chain.getMethodParams();

        begin();
        try {
            if (intercept(targetClass, targetMethod, methodParams)){
                before(targetClass, targetMethod, methodParams);
                result=chain.doProxyChain();
                after(targetClass, targetMethod, methodParams,result);
            }else {
                result = chain.doProxyChain();
            }

        }catch (Exception e){
            logger.error("proxy failure", e);
            error(targetClass, targetMethod, methodParams, e);
            throw e;
        }finally {
            end();
        }
        return result;
    }

    public void begin() {
    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable e) {
    }

    public void end() {
    }
}
