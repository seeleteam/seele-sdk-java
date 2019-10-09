package com.seeletech.core.key;

import com.alibaba.fastjson.JSON;
import com.seeletech.core.address.AddressManager;
import com.seeletech.util.HttpResult;
import com.seeletech.util.constant.Common;
import org.ethereum.crypto.ECKey;
import org.spongycastle.util.encoders.Hex;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class KeyManager {

    /**
     * Random generation of private key and public key on sharding one or two,if success will return a json string
     *
     * @param shard int between 1 and 2
     * @return String
     */
    public static String key(int shard) {
        HttpResult httpResult = new HttpResult();
        if (shard == 0 || shard > Common.ShardCount) {
            httpResult.setErrMsg("invalid shard number, should be between 1 and " + Common.ShardCount);
            return JSON.toJSONString(httpResult);
        }

        while (true) {
            ECKey key = new ECKey();
            Map map = new HashMap();

            byte[] addr = AddressManager.getAddress(key.getPubKey());
            if (shard(addr) == shard) {
                map.put("public key", "0x" + Hex.toHexString(AddressManager.getAddress(key.getPubKey())));
                map.put("private key", "0x" + Hex.toHexString(key.getPrivKeyBytes()));
                httpResult.setResult(map);
                return JSON.toJSONString(httpResult);
            }
        }
    }

    /**
     * calcuate shard num by address
     *
     * @param addr byte[]
     * @return int
     */
    public static int shard(byte[] addr) {
        int sum = 0;

        // sum [0:18]
        for (int i = 0; i < 18; i++) {
            sum += addr[i];
        }

        // sum [18:20] except address type
        byte[] a = Arrays.copyOfRange(addr, 16, 20);
        int tail = byteArrayToInt(a);
        sum += (tail >> 4);

        return (sum % Common.ShardCount) + 1;
    }

    /**
     * transfer byte array to int
     *
     * @param bytes byte[]
     * @return int
     */
    private static int byteArrayToInt(byte[] bytes) {
        int value = 0;

        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;
        }

        return value;
    }
}
