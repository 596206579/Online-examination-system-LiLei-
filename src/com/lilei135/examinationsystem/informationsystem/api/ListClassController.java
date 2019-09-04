package com.lilei135.examinationsystem.informationsystem.api;

import org.apache.ibatis.session.SqlSession;
import resource.servletapi.BaseHttpServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/** @author wangsiqian */
@WebServlet("/v1/classes")
public class ListClassController extends BaseHttpServlet {
    @Override
    protected byte[] handleGet(HttpServletRequest request) throws UnsupportedEncodingException {
        SqlSession session = getSession();
        List<Class> classes = session.selectList("listClass");
        session.commit();
        return okResponse(classes);
    }
}
