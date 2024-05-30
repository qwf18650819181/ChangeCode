package com.wanzi.changecode.stringaction.strategy;

import com.wanzi.changecode.stringaction.MyString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by: ly
 * @ClassName: UnderlineLowerStringFormat
 * @Description: 下划线小写
 * @Date: 2023/12/22 下午3:56
 */
public class UnderlineLowerStringConverter implements StringConverter {

    public static UnderlineLowerStringConverter getInstance() {
        return INSTANCE;
    }

    private static final UnderlineLowerStringConverter INSTANCE = new UnderlineLowerStringConverter();

    private UnderlineLowerStringConverter() {}

    @Override
    public String execute(String msg) {
        String[] words = msg.split(MyString.SPLIT_REGEX);
        List<String> resultList = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                List<String> strings = MyString.splitCamelCase(word);
                for (String string : strings) {
                    resultList.add(string.toLowerCase());
                }
            }
        }
        return String.join("_", resultList);
    }

}
