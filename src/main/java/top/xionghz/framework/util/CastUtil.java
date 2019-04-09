package top.xionghz.framework.util;

/**
 * 转型操作工具类
 * @author bj
 * @version 1.0
 */
public final class CastUtil {
    /**
     * 转为String类型(提供默认值为空)
     * @param obj
     * @return
     */
    public static String castString(Object obj){
        return castString( obj, "");
    }
    public static String castString(Object obj,String defaultValue){

        return obj != null? String.valueOf(obj):defaultValue;
    }

    /**
     * 转为Double类型(提供默认值为0)
     * @param obj
     * @return
     */
    public static double castDouble(Object obj){
        return castDouble(obj,0);
    }
    public static double castDouble(Object obj,double defaultValue){
        double doubleValue = defaultValue;
        if (obj!=null){
            String strValue= castString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try{
                    doubleValue=Double.parseDouble(strValue);
                }catch (NumberFormatException ex){
                    doubleValue=defaultValue;
                }
            }
        }
        return doubleValue;
    }
    /**
     * 转为Long类型(提供默认值为0)
     * @param obj
     * @return
     */
    public static long castLong(Object obj){
        return castLong(obj,0);
    }
    public static long castLong(Object obj,long defaultValue){
        long longValue=defaultValue;
        if (obj!=null){
            String strValue=castString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try{
                    longValue=Long.parseLong(strValue);
                }catch (NumberFormatException ex){
                    longValue=defaultValue;
                }
            }
        }
        return longValue;
    }
    /**
     * 转为int类型(提供默认值为0)
     * @param obj
     * @return
     */
    public static int castInt(Object obj){
        return castInt(obj,0);
    }
    public static int castInt(Object obj,int defaultValue){
        int intValue=defaultValue;
        if (obj!=null){
            String strValue=castString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try {
                    intValue = Integer.parseInt(strValue);
                }catch (NumberFormatException ex){
                    intValue=defaultValue;
                }
            }
        }
        return intValue;
    }
    /**
     * 转为 boolean 类型(提供默认值为false)
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj){
        return castBoolean(obj,false);
    }
    public static boolean castBoolean(Object obj,boolean defaultValue){
        boolean booValue=defaultValue;
        if (obj!=null){
            String strValue=castString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try {
                    booValue = Boolean.parseBoolean(strValue);
                }catch (NumberFormatException ex){
                    booValue=defaultValue;
                }
            }
        }
        return booValue;
    }
}
