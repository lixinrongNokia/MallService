package cn.iliker.mall.interceptor;


import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

import cn.iliker.mall.entity.Adminmanager;

//用户是否登录校验拦截
public class LoginCheck extends MethodFilterInterceptor {

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
        Adminmanager info = (Adminmanager) ActionContext.getContext()
                .getSession().get("user");//获取session域用户
        Map<String, Adminmanager> managermap = (Map<String, Adminmanager>) ActionContext
                .getContext().getApplication().get("managermap");
        if (info == null) {
            return ActionSupport.LOGIN;//没有登陆
        }
       /*判断session域用户与application域是否相同*/
        if (managermap != null && !managermap.isEmpty()) {
            Adminmanager oldinfo = managermap.get(info.getNickname());
            if (oldinfo == null || oldinfo.hashCode() != info.hashCode()) {
                ActionContext.getContext().getSession().remove("user");
                ActionContext.getContext().put("error", "你被踢了,注意密码安全");
                return ActionSupport.LOGIN;
            }
        }
        return invocation.invoke();
    }

}
