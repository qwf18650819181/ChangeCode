package com.wanzi.changecode.stringaction.strategy;

import org.jetbrains.annotations.NotNull;

/**
 * @author by: ly
 * @ClassName: CamelCaseStringFormat
 * @Description: 驼峰命名转换
 * @Date: 2023/12/22 上午11:15
 */
public class StringToCamelCaseOrUnderLineConverter implements StringConverter {

    private static final int CAMEL = 0;
    private static final int UNDER_LINE = 1;
    private int curType = CAMEL;
    private String curMsg = "";

    public static StringToCamelCaseOrUnderLineConverter getInstance() {
        return INSTANCE;
    }

    private final static StringToCamelCaseOrUnderLineConverter INSTANCE = new StringToCamelCaseOrUnderLineConverter();

    private StringToCamelCaseOrUnderLineConverter() {

    }

    /**
     *
     * @param msg
     * @return
     */
    @Override
    public String execute(String msg) {
        if (curMsg.equals(msg)) {
            curType = (curType + 1) & 1;
        }
        if (curType == CAMEL) {
            curMsg = doCamel(msg);

        } else if (curType == UNDER_LINE) {
            curMsg = doUnderLine(msg);
        }
        return curMsg;
    }

    @NotNull
    private String doUnderLine(String msg) {
        return UnderlineLowerStringConverter.getInstance().execute(msg);
    }

    @NotNull
    private static String doCamel(String msg) {
        ClassCamelStringConverter classCamelStringFormat = ClassCamelStringConverter.getInstance();
        StringBuilder result = new StringBuilder(classCamelStringFormat.execute(msg));
        // 将首字母改为小写
        if (!result.toString().isEmpty()) {
            result.replace(0, 1, String.valueOf(result.charAt(0)).toLowerCase());
        }
        return result.toString();
    }


}
