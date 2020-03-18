package cn.al.ldemo.web;

import cn.al.ldemo.domain.PageBean;
import cn.al.ldemo.domain.User;
import cn.al.ldemo.service.UserService;
import cn.al.ldemo.service.serviceimpl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/partPageServlet")
public class PartPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String currentPage = request.getParameter("currentPage");
        String rows = request.getParameter("rows");

        if(currentPage == null || currentPage.length() == 0){
            currentPage = "1";
        }
        if(rows == null || rows.length() == 0 ){
            rows = "5";
        }

        HashMap<String, String[]> condition = new HashMap<>(request.getParameterMap());

        UserService userService = new UserServiceImpl();
        PageBean<User> pb = userService.partPageService(condition,currentPage,rows);

        HttpSession session = request.getSession();
        session.setAttribute("pb",pb);
        session.setAttribute("condition",condition);
        response.sendRedirect(request.getContextPath()+"/list.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
