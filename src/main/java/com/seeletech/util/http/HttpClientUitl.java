package com.seeletech.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.seeletech.util.HttpResult;
import com.seeletech.util.response.ResponseUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;

public class HttpClientUitl {

    public static HttpResult httpPostWithJson(String jsonObjStr, String url, int timeout, String user, String password){
        HttpResult result = new HttpResult();
        HttpPost post = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            // set timeout
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
            post = new HttpPost(url);
            // construct  header
            post.setHeader("Content-type", "application/json");
            // user and password
            if(!StringUtils.isEmpty(user)&&!StringUtils.isEmpty(password)){
                byte[] userPwdByte = (user + ":" + password).getBytes();
                String auth = "Basic " +(Base64.encodeBase64(userPwdByte).toString());
                post.setHeader("Authorization", auth);
            }
            String sessionId = getSessionId();
            post.setHeader("SessionId", sessionId);
            // construct  body
            StringEntity entity = new StringEntity(jsonObjStr, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // send Json request
            entity.setContentType("application/json");
            post.setEntity(entity);
            HttpResponse response = httpClient.execute(post);
            // check response code
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != HttpStatus.SC_OK){
                result.setErrMsg("err statusCode not equals success："+statusCode);
            }else{
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null){
                    String str =  EntityUtils.toString(httpEntity,"UTF-8");
                    ResponseUtil res = JSON.parseObject(str, new TypeReference<ResponseUtil>() {});
                    if(res.getError() != null){
                        result.setErrMsg(res.getError().getMessage());
                    }else{
                        if(!StringUtils.isEmpty(res.getResult())){
                            Map mapTypes = JSON.parseObject(str);
                            result.setResult(mapTypes);
                        }
                    }
                }
            }
        } catch (Exception e) {
            result.setErrMsg("err："+e.getMessage());
        }finally{
            if(post != null){
                try {
                    post.releaseConnection();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    result.setErrMsg("err："+e.getMessage());
                }
            }
        }
        return result;
    }

    // 构建唯一会话Id
    public static String getSessionId(){
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
    }
}
