package com.test.model;

import lombok.Data;

/**
 * describe:
 *
 * @author douyanfeng
 * @date 2020/01/16
 */
@Data
public class AddUserCase {

    public String username;
    public String password;
    public String age;
    public String sex;
    public String permission;
    public String isDelete;
    public String expected;
}
