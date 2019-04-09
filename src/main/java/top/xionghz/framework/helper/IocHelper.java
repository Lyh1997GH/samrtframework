package top.xionghz.framework.helper;

import top.xionghz.framework.annotation.Inject;
import top.xionghz.framework.util.ArrayUtil;
import top.xionghz.framework.util.CollectionUtil;
import top.xionghz.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

/**
 * 依赖注入
 * @author bj
 * @version 1.0
 */
public final class IocHelper {

    static {
        //先获取所有的 bean 映射 map
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            //遍历，获取类、类的实例
            for (Map.Entry<Class<?>, Object> objectEntry : beanMap.entrySet()) {
                Class<?> cls=objectEntry.getKey();
                Object obj = objectEntry.getValue();
                //获取类中所有的属性(public、protected、default、private),但不包括继承的属性
                Field[] fields = cls.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(fields)) {
                    for (Field field : fields) {
                        //判断成员变量是否带有 inject 注解
                        if (field.isAnnotationPresent(Inject.class)) {
                            //获取成员的类
                            Class<?> type = field.getType();
                            //根据成员的类获取该类的实例
                            Object beanFieldInstance = beanMap.get(type);
                            if (beanFieldInstance != null) {
                                //赋值
                                ReflectionUtil.SetField(obj, field, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
