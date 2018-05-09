package cn.iliker.mall.filter;

import cn.iliker.mall.entity.Adminmanager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PushPageFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
                         FilterChain arg2) throws IOException, ServletException {
        HttpServletRequest httpreq = (HttpServletRequest) arg0;

        // 获取session
        HttpSession session = httpreq.getSession();

        // 获取登录的用户名
        Adminmanager info = (Adminmanager) session.getAttribute("user");

        // 用户未登录时则禁止下载，跳转到登录页面
        if (info != null) {
            httpreq.getRequestDispatcher("/nopermissionpage.jsp").forward(arg0, arg1);
        } else {
            arg2.doFilter(arg0, arg1);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
