package com.lilei135.examinationsystem.informationsystem.tests;

import com.lilei135.examinationsystem.informationsystem.models.Department;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wangsiqian
 */
public class TestDepartment {
    public static void main(String[] args) throws IOException {
        // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
        String resource = "resource/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 然后根据 sqlSessionFactory 得到 session
        SqlSession session = sqlSessionFactory.openSession();

//        getDepartment(session, "1");
        addDepartment(session);

        session.commit();
        session.close();
    }

    private static void getDepartment(SqlSession session, String departmentID) {
        Department department = session.selectOne("getDepartment", departmentID);
        System.out.println(department.getDepartmentId() + " " +  department.getDepartmentName());
    }

    private static void addDepartment(SqlSession session) {
        Department department = new Department(1, "英语系");
        session.insert("addDepartment", department);
    }
}
