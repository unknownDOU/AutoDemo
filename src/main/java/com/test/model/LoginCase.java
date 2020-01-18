package com.test.model;

import lombok.Data;

/**
 * describe:
 *
 * @author douyanfeng
 * @date 2020/01/15
 * loginCase各个字段
 */
@Data
public  class LoginCase {

    private  int id ;
    private  String username;
    private  String password;
    private  String expected;


    @Override
    public String toString(){
        return (
                "{id:"+ id + "," +
                "username:" + username + ","+
                "password:" + password + ","+
                "expected:" + expected + "}"
        );

    }


}
