package com.seeletech.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.NotBlank;

public class SeeleSignature {
    @JSONField(name = "Sig")
    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }
    @NotBlank(message="sig can not be empty")
    private String sig;
}
