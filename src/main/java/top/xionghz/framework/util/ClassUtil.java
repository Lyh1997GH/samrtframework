package top.xionghz.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类加载器
 * @author bj
 * @version 1.0
 */
public final class ClassUtil {

    private static final Logger LOGGER= LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     * @return
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类（默认将初始化类）
     */
    public static Class<?> loadClass(String className){
        return loadClass(className, true);
    }

    /**
     * 加载类
     *  需要提供类名与 是否初始化的标志
     * @param className
     * @param isInit 初始化：是否执行类的静态代码块
     * @return
     */
    public static Class<?> loadClass(String className,boolean isInit){
        Class<?> cls;
        try {
            cls=Class.forName(className,isInit,getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure",e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 获取指定包名下的所有类
     * 根据包名转换路径，找到.class和jar
     * @param packName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packName){
        Set<Class<?>> classSet=new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls =getClassLoader().getResources(packName.replace(".","/" ));
            while (urls.hasMoreElements()) {
                URL url=urls.nextElement();
                if (url != null) {
                    //URL的协议,它的值: HTTP、HTTPS、FTP 和 File
                   String protocol= url.getProtocol();
                    if (protocol.equals("file")) {
                        //如若路径包含空格，java会将它转换为"%20",所以需要替换掉
                        String packagePath= url.getPath().replaceAll("%20","");
                        AddClass(classSet,packagePath,packName);
                    } else if (protocol.equals("jar")) {
                        JarURLConnection connection= (JarURLConnection) url.openConnection();
                        if (connection!=null) {
                            JarFile jarFile=connection.getJarFile();
                            if (jarFile!=null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry=jarEntries.nextElement();
                                    String jarEntryName=jarEntry.getName();
                                    if (jarEntryName.equals(".class")) {
                                        String className=jarEntryName.substring(0,jarEntryName.lastIndexOf("."))
                                                .replace("/",".");
                                        doAddClass(classSet,className);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }catch (Exception ex){
            LOGGER.error("Get class set failure",ex);
            throw new RuntimeException(ex);
        }
        return classSet;
    }

    /**
     * 加载类，并添加
     * @param classSet
     * @param className
     */
    public static void doAddClass(Set<Class<?>> classSet,String className){
        Class<?> cls=loadClass(className,false);
        classSet.add(cls);
    }

    public static void AddClass(Set<Class<?>> classSet,String packagePath,String packageName){
        File[] files=new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                //是否是一个java标准文件，或者 是一个文件夹
                return (file.isFile() && file.getName().endsWith(".class") || file.isDirectory());
            }
        });
        for (File file : files) {
            String fileName =file.getName();
            if (file.isFile()) {
                String className=packageName.substring(0,fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)) {
                    className=packagePath+"."+className;
                }
                doAddClass(classSet, className);
            }else {
                String subPackagePath=fileName;
                if (StringUtil.isNotEmpty(packagePath)) {
                    subPackagePath=packagePath+"/"+subPackagePath;
                }
                String subPackageName =fileName;
                if (StringUtil.isNotEmpty(packageName)){
                    subPackageName=packageName+"."+subPackageName;
                }
                AddClass(classSet,subPackagePath,subPackageName);
            }

        }

    }






}
