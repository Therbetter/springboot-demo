package com.schx.docadmin.utils;

import java.io.*;

/**
 * @description: 文件工具类
 * @author: xutao
 * @create: 2019-06-18 11:27
 **/
public class FileUtils {

    private  static byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    public static byte[] file2byte(String path) throws IOException{
        FileInputStream fin=new FileInputStream(path);
        return toByteArray(fin);
    }
}
