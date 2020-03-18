package cn.al.ldemo.web;

import cn.al.ldemo.service.UserService;
import cn.al.ldemo.service.serviceimpl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");*/
        String verifycode = req.getParameter("verifycode");
        HttpSession session = req.getSession();
        String checkcode_server =(String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        if(checkcode_server == null || checkcode_server.length() ==0 || !verifycode.equalsIgnoreCase(checkcode_server)){
            req.setAttribute("login_msg","验证码输入错误");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserService userService = new UserServiceImpl();
        String s = userService.loginService(username,password);
        if(s == null){
            req.setAttribute("login_msg","用户名或密码输入错误");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }else {
            session.setAttribute("s",s);
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
