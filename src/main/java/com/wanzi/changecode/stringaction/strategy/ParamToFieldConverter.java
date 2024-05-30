package com.wanzi.changecode.stringaction.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by: ly
 * @ClassName: UnderlineLowerStringFormat
 * @Description: 下划线小写
 * @Date: 2023/12/22 下午3:56
 */
public class ParamToFieldConverter implements StringConverter {
    public static final String SPILT = ",";
    public static final String PRIVATE = "private ";
    public static final String END = ";";
    public static final String TAB = "\t";
    public static final String ROW = "\n\n\t";
    public static StringConverter getInstance() {
        return INSTANCE;
    }

    private static final ParamToFieldConverter INSTANCE = new ParamToFieldConverter();

    private ParamToFieldConverter() {}

    @Override
    public String execute(String msg) {
        String[] words = msg.split(SPILT);
        List<String> resultList = new ArrayList<>();
        for (String word : words) {
            word = word.trim();
            if (!word.isEmpty()) {
                if (resultList.isEmpty()) {
                    resultList.add(TAB + PRIVATE + word + END);
                } else {
                    resultList.add(PRIVATE + word + END);
                }
            }
        }
        return String.join(ROW, resultList);
    }


}
