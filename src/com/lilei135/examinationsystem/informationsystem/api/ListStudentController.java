package com.lilei135.examinationsystem.informationsystem.api;

import com.lilei135.examinationsystem.informationsystem.models.Student;
import org.apache.ibatis.session.SqlSession;
import resource.servletapi.BaseHttpServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/** @author wangsiqian */
@WebServlet("/api/v1/students")
public class ListStudentController extends BaseHttpServlet {
    @Override
    protected byte[] handleGet(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        List<Student> students = session.selectList("listStudent");
        session.commit();
        return okResponse(students);
    }
}
