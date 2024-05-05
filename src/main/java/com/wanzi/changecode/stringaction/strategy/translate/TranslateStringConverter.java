package com.wanzi.changecode.stringaction.strategy.translate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.wanzi.changecode.stringaction.strategy.CamelCaseStringConverter;
import com.wanzi.changecode.stringaction.strategy.StringConverter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * @author by: ly
 * @ClassName: TranslateStringConverter
 * @Description: 中文翻译英文,直接驼峰写方法
 * @Date: 2023/12/22 上午11:15
 */
public class TranslateStringConverter implements StringConverter {
    private static final String GOOGLE_TRANSLATE_URL = "http://translate.googleapis.com/translate_a/single?client=gtx&sl=zh-CN&tl=en&dt=t&q=";


    @Override
    public String execute(String msg) {
        /**
         * 百度
         */
        return translate(msg);
        /**
         * google
         */
//        Request request = new Request.Builder().url(GOOGLE_TRANSLATE_URL + msg).build();
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//            String ans = parseResponse(response.body().string());
//            return camelCaseStringConverter.execute(ans);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
    }

    private CamelCaseStringConverter camelCaseStringConverter = new CamelCaseStringConverter();

    private final String appKey = "20200805000533739";
    private final String securityKey = "vE5UCGVWPDwqsXQAjjk9";

    private static final OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build();

    public String translate(String query) {
        try {
            // 随机数
            String salt = String.valueOf(System.currentTimeMillis());
            String src = appKey + query + salt + securityKey;
            String sign = TranslateStringConverter.crypt(src);
            String url = String.format("http://api.fanyi.baidu.com/api/trans/vip/translate?q=%s&from=%s&to=%s&appid=%s&salt=%s&sign=%s"
                    , query, "zh", "en", appKey, salt, sign);
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                String ans = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();
                TranslateResult translateResult = objectMapper.readValue(ans, TranslateResult.class);
                return camelCaseStringConverter.execute(translateResult.getTranslateDataList().get(0).getDst());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        // 如果没有结果，返回原文
        return query;
    }

    public static String crypt(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] hash = md.digest();
            return hashByte2MD5(hash);
        } catch (NoSuchAlgorithmException var4) {
            return null;
        }
    }

    private static String hashByte2MD5(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            if ((255 & b) < 16) {
                hexString.append("0").append(Integer.toHexString(255 & b));
            } else {
                hexString.append(Integer.toHexString(255 & b));
            }
        }
        return hexString.toString();
    }

    private String parseResponse(String response) {
        JsonArray jsonArray = new JsonParser().parse(response).getAsJsonArray();
        JsonArray firstArray = jsonArray.get(0).getAsJsonArray();
        JsonArray innerArray = firstArray.get(0).getAsJsonArray();
        return innerArray.get(0).getAsString();
    }
}
