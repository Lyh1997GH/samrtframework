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
 * 类操作工具类
 * @author Xionghz
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
     * 加载类
     * 需要提供类名与 是否初始化的标志，
     * --初始化：是否执行类的静态代码块
     * @param className
     * @param isInitialized
     * @return
     */
    public static Class<?> loadClass(String className,boolean isInitialized){
        Class<?> cls;
        try {
            cls=Class.forName(className,isInitialized,getClassLoader());
        }catch (ClassNotFoundException ex){
            LOGGER.error("load class failure",ex);
            throw new RuntimeException(ex);
        }
        return cls;
    }
    /**
     * 加载类（默认将初始化类）
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    /**
     * 获取指定包名下的所有类
     * 根据包名转换路径，找到.class和jar
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classSet= new HashSet<Class<?>>();
        try{
            Enumeration<URL> urls=getClassLoader().getResources(packageName.replace(".","/"));
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                if (url!=null){
                    String protocol =url.getProtocol();
                    if (protocol.equals("file")){
                        String packagePath=url.getPath().replaceAll("%20","");
                        addClass(classSet,packagePath,packageName);
                    }else if (protocol.equals("jar")){
                        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                        if (jarURLConnection!=null){
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile!=null){
                                Enumeration<JarEntry> jarEntrys = jarFile.entries();
                                while (jarEntrys.hasMoreElements()){
                                    JarEntry jarEntry = jarEntrys.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.equals(".class")){
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
            LOGGER.error("get class set failure",ex);
            throw new RuntimeException(ex);
        }
        return classSet;
    }

    /**
     * isFile()
     * 测试此抽象路径名表示的文件是否是一个标准文.如果该文件不是一个目录,并且满足其他与系统有关的标准,那么该文件是标准文件.由Java应用程序创建的所有非目录文件一定是标准文件.
     * 返回:当且仅当此抽象路径名表示的文件存在且是一个标准文件时,返回true;否则返回false;
     * @param classSet
     * @param packagePath
     * @param packageName
     */
    public static void addClass(Set<Class<?>> classSet,String packagePath,String packageName){
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();//isDirectory()是检查一个对象是否是文件夹
            }
        });
        for (File file:files) {
            String fileName= file.getName();
            if (file.isFile()){
                String className=packageName.substring(0,fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)){
                    className=packagePath+"."+className;
                }
                doAddClass(classSet,className);
            }else {
                String subPackagePath=fileName;
                if(StringUtil.isNotEmpty(packagePath)){
                    subPackagePath=packagePath+"/"+subPackagePath;
                }
                String subPackageName =fileName;
                if (StringUtil.isNotEmpty(packageName)){
                    subPackageName=packageName+"."+subPackageName;
                }
                addClass(classSet,subPackagePath,subPackageName);
            }
        }
    }

    public static void doAddClass(Set<Class<?>> classSet,String className){
        Class<?> cls=loadClass(className,false);
        classSet.add(cls);
    }
}
