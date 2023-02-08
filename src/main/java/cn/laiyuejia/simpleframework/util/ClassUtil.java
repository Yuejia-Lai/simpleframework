package cn.laiyuejia.simpleframework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ClassUtil {

    /**
     * 获取包下所有类集合
     * @param packageName
     * @return
     */
    public static Set<Class<?>> extractPackageClass(String packageName){
        //获取类加载器
        ClassLoader classLoader = getClassLoader();
        //获取加载的资源
        URL url = classLoader.getResource(packageName.replace('.', '/'));
        if(url==null){
            log.warn("包中找不到任何类！");
            return null;
        }
        Set<Class<?>> classSet = null;
        if(url.getProtocol().equalsIgnoreCase("file")){
            //协议是"file"，做进一步处理
            classSet = new HashSet<>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet,packageDirectory,packageName);
        }
        return classSet;
    }

    /**
     * 递归获取路径及其子路径下所有class文件
     * @param classSet
     * @param fileSource
     * @param packageName
     */
    private static void extractClassFile(Set<Class<?>> classSet, File fileSource, String packageName) {
        if(!fileSource.isDirectory()){
            return;
        }
        //获取当前目录下所有文件和文件夹（不包括子文件夹）
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if(file.isDirectory()) return true;
                else{
                    String fileAbsolutePath = file.getAbsolutePath();
                    if(fileAbsolutePath.endsWith(".class")){
                        addToClassSet(fileAbsolutePath);
                    }
                }
                return false;
            }

            //根据class文件绝对路径，获取生成class对象，放入classSet
            private void addToClassSet(String fileAbsolutePath) {
                //获取类的全路径
                fileAbsolutePath = fileAbsolutePath.replace(File.separator,".");
                String className = fileAbsolutePath.substring(fileAbsolutePath.indexOf(packageName));
                className  = className.substring(0,className.lastIndexOf("."));
                //通过反射机制获取对应的class对象加入classSet
                Class<?> targetClass = loadClass(className);
                classSet.add(targetClass);
            }
        });
        if(files !=null){
            for(File file: files){
                extractClassFile(classSet,file,packageName);
            }
        }
    }

    //加载对应的类
    public static Class<?> loadClass(String className){
        try {
            return  Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("类加载失败！",e);
            throw new RuntimeException(e);
        }
    }
    /**
     * 获取ClassLoader实例
     * @return
     */
    public static ClassLoader getClassLoader(){
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        return contextClassLoader;
    }

    //测试
    public static void main(String[] args) {
        extractPackageClass("cn.laiyuejia.demo.controller");
    }

    //实例化class
    public static <T> T newInstance(Class<?> clazz){
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = (T) constructor.newInstance();
            return instance;
        } catch (NoSuchMethodException e) {
            log.error("未找到无参构造方法！",e);
        } catch (Exception e){
            log.error("创建对象实例错误！",e);
        }
        return null;
    }

    public static void setField(Field field,Object target,Object value){
        field.setAccessible(true);
        try {
            field.set(target,value);
        } catch (IllegalAccessException e) {
            log.error("设置属性值失败",e);
            throw new RuntimeException(e);
        }
    }
}
