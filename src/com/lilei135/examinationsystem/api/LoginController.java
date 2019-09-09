package com.lilei135.examinationsystem.api;

import com.lilei135.examinationsystem.models.Student;
import com.lilei135.examinationsystem.models.Teacher;
import org.apache.ibatis.session.SqlSession;
import resource.servletapi.BaseHttpServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/** @author wangsiqian */
@WebServlet("/v1/login")
public class LoginController extends BaseHttpServlet {
    protected byte[] handlePost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Map<String, Object> jsonResult = getJson(request);
        String accountType = (String) jsonResult.get("accountType");
        String accountId = (String) jsonResult.get("accountId");
        String accountPassword = (String) jsonResult.get("accountPassword");
        if (accountType == null || accountId == null || accountPassword == null) {
            return falseResponse("请输入账号, 密码, 账号类型");
        }

        SqlSession session = getSession();

        String teacherAccount = "teacher";
        if (accountType.equals(teacherAccount)) {
            Teacher teacher = session.selectOne("getTeacher", teacherAccount);
            if (teacher == null) {
                return falseResponse("没有这个教师账号");
            }
            String teacherPassword = teacher.getTeacherPassword();
            if (!teacherPassword.equals(accountPassword)) {
                return falseResponse("密码错误");
            } else {
                response.addCookie(new Cookie("teacherId", accountId));
                return okResponse("登陆成功");
            }
        }

        String studentAccount = "student";
        if (accountType.equals(studentAccount)) {
            Student student = session.selectOne("getStudent", studentAccount);
            if (student == null) {
                return falseResponse("没有这个学生账号");
            }
            String studentPassword = student.getStudentPassword();
            if (!studentPassword.equals(accountPassword)) {
                return falseResponse("密码错误");
            } else {
                response.addCookie(new Cookie("studentId", accountId));
                return okResponse("登陆成功");
            }
        }

        String adminAccount = "admin";
        if (accountType.equals(adminAccount)) {
            String adminPassword = "admin";
            if (accountId.equals(adminAccount) && accountPassword.equals(adminPassword)) {
                response.addCookie(new Cookie("adminId", accountId));
                return okResponse("登陆成功");
            } else {
                return falseResponse("密码或账号错误");
            }
        }

        return falseResponse("没有这个账号类型");
    }
}
