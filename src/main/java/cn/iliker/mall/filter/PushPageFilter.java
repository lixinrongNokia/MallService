package cn.iliker.mall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.iliker.mall.entity.Adminmanager;

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
