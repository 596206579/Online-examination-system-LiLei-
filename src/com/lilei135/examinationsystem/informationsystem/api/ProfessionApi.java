package com.lilei135.examinationsystem.informationsystem.api;

import com.lilei135.examinationsystem.informationsystem.models.Department;
import com.lilei135.examinationsystem.informationsystem.models.Profession;
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
@WebServlet("/v1/profession")
public class ProfessionApi extends BaseHttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setCharacterEncoding("UTF-8");

        SqlSession session = getSession();
        Profession profession =
                session.selectOne("getProfession", req.getParameter("professionId"));

        OutputStream out = resp.getOutputStream();
        if (profession == null) {
            out.write(falseResponse("没有这个专业"));
        } else {
            out.write(okResponse(profession));
        }

        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> jsonResult = getJson(req);
        String professionId = (String) jsonResult.get("professionId");
        String professionName = (String) jsonResult.get("professionName");
        String departmentId = (String) jsonResult.get("departmentId");

        OutputStream out = resp.getOutputStream();
        if (professionId == null || professionName == null || departmentId == null) {
            out.write(falseResponse("请输入专业id, 名称, 系部"));
            out.flush();
            return;
        }

        SqlSession session = getSession();
        Department department = session.selectOne("getDepartment", departmentId);
        if (department == null) {
            out.write(falseResponse("没有这个部门"));
            out.flush();
            return;
        }

        Profession profession =
                new Profession(
                        Integer.parseInt(professionId),
                        department.getDepartmentId(),
                        professionName);
        try {
            session.insert("addProfession", profession);
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
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> json = getJson(request);
        String professionId = (String) json.get("professionId");

        SqlSession session = getSession();
        Profession profession = session.selectOne("getProfession", professionId);
        OutputStream out = response.getOutputStream();
        if (profession == null) {
            out.write(falseResponse("没有这个专业"));
            out.flush();
            return;
        }

        Profession newProfession = new Profession(profession.getProfessionId());
        String professionName = (String) json.get("professionName");
        if (professionName != null) {
            newProfession.setProfessionName(professionName);
        } else {
            newProfession.setProfessionName(profession.getProfessionName());
        }

        String departmentId = (String) json.get("departmentId");
        if (departmentId != null) {
            newProfession.setDepartmentId(Integer.parseInt(departmentId));
        } else {
            newProfession.setDepartmentId(profession.getDepartmentId());
        }

        session.update("updateProfession", newProfession);
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
        String professionId = request.getParameter("professionId");
        Profession profession = session.selectOne("getProfession", professionId);

        OutputStream out = response.getOutputStream();
        if (profession == null) {
            out.write(falseResponse("没有这个专业"));
        } else {
            session.delete("deleteProfession", professionId);
            session.commit();
            out.write(okResponse(""));
        }

        out.flush();
    }
}
