package com.seeletech;

import com.seeletech.core.transaction.SeeleTransactionManager;
import com.seeletech.model.RawTx;
import com.seeletech.model.dto.SignTransactionDTO;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SeeleTransactionManagerTest {

    @Test
    public void testSign(){
        String actualResult = "{\"result\":{\"data\":{\"from\":\"0xe95d99fec90954eb8f6f899c188aef5caa20d501\",\"to\":\"0x0a57a2714e193b7ac50475ce625f2dcfb483d741\",\"amount\":0,\"accountNonce\":0,\"gasPrice\":1,\"gasLimit\":3000000,\"timestamp\":0,\"payload\":\"\"},\"signature\":{\"sig\":\"ob6nXGQy7VKylMPHJTfmxbsJZVQr6HdV2U/dYF/bc9kIU55u/2HMWo16ngsIWlo87aZCqlUY6H5h1+boImfDowA=\"},\"hash\":\"0x78be64c6d3c1438184713f3dc1c207eeb93543d82808292b8ce74019511cb057\"}}";
        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xa417551e1522d88d8b2c1592f9e273f7f8bf68517195418b4b21d40e17cdaa1f");
        RawTx rawTx = new RawTx();
        rawTx.setTo("0x0a57a2714e193b7ac50475ce625f2dcfb483d741");
        rawTx.setFrom("0xe95d99fec90954eb8f6f899c188aef5caa20d501");
        rawTx.setAmount(0);
        rawTx.setAccountNonce(0);
        rawTx.setTimestamp(0);
        rawTx.setPayload("");
        rawTx.setGasPrice(1);
        rawTx.setGasLimit(3000000);
        signTransactionDTO.setRawTx(rawTx);
        String jsonResult = SeeleTransactionManager.sign(signTransactionDTO);
        assertEquals(actualResult, jsonResult);
    }

    @Test
    public void testSignEmptyFrom(){
        String actualResult = "{\"errMsg\":\"transaction from address is empty\"}";
        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xa417551e1522d88d8b2c1592f9e273f7f8bf68517195418b4b21d40e17cdaa1f");
        RawTx rawTx = new RawTx();
        rawTx.setTo("0x0a57a2714e193b7ac50475ce625f2dcfb483d741");
        rawTx.setFrom("");
        rawTx.setAmount(0);
        rawTx.setAccountNonce(0);
        rawTx.setTimestamp(0);
        rawTx.setPayload("");
        rawTx.setGasPrice(1);
        rawTx.setGasLimit(3000000);
        signTransactionDTO.setRawTx(rawTx);
        String jsonResult = SeeleTransactionManager.sign(signTransactionDTO);
        assertEquals(actualResult, jsonResult);
    }

    @Test
    public void testSignEmtyTo(){
        String actualResult = "{\"errMsg\":\"transaction to address is empty\"}";
        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xa417551e1522d88d8b2c1592f9e273f7f8bf68517195418b4b21d40e17cdaa1f");
        RawTx rawTx = new RawTx();
        rawTx.setTo("");
        rawTx.setFrom("0xe95d99fec90954eb8f6f899c188aef5caa20d501");
        rawTx.setAmount(0);
        rawTx.setAccountNonce(0);
        rawTx.setTimestamp(0);
        rawTx.setPayload("");
        rawTx.setGasPrice(1);
        rawTx.setGasLimit(3000000);
        signTransactionDTO.setRawTx(rawTx);
        String jsonResult = SeeleTransactionManager.sign(signTransactionDTO);
        assertEquals(actualResult, jsonResult);
    }

    @Test
    public void testSendTxEmptyFrom(){
        String actualResult = "{\"errMsg\":\"transaction from address is empty\"}";
        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xa417551e1522d88d8b2c1592f9e273f7f8bf68517195418b4b21d40e17cdaa1f");
        RawTx rawTx = new RawTx();
        rawTx.setTo("0x0a57a2714e193b7ac50475ce625f2dcfb483d741");
        rawTx.setFrom("");
        rawTx.setAmount(0);
        rawTx.setAccountNonce(0);
        rawTx.setTimestamp(0);
        rawTx.setPayload("");
        rawTx.setGasPrice(1);
        rawTx.setGasLimit(3000000);
        signTransactionDTO.setRawTx(rawTx);
        String jsonResult = SeeleTransactionManager.sendTx(signTransactionDTO,"http://117.50.20.225:8037");
        assertEquals(actualResult, jsonResult);
    }

    @Test
    public void testSendTxEmptyTo(){
        String actualResult = "{\"errMsg\":\"transaction to address is empty\"}";
        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xa417551e1522d88d8b2c1592f9e273f7f8bf68517195418b4b21d40e17cdaa1f");
        RawTx rawTx = new RawTx();
        rawTx.setTo("");
        rawTx.setFrom("0xe95d99fec90954eb8f6f899c188aef5caa20d501");
        rawTx.setAmount(0);
        rawTx.setAccountNonce(0);
        rawTx.setTimestamp(0);
        rawTx.setPayload("");
        rawTx.setGasPrice(1);
        rawTx.setGasLimit(3000000);
        signTransactionDTO.setRawTx(rawTx);
        String jsonResult = SeeleTransactionManager.sendTx(signTransactionDTO,"http://117.50.20.225:8037");
        assertEquals(actualResult, jsonResult);
    }

    @Test
    public void testSendTxSmallNonce(){
        String actualResult = "{\"errMsg\":\"failed to validate object ===> failed to validate tx ===> nonce is too small, acount:0xe95d99fec90954eb8f6f899c188aef5caa20d501, tx nonce:9, state db nonce:11\"}";
        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xa417551e1522d88d8b2c1592f9e273f7f8bf68517195418b4b21d40e17cdaa1f");
        RawTx rawTx = new RawTx();
        rawTx.setTo("0x0a57a2714e193b7ac50475ce625f2dcfb483d741");
        rawTx.setFrom("0xe95d99fec90954eb8f6f899c188aef5caa20d501");
        rawTx.setAmount(0);
        rawTx.setAccountNonce(9);
        rawTx.setTimestamp(0);
        rawTx.setPayload("");
        rawTx.setGasPrice(1);
        rawTx.setGasLimit(3000000);
        signTransactionDTO.setRawTx(rawTx);
        String jsonResult = SeeleTransactionManager.sendTx(signTransactionDTO,"http://117.50.20.225:8037");
        assertEquals(actualResult.substring(0,actualResult.indexOf("tx nonce")), jsonResult.substring(0,jsonResult.indexOf("tx nonce")));
    }

    @Test
    public void testGetTxByHash(){
        String actualResult = "{\"result\":{\"result\":{\"blockHash\":\"0x00000152de8784bc264cc43d05b1ac5da040141aeb087ca01761a2028b6fd7f7\",\"blockHeight\":3,\"transaction\":{\"gasLimit\":3000000,\"amount\":0,\"payload\":\"\",\"from\":\"0xe95d99fec90954eb8f6f899c188aef5caa20d501\",\"to\":\"0x0a57a2714e193b7ac50475ce625f2dcfb483d741\",\"accountNonce\":0,\"hash\":\"0x78be64c6d3c1438184713f3dc1c207eeb93543d82808292b8ce74019511cb057\",\"gasPrice\":1},\"txIndex\":1,\"status\":\"block\"},\"id\":1543297378511,\"jsonrpc\":\"2.0\"}}";
        actualResult = actualResult.substring(0,actualResult.indexOf("id"));
        String jsonResult = SeeleTransactionManager.getTxByHash("0x78be64c6d3c1438184713f3dc1c207eeb93543d82808292b8ce74019511cb057","http://117.50.20.225:8037");
        jsonResult = jsonResult.substring(0,jsonResult.indexOf("id"));
        assertEquals(actualResult, jsonResult);
    }

    @Test
    public void testGetTxByHashFailed(){
        String actualResult = "{\"errMsg\":\"leveldb: not found\"}";
        String jsonResult = SeeleTransactionManager.getTxByHash("0x12","http://117.50.20.225:8037");
        assertEquals(actualResult, jsonResult);
    }

    @Test
    public void testGetTxByHashOddLength(){
        String actualResult = "{\"errMsg\":\"hex string of odd length\"}";
        String jsonResult = SeeleTransactionManager.getTxByHash("0x1","http://117.50.20.225:8037");
        assertEquals(actualResult, jsonResult);
    }

    @Test
    public void testGetTxByHashWithoutPrefix0x(){
        String actualResult = "{\"errMsg\":\"hex string without 0x prefix\"}";
        String jsonResult = SeeleTransactionManager.getTxByHash("12","http://117.50.20.225:8037");
        assertEquals(actualResult, jsonResult);
    }

    @Test
    public void testGetTxByHashSyntaxCharaceter(){
        String actualResult = "{\"errMsg\":\"invalid hex string\"}";
        String jsonResult = SeeleTransactionManager.getTxByHash("0x78be64c6d3c1438184713f3dc1c207eeb93543d82808292b8ce74019511cb05-","http://117.50.20.225:8037");
        assertEquals(actualResult, jsonResult);
    }
}
