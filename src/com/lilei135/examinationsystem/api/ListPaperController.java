package com.lilei135.examinationsystem.api;

import com.lilei135.examinationsystem.models.Paper;
import org.apache.ibatis.session.SqlSession;
import resource.servletapi.BaseHttpServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/** @author wangsiqian */
@WebServlet("/v1/papers")
public class ListPaperController extends BaseHttpServlet {
    @Override
    protected byte[] handleGet(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        List<Paper> papers = session.selectList("listPaper");
        return okResponse(papers);
    }
}
