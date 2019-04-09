package top.xionghz.framework;

import top.xionghz.framework.bean.Data;
import top.xionghz.framework.bean.Handler;
import top.xionghz.framework.bean.Param;
import top.xionghz.framework.bean.View;
import top.xionghz.framework.helper.BeanHelper;
import top.xionghz.framework.helper.ConfigHelper;
import top.xionghz.framework.helper.ControllerHelper;
import top.xionghz.framework.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 * @author bj
 * @version 1.0
 */

/*
    loadOnStartup = 0
    当值为 >=0 时，表示容器在应用启动时就加载这个servlet；
    当是一个负数时或者没有指定时，则指示容器在该servlet被选择时才加载。
    正数的值越小，启动该servlet的优先级越高。
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关的 Helper 类
        HelperLoader.init();
        //获取 ServletContext 对象(用于注册 Servlet)
        ServletContext context = config.getServletContext();
        //注册处理 JSP 的 Servlet
        ServletRegistration servletRegistration = context.getServletRegistration("jsp");
        servletRegistration.addMapping(ConfigHelper.getAppJspPath()+"*");
        //注册处理静态资源的默认 Servlet
        ServletRegistration defaultRegistration = context.getServletRegistration("default");
        defaultRegistration.addMapping(ConfigHelper.getAppAssetPath()+"*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求方法与请求路径
        String requestMethod=req.getMethod().toLowerCase();
        String requestPath=req.getPathInfo();
        //获取 Action 处理器
        Handler handler= ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler!=null) {
            //获取 Controller 类及其 Bean 实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            Map<String,Object> paramMap = new HashMap<String, Object>();
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();//参数名称
                String paramValue = req.getParameter(paramName);//参数值
                paramMap.put(paramName, paramValue);
            }
            String body= CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if (StringUtil.isNotEmpty(body)) {
                String[] params = StringUtil.splitString(body, "&");
                if (ArrayUtil.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = StringUtil.splitString(param, "=");
                        if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                            paramMap.put(array[0], array[1]);
                        }
                    }
                }
            }
            Param param= new Param(paramMap);
            Object result;
            //调用 Action 方法
            Method method = handler.getActionMethod();
            if (param.isEmpty()) {
                result = ReflectionUtil.invokeMethod(controllerBean, method);
            }else {
                result = ReflectionUtil.invokeMethod(controllerBean, method, param);
            }

            //处理 Action 方法返回值
            if (result instanceof View) {
                //返回 JSP 页面
                View view=(View)result;
                String path = view.getPath();
                if (StringUtil.isNotEmpty(path)) {
                    if (path.startsWith("/")) {
                        resp.sendRedirect(req.getContextPath()+path);
                    }else {
                        Map<String, Object> viewModel = view.getModel();
                        for (Map.Entry<String, Object> entry : viewModel.entrySet()) {
                            req.setAttribute(entry.getKey(), entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(req, resp);
                    }
                }

            } else if (result instanceof Data) {
                //返回 JSON 数据
                Data data=(Data)result;
                Object model = data.getModel();
                if (model!=null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer=resp.getWriter();
                    String jsonStr = JsonUtil.toJson(model);
                    writer.print(jsonStr);
                    writer.flush();
                    writer.close();
                }
            }

        }
    }
}