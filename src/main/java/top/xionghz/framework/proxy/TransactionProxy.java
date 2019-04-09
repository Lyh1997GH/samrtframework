package top.xionghz.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.xionghz.framework.annotation.Transaction;
import top.xionghz.framework.helper.DatabaseHelper;

import java.lang.reflect.Method;

/**
 * 事务代理
 * @author bj
 * @version 1.0
 */
public class TransactionProxy implements Proxy{
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);
    //保证同一线程中事物控制相关逻辑只会执行一次
    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain chain) throws Throwable {
        Object result;
        Boolean flag = FLAG_HOLDER.get();
        //获取目标方法
        Method targetMethod = chain.getTargetMethod();
//        代理方法是否有 Transaction 注解
        if (!flag && targetMethod.isAnnotationPresent(Transaction.class)) {
            try {
                FLAG_HOLDER.set(true);
                DatabaseHelper.beginTransaction();
                LOGGER.debug("begin transaction");
                result = chain.doProxyChain();
                DatabaseHelper.commitTransaction();
                LOGGER.debug("commit transaction");
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                LOGGER.debug("rollback transaction");
                throw e;
            }finally {
                FLAG_HOLDER.remove();
            }
        }else {
            result = chain.doProxyChain();
        }
        return result;
    }

}
