package cn.iliker.mall.interceptor;

import cn.iliker.mall.entity.Adminmanager;
import cn.iliker.mall.privilege.PrivilegeGroup;
import cn.iliker.mall.privilege.SystemPrivilege;
import cn.iliker.mall.privilege.SystemPrivilegePK;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

import java.lang.reflect.Method;
import java.util.Set;

public class PermissionCheck extends MethodFilterInterceptor {
    @Override
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
        String methodName = actionInvocation.getProxy().getMethod();
        Method currentMethod = actionInvocation.getAction().getClass().getMethod(methodName);
        if (currentMethod != null && currentMethod.isAnnotationPresent(Permission.class)) {
            Adminmanager info = (Adminmanager) actionInvocation.getInvocationContext().getSession().get("user");
            //获取权限校验的注解
            Permission permission = currentMethod.getAnnotation(Permission.class);
            //可以在此判断当前客户是否拥有对应的权限，如果没有可以跳到指定的无权限提示页面，如果拥有则可以继续往下执行。
            SystemPrivilege methodPrivilege = new SystemPrivilege(new SystemPrivilegePK(permission.module(), permission.privilege()));
            if (!validate(info.getPrivilegeGroups(), methodPrivilege)) {
                return "nopermission";
            }
        }
        return actionInvocation.invoke();
    }

    private static boolean validate(Set<PrivilegeGroup> privilegeGroups, SystemPrivilege requirePrivilege) {
        if (!privilegeGroups.isEmpty()) {
            for (PrivilegeGroup group : privilegeGroups) {
                if (group.getSystemPrivileges().contains(requirePrivilege)) {
                    return true;
                }
            }
        }
        return false;
    }
}
