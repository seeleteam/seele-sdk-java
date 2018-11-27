package com.seeletech.util.request;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.seeletech.model.Command;
import com.seeletech.model.dto.RequestDTO;
import java.util.Date;
import java.util.List;

public class RequestUtil {

    public static String getRequestJson(String methodName, List<Object> objList) throws JsonProcessingException {
        RequestDTO requestDTO = new RequestDTO();
        Object[] paramArr = new Object[objList.size()];
        for(int i = 0;i < objList.size();i++) {
            paramArr[i] = objList.get(i);
        }
        requestDTO.setParams(paramArr);
        requestDTO.setId(new Date().getTime());
        requestDTO.setMethod(Command.getNamespace(methodName).concat("_").concat(methodName));

        return JSON.toJSONString(requestDTO);
    }
}
