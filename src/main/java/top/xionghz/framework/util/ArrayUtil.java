package top.xionghz.framework.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 * @author Xionghz
 */
public class ArrayUtil {

    public static boolean isNotEmpty(Object[] array){

        return !ArrayUtils.isEmpty(array);
    }

    public static boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }
}
