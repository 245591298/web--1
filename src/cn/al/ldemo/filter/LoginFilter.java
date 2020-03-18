package cn.al.ldemo.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(value = "/*", dispatcherTypes = {DispatcherType.REQUEST})
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest requset = (HttpServletRequest) req;
        String uri = requset.getRequestURI();
        if(uri.contains("/js/") || uri.contains("/css/") || uri.contains("/fonts/") || uri.contains("/checkCode")
            || uri.contains("/login.jsp") || uri.contains("/loginServlet") || uri.contains("/register.jsp")
        || uri.contains("/findAllServlet") || uri.contains("/registerServlet")){
            chain.doFilter(req,resp);
        }else{
            HttpSession session = requset.getSession();
            String s = (String) session.getAttribute("s");
            if(s != null){
                chain.doFilter(req,resp);
            }else {
                req.setAttribute("login_msg", "请先登录");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

}
