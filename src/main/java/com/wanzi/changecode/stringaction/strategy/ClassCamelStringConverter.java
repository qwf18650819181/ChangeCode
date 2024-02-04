package com.wanzi.changecode.stringaction.strategy;

import com.wanzi.changecode.stringaction.MyString;

import java.util.List;

/**
 * @author by: ly
 * @ClassName: ClassCamelStringFormat
 * @Description: 类名驼峰
 * @Date: 2023/12/22 下午2:21
 */
public class ClassCamelStringConverter implements StringConverter {
    @Override
    public String execute(String msg) {
        String[] words = msg.split(MyString.SPLIT_REGEX);
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            List<String> splitString = MyString.splitCamelCase(word);
            for (String string : splitString) {
                if (!string.isEmpty()) {
                    String lowercaseWord = string.toLowerCase();
                    String firstLetter = lowercaseWord.substring(0, 1);
                    String remainingLetters = lowercaseWord.substring(1);
                    result.append(firstLetter.toUpperCase()).append(remainingLetters);
                }
            }
        }
        return result.toString();
    }
}
