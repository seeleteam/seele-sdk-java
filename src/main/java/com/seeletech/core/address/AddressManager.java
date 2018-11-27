package com.seeletech.core.address;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.seeletech.util.HttpResult;
import com.seeletech.util.constant.AddressConstant;
import com.seeletech.util.constant.HttpClientConstant;
import com.seeletech.util.http.HttpClientUitl;
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
        httpResult = HttpClientUitl.httpPostWithJson(requestJson, uri, HttpClientConstant.TIMEOUT, null, null);

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
    private static byte getAddressType(byte[] addrArr){
        if(isReserved(addrArr)){
            return AddressConstant.AddressTypeReserved;
        }

        return (byte)(addrArr[AddressConstant.AddressLen-1] & 0x0F);
    }

    private static boolean isReserved(byte[] addrArr){
        byte[] b = new byte[20];
        b[18] = 4;
        b[19] = (byte)255;
        byte[] MaxSystemContractAddress = b ;

        return comp(addrArr,MaxSystemContractAddress,0) <= 0;
    }

    public static byte[] BytesToAddress(byte[] bs){
        byte[] addr = new byte[20];

        if(bs.length > addr.length){
            bs = Arrays.copyOfRange(bs,bs.length-addr.length,bs.length-1);
        }

        addr = Arrays.copyOfRange(bs,0,bs.length-1);

        return addr;
    }

    public static int comp(byte[] a,byte[] b,int i)
    {
        if(a[i]>b[i]){
            return 1;
        }else if(a[i]<b[i]){
            return -1;
        }else {
            i++;
            return comp(a,b,i);
        }
    }
}
