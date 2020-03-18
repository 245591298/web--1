package cn.al.ldemo.web;

import cn.al.ldemo.service.UserService;
import cn.al.ldemo.service.serviceimpl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/deleteSelectServlet")
public class DeleteSelectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] uids = request.getParameterValues("uid");

        System.out.println(Arrays.toString(uids));

        UserService userService = new UserServiceImpl();
        userService.deleteSelectUserService(uids);

        response.sendRedirect(request.getContextPath()+"/partPageServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
