package cn.al.ldemo.ajax;

import cn.al.ldemo.domain.User;
import cn.al.ldemo.service.UserService;
import cn.al.ldemo.service.serviceimpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/findAllServlet")
public class FindAllServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String username = request.getParameter("username");

        UserService userService = new UserServiceImpl();
        Boolean flag = userService.findUsernameService(username);

        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = new HashMap<>();
        if(flag){
            map.put("Exists",false);
            map.put("hin","用户名可用");
        }else{
            map.put("Exists",true);
            map.put("hin","已存在"+username+"用户名");
        }
        mapper.writeValue(response.getWriter(),map);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
