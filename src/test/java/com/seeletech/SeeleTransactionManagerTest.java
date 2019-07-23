package com.seeletech;

import com.seeletech.core.transaction.SeeleTransactionManager;
import com.seeletech.model.RawTx;
import com.seeletech.model.SeeleSignature;
import com.seeletech.model.dto.SignTransactionDTO;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SeeleTransactionManagerTest {

    @Test
    public void testSign(){
        String actualResult = "{\"result\":{\"data\":{\"type\":0,\"from\":\"0xe95d99fec90954eb8f6f899c188aef5caa20d501\",\"to\":\"0x0a57a2714e193b7ac50475ce625f2dcfb483d741\",\"amount\":0,\"accountNonce\":0,\"gasPrice\":1,\"gasLimit\":3000000,\"timestamp\":0,\"payload\":\"\"},\"signature\":{\"sig\":\"H8VP/eTJVUohMa08OL5N3tk+qceOnmGTrN3xWbA3HuBmgus1z7mLJg4Gqgv1a8kB6eSIwtAkXX8yRGgK42WY5gE=\"},\"hash\":\"0xd4acdd7bda7229a66ab6b871a3128629b5569ad6fd783fb3be3d53adab64cf32\"}}";
        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xa417551e1522d88d8b2c1592f9e273f7f8bf68517195418b4b21d40e17cdaa1f");
        RawTx rawTx = new RawTx();
        rawTx.setType(0);
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
        rawTx.setType(0);
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
        rawTx.setType(0);
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
        rawTx.setType(0);
        rawTx.setTo("0x0a57a2714e193b7ac50475ce625f2dcfb483d741");
        rawTx.setFrom("");
        rawTx.setAmount(0);
        rawTx.setAccountNonce(0);
        rawTx.setTimestamp(0);
        rawTx.setPayload("");
        rawTx.setGasPrice(1);
        rawTx.setGasLimit(3000000);
        signTransactionDTO.setRawTx(rawTx);
        String jsonResult = SeeleTransactionManager.sendTx(signTransactionDTO,"http://127.0.0.1:8037");
        assertEquals(actualResult, jsonResult);
    }
    
    @Test
    public void testSendTxForCreateContracts(){
        String actualResult = "{\"result\":{\"result\":true,\"id\":1543387166264,\"jsonrpc\":\"2.0\"}}";
        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xd117eeae7189d461ceca53f8c6ed24e148ff47c68f36d1560d658aaaa7b8c8e1");
        RawTx rawTx = new RawTx();
        rawTx.setType(0);
        rawTx.setTo(null);
        rawTx.setFrom("0x9d5bc073176b3911c1bb05970decca89723426b1");
        rawTx.setAmount(0);
        rawTx.setAccountNonce(19);
        rawTx.setTimestamp(0);
        rawTx.setPayload("0x0a57a2714e193b7ac50475ce625f2dcfb483d765");
        rawTx.setGasPrice(1);
        rawTx.setGasLimit(3000000);
        signTransactionDTO.setRawTx(rawTx);
        String jsonResult = SeeleTransactionManager.sendTx(signTransactionDTO,"http://127.0.0.1:8037");
        assertEquals(actualResult.substring(0,actualResult.indexOf("id")), jsonResult.substring(0,jsonResult.indexOf("id")));
    }

    @Test
    public void testSendTxEmptyTo(){
        String actualResult = "{\"errMsg\":\"transaction to address is empty\"}";
        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xa417551e1522d88d8b2c1592f9e273f7f8bf68517195418b4b21d40e17cdaa1f");
        RawTx rawTx = new RawTx();
        rawTx.setType(0);
        rawTx.setTo("");
        rawTx.setFrom("0xe95d99fec90954eb8f6f899c188aef5caa20d501");
        rawTx.setAmount(0);
        rawTx.setAccountNonce(0);
        rawTx.setTimestamp(0);
        rawTx.setPayload("");
        rawTx.setGasPrice(1);
        rawTx.setGasLimit(3000000);
        signTransactionDTO.setRawTx(rawTx);
        String jsonResult = SeeleTransactionManager.sendTx(signTransactionDTO,"http://127.0.0.1:8037");
        assertEquals(actualResult, jsonResult);
    }

    @Test
    public void testSendTxSmallNonce(){
        String actualResult = "{\"errMsg\":\"failed to validate object ===> failed to validate tx ===> nonce is too small, account:0x9d5bc073176b3911c1bb05970decca89723426b1, tx nonce:1, state db nonce:5\"}";
        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xd117eeae7189d461ceca53f8c6ed24e148ff47c68f36d1560d658aaaa7b8c8e1");
        RawTx rawTx = new RawTx();
        rawTx.setType(0);
        rawTx.setTo("0x0a57a2714e193b7ac50475ce625f2dcfb483d741");
        rawTx.setFrom("0x9d5bc073176b3911c1bb05970decca89723426b1");
        rawTx.setAmount(1);
        rawTx.setAccountNonce(1);
        rawTx.setTimestamp(0);
        rawTx.setPayload("");
        rawTx.setGasPrice(10);
        rawTx.setGasLimit(200000);
        signTransactionDTO.setRawTx(rawTx);
        String jsonResult = SeeleTransactionManager.sendTx(signTransactionDTO,"http://127.0.0.1:8037");
        assertEquals(actualResult.substring(0,actualResult.indexOf("tx nonce")), jsonResult.substring(0,jsonResult.indexOf("tx nonce")));
    }

    @Test
    public void testGetTxByHash(){
      String actualResult = "{\"result\":{\"result\":{\"blockHash\":\"0x24db76754e870c62692d04f1014b02837aa8c7203483be221ba65bf099039434\",\"blockHeight\":2,\"transaction\":{\"gasLimit\":0,\"amount\":600000000,\"payload\":\"\",\"from\":\"0x0000000000000000000000000000000000000000\",\"to\":\"0x22858208a1d6402afd4cc504518513b60d43a311\",\"accountNonce\":0,\"hash\":\"0xb0d9e6c7665c2f1fbfcfda8b8fb95875ab145dfda39dff00a9339db303a174d7\",\"gasPrice\":0},\"txIndex\":0,\"status\":\"block\"},\"id\":1563580036311,\"jsonrpc\":\"2.0\"}}";
      actualResult = actualResult.substring(0,actualResult.indexOf("id"));
      String jsonResult = SeeleTransactionManager.getTxByHash("0xb0d9e6c7665c2f1fbfcfda8b8fb95875ab145dfda39dff00a9339db303a174d7","http://127.0.0.1:8037");
      jsonResult = jsonResult.substring(0,jsonResult.indexOf("id"));
      assertEquals(actualResult, jsonResult);
    }

    @Test
    public void testGetTxByHashFailed(){
        String actualResult = "{\"errMsg\":\"leveldb: not found\"}";
        String jsonResult = SeeleTransactionManager.getTxByHash("0x12","http://127.0.0.1:8037");
        assertEquals(actualResult, jsonResult);
    }

    @Test
    public void testGetTxByHashOddLength(){
        String actualResult = "{\"errMsg\":\"hex string of odd length\"}";
        String jsonResult = SeeleTransactionManager.getTxByHash("0x1","http://127.0.0.1:8037");
        assertEquals(actualResult, jsonResult);
    }

    @Test
    public void testGetTxByHashWithoutPrefix0x(){
        String actualResult = "{\"errMsg\":\"hex string without 0x prefix\"}";
        String jsonResult = SeeleTransactionManager.getTxByHash("12","http://127.0.0.1:8037");
        assertEquals(actualResult, jsonResult);
    }

    @Test
    public void testGetTxByHashSyntaxCharaceter(){
        String actualResult = "{\"errMsg\":\"invalid hex string\"}";
        String jsonResult = SeeleTransactionManager.getTxByHash("0x78be64c6d3c1438184713f3dc1c207eeb93543d82808292b8ce74019511cb05-","http://127.0.0.1:8037");
        assertEquals(actualResult, jsonResult);
    }
}
