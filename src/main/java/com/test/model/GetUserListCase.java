package com.test.model;

import lombok.Data;

/**
 * describe:
 *
 * @author douyanfeng
 * @date 2020/01/16
 */
@Data
public class GetUserListCase {
    public String username;
    public String age;
    public String sex;
    public String expected;
}
