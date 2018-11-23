package com.seeletech;

import com.seeletech.core.transaction.SeeleTransactionManager;
import com.seeletech.model.RawTx;
import com.seeletech.model.dto.SignTransactionDTO;
import org.junit.Test;

public class SeeleTransactionManagerTest {

    @Test
    public void testSign(){
        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xa417551e1522d88d8b2c1592f9e273f7f8bf68517195418b4b21d40e17cdaa1f");
        RawTx rawTx = new RawTx();
        rawTx.setTo("0x0a57a2714e193b7ac50475ce625f2dcfb483d741");//
        rawTx.setFrom("0xe95d99fec90954eb8f6f899c188aef5caa20d501");
        rawTx.setAmount(0);
        rawTx.setAccountNonce(0);
        rawTx.setTimestamp(0);
        rawTx.setPayload("");
        rawTx.setGasPrice(1);
        rawTx.setGasLimit(3000000);
        signTransactionDTO.setRawTx(rawTx);
        System.out.println(SeeleTransactionManager.sign(signTransactionDTO));
    }

    @Test
    public void testSendTx(){
        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xa417551e1522d88d8b2c1592f9e273f7f8bf68517195418b4b21d40e17cdaa1f");
        RawTx rawTx = new RawTx();
        rawTx.setTo("0x0a57a2714e193b7ac50475ce625f2dcfb483d741");//
        rawTx.setFrom("0xe95d99fec90954eb8f6f899c188aef5caa20d501");
        rawTx.setAmount(0);
        rawTx.setAccountNonce(0);
        rawTx.setTimestamp(0);
        rawTx.setPayload("");
        rawTx.setGasPrice(1);
        rawTx.setGasLimit(3000000);
        signTransactionDTO.setRawTx(rawTx);
        System.out.println(SeeleTransactionManager.sendTx(signTransactionDTO,"http://117.50.20.225:8037"));
    }

    @Test
    public void testGettxbyhash(){
        System.out.println(SeeleTransactionManager.gettxbyhash("0x78be64c6d3c1438184713f3dc1c207eeb93543d82808292b8ce74019511cb057","http://117.50.20.225:8037"));
    }
}
