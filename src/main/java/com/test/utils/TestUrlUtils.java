package com.test.utils;

import com.test.config.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * describe:
 *
 * @author douyanfeng
 * @date 2020/01/15
 */
public class TestUrlUtils {

    private  static ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.getDefault());
    private  static String  hostUrl = bundle.getString("host.url");
    public static  String getUrl(InterfaceName interfaceName){

        String uri = "";
        if(interfaceName == InterfaceName.LOGIN){
            uri = bundle.getString("login.uri");

        }
        if (interfaceName == InterfaceName.ADDUSER){
            uri = bundle.getString("addUser.uri");
        }
        if (interfaceName == InterfaceName.GETUSERLIST){
            uri = bundle.getString("getUserList.uri");
        }
        if (interfaceName == InterfaceName.GETUSERINFO){
            uri = bundle.getString("getUserInfo.uri");
        }
        if (interfaceName == InterfaceName.UPDATEUSERINFO){
            uri = bundle.getString("updateUserInfo.uri");
        }

        return hostUrl + uri;



    }

}
