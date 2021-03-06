package top.xionghz.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回视图对象
 * @author bj
 * @since 1.0.0
 */
public class View {
    /**
     * 视图路径
     */
    private String path;
    /**
     * 模型数据
     */
    private Map<String,Object> model;

    public View(String path) {
        this.path = path;
        model= new HashMap<String, Object>();
    }
    public View addModel(String Key,Object value){
        model.put(Key, value);
        return this;
    }
    public  String getPath (){
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
