package com.lilei135.examinationsystem.informationsystem.api;

import com.lilei135.examinationsystem.informationsystem.models.Department;
import com.lilei135.examinationsystem.informationsystem.models.Profession;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import resource.servletapi.BaseHttpServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/** @author wangsiqian */
@WebServlet("/api/v1/profession")
public class ProfessionController extends BaseHttpServlet {
    @Override
    protected byte[] handleGet(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        Profession profession =
                session.selectOne("getProfession", request.getParameter("professionId"));
        if (profession == null) {
            return falseResponse("没有这个专业");
        } else {
            return okResponse(profession);
        }
    }

    @Override
    protected byte[] handlePost(HttpServletRequest request)
            throws UnsupportedEncodingException, IOException {
        Map<String, Object> jsonResult = getJson(request);
        String professionId = (String) jsonResult.get("professionId");
        String professionName = (String) jsonResult.get("professionName");
        String departmentId = (String) jsonResult.get("departmentId");

        if (professionId == null || professionName == null || departmentId == null) {
            return falseResponse("请输入专业id, 名称, 系部");
        }

        SqlSession session = getSession();
        Department department = session.selectOne("getDepartment", departmentId);
        if (department == null) {
            return falseResponse("没有这个部门");
        }

        Profession profession = session.selectOne("getProfession", professionId);
        if (profession != null) {
            return falseResponse("该专业已经存在");
        }

        Profession newProfession =
                new Profession(
                        Integer.parseInt(professionId),
                        department.getDepartmentId(),
                        professionName);
        try {
            session.insert("addProfession", newProfession);
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
        String professionId = (String) json.get("professionId");

        SqlSession session = getSession();
        Profession profession = session.selectOne("getProfession", professionId);
        if (profession == null) {
            return falseResponse("没有这个专业");
        }

        String professionName = (String) json.get("professionName");
        if (professionName != null) {
            // 设置新的专业名
            profession.setProfessionName(professionName);
        }

        String departmentId = (String) json.get("departmentId");
        if (departmentId != null) {
            profession.setDepartmentId(Integer.parseInt(departmentId));
        }

        try {
            session.update("updateProfession", profession);
        } catch (PersistenceException error) {
            return falseResponse("更新失败");
        }

        session.commit();
        return okResponse("更新成功");
    }

    @Override
    protected byte[] handleDelete(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        String professionId = request.getParameter("professionId");
        Profession profession = session.selectOne("getProfession", professionId);

        if (profession == null) {
            return falseResponse("没有这个专业");
        }

        try {
            session.delete("deleteProfession", professionId);
        } catch (PersistenceException error) {
            return falseResponse("删除失败");
        }
        session.commit();
        return okResponse("删除成功");
    }
}
