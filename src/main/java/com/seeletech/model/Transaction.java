package com.seeletech.model;


public class Transaction {
    private RawTx data;

    private SeeleSignature signature;

    public SeeleSignature getSignature() {
        return signature;
    }

    public void setSignature(SeeleSignature signature) {
        this.signature = signature;
    }


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    private String hash;

    public RawTx getData() {
        return data;
    }

    public void setData(RawTx data) {
        this.data = data;
    }

    public Transaction(RawTx data){
        this.data = data;
    }
    public Transaction(){}
}
