package com.test.cases;

import com.alibaba.fastjson.JSON;
import com.test.config.TestConfig;
import com.test.model.AddUserCase;
import com.test.utils.DatabaseUtils;
import io.restassured.response.Response;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

/**
 * describe:
 *
 * @author douyanfeng
 * @date 2020/01/16
 */
public class AddUserTest {


    @Test(dependsOnGroups = "loginSuccess" ,description = "添加用户接口测试")
    public void addUserTest() throws IOException, InterruptedException {

        SqlSession sqlSession = DatabaseUtils.getSqlSession();
        AddUserCase addUserCase = sqlSession.selectOne("addUserInfo",1);

        //请求添加用户接口
        String responseResult = getResult(addUserCase);

        //验证结果

        Thread.sleep(3000);

        Assert.assertEquals(responseResult,addUserCase.getExpected());




    }

    private String getResult(AddUserCase addUserCase) {

        Map<String,Object> map = new HashMap<>();
        map.put("username",addUserCase.getUsername());
        map.put("password",addUserCase.getPassword());
        map.put("age",addUserCase.getAge());
        map.put("sex",addUserCase.getSex());
        map.put("permission",addUserCase.getPermission());
        map.put("isDelete",addUserCase.getIsDelete());

        String mapJson = JSON.toJSONString(map);

        Response response =
                given().contentType("application/json").body(mapJson).cookies(TestConfig.cookies)
                .when().request("post", TestConfig.addUserUrl)
                ;

        String responseResult = response.print();


        return responseResult;


    }


}
