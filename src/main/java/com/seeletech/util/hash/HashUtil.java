package com.seeletech.util.hash;

import com.seeletech.model.Transaction;
import com.seeletech.util.bean.BeanUtil;
import org.ethereum.util.ByteUtil;
import org.ethereum.util.RLP;
import org.spongycastle.util.encoders.Hex;

public class HashUtil {
    /**
     *convert bean to hash
     * @param tx
     * @return byte[]
     * @throws Exception
     */
    public static  byte[] BeanToHash(Transaction tx) throws Exception {
        String[] fieldArr = BeanUtil.getField(tx.getData());
        byte[][] a= new byte[fieldArr.length][];

        try{
            a[0] = Hex.decode(tx.getData().getFrom().trim().substring(2));
            a[1] = Hex.decode(tx.getData().getTo().trim().substring(2));
            a[2] = ByteUtil.longToBytesNoLeadZeroes(tx.getData().getAmount());
            a[3] =  ByteUtil.longToBytesNoLeadZeroes(tx.getData().getAccountNonce());
            a[4] =  ByteUtil.longToBytesNoLeadZeroes(tx.getData().getGasPrice());
            a[5] = ByteUtil.longToBytesNoLeadZeroes(tx.getData().getGasLimit());
            a[6] =  ByteUtil.longToBytesNoLeadZeroes(tx.getData().getTimestamp());
            if(null != tx.getData().getPayload()){
                if(!"".equals(tx.getData().getPayload())){
                    a[7] = Hex.decode(tx.getData().getPayload().trim().substring(2));
                }else{
                    a[7] = Hex.decode(tx.getData().getPayload().trim());
                }
            }
        }catch(Exception e){
            throw new Exception("BeanTohash failed");
        }

        byte[] messageBytes = RLP.encode(a);
        return org.ethereum.crypto.HashUtil.sha3(messageBytes);
    }
}
