package org.geekbang.u.course.homework.c01jvm;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * 第一周作业:
 * <p>
 * 2.（必做）自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。
 * </p>
 * Created by Jays23 on 2021/5/1.
 *
 * @author Jays
 * @date 2021/5/1
 */
public class XlassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        // 相关参数
        String className = "Hello";
        String methodName = "hello";

        if (args != null && args.length == 2) {
            className = args[0];
            methodName = args[1];
        }

        // 创建类加载器
        ClassLoader classLoader = new XlassLoader();
        // 加载相应的类
        Class<?> clazz = classLoader.loadClass(className);

        // 看看里面有些什么
        System.out.println("package: " + clazz.getPackage());
        System.out.println("name: " + clazz.getName());
        for (Method m : clazz.getDeclaredMethods()) {
            System.out.println("method: " + clazz.getSimpleName() + "." + m.getName());
        }
        // 创建对象
        Object instance = clazz.getDeclaredConstructor().newInstance();
        // 调用实例方法
        Method method = clazz.getMethod(methodName);
        method.invoke(instance);
    }

    /**
     * Finds the class with the specified <a href="#name">binary name</a>.
     * This method should be overridden by class loader implementations that
     * follow the delegation model for loading classes, and will be invoked by
     * the {@link #loadClass <tt>loadClass</tt>} method after checking the
     * parent class loader for the requested class.  The default implementation
     * throws a <tt>ClassNotFoundException</tt>.
     *
     * @param name The <a href="#name">binary name</a> of the class
     * @return The resulting <tt>Class</tt> object
     * @throws ClassNotFoundException If the class could not be found
     * @since 1.2
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 如果支持包名, 则需要进行路径转换
        String resourcePath = name.replace(".", "/");
        // 文件后缀
        final String suffix = ".xlass";
        // 获取输入流
        InputStream xlassInput = this.getClass().getClassLoader().getResourceAsStream(resourcePath + suffix);
        try {
            // 读取数据
            assert xlassInput != null;
            int length = xlassInput.available();
            byte[] byteArray = new byte[length];
            xlassInput.read(byteArray);
            // 转换
            byte[] classBytes = decode(byteArray);
            // 通知底层定义这个类
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        } finally {
            closeQuietly(xlassInput);
        }
    }

    /**
     * 解码
     *
     * @param decBytes 所有字节（x=255-x）
     * @return 解码后的byte数组
     */
    private static byte[] decode(byte[] decBytes) {
        byte[] encBytes = new byte[decBytes.length];
        for (int i = 0; i < decBytes.length; i++) {
            encBytes[i] = (byte) (255 - decBytes[i]);
        }
        return encBytes;
    }

    /**
     * 静默关闭
     *
     * @param res
     */
    private static void closeQuietly(Closeable res) {
        if (null != res) {
            try {
                res.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
