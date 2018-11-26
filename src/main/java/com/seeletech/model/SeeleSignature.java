package com.seeletech.model;

import com.alibaba.fastjson.annotation.JSONField;

public class SeeleSignature {

    private String sig;

    @JSONField(name = "Sig")
    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }
}
