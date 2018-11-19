package top.xionghz.framework;

import top.xionghz.framework.helper.*;
import top.xionghz.framework.util.ClassUtil;

/**
 * 加载相应的 Helper 类
 * @author bj
 * @since 1.0.0
 */
public final class HelperLoader {

    public static void init(){
        Class<?> [] classes ={
                ClassHelper.class,
                BeanHelper.class,
                ControllerHelper.class,
                IocHelper.class,
                AopHelper.class

        };
        for (Class<?> cls:classes) {
            //为了提高加载类的性能，isInitialized 可设为 false
            ClassUtil.loadClass(cls.getName(),true);
        }
    }
}
