package com.seeletech.core.address;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.seeletech.util.HttpResult;
import com.seeletech.util.constant.HttpClientConstant;
import com.seeletech.util.http.HttpClientUitl;
import com.seeletech.util.request.RequestUtil;
import org.ethereum.crypto.HashUtil;
import org.ethereum.util.RLP;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class AddressManager {

    /**
     * get a address from public key
     * @param publicKey byte[]
     * @return byte[]
     */
    public static byte[] getAddress(byte[] publicKey) {
        byte[] temp = Arrays.copyOfRange(publicKey, 1, publicKey.length);
        byte[] hash = HashUtil.sha3(RLP.encode(temp));
        byte b = 1;
        byte[] addr = Arrays.copyOfRange(hash, hash.length - 20, hash.length);

        addr[19] &= 0xF0;
        addr[19] |= b;

        return addr;
    }

    /**
     *
     * @param accountAddress
     * @return
     */
    public static String getBalance(String accountAddress,String uri){
        String requestJson = null;
        HttpResult httpResult = new HttpResult();
        List<Object> list = new ArrayList<>();
        list.add(accountAddress);
        list.add("");
        list.add(-1);

        try {
            requestJson = RequestUtil.getRequestJson("getBalance", list);
        } catch (JsonProcessingException e) {
            httpResult.setErrMsg("requestJson is valid:" + e.getMessage());
            return JSON.toJSONString(httpResult);
        }
        httpResult = HttpClientUitl.httpPostWithJson(requestJson, uri, HttpClientConstant.TIMEOUT, null, null);

        return JSON.toJSONString(httpResult);
    }
}
