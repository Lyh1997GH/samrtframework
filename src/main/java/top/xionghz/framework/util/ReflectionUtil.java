package top.xionghz.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * :实例化对象
 * @author bj
 * @version 1.0
 */
public final class ReflectionUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建类的实例
     * @param cls
     * @return
     */
    public static Object newInstance(Class<?> cls){
        Object obj;
        try {
            obj= cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("New instance failure",e);
            throw new RuntimeException(e);
        }
        return obj;

    }

    /**
     * 根据类名创建类的实例
     * @param className
     * @return
     */
    public static Object newInstance(String className){
        Class<?> cls=ClassUtil.loadClass(className);
        return newInstance(cls);
    }

    /**
     * 调用方法
     * @param obj 类的实例
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod(Object obj, Method method,Object... args){
        Object result;
        try {
            result=method.invoke(obj, args);
        }catch (Exception e){
            LOGGER.error("Invoke method failure", e);
            throw new RuntimeException(e);
        }
        return result;

    }

    /**
     * 设置成员变量的值
     * @param obj
     * @param field
     * @param value 需要设置的值
     */
    public static void SetField(Object obj,Field field,Object value){
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("Set field failure",e);
            throw new RuntimeException(e);
        }
    }


}
