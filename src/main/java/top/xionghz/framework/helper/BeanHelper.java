package top.xionghz.framework.helper;

import top.xionghz.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean 助手类
 * @author bj
 * @version 1.0
 */
public final class BeanHelper {
    /**
     * 定义 Bean 映射 (用来存放 Bean 类与 Bean 实例的映射关系)
     */
    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        for (Class<?> cls : classSet) {
            Object instance = ReflectionUtil.newInstance(cls);
            BEAN_MAP.put(cls,instance);
        }
    }

    /**
     * 获取 Bean 映射
     * @return
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取 Bean 实例
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cls){
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("Can not get bean by class, failure: "+cls);
        }
        return (T)BEAN_MAP.get(cls);
    }

    /**
     * 设置 Bean 实例
     * @param cls
     * @param object
     */
    public static void setBean(Class<?> cls,Object object){
        BEAN_MAP.put(cls, object);
    }

}
