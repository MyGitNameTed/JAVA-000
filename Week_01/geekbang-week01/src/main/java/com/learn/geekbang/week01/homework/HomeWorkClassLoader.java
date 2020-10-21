package com.learn.geekbang.week01.homework;

import java.io.*;
import java.lang.reflect.Method;

public class HomeWorkClassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            Object object = new HomeWorkClassLoader().findClass("Hello").newInstance();
            Method method = object.getClass().getMethod("hello");
            method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("src/main/java/com/learn/geekbang/week01/homework/Hello.xlass");
        byte[] bytes = getContent(file.getPath());
        byte[] bytesEdit = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            bytesEdit[i] = (byte) (255 - bytes[i]);
        }
        return defineClass(name, bytesEdit, 0, bytes.length);
    }

    public static byte[] getContent(String filePath) {
        File file = new File(filePath);
        //
        byte[] outputBytes = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayInputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byteArrayInputStream = new ByteArrayOutputStream();
            int limit = 0;
            byte[] buffer = new byte[1024];
            while ((limit = inputStream.read(buffer)) != -1) {
                byteArrayInputStream.write(buffer, 0, limit);
            }
            byteArrayInputStream.flush();
            outputBytes = byteArrayInputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != byteArrayInputStream) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return outputBytes;
    }
}
