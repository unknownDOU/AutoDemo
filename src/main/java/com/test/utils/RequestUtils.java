package com.test.utils;

import com.alibaba.fastjson.JSON;
import com.test.config.TestConfig;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * describe:
 *
 * @author douyanfeng
 * @date 2020/01/16
 */
public class RequestUtils {

    public static Response getResponse(String url,String method, Map map){
        Response response = null;
        if (map != null){
            if (method.equals("get")){
               response = given().contentType("application/json").params(map)
                        .when().request(method,url);

            }else {
                String mapJson = JSON.toJSONString(map);
                response = given().contentType("application/json").body(mapJson)
                        .when().request(method,url);
            }


        }
        else {
            response = given().when().request(method,url);
        }


        return  response;
    }
}
