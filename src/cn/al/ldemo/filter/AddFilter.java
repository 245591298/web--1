package cn.al.ldemo.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Set;

@WebFilter(value = "/addServlet", dispatcherTypes = {DispatcherType.REQUEST})
public class AddFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        ServletRequest proxyRequest = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader()
                , req.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        if (method.getName().equals("getParameterMap")) {
                            HashMap<String, String[]> map = new HashMap<>(req.getParameterMap());
                            Set<String> keys = map.keySet();
                            for (String key : keys) {
                                String value = map.get(key)[0];
                                if (value.equals("段淇昱")) {
                                    value = value.replaceAll("段淇昱", "***");
                                    String[] s = {value};
                                    map.put(key, s);
                                }
                            }
                            return map;
                        }
                        return method.invoke(req, args);
                    }
                });
        chain.doFilter(proxyRequest,resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

}
