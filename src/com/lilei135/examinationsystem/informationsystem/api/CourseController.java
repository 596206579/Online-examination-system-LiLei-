package com.lilei135.examinationsystem.informationsystem.api;

import com.lilei135.examinationsystem.informationsystem.models.Class;
import com.lilei135.examinationsystem.informationsystem.models.Course;
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
@WebServlet("/v1/course")
public class CourseController extends BaseHttpServlet {
    @Override
    protected byte[] handleGet(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        Course course = session.selectOne("getCourse", request.getParameter("courseId"));
        if (course == null) {
            return falseResponse("没有这个课程");
        } else {
            return okResponse(course);
        }
    }

    @Override
    protected byte[] handlePost(HttpServletRequest request)
            throws UnsupportedEncodingException, IOException {

        Map<String, Object> jsonResult = getJson(request);
        String courseId = (String) jsonResult.get("courseId");
        String teacherId = (String) jsonResult.get("teacherId");
        String classId = (String) jsonResult.get("classId");
        String courseName = (String) jsonResult.get("courseName");
        String courseStatus = (String) jsonResult.get("courseStatus");

        if (courseId == null
                || teacherId == null
                || classId == null
                || courseName == null
                || courseStatus == null) {
            return falseResponse("请输入课程Id, 教师ID, 班级ID, 课程名，课程状态");
        }

        SqlSession session = getSession();
        Course course = session.selectOne("getCourse", courseId);
        if (course != null) {
            return falseResponse("该课程已经存在");
        }

        Teacher teacher = session.selectOne("getTeacher", teacherId);
        if (teacher == null) {
            return falseResponse("没有该教师");
        }

        Class queryClass = session.selectOne("getClass", classId);
        if (queryClass == null) {
            return falseResponse("没有该班级");
        }

        Course newCourse =
                new Course(
                        Integer.parseInt(courseId),
                        teacher.getTeacherId(),
                        queryClass.getClassId(),
                        courseName,
                        courseStatus);
        try {
            session.insert("addCourse", newCourse);
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
        String courseId = (String) jsonResult.get("courseId");

        SqlSession session = getSession();
        Course course = session.selectOne("getCourse", courseId);
        if (course == null) {
            return falseResponse("没有这个课程");
        }

        String teacherId = (String) jsonResult.get("teacherId");
        if (teacherId != null) {
            course.setTeacherId(Integer.parseInt(teacherId));
        }

        String classId = (String) jsonResult.get("classId");
        if (classId != null) {
            course.setClassId(Integer.parseInt(courseId));
        }

        String courseName = (String) jsonResult.get("courseName");
        if (courseName != null) {
            course.setCourseName(courseName);
        }

        String courseStatus = (String) jsonResult.get("courseStatus");
        if (courseStatus != null) {
            course.setCourseStatus(courseStatus);
        }

        try {
            session.update("updateCourse", course);
        } catch (PersistenceException error) {
            return falseResponse("更新失败");
        }

        session.commit();
        return okResponse("更新成功");
    }

    @Override
    protected byte[] handleDelete(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        String courseId = request.getParameter("courseId");
        Course course = session.selectOne("getCourse", courseId);
        if (course == null) {
            return falseResponse("没有该课程");
        }

        try {
            session.delete("deleteCourse", courseId);
        } catch (PersistenceException error) {
            return falseResponse("删除失败");
        }

        session.commit();
        return okResponse("删除成功");
    }
}
