package com.seeletech.core.transaction;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.seeletech.util.http.HttpClientUtil;
import com.seeletech.util.request.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.ethereum.crypto.ECKey;
import org.spongycastle.util.encoders.Hex;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SeeleTransactionManager {

    /**
     * create a transaction and sign it,if any error occur then return error message,otherwise return a json string
     *
     * @param transactionDTO
     * @return String
     * @throws BaseException
     */
    public static String sign(SignTransactionDTO transactionDTO) throws BaseException {
        HttpResult httpResult = new HttpResult();
        String msg = checkBlank(transactionDTO);

        if (!StringUtils.isEmpty(msg)) {
            httpResult.setErrMsg(msg);
            return JSON.toJSONString(httpResult);
        }

        Transaction tx = null;
        try {
            tx = generateTx(transactionDTO);
            httpResult.setResult(BeanUtil.objectToMap(tx));
        } catch (Exception e) {
            httpResult.setErrMsg("objectToMap failed:" + e.getMessage());
            return JSON.toJSONString(httpResult);
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(httpResult);
        } catch (JsonProcessingException e) {
            httpResult.setErrMsg("json serialize failed:" + e.getMessage());
            return JSON.toJSONString(httpResult);
        }

        return json;
    }

    /**
     * send transaction,if any error occur then return error message,otherwise return a json string
     *
     * @param tx
     * @param uri
     * @return String
     */
    public static String sendTx(Transaction tx, String uri) {
        HttpResult httpResult = new HttpResult();
        if (tx != null && tx.getData()!=null && tx.getHash()!=null && tx.getSignature()!=null) {
            httpResult = addTx(tx, uri);
        } else {
            httpResult.setErrMsg("error:input tx not valid");
        }
        return JSON.toJSONString(httpResult);
    }

    /**
     * get transactions by hash,if any error occur then return error message,otherwise return a json string
     *
     * @param hash
     * @param uri
     * @return String
     */
    public static String getTxByHash(String hash, String uri) {
        String requestJson = null;
        HttpResult httpResult = new HttpResult();
        List<Object> list = new ArrayList();
        list.add(hash);
        try {
            requestJson = RequestUtil.getRequestJson("getTransactionByHash", list);
        } catch (JsonProcessingException e) {
            httpResult.setErrMsg("requestJson is valid:" + e.getMessage());
            return JSON.toJSONString(httpResult);
        }
        httpResult = HttpClientUtil.httpPostWithJson(requestJson, uri, HttpClientConstant.TIMEOUT, null, null);

        return JSON.toJSONString(httpResult);
    }

    /**
     * generate a transaction, if any error occur then throw a exception,otherwise return Transaction object
     *
     * @param transactionDTO
     * @return Transaction
     * @throws BaseException
     */
    public static Transaction generateTx(SignTransactionDTO transactionDTO) throws BaseException {
        String privatekey = transactionDTO.getPrivateKey();
        if (privatekey.startsWith("0x")) {
            privatekey = privatekey.substring(2);
        }

        RawTx rawTx = transactionDTO.getRawTx();
        Transaction tx = new Transaction(rawTx);
        String sig = "";
        try {
            byte[] hashBuffer = HashUtil.BeanToHash(tx);
            tx.setHash("0x" + Hex.toHexString(hashBuffer));
            ECKey key = ECKey.fromPrivate(Hex.decode(privatekey));
            ECKey.ECDSASignature signature = key.sign(hashBuffer);
            sig = Base64.getEncoder().encodeToString(signature.toByteArray());
        } catch (Exception e) {
            throw new BaseException("generateTx failed:" + e.getMessage());
        }

        SeeleSignature seeleSignature = new SeeleSignature();
        seeleSignature.setSig(sig);
        tx.setSignature(seeleSignature);

        return tx;
    }

    /**
     * send transaction to go server by JSON-RPC
     *
     * @param tx
     * @param uri
     * @return HttpResult
     */
    private static HttpResult addTx(Transaction tx,String uri) {
        HttpResult httpResult = new HttpResult();
        List<Object> list = new ArrayList<>();
        list.add(tx);
        String requestJson = null;
        try {
            requestJson = RequestUtil.getRequestJson("addTx",list );
        } catch (JsonProcessingException e) {
            httpResult.setErrMsg("requestJson is invalid:" + e.getMessage());
            return httpResult;
        }

        return HttpClientUtil.httpPostWithJson(requestJson, uri, HttpClientConstant.TIMEOUT, null, null);
    }

    /**
     * transfer SignTransactionDTO to AddTransactionDTO,if any error occur return null,otherwise return  AddTransactionDTO object
     *
     * @param signTxDTO
     * @return AddTransactionDTO
     */
    private static AddTransactionDTO getAddTransactionDTO(SignTransactionDTO signTxDTO) {
        Transaction tx = null;
        try {
            tx = generateTx(signTxDTO);
            AddTransactionDTO addTxDTO = new AddTransactionDTO();
            addTxDTO.setData(tx.getData());
            addTxDTO.setHash(tx.getHash());
            addTxDTO.setSignature(tx.getSignature());
            return addTxDTO;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * check the object field whether blank,if the filed is blank then return error message,otherwise return a empty String
     *
     * @param obj
     * @return String
     */
    private static String checkBlank(Object obj) {
        String msg = "";
        if (obj instanceof AddTransactionDTO) {
            AddTransactionDTO tx = (AddTransactionDTO) obj;
            if (StringUtils.isEmpty(tx.getHash())) {
                msg = "transaction hash is empty";
            } else if (StringUtils.isEmpty(tx.getSignature().getSig())) {
                msg = "transaction signature is empty";
            }
        }

        if (obj instanceof SignTransactionDTO) {
            SignTransactionDTO txDTO = (SignTransactionDTO) obj;
            if (StringUtils.isEmpty(txDTO.getRawTx().getFrom())) {
                msg = "transaction from address is empty";
            } else if (txDTO.getRawTx().getTo() != null && "".equals(txDTO.getRawTx().getTo())) {
                msg = "transaction to address is empty";
            }
        }

        return msg;
    }


}
