package com.seeletech.model.dto;

import com.seeletech.model.RawTx;

public class SignTransactionDTO {

    private String privateKey;

    private RawTx rawTx;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public RawTx getRawTx() {
        return rawTx;
    }

    public void setRawTx(RawTx rawTx) {
        this.rawTx = rawTx;
    }
}
