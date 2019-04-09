package top.xionghz.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流操作
 * @author bj
 * @version 1.0
 */
public final class StreamUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流读取字符串
     * @param is
     * @return
     */
    public static String getString(InputStream is){
        StringBuilder sb= new StringBuilder();
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line=reader.readLine()) != null) {
                sb.append(line);
            }
        }catch (Exception e){
            LOGGER.error("Get String failure!"+e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

}
