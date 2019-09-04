package com.lilei135.examinationsystem.informationsystem.api;

import com.lilei135.examinationsystem.informationsystem.models.Teacher;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import resource.servletapi.BaseHttpServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/** @author wangsiqian */
@WebServlet("/v1/teacher")
public class TeacherController extends BaseHttpServlet {
    @Override
    protected byte[] handleGet(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        Teacher teacher = session.selectOne("getTeacher", request.getParameter("teacherId"));
        if (teacher == null) {
            return falseResponse("没有这个教师");
        }
        {
            return okResponse(teacher);
        }
    }

    @Override
    protected byte[] handlePost(HttpServletRequest request)
            throws UnsupportedEncodingException, IOException {
        Map<String, Object> jsonResult = getJson(request);
        String teacherId = (String) jsonResult.get("teacherId");
        String teacherName = (String) jsonResult.get("teacherName");
        String teacherLevel = (String) jsonResult.get("teacherLevel");

        if (teacherId == null || teacherName == null || teacherLevel == null) {
            return falseResponse("请输入教师的Id, 姓名, 等级");
        }

        SqlSession session = getSession();
        Teacher teacher = session.selectOne("getTeacher", teacherId);
        if (teacher != null) {
            return falseResponse("该教师ID已存在");
        }

        Teacher newTeacher = new Teacher(Integer.parseInt(teacherId), teacherName, teacherLevel);
        try {
            session.insert("addTeacher", newTeacher);
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
        String teacherId = (String) jsonResult.get("teacherId");

        SqlSession session = getSession();
        Teacher teacher = session.selectOne("getTeacher", teacherId);
        if (teacher == null) {
            return falseResponse("没有这个老师");
        }

        String teacherName = (String) jsonResult.get("teacherName");
        if (teacherName != null) {
            teacher.setTeacherName(teacherName);
        }

        String teacherLevel = (String) jsonResult.get("teacherLevel");
        if (teacherLevel != null) {
            teacher.setTeacherLevel(teacherLevel);
        }

        try {
            session.update("updateTeacher", teacher);
        } catch (PersistenceException error) {
            return falseResponse("更新失败");
        }

        session.commit();
        return okResponse("更新成功");
    }

    @Override
    protected byte[] handleDelete(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        String teacherId = request.getParameter("teacherId");
        Teacher teacher = session.selectOne("getTeacher", teacherId);
        if (teacher == null) {
            return falseResponse("没有这个老师");
        }

        try {
            session.delete("deleteTeacher", teacherId);
        } catch (PersistenceException error) {
            return falseResponse("删除失败");
        }

        session.commit();
        return okResponse("删除成功");
    }
}
