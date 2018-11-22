package com.seeletech.util.response;

public class ResponseUtil {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public ResError getError() {
        return error;
    }

    public void setError(ResError error) {
        this.error = error;
    }

    private int id;

    private String jsonrpc;

    private ResError error;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;

    public ResSuccess getSuccess() {
        return success;
    }

    public void setSuccess(ResSuccess success) {
        this.success = success;
    }

    private ResSuccess success;
}
