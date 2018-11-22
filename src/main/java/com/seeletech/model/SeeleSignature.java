package com.seeletech.model;

import com.alibaba.fastjson.annotation.JSONField;

public class SeeleSignature {
    @JSONField(name = "Sig")
    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    private String sig;
}
