package top.xionghz.framework.helper;

import top.xionghz.framework.annotation.Action;
import top.xionghz.framework.bean.Handler;
import top.xionghz.framework.bean.Request;
import top.xionghz.framework.util.ArrayUtil;
import top.xionghz.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 * @author bj
 * @version 1.0
 */
public final class ControllerHelper {

    /**
     * 用于存放请求与处理器的映射关系 (简称 Action Map)
     */
    private  static final Map<Request, Handler> ACTION_MAP=new HashMap<Request, Handler>();

    static {
        Set<Class<?>> classSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(classSet)) {
            for (Class<?> cls : classSet) {
                Method[] methods = cls.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)) {
                            //获取 action 注解的 URL 值
                            String mapping= method.getAnnotation(Action.class).value();
                            if (mapping.matches("\\w+:\\w*")){
                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length==2) {
                                    //获取请求方法和请求路径
                                    String requestMethod=array[0];
                                    String requestPath=array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(cls, method);
                                    //初始化 Action Map
                                    ACTION_MAP.put(request, handler);
                                }
                            }

                        }
                    }
                }
            }

        }

    }

    /**
     * 获取 handler
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod,String requestPath) {
        return ACTION_MAP.get(new Request(requestMethod, requestPath));
    }
}
