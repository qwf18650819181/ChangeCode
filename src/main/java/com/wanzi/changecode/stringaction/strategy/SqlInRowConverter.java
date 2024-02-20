package com.wanzi.changecode.stringaction.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by: ly
 * @ClassName: UnderlineLowerStringFormat
 * @Description: 下划线小写
 * @Date: 2023/12/22 下午3:56
 */
public class SqlInRowConverter implements StringConverter {

    public static final String ROW_SPILT = "\n";
    public static final String ROW_LEFT = "'";
    public static final String ROW_RIGHT = "',";
    @Override
    public String execute(String msg) {
        String[] words = msg.split(ROW_SPILT);
        List<String> resultList = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                resultList.add(ROW_LEFT + word + ROW_RIGHT);
            }
        }
        return String.join(ROW_SPILT, resultList);
    }
}
