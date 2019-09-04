package com.lilei135.examinationsystem.informationsystem.api;

import com.lilei135.examinationsystem.informationsystem.models.Class;
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
@WebServlet("/api/v1/class")
public class ClassController extends BaseHttpServlet {
    @Override
    protected byte[] handleGet(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        Class queryClass = session.selectOne("getClass", request.getParameter("classId"));
        if (queryClass == null) {
            return falseResponse("没有这个班级");
        } else {
            return okResponse(queryClass);
        }
    }

    @Override
    protected byte[] handlePost(HttpServletRequest request)
            throws UnsupportedEncodingException, IOException {
        Map<String, Object> jsonResult = getJson(request);
        String classId = (String) jsonResult.get("classId");
        String professionId = (String) jsonResult.get("professionId");
        String className = (String) jsonResult.get("className");

        if (classId == null || professionId == null || className == null) {
            return falseResponse("请输入班级Id, 所属专业, 名称");
        }

        SqlSession session = getSession();
        Profession profession = session.selectOne("getProfession", professionId);
        if (profession == null) {
            return falseResponse("没有这个专业");
        }

        Class newClass =
                new Class(Integer.parseInt(classId), profession.getProfessionId(), className);

        try {
            session.insert("addClass", newClass);
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
        String classId = (String) jsonResult.get("classId");

        SqlSession session = getSession();
        Class queryClass = session.selectOne("getClass", classId);
        if (queryClass == null) {
            return falseResponse("没有这个班级");
        }

        String professionId = (String) jsonResult.get("professionId");
        if (professionId != null) {
            queryClass.setProfessionId(Integer.parseInt(professionId));
        }

        String className = (String) jsonResult.get("className");
        if (className != null) {
            queryClass.setClassName(className);
        }

        try {
            session.update("updateClass", queryClass);
        } catch (PersistenceException error) {
            return falseResponse("更新失败");
        }

        session.commit();
        return okResponse("更新成功");
    }

    @Override
    protected byte[] handleDelete(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        String classId = request.getParameter("classId");
        Class queryClass = session.selectOne("getClass", classId);
        if (queryClass == null) {
            return falseResponse("没有这个班级");
        }

        try {
            session.delete("deleteClass", classId);
        } catch (PersistenceException error) {
            return falseResponse("删除失败");
        }

        session.commit();
        return okResponse("删除成功");
    }
}
