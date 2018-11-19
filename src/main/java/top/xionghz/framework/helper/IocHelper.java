package top.xionghz.framework.helper;

import top.xionghz.framework.annotation.Inject;
import top.xionghz.framework.util.ArrayUtil;
import top.xionghz.framework.util.CollectionUtil;
import top.xionghz.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 * @author Xionghz
 */
public final class IocHelper {
    static {
        //获取所有的 bean 类与 bean 实例之间的映射关系
        Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)){
            //遍历beanMap
            for(Map.Entry<Class<?>,Object> beanEntry:beanMap.entrySet()){
                Class<?> beanClass= beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //获取类中所有的属性(public、protected、default、private),但不包括继承的属性
                Field[] beanFields= beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    //遍历 Field[]
                    for (Field beanField : beanFields) {
                        //判断当前 Bean Field 是否带有 Inject(注解)
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            //在 BeanMap 中获取 BeanField 对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                //通过反射 初始化 BeanField 的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }

            }
        }
    }
}
