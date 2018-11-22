package com.seeletech.core.address;

import org.ethereum.crypto.ECKey;
import org.spongycastle.util.encoders.Hex;

public class AddressManager {
    public static byte[] getAddress(byte[] publicKey){
        ECKey key = ECKey.fromPublicOnly(publicKey);
        return key.getAddress();
    }
    public static void main(String[] args){
        String pubString = "040947751e3022ecf3016be03ec77ab0ce3c2662b4843898cb068d74f698ccc8ad75aa17564ae80a20bb044ee7a6d903e8e8df624b089c95d66a0570f051e5a05b";
        byte[] pubKey = Hex.decode(pubString);
        System.out.println(Hex.toHexString(AddressManager.getAddress(pubKey)));
    }
}
