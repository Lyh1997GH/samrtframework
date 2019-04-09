package top.xionghz.framework.bean;

import top.xionghz.framework.util.CastUtil;
import top.xionghz.framework.util.CollectionUtil;

import java.util.Map;

/**
 * 请求参数对象
 * @author bj
 * @version 1.0
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }


    /**
     * 根据参数名获取 long 型参数
     * @param name
     * @return long
     */
    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * 获取所有字段信息
     * @return Map<String, Object>
     */
    public Map<String, Object> getMap() {
        return paramMap;
    }

    /**
     * 验证参数是否为空
     * @return
     */
    public Boolean isEmpty(){
       return CollectionUtil.isEmpty(paramMap);
    }
}
