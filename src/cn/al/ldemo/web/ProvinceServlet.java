package cn.al.ldemo.web;

import cn.al.ldemo.domain.Province;
import cn.al.ldemo.service.UserService;
import cn.al.ldemo.service.serviceimpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/provinceServlet")
public class ProvinceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        BufferedReader reader = request.getReader();
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null){
            sb.append(line);
        }
        reader.close();
        ObjectMapper mapper = new ObjectMapper();
       // String value = mapper.writeValueAsString(sb.toString());
        System.out.println(sb.toString());

        response.setContentType("application/json;charset=utf-8");
        UserService userService = new UserServiceImpl();
        String list = userService.provinceService();

        //mapper.writeValue(response.getWriter(),list);
        response.getWriter().write(list);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
