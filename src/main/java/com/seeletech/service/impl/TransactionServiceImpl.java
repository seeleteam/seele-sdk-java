package com.seeletech.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.seeletech.model.RawTx;
import com.seeletech.model.SeeleSignature;
import com.seeletech.model.Transaction;
import com.seeletech.model.dto.AddTransactionDTO;
import com.seeletech.model.dto.SignTransactionDTO;
import com.seeletech.util.HttpResult;
import com.seeletech.util.bean.BeanUtil;
import com.seeletech.util.constant.HttpClientConstant;
import com.seeletech.util.exception.BaseException;
import com.seeletech.util.hash.HashUtil;
import com.seeletech.util.http.HttpClientUitl;
import com.seeletech.util.request.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.ethereum.crypto.ECKey;
import org.spongycastle.util.encoders.Hex;
import java.util.Base64;


public class TransactionServiceImpl{

    public static String sign(SignTransactionDTO transactionDTO) throws BaseException {
        HttpResult httpResult = new HttpResult();
        String msg = checkBlank(transactionDTO);
        if(!StringUtils.isEmpty(msg)){
            httpResult.setErrMsg(msg);
            return JSON.toJSONString(httpResult);
        }
        Transaction tx = null;
        try {
            tx = generateTransaction(transactionDTO);
            httpResult.setResult(BeanUtil.objectToMap(tx));
        } catch (Exception e) {
            httpResult.setErrMsg("objectToMap failed:"+e.getMessage());
            return JSON.toJSONString(httpResult);
        }
        return JSON.toJSONString(httpResult);
    }

    /**
     * send transaction
     * @param signTransactionDTO
     * @param uri
     * @return
     */
    public static String sendTx(SignTransactionDTO signTransactionDTO,String uri) {
        HttpResult httpResult = new HttpResult();
        String msg = checkBlank(signTransactionDTO);
        if(!StringUtils.isEmpty(msg)){
            httpResult.setErrMsg(msg);
            return JSON.toJSONString(httpResult);
        }
        AddTransactionDTO addTransactionDTO = getAddTransactionDTO(signTransactionDTO);
        if(addTransactionDTO != null ){
            httpResult =  addTx("addTx",addTransactionDTO,httpResult,uri);
        }else{
            httpResult.setErrMsg("error:addTransactionDTO is null");
        }
        return JSON.toJSONString(httpResult);
    }

    /**
     * get transaction by hash
     * @param hash
     * @param uri
     * @return
     */
    public static String gettxbyhash(String hash,String uri) {
        String requestJson = null;
        HttpResult httpResult = new HttpResult();
        try {
            requestJson = RequestUtil.getRequestJson("getTransactionByHash", hash);
        } catch (JsonProcessingException e) {
            httpResult.setErrMsg("requestJson is valid:"+e.getMessage());
            return JSON.toJSONString(httpResult);
        }
         httpResult =  HttpClientUitl.httpPostWithJson(requestJson, uri, HttpClientConstant.TIMEOUT,null,null);
         return JSON.toJSONString(httpResult);
    }



    private static Transaction generateTransaction(SignTransactionDTO transactionDTO) throws BaseException {
        String privatekey = transactionDTO.getPrivateKey();
        if (privatekey.startsWith("0x")) {
            privatekey = privatekey.substring(2);
        }
        RawTx rawTx = transactionDTO.getRawTx();
        Transaction tx = new Transaction(rawTx);
        String sig = "";
        try {
            byte[] hashBuffer = HashUtil.BeanTohash(tx);
            tx.setHash("0x" + Hex.toHexString(hashBuffer));
            ECKey key = ECKey.fromPrivate(Hex.decode(privatekey));
            ECKey.ECDSASignature signature = key.sign(hashBuffer);
            sig = Base64.getEncoder().encodeToString(signature.toByteArray());
        }catch(Exception e){
            throw new BaseException("generateTx failed:"+e.getMessage());
        }
        SeeleSignature seeleSignature = new SeeleSignature();
        seeleSignature.setSig(sig);
        tx.setSignature(seeleSignature);
        return tx;
    }

    private static HttpResult addTx(String methodName, AddTransactionDTO transaction, HttpResult httpResult,String uri){
        String msg = checkBlank(transaction);
        if(!StringUtils.isEmpty(msg)){
            httpResult.setErrMsg(msg);
            return httpResult;
        }
        String requestJson = null;
        try {
            requestJson = RequestUtil.getRequestJson(methodName, transaction);
        } catch (JsonProcessingException e) {
            httpResult.setErrMsg("requestJson is valid:"+e.getMessage());
            return httpResult;
        }
        return HttpClientUitl.httpPostWithJson(requestJson, uri, HttpClientConstant.TIMEOUT,null,null);
    }


    private static AddTransactionDTO getAddTransactionDTO(SignTransactionDTO signTransactionDTO){
        Transaction transaction = null;
        try{
            transaction = generateTransaction(signTransactionDTO);
            AddTransactionDTO addTransactionDTO = new AddTransactionDTO();
            addTransactionDTO.setData(transaction.getData());
            addTransactionDTO.setHash(transaction.getHash());
            addTransactionDTO.setSignature(transaction.getSignature());
            return addTransactionDTO;
        }catch(Exception e){
            return null;
        }
    }

    private static  String checkBlank(Object obj) {
        String msg = "";
        if(obj instanceof  AddTransactionDTO){
            AddTransactionDTO transaction = (AddTransactionDTO)obj;
            if(StringUtils.isEmpty(transaction.getHash())){
                msg = "transaction hash is empty";
            }else if(StringUtils.isEmpty(transaction.getSignature().getSig())){
                msg = "transaction signature is empty";
            }
        }
        if(obj instanceof  SignTransactionDTO){
            SignTransactionDTO transactionDTO = (SignTransactionDTO) obj;
            if(StringUtils.isEmpty(transactionDTO.getRawTx().getFrom())){
                msg = "transaction from address is empty";
            }else if(StringUtils.isEmpty(transactionDTO.getRawTx().getTo())){
                msg = "transaction to address is empty";
            }
        }
        return msg;
    }

    public static void main(String[] args){
        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xd738b0c1198e55050f754bdf0f824ee4febd962a6b751faab86c081ad5033b0d");
        RawTx rawTx = new RawTx();
        rawTx.setTo("0x0a57a2714e193b7ac50475ce625f2dcfb483d741");//
        rawTx.setFrom("0xb265a2e04087a9a83492ffe191316f46b4730751");
        rawTx.setAmount(0);
        rawTx.setAccountNonce(0);
        rawTx.setTimestamp(0);
        rawTx.setPayload("");
        rawTx.setGasPrice(1);
        rawTx.setGasLimit(3000000);
        signTransactionDTO.setRawTx(rawTx);
        String b = TransactionServiceImpl.gettxbyhash("0xa489ac38226716ce9cb7f72a41ac09e77599599df0c2f80152870461b55a1b98","http://127.0.0.1:8037");
        System.out.println(b);
    }
}
