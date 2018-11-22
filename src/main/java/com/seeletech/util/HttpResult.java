package com.seeletech.util;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public class HttpResult {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errMsg;
    private Map result;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Map getResult() {
        return result;
    }

    public void setResult(Map result) {
        this.result = result;
    }
}
