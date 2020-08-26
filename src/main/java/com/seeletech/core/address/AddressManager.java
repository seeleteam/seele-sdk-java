package com.seeletech.core.address;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.seeletech.util.HttpResult;
import com.seeletech.util.constant.AddressConstant;
import com.seeletech.util.constant.HttpClientConstant;
import com.seeletech.util.http.HttpClientUtil;
import com.seeletech.util.request.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.ethereum.crypto.HashUtil;
import org.ethereum.util.RLP;
import org.spongycastle.util.encoders.Hex;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class AddressManager {

    /**
     * get a address from public key
     * @param pubString byte[]
     * @return byte[]
     */
    public static byte[] getAddress(byte[] pubString) {
        byte[] temp = Arrays.copyOfRange(pubString, 1, pubString.length);
        byte[] hash = HashUtil.sha3(RLP.encode(temp));
        byte b = 1;
        byte[] addr = Arrays.copyOfRange(hash, hash.length - 20, hash.length);

        addr[19] &= 0xF0;
        addr[19] |= b;

        return addr;
    }

    /**
     * get balance by account address
     * @param accountAddress
     * @return String
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
        httpResult = HttpClientUtil.httpPostWithJson(requestJson, uri, HttpClientConstant.TIMEOUT, null, null);

        return JSON.toJSONString(httpResult);
    }

    /**
     * get nonce by account address
     * @param accountAddress
     * @return String
     */
    public static String getNonce(String accountAddress,String uri){
        String requestJson = null;
        HttpResult httpResult = new HttpResult();
        List<Object> list = new ArrayList<>();
        list.add(accountAddress);
        list.add("");
        list.add(-1);

        try {
            requestJson = RequestUtil.getRequestJson("getAccountNonce", list);
        } catch (JsonProcessingException e) {
            httpResult.setErrMsg("requestJson is valid:" + e.getMessage());
            return JSON.toJSONString(httpResult);
        }
        httpResult = HttpClientUtil.httpPostWithJson(requestJson, uri, HttpClientConstant.TIMEOUT, null, null);
        return JSON.toJSONString(httpResult);
    }

    public static boolean addressValidate(String address){
        if(StringUtils.isEmpty(address)){
            return false;
        }

        byte[] addrArr = Hex.decode(address);
        byte addrType =getAddressType(addrArr);

        if(addrType < AddressConstant.AddressTypeReserved && (addrType < AddressConstant.AddressTypeExternal || addrType > AddressConstant.AddressTypeContract)){
            return false;
        }

        return true;
    }
    private static byte getAddressType(byte addrArr[]){
        if(isReserved(addrArr)){
            return AddressConstant.AddressTypeReserved;
        }

        return (byte)(addrArr[AddressConstant.AddressLen-1] & 0x0F);
    }

    private static boolean isReserved(byte addrArr[]){
        byte b[] = new byte[20];
        b[18] = 4;
        b[19] = (byte)255;
        byte MaxSystemContractAddress[] = b ;

        return comparenTwoArray(addrArr,MaxSystemContractAddress,addrArr.length,MaxSystemContractAddress.length) <= 0;
    }

    static int comparenTwoArray(byte a[],byte b[],int aLen,int bLen){
        int i=0,j=0;
        while(i<aLen&&j<bLen){
            if(a[i]==b[j]){
                i++;
                j++;
            }else{
                return a[i]>b[j]?1:-1;
            }
        }
        if(i==aLen&&j==bLen){
            return 0;
        }else{
            return i==aLen?-1:1;
        }
    }
}
