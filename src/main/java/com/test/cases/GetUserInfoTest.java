package com.test.cases;

import com.alibaba.fastjson.JSON;
import com.test.config.TestConfig;
import com.test.model.GetUserInfoCase;
import com.test.model.User;
import com.test.utils.DatabaseUtils;
import com.test.utils.RequestUtils;
import io.restassured.response.Response;
import jdk.nashorn.internal.scripts.JS;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * describe:
 *
 * @author douyanfeng
 * @date 2020/01/17
 */
public class GetUserInfoTest {

    @Test(dependsOnGroups = "loginSuccess",description = "获取用户信息接口测试")
    public void getUserInfo() throws IOException, InterruptedException {
        SqlSession sqlSession = DatabaseUtils.getSqlSession();
        GetUserInfoCase getUserInfoCase = sqlSession.selectOne("getUserInfoCase",1);

        //请求用户信息接口获取响应值
        JSONArray responseResult = getResult(getUserInfoCase);
        System.out.println(responseResult);

        Thread.sleep(3000);

        //验证结果
        User user = sqlSession.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);
//        System.out.println(user);
//        JSONObject json = new JSONObject(user.toString());
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.put(json);
//        System.out.println(jsonArray);

        List<User> userList = new ArrayList<>();
        userList.add(user);

        JSONArray jsonArray = new JSONArray(userList);
        System.out.println(jsonArray);

        Assert.assertEquals(jsonArray.getJSONObject(0).get("isDelete"),responseResult.getJSONObject(0).get("isDelete"));

    }

    private JSONArray getResult(GetUserInfoCase getUserInfoCase) {

        Map<String,Object> map = new HashMap<>();
        map.put("id",getUserInfoCase.getUserId());
        String jsonMap = JSON.toJSONString(map);
        String response = given().cookies(TestConfig.cookies).contentType("application/json").body(jsonMap)
                .when().request("post",TestConfig.getUserInfoUrl).asString();

        JSONArray result = new JSONArray(response);
        return result;


    }
}
