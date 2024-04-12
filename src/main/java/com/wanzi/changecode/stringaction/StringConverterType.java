package com.wanzi.changecode.stringaction;

import com.wanzi.changecode.stringaction.strategy.CamelCaseStringConverter;
import com.wanzi.changecode.stringaction.strategy.SqlInRowConverter;
import com.wanzi.changecode.stringaction.strategy.StringConverter;
import com.wanzi.changecode.stringaction.strategy.translate.TranslateStringConverter;
import com.wanzi.changecode.stringaction.strategy.UnderlineLowerStringConverter;

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
    CAMEL_CASE("驼峰", CamelCaseStringConverter.class),
    TRANSFER("翻译", TranslateStringConverter.class),
//    /**
//     * 类名驼峰
//     */
//    CLASS_CAMEL_CASE("类驼峰", ClassCamelStringConverter.class),
    /**
     * 转下划线小写
     */
    TO_UNDERLINE_LOWER("下划线", UnderlineLowerStringConverter.class),
    SQL_IN_ROW("SQL IN", SqlInRowConverter.class);
    private final String typeName;
    private final Class<? extends StringConverter> strategyClass;

    StringConverterType(String typeName, Class<? extends StringConverter> strategyClass) {
        this.typeName = typeName;
        this.strategyClass = strategyClass;
    }

    public String getTypeName() {
        return typeName;
    }

    public Class<? extends StringConverter> getStrategyClass() {
        return strategyClass;
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
                try {
                    Class<? extends StringConverter> strategyClass = value.getStrategyClass();
                    return strategyClass.getDeclaredConstructor().newInstance();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        }
        return null;
    }

}
