package com.lilei135.examinationsystem.informationsystem.api;

import com.lilei135.examinationsystem.informationsystem.models.Department;
import org.apache.ibatis.session.SqlSession;
import resource.servletapi.BaseHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


/**
 * @author wangsiqian
 */
@WebServlet("/v1/departments")
public class ListDepartmentApi extends BaseHttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        SqlSession session = getSession();
        List<Department> departments = session.selectList("listDepartment");
        session.commit();

        OutputStream out = response.getOutputStream();
        out.write(okResponse(departments));
        out.flush();
    }
}
