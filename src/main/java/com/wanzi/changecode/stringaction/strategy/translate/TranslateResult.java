package com.wanzi.changecode.stringaction.strategy.translate;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TranslateResult {
    private String from;
    private String to;
    @JsonProperty("trans_result")
    private List<TranslateData> translateDataList;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<TranslateData> getTranslateDataList() {
        return translateDataList;
    }

    public void setTranslateDataList(List<TranslateData> translateDataList) {
        this.translateDataList = translateDataList;
    }
}
