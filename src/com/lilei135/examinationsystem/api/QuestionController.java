package com.lilei135.examinationsystem.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import resource.servletapi.BaseHttpServlet;

import org.apache.commons.io.FileUtils;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

/** @author wangsiqian */
@WebServlet("/v1/questions")
public class QuestionController extends BaseHttpServlet {
    @Override
    protected byte[] handleGet(HttpServletRequest request) throws IOException {
        File inputStream = new File(getResourcePath(request) + "static/exam.json");
        String content = FileUtils.readFileToString(inputStream, "UTF-8");
        JSONArray array = (JSONArray) JSON.parse(content);
        int index = (int) (Math.random() * array.size());
        return okResponse(array.get(index));
    }
}
