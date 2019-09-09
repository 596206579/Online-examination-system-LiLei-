package com.lilei135.examinationsystem.api;

import com.lilei135.examinationsystem.models.Department;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import resource.servletapi.BaseHttpServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/** @author wangsiqian */
@WebServlet("/v1/department")
public class DepartmentController extends BaseHttpServlet {
    @Override
    protected byte[] handleGet(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        Department department =
                session.selectOne("getDepartment", request.getParameter("departmentId"));
        if (department == null) {
            return falseResponse("没有这个部门");
        } else {
            return okResponse(department);
        }
    }

    @Override
    protected byte[] handlePost(HttpServletRequest request)
            throws UnsupportedEncodingException, IOException {
        Map<String, Object> jsonResult = getJson(request);
        String departmentName = (String) jsonResult.get("departmentName");
        String departmentId = (String) jsonResult.get("departmentId");

        if (departmentName == null || departmentId == null) {
            return falseResponse("请输入部门名称或ID");
        }

        SqlSession session = getSession();
        Department department = session.selectOne("getDepartment", departmentId);
        if (department != null) {
            return falseResponse("该部门已经存在");
        }

        Department newDepartment = new Department(Integer.parseInt(departmentId), departmentName);
        try {
            session.insert("addDepartment", newDepartment);
        } catch (PersistenceException error) {
            return falseResponse("添加失败");
        }

        session.commit();
        return okResponse("添加成功");
    }

    @Override
    protected byte[] handlePut(HttpServletRequest request)
            throws UnsupportedEncodingException, IOException {
        Map<String, Object> json = getJson(request);
        String departmentId = (String) json.get("departmentId");

        SqlSession session = getSession();
        Department department = session.selectOne("getDepartment", departmentId);
        if (department == null) {
            return falseResponse("没有这个部门");
        }

        String departmentName = (String) json.get("departmentName");
        if (departmentName == null) {
            return falseResponse("请输入新的系名");
        }

        department.setDepartmentName(departmentName);
        try {
            session.update("updateDepartment", department);
        } catch (PersistenceException error) {
            return falseResponse("更新失败");
        }
        session.commit();
        return okResponse("更新成功");
    }

    @Override
    protected byte[] handleDelete(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        String departmentId = request.getParameter("departmentId");
        Department department = session.selectOne("getDepartment", departmentId);

        if (department == null) {
            return falseResponse("没有这个部门");
        }

        try {
            session.delete("deleteDepartment", departmentId);
        } catch (PersistenceException error) {
            return falseResponse("删除失败");
        }

        session.commit();
        return okResponse("删除成功");
    }
}
