package top.xionghz.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流操作工具类
 * @author bj
 * @since 1.0.0
 */
public final class StreamUtil {

    private static final Logger LOGGER= LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流中获取字符串
     */
    public static String getString(InputStream is){
        StringBuilder sb=new StringBuilder();
        try{
            BufferedReader reader= new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line=reader.readLine()) != null){
                sb.append(line);
            }
        }catch (Exception ex){
            LOGGER.error("get String failure!"+ex);
            throw new RuntimeException(ex);
        }
        return sb.toString();
    }
}
