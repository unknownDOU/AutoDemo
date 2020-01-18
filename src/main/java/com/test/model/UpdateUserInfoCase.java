package com.test.model;

import lombok.Data;

/**
 * describe:
 *
 * @author douyanfeng
 * @date 2020/01/17
 */
@Data
public class UpdateUserInfoCase {

    private int userId;
    private String username;
    private String password;
    private String age;
    private String sex;
    private String permission;
    private String isDelete;
    private String expected;
}
