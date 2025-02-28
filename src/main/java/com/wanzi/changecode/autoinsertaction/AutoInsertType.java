package com.wanzi.changecode.autoinsertaction;

import com.wanzi.changecode.stringaction.strategy.StringConverter;
import com.wanzi.changecode.stringaction.strategy.StringToCamelCaseOrUnderLineConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by: ly
 * @ClassName: StringFormatTypeEnum
 * @Description: String转换类枚举
 * @Date: 2023/12/21 上午11:47
 */
public enum AutoInsertType {
    /**
     * 转驼峰命名
     */
    SAFE_GET("safe get", StringToCamelCaseOrUnderLineConverter.getInstance());
    private final String typeName;
    private final StringConverter stringConverter;

    AutoInsertType(String typeName, StringConverter stringConverter) {
        this.typeName = typeName;
        this.stringConverter = stringConverter;
    }

    public String getTypeName() {
        return typeName;
    }

    public StringConverter getStringConverter() {
        return stringConverter;
    }

    /**
     * 获取全部的转换功能
     *
     * @return
     */
    public static List<String> getTypeNameList() {
        List<String> typeNameList = new ArrayList<>();
        for (AutoInsertType value : AutoInsertType.values()) {
            typeNameList.add(value.getTypeName());
        }
        return typeNameList;
    }

    /**
     * 通过类型名称获取实例对象
     *
     * @param typeName
     * @return
     */
    public static StringConverter getStrategyInstance(String typeName) {
        for (AutoInsertType value : AutoInsertType.values()) {
            if (value.getTypeName().equals(typeName)) {
                return value.stringConverter;
            }
        }
        return null;
    }

}
