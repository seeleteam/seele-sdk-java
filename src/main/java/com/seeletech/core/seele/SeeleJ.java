package com.seeletech.core.seele;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.seeletech.core.address.AddressManager;
import com.seeletech.util.HttpResult;
import com.seeletech.util.constant.Common;
import com.seeletech.util.constant.HttpClientConstant;
import com.seeletech.util.http.HttpClientUtil;
import com.seeletech.util.request.RequestUtil;
import org.ethereum.crypto.ECKey;
import org.spongycastle.util.encoders.Hex;

import java.util.*;

public class SeeleJ {

    private String nodeUri;

    public SeeleJ(String uri){
        nodeUri = uri;
    }
    /**
     * execute RPC command
     *
     * @param
     * @return String
     */
    public String execCmd(String methodName, List args) {
        String requestJson = null;
        HttpResult httpResult = new HttpResult();
        try {
            requestJson = RequestUtil.getRequestJson(methodName, args);
        } catch (JsonProcessingException e) {
            httpResult.setErrMsg("requestJson is valid:" + e.getMessage());
            return JSON.toJSONString(httpResult);
        }
        httpResult = HttpClientUtil.httpPostWithJson(requestJson, nodeUri, HttpClientConstant.TIMEOUT, null, null);
        return JSON.toJSONString(httpResult);
    }

}
