package top.xionghz.framework.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSON 工具类
 * @author bj
 * @version  1.0
 */
public final class JsonUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();

    /**
     * 将 POJO 转换为 JSON
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj){
        String jsonStr;
        try {
            jsonStr=OBJECT_MAPPER.writeValueAsString(obj);
        }catch (Exception e){
            LOGGER.error("Convert POJO to Json failure!"+e);
            throw new RuntimeException(e);
        }
        return jsonStr;
    }

    /**
     * 将 JSON 转换为 POJO
     * @param jsonStr
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T toPOJO(String jsonStr,Class<T> cls){
        T pojo;
        try {
            pojo=OBJECT_MAPPER.readValue(jsonStr, cls);
        }catch (Exception e){
            LOGGER.error("Convert Json to POJO failure!"+e);
            throw new RuntimeException(e);
        }
        return pojo;
    }

}
