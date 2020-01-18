package com.test.cases;

import com.alibaba.fastjson.JSON;
import com.test.config.InterfaceName;
import com.test.config.TestConfig;
import com.test.model.LoginCase;
import com.test.utils.DatabaseUtils;
import com.test.utils.RequestUtils;
import com.test.utils.TestUrlUtils;
import io.restassured.response.Response;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * describe:
 *
 * @author douyanfeng
 * @date 2020/01/15
 */
public class LoginTest {

    @BeforeTest(groups = "loginSuccess" ,description = "登陆前的准备工作")
    public void beforeTest(){
        TestConfig.loginUrl = TestUrlUtils.getUrl(InterfaceName.LOGIN);
        TestConfig.addUserUrl = TestUrlUtils.getUrl(InterfaceName.ADDUSER);
        TestConfig.getUserListUrl = TestUrlUtils.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.getUserInfoUrl = TestUrlUtils.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.updateUserInfoUrl = TestUrlUtils.getUrl(InterfaceName.UPDATEUSERINFO);

    }

    @Test(groups = "loginSuccess",description = "登陆接口测试")
    public void loginTest() throws IOException {

        //查询loginCase表中id=1的信息，并把此信息赋值到LoginCase类中
        SqlSession sqlSession = DatabaseUtils.getSqlSession();
        LoginCase loginCase = sqlSession.selectOne("loginInfo",1);

        System.out.println(loginCase.toString());

        //发送请求

        String requestResult = getResult(loginCase);




        //验证结果
        Assert.assertEquals(loginCase.getExpected(),requestResult);






    }

    private String getResult(LoginCase loginCase) {


        //设置请求信息
        Map<String,Object> map = new HashMap<>();
        map.put("username",loginCase.getUsername());
        map.put("password",loginCase.getPassword());


//        //把 map值转化为json格式
//        String jsonParams = JSON.toJSONString(map);
////        String jsonParams = new Gson().toJson(map);
//
//
//        //请求login接口,在省略了then() extract()时，要特别注意每个属性的位置
//        Response response = given()
//                .contentType("application/json;charset=UTF-8")
//                .body(jsonParams)
//                .request("post",TestConfig.loginUrl)
//
//                ;

        //获取登陆接口的相应信息
        Response response = RequestUtils.getResponse(TestConfig.loginUrl,"post",map);

        //获取相应body值
        String responseBody = response.print();


        //设置cookies
       TestConfig.cookies = response.then().extract().cookies();



        return responseBody;


    }

    public static void main(String[] args) {

        Map<String,Object> map = new HashMap<>();
        map.put("username","zhangsan");
        map.put("password","123456");

        Response response = RequestUtils.getResponse("http://localhost:8877/v1/login","post",map);

        System.out.println(response.then().extract().response().print());



    }




}
