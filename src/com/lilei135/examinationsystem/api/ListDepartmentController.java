package com.lilei135.examinationsystem.api;

import com.lilei135.examinationsystem.models.Department;
import org.apache.ibatis.session.SqlSession;
import resource.servletapi.BaseHttpServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/** @author wangsiqian */
@WebServlet("/v1/departments")
public class ListDepartmentController extends BaseHttpServlet {
    @Override
    protected byte[] handleGet(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        List<Department> departments = session.selectList("listDepartment");
        session.commit();
        return okResponse(departments);
    }
}
