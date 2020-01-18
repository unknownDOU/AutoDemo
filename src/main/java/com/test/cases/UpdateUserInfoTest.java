package com.test.cases;

import com.alibaba.fastjson.JSON;
import com.test.config.TestConfig;
import com.test.model.UpdateUserInfoCase;
import com.test.model.User;
import com.test.utils.DatabaseUtils;
import io.restassured.response.Response;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * describe:
 *
 * @author douyanfeng
 * @date 2020/01/17
 */
public class UpdateUserInfoTest {

    @Test(dependsOnGroups = "loginSuccess" ,description = "用户更新接口测试")
    public void updateUserInfo() throws IOException, InterruptedException {
        SqlSession sqlSession = DatabaseUtils.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = sqlSession.selectOne("updateUserInfoCase",1);


        //请求更新接口获取响应值
        int responseResult = getResult(updateUserInfoCase);
        System.out.println(responseResult);

        //验证结果
        //休眠3000毫秒
        Thread.sleep(10000);

        User user = sqlSession.selectOne(updateUserInfoCase.getExpected(),updateUserInfoCase);

        System.out.println(user.getUsername());
        System.out.println(updateUserInfoCase.getUsername());

        Assert.assertEquals(user.getUsername(),updateUserInfoCase.getUsername());
        Assert.assertEquals(user.getPassword(),updateUserInfoCase.getPassword());
        Assert.assertEquals(user.getAge(),updateUserInfoCase.getAge());
        Assert.assertEquals(user.getSex(),updateUserInfoCase.getSex());
        Assert.assertEquals(user.getPermission(),updateUserInfoCase.getPermission());
        Assert.assertEquals(user.getIsDelete(),updateUserInfoCase.getIsDelete());

    }

    private int getResult(UpdateUserInfoCase updateUserInfoCase) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",updateUserInfoCase.getUserId());
        map.put("username",updateUserInfoCase.getUsername());
        map.put("password",updateUserInfoCase.getPassword());
        map.put("age",updateUserInfoCase.getAge());
        map.put("sex",updateUserInfoCase.getSex());
        map.put("permission",updateUserInfoCase.getPermission());
        map.put("isDelete",updateUserInfoCase.getIsDelete());

        String mapJson = JSON.toJSONString(map);

        Response response = given().contentType("application/json").body(mapJson).cookies(TestConfig.cookies)
                .when().post(TestConfig.updateUserInfoUrl);

        String result = response.asString();

        return Integer.parseInt(result);
    }
}
