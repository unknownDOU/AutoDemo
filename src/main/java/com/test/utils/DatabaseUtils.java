package com.test.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * describe:
 *
 * @author douyanfeng
 * @date 2020/01/15
 * 此类用于执行sql语句的准备工作
 *
 */
public class DatabaseUtils {


    /**
     * static 关键字用于方法或变量被调用时不需要创建对象
     */
    public  static SqlSession getSqlSession() throws IOException {

        String resource = "mybatis-config.xml";
        //获取mybatis的xml配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //sqlSession用于执行sql语句
        SqlSession sqlSession = sqlSessionFactory.openSession();

        return sqlSession;

    }

}
