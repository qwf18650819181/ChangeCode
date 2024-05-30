package com.wanzi.changecode.stringaction;

import com.wanzi.changecode.stringaction.strategy.ParamToFieldConverter;
import com.wanzi.changecode.stringaction.strategy.SqlInRowConverter;
import com.wanzi.changecode.stringaction.strategy.StringConverter;
import com.wanzi.changecode.stringaction.strategy.StringToCamelCaseOrUnderLineConverter;
import com.wanzi.changecode.stringaction.strategy.translate.TranslateStringConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by: ly
 * @ClassName: StringFormatTypeEnum
 * @Description: String转换类枚举
 * @Date: 2023/12/21 上午11:47
 */
public enum StringConverterType {
    /**
     * 转驼峰命名
     */
    CAMEL_CASE("驼峰/下划线", StringToCamelCaseOrUnderLineConverter.getInstance()),
    TRANSFER("翻译", TranslateStringConverter.getInstance()),
    PARAM_TO_FIELD("参数转变量", ParamToFieldConverter.getInstance()),
    SQL_IN_ROW("SQL IN", SqlInRowConverter.getInstance());
    private final String typeName;
    private final StringConverter stringConverter;

    StringConverterType(String typeName, StringConverter stringConverter) {
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
        for (StringConverterType value : StringConverterType.values()) {
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
        for (StringConverterType value : StringConverterType.values()) {
            if (value.getTypeName().equals(typeName)) {
                return value.stringConverter;
            }
        }
        return null;
    }

}
