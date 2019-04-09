package top.xionghz.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.xionghz.framework.annotation.Aspect;
import top.xionghz.framework.annotation.Service;
import top.xionghz.framework.proxy.AspectProxy;
import top.xionghz.framework.proxy.Proxy;
import top.xionghz.framework.proxy.ProxyManager;
import top.xionghz.framework.proxy.TransactionProxy;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 方法拦截助手类
 * @author bj
 * @version 1.0
 */
public final class AopHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
//          代理类与目标类集合的映射关系
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
//          获取目标类与代对象列表之间的映射关系
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()) {
                //目标类
                Class<?> key = entry.getKey();
//                代理对象列表
                List<Proxy> value = entry.getValue();
//                获取代理对象
                Object proxy = ProxyManager.createProxy(key, value);
//                将代理对象重新放入 BeanMap
                BeanHelper.setBean(key, proxy);
            }
        } catch (Exception e) {
            LOGGER.error("aop failure", e);
        }
    }

    /**
     * 代理类与目标类集合的映射关系
     * @return Map<Class<?>, List<Proxy>>
     * @throws Exception
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        //获取带有 Aspect 注解的类
        addAspectProxy(proxyMap);
        //获取带有 TransactionProxy
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    /**
     * 获取代理类 和 目标类集合 之间的映射关系
     * 一个代理类可以有多个目标类
     * @param proxyMap
     * @throws Exception
     */
    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> cls : proxyClassSet) {
            if (cls.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = cls.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(cls, targetClassSet);
            }

        }

    }

    /**
     * 获取带有 Aspect 注解的类
     * @param aspect
     * @return Set<Class<?>>
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        //获取注解的 value 的值
        Class<? extends Annotation> value = aspect.value();
        if (value != null && !value.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(value));
        }
        return targetClassSet;
    }

    /**
     * 添加事务代理
     * @param proxyMap
     */
    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class, serviceClassSet);
    }

    /**
     * 获取目标类于代理对象列表之间的映射关系
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            //代理类
            Class<?> proxyClass = proxyEntry.getKey();
            //目标类集合
            Set<Class<?>> proxyValue = proxyEntry.getValue();
            for (Class<?> targetClass : proxyValue) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                }else {
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }

        }
        return targetMap;

    }
}