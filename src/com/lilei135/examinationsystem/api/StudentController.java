package com.lilei135.examinationsystem.api;

import com.lilei135.examinationsystem.models.Student;
import com.lilei135.examinationsystem.models.Class;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import resource.servletapi.BaseHttpServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/** @author wangsiqian */
@WebServlet("/v1/student")
public class StudentController extends BaseHttpServlet {
    @Override
    protected byte[] handleGet(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        Student student = session.selectOne("getStudent", request.getParameter("studentId"));
        if (student == null) {
            return falseResponse("没有这个学生");
        } else {
            return okResponse(student);
        }
    }

    @Override
    protected byte[] handlePost(HttpServletRequest request)
            throws UnsupportedEncodingException, IOException {
        Map<String, Object> jsonResult = getJson(request);
        String studentId = (String) jsonResult.get("studentId");
        String classId = (String) jsonResult.get("classId");
        String studentName = (String) jsonResult.get("studentName");
        String studentGender = (String) jsonResult.get("studentGender");
        String studentPassword = (String) jsonResult.get("studentPassword");

        if (studentId == null
                || classId == null
                || studentName == null
                || studentGender == null
                || studentPassword == null) {
            return falseResponse("请输入学生Id, 所属班级, 姓名, 性别, 密码");
        }

        SqlSession session = getSession();
        Class queryClass = session.selectOne("getClass", classId);
        if (queryClass == null) {
            return falseResponse("没有这个班级");
        }

        Student student = session.selectOne("getStudent", studentId);
        if (student != null) {
            return falseResponse("该学生已经存在");
        }

        Student newStudent =
                new Student(
                        studentId,
                        queryClass.getClassId(),
                        studentName,
                        studentGender,
                        studentPassword);
        try {
            session.insert("addStudent", newStudent);
        } catch (PersistenceException error) {
            return falseResponse("添加失败");
        }

        session.commit();
        return okResponse("添加成功");
    }

    @Override
    protected byte[] handlePut(HttpServletRequest request)
            throws UnsupportedEncodingException, IOException {
        Map<String, Object> jsonResult = getJson(request);
        String studentId = (String) jsonResult.get("studentId");

        SqlSession session = getSession();
        Student student = session.selectOne("getStudent", studentId);
        if (student == null) {
            return falseResponse("没有这个学生");
        }

        String studentName = (String) jsonResult.get("studentName");
        if (studentName != null) {
            student.setStudentName(studentName);
        }

        String classId = (String) jsonResult.get("classId");
        if (classId != null) {
            student.setClassId(Integer.parseInt(classId));
        }

        String studentGender = (String) jsonResult.get("studentGender");
        if (studentGender != null) {
            student.setStudentGender(studentGender);
        }

        String studentPassword = (String) jsonResult.get("studentPassword");
        if (studentPassword != null) {
            student.setStudentPassword(studentPassword);
        }

        try {
            session.update("updateStudent", student);
        } catch (PersistenceException error) {
            return falseResponse("更新失败");
        }

        session.commit();
        return okResponse("更新成功");
    }

    @Override
    protected byte[] handleDelete(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        String studentId = request.getParameter("studentId");
        Student student = session.selectOne("getStudent", studentId);

        if (student == null) {
            return falseResponse("没有这个学生");
        }

        try {
            session.delete("deleteStudent", studentId);
        } catch (PersistenceException error) {
            return falseResponse("删除失败");
        }

        session.commit();
        return okResponse("删除成功");
    }
}
