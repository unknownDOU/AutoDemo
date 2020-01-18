package com.test.cases;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonArray;
import com.test.config.TestConfig;
import com.test.model.GetUserListCase;
import com.test.model.User;
import com.test.utils.DatabaseUtils;
import com.test.utils.RequestUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * describe:
 *
 * @author douyanfeng
 * @date 2020/01/16
 */
public class GetUserListTest {

    @Test(dependsOnGroups = "loginSuccess",description = "获取用户列表接口")
    public void  getUserListTest() throws IOException, InterruptedException {

        SqlSession sqlSession = DatabaseUtils.getSqlSession();
        GetUserListCase getUserListCase = sqlSession.selectOne("getUserListCase",1);

        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);

        //请求用户列表接口

        JSONArray responseResult = getResult(getUserListCase);


        //休眠3000毫秒,避免出现userList结果未查询结束，就执行了断言语句
        Thread.sleep(3000);

        // 验证结果
        //1、根据getUserListCase中的参数值提取数据库User对应的查询接口
        List<User> userList = sqlSession.selectList(getUserListCase.getExpected(),getUserListCase);

        //2、使用迭代器遍历List，检查userList取值是否成功
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        //3、比较responseResult和userList中的值是否一致
        JSONArray userJsonArray = new JSONArray(userList);
        for (int i =0; i< userJsonArray.length();i++){
            JSONObject expected = responseResult.getJSONObject(i);
            JSONObject actaul = userJsonArray.getJSONObject(i);

            Assert.assertEquals(actaul.get("username"),expected.get("username"));
            System.out.println(expected);
            System.out.println(actaul);
        }



    }

    private JSONArray getResult(GetUserListCase getUserListCase) {

        Map<String,Object> map = new HashMap<>();
        map.put("username",getUserListCase.getUsername());
        map.put("age",getUserListCase.getAge());
        map.put("sex",getUserListCase.getSex());

        String mapJson = JSON.toJSONString(map);

        Response response = given().contentType("application/json").body(mapJson).cookies(TestConfig.cookies)
                .when().request("post",TestConfig.getUserListUrl);


        JSONArray jsonArray = new JSONArray(response.asString());

        return jsonArray;





    }
}
