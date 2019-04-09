package top.xionghz.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public final class StringUtil {
    /**
     * 判断字符串是否为空
     * @param str
     * @return boolean
     */
    public static boolean isEmpty(String str){
        if (str!=null){
            str=str.trim();
        }
        return StringUtils.isEmpty(str);
    }
    /**
     * 判断字符串是否为非空
     * @param str
     * @return boolean
     */
    public  static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * 分割固定格式的字符串
     * @param str
     * @param separator
     * @return String[]
     */
    public static String[] splitString(String str,String separator){

        return StringUtils.splitByWholeSeparator(str, separator);
    }
}
