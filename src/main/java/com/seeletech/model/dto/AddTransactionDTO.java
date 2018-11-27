package com.seeletech.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.seeletech.model.RawTx;
import com.seeletech.model.SeeleSignature;

public class AddTransactionDTO {

    private String hash;

    private RawTx data;

    private SeeleSignature signature;

    @JSONField(name = "Data")
    public RawTx getData() {
        return data;
    }

    public void setData(RawTx data) {
        this.data = data;
    }

    @JSONField(name = "Signature")
    public SeeleSignature getSignature() {
        return signature;
    }

    public void setSignature(SeeleSignature signature) {
        this.signature = signature;
    }

    @JSONField(name = "Hash")
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
