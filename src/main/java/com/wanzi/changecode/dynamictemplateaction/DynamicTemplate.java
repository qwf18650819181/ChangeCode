package com.wanzi.changecode.dynamictemplateaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @auth: qwf
 * @date: 2024年2月4日 0004
 * @description:
 */
public class DynamicTemplate {

    private static final String FILE_PACKAGE = "D:\\gitprogram\\ChangeCode\\src\\main\\resources\\dynamic_template" + File.separator;
    private static final String TYPE = "dynamic_template";
    private static final String FILE_SUFFIX = ".txt";
    public static final String DYNAMIC = "自定义";

    private static final List<String> DYNAMIC_TYPES = new ArrayList<>();
    static {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PACKAGE + TYPE + FILE_SUFFIX), StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            while (line != null) {
                DYNAMIC_TYPES.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getTypes() {
        ArrayList<String> types = new ArrayList<>(DYNAMIC_TYPES);
        types.add(DYNAMIC);
        return types;
    }

    /**
     * 是否存在类型
     * @param type
     * @return
     */
    public static boolean exist(String type) {
        return DYNAMIC_TYPES.contains(type);
    }

    /**
     * 删除类型
     * @param type
     */
    public static void removeType(String type) {
        if (!exist(type)) return;
        if (DYNAMIC_TYPES.size() == 1) throw new RuntimeException("不能删除最后一条记录");
        File typeFile = new File(FILE_PACKAGE + type + FILE_SUFFIX);
        if (typeFile.exists() && typeFile.delete() && DYNAMIC_TYPES.remove(type)) {
            writeType();
        }
    }

    /**
     * 增加类型 包括名字,内容
     * @param type
     * @param content
     */
    public static void addType(String type, String content) {
        if (exist(type)) return;
        if (write(type, content)) {
            DYNAMIC_TYPES.add(type);
            writeType();
        }
    }

    /**
     * 读取内容
     * @param type
     * @return
     */
    public static String read(String type) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PACKAGE + type + FILE_SUFFIX), StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            while (line != null) {
                content.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return content.toString();
    }

    private static boolean writeType() {
        return write(TYPE, DYNAMIC_TYPES.isEmpty() ? "" : String.join("\n", DYNAMIC_TYPES));
    }

    private static boolean write(String type, String content) {
        File file = new File(FILE_PACKAGE + type + FILE_SUFFIX);
        OutputStreamWriter writer = null;
        try {
            if (!file.exists()) file.createNewFile();
            writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

}
