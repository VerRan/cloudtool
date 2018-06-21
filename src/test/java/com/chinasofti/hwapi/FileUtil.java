package com.chinasofti.hwapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by VerRan.Liu on 2018/6/7.
 */
public class FileUtil {
    public static String getStringFromFile(String pathName) {
        File file02 = new File(pathName);
        FileInputStream is = null;
        StringBuilder stringBuilder = null;
        try {
            if (file02.length() != 0) {//文件有内容才去读文件
                is = new FileInputStream(file02);
                InputStreamReader streamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(streamReader);
                String line;
                stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(stringBuilder);
    }
}
