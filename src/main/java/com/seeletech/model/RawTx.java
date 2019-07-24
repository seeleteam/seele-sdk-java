package com.seeletech.model;

import com.alibaba.fastjson.annotation.JSONField;

public class RawTx {
    private long type;
    
    private String from;

    private String to;

    private long amount;

    private long accountNonce;

    private long gasPrice;

    private long gasLimit;

    private long timestamp;

    private String payload;
    
    @JSONField(name = "Type")
    public long getType(){
        return type;
    }
    
    public void setType(long type){
        this.type = type;
    }

    @JSONField(name = "From")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @JSONField(name = "To")
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @JSONField(name = "Amount")
    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @JSONField(name = "AccountNonce")
    public long getAccountNonce() {
        return accountNonce;
    }

    public void setAccountNonce(long accountNonce) {
        this.accountNonce = accountNonce;
    }

    @JSONField(name = "GasPrice")
    public long getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(long gasPrice) {
        this.gasPrice = gasPrice;
    }

    @JSONField(name = "GasLimit")
    public long getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(long gasLimit) {
        this.gasLimit = gasLimit;
    }

    @JSONField(name = "Timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @JSONField(name = "Payload")
    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
    
    
    
    public void showTx(){
      // System.out.println(this.type);
      System.out.println(this.from);
      System.out.println(this.to);
      System.out.println(this.amount);
      System.out.println(this.accountNonce);
      System.out.println(this.gasPrice);
      System.out.println(this.gasLimit);
      System.out.println(this.timestamp);
      System.out.println(this.payload);
    }
}
