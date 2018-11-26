package com.seeletech.util.request;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.seeletech.model.Command;
import com.seeletech.model.dto.RequestDTO;
import java.util.Date;

public class RequestUtil {

    public static String getRequestJson(String methodName, Object obj) throws JsonProcessingException {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setParams(new Object[]{obj});
        requestDTO.setId(new Date().getTime());
        requestDTO.setMethod(Command.getNamespace(methodName).concat("_").concat(methodName));

        return JSON.toJSONString(requestDTO);
    }
}
