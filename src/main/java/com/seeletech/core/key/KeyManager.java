package com.seeletech.core.key;

import com.alibaba.fastjson.JSON;
import com.seeletech.util.HttpResult;
import org.ethereum.crypto.ECKey;
import org.spongycastle.util.encoders.Hex;
import java.util.HashMap;
import java.util.Map;

public class KeyManager {
    /**
     * generate the private key
     * @return
     */
    public static String key() {
        HttpResult httpResult = new HttpResult();
        ECKey key = new ECKey();
        Map map = new HashMap();
        map.put("public key","0x"+ Hex.toHexString(key.getAddress()));
        map.put("private key","0x"+Hex.toHexString(key.getPrivKeyBytes()));
        httpResult.setResult(map);
        return JSON.toJSONString(httpResult);
    }
    public static void main(String[] args){
        System.out.println(KeyManager.key());
    }
}
