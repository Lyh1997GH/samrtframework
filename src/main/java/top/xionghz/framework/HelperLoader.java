package top.xionghz.framework;

import top.xionghz.framework.helper.*;
import top.xionghz.framework.util.ClassUtil;

/**
 * 加载相应的 Helper 类
 * @author bj
 * @version  1.0
 */
public final class HelperLoader {
    /*
        集中加载
     */
    public static void init(){
        Class<?>[] clsArr=
                { BeanHelper.class
                , ClassHelper.class
                , ConfigHelper.class
                , ControllerHelper.class
                , IocHelper.class
                , AopHelper.class};
        for (Class<?> cls : clsArr) {
            ClassUtil.loadClass(cls.getName());
        }
    }
    
}
