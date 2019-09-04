package com.lilei135.examinationsystem.informationsystem.api;

import com.lilei135.examinationsystem.informationsystem.models.Department;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import resource.servletapi.BaseHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/** @author wangsiqian */
@WebServlet("/v1/department")
public class DepartmentApi extends BaseHttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        SqlSession session = getSession();
        Department department =
                session.selectOne("getDepartment", request.getParameter("departmentId"));

        OutputStream out = response.getOutputStream();
        if (department == null) {
            out.write(falseResponse("没有这个部门"));
        } else {
            out.write(okResponse(department));
        }

        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> jsonResult = getJson(request);
        String departmentName = (String) jsonResult.get("departmentName");
        String departmentId = (String) jsonResult.get("departmentId");

        OutputStream out = response.getOutputStream();
        if (departmentName == null || departmentId == null) {
            out.write(falseResponse("请输入部门名称或ID"));
            out.flush();
            return;
        }

        SqlSession session = getSession();
        Department department = new Department(Integer.parseInt(departmentId), departmentName);
        try {
            session.insert("addDepartment", department);
        } catch (PersistenceException error) {
            out.write(falseResponse("添加失败"));
            out.flush();
            return;
        }

        session.commit();
        out.write(okResponse(""));
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        SqlSession session = getSession();
        String departmentId = request.getParameter("departmentId");
        Department department = session.selectOne("getDepartment", departmentId);

        OutputStream out = response.getOutputStream();
        if (department == null) {
            out.write(falseResponse("没有这个部门"));
        } else {
            session.delete("deleteDepartment", departmentId);
            session.commit();
            out.write(okResponse(""));
        }

        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> json = getJson(request);
        String departmentId = (String) json.get("departmentId");

        SqlSession session = getSession();
        Department department = session.selectOne("getDepartment", departmentId);

        OutputStream out = response.getOutputStream();
        if (department == null) {
            out.write(falseResponse("没有这个部门"));
        } else {
            String departmentName = (String) json.get("departmentName");
            Department newDepartment =
                    new Department(Integer.parseInt(departmentId), departmentName);
            session.update("updateDepartment", newDepartment);
            session.commit();
            out.write(okResponse(""));
        }

        out.flush();
    }
}
