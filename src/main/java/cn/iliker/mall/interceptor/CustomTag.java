package cn.iliker.mall.interceptor;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import cn.iliker.mall.entity.Adminmanager;
import cn.iliker.mall.privilege.PrivilegeGroup;
import cn.iliker.mall.privilege.SystemPrivilege;
import cn.iliker.mall.privilege.SystemPrivilegePK;

public class CustomTag extends TagSupport {
    private String module;
    private String privilege;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    private PageContext pageContext;

    @Override
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    @Override
    public int doStartTag() throws JspException {
        boolean result = false;
        Adminmanager adminmanager = (Adminmanager) pageContext.getSession().getAttribute("user");
        SystemPrivilege privilege = new SystemPrivilege(new SystemPrivilegePK(this.module, this.privilege));
        for (PrivilegeGroup group : adminmanager.getPrivilegeGroups()) {
            if (group.getSystemPrivileges().contains(privilege)) {
                result = true;
                break;
            }
        }
        return result ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

}
