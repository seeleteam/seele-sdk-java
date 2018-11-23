package com.seeletech.core.address;

import org.ethereum.crypto.HashUtil;
import org.ethereum.util.RLP;
import java.util.Arrays;

public class AddressManager {

    /**
     * get a address from public key
     * @param publicKey byte[]
     * @return byte[]
     */
    public static byte[] getAddress(byte[] publicKey){
        byte[] temp = Arrays.copyOfRange(publicKey,1,publicKey.length);
        byte[] hash = HashUtil.sha3(RLP.encode(temp));
        byte b = 1;
        byte[] addr = Arrays.copyOfRange(hash,hash.length-20,hash.length);
        addr[19] &= 0xF0;
        addr[19] |= b;
        return addr;
    }

}
