package com.seeletech.util.hash;

import com.seeletech.model.Transaction;
import com.seeletech.util.bean.BeanUtil;
import org.apache.commons.lang3.StringUtils;
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
            if(tx.getData().getTo() != null){
                a[1] = Hex.decode(tx.getData().getFrom().trim().substring(2));
                a[2] = Hex.decode(tx.getData().getTo().trim().substring(2));
            }else{
                a= new byte[fieldArr.length+1][];
                a[1] = Hex.decode(tx.getData().getFrom().trim().substring(2));
                a[2] = Hex.decode("0x0000000000000000000000000000000000000000".substring(2));
            }
            a[0] = ByteUtil.longToBytesNoLeadZeroes(tx.getData().getType());
            a[3] = ByteUtil.longToBytesNoLeadZeroes(tx.getData().getAmount());
            a[4] = ByteUtil.longToBytesNoLeadZeroes(tx.getData().getAccountNonce());
            a[5] = ByteUtil.longToBytesNoLeadZeroes(tx.getData().getGasPrice());
            a[6] = ByteUtil.longToBytesNoLeadZeroes(tx.getData().getGasLimit());
            a[7] = ByteUtil.longToBytesNoLeadZeroes(tx.getData().getTimestamp());

            if(null != tx.getData().getPayload()){
                if(!"".equals(tx.getData().getPayload())){
                    a[8] = Hex.decode(tx.getData().getPayload().trim().substring(2));
                }else{
                    a[8] = Hex.decode(tx.getData().getPayload().trim());
                }
            }
        }catch(Exception e){
            throw new Exception("BeanTohash failed");
        }

        byte[] messageBytes = RLP.encode(a);
        return org.ethereum.crypto.HashUtil.sha3(messageBytes);
    }
}
