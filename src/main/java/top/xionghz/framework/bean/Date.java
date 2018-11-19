package top.xionghz.framework.bean;
/**
 * 返回数据对象
 * @author bj
 * @since 1.0.0
 */
public class Date {
    /**
     * 模型数据
     */
    private  Object model;

    public Date(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
