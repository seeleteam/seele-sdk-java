package com.seeletech.util.response;

public class ResponseUtil {

    private long id;

    private String jsonrpc;

    private ResError error;

    private String result;

    private ResSuccess success;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ResSuccess getSuccess() {
        return success;
    }

    public void setSuccess(ResSuccess success) {
        this.success = success;
    }
}
