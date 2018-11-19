package top.xionghz.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * ：实例化对象
 * @author Xionghz
 * @since 1.0.0
 */
public final class ReflectionUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     * @param cls
     * @return
     */
    public static Object newInstance(Class<?> cls){
        Object instance;
        try {
            instance=cls.newInstance();
        }catch (Exception ex){
            LOGGER.error("new instance failure",ex);
            throw new RuntimeException(ex);
        }
        return instance;
    }

    /**
     * 创建实例（根据类名）
     */
    public static Object newInstance(String className) {
        Class<?> cls = ClassUtil.loadClass(className);
        return newInstance(cls);
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            LOGGER.error("invoke method failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }
    /**
     * 设置成员变量的值
     * @param obj
     * @param field
     * @param value
     */
    public static void setField(Object obj, Field field, Object value){
        try {
            field.setAccessible(true);
            field.set(obj, value);
        }catch (Exception  ex){
            LOGGER.error("set field failure",ex);
            throw new RuntimeException(ex);
        }
    }
}
