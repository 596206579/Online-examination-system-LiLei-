package com.lilei135.examinationsystem.api;

import com.lilei135.examinationsystem.models.Paper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import resource.servletapi.BaseHttpServlet;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/** @author wangsiqian */
public class PaperController extends BaseHttpServlet {
    @Override
    protected byte[] handleGet(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        Paper paper = session.selectOne("getPaper", request.getParameter("paperId"));
        if (paper == null) {
            return falseResponse("没有这个试卷信息");
        } else {
            return okResponse(paper);
        }
    }

    @Override
    protected byte[] handlePost(HttpServletRequest request)
            throws UnsupportedEncodingException, IOException {
        Map<String, Object> jsonResult = getJson(request);
        String studentId = (String) jsonResult.get("studentId");
        String paperGrade = (String) jsonResult.get("paperGrade");
        String paperSubject = (String) jsonResult.get("paperSubject");

        if (studentId == null || paperGrade == null || paperSubject == null) {
            return falseResponse("请输入试卷Id, 学生Id, 分数, 科目");
        }

        Paper newPaper = new Paper(studentId, Integer.parseInt(paperGrade), paperSubject);
        SqlSession session = getSession();
        try {
            session.insert("addPaper", newPaper);
        } catch (PersistenceException error) {
            return falseResponse("添加失败");
        }

        session.commit();
        return okResponse("添加成功");
    }

    @Override
    protected byte[] handleDelete(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        String paperId = request.getParameter("paperId");
        Paper paper = session.selectOne("getPaper", paperId);

        if (paper == null) {
            return falseResponse("没有这个提交记录");
        }

        try {
            session.delete("deletePaper", paperId);
        } catch (PersistenceException error) {
            return falseResponse("删除失败");
        }
        session.commit();
        return okResponse("删除成功");
    }
}
