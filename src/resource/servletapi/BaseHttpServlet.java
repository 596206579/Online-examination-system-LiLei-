package resource.servletapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/** @author wangsiqian */
public abstract class BaseHttpServlet extends HttpServlet {
    private SqlSession session;
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        String resource = "resource/mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.session = sqlSessionFactory.openSession();

        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setCharacterEncoding("UTF-8");

        OutputStream out = resp.getOutputStream();
        out.write(handleGet(req));
        out.flush();
    }

    protected byte[] handleGet(HttpServletRequest request) throws IOException {
        return falseResponse("不支持Get 请求");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setCharacterEncoding("UTF-8");

        OutputStream out = resp.getOutputStream();
        out.write(handlePost(req));
        out.flush();
    }

    protected byte[] handlePost(HttpServletRequest request)
            throws UnsupportedEncodingException, IOException {
        return falseResponse("不支持Post 请求");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setCharacterEncoding("UTF-8");

        OutputStream out = resp.getOutputStream();
        out.write(handlePut(req));
        out.flush();
    }

    protected byte[] handlePut(HttpServletRequest request)
            throws UnsupportedEncodingException, IOException {
        return falseResponse("不支持Put 请求");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setCharacterEncoding("UTF-8");

        OutputStream out = resp.getOutputStream();
        out.write(handleDelete(req));
        out.flush();
    }

    protected byte[] handleDelete(HttpServletRequest request) throws UnsupportedEncodingException {
        return falseResponse("不支持Delete 请求");
    }

    @Override
    public void destroy() {
        this.session.close();
        super.destroy();
    }

    public byte[] okResponse(Object body) throws UnsupportedEncodingException {
        Map<String, Object> response = new HashMap<>(3);
        response.put("result", body);
        response.put("ok", true);
        return JSON.toJSONString(response).getBytes("UTF-8");
    }

    public byte[] falseResponse(String message) throws UnsupportedEncodingException {
        Map<String, Object> response = new HashMap<>(3);
        response.put("message", message);
        response.put("ok", false);
        return JSON.toJSONString(response).getBytes("UTF-8");
    }

    public Map<String, Object> getJson(HttpServletRequest request) throws IOException {
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return JSONObject.parseObject(stringBuilder.toString());
    }

    public SqlSession getSession() {
        return session;
    }

    public String getResourcePath(HttpServletRequest request) {
        return request.getServletContext().getRealPath("") + "WEB-INF/classes/resource/";
    }
}
