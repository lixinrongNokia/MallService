package cn.iliker.mall.control;

import cn.iliker.mall.entity.Adminmanager;
import cn.iliker.mall.error.ExistsError;
import cn.iliker.mall.error.MyError;
import cn.iliker.mall.interceptor.Permission;
import cn.iliker.mall.privilege.PrivilegeGroup;
import cn.iliker.mall.privilege.SystemPrivilege;
import cn.iliker.mall.privilege.SystemPrivilegePK;
import cn.iliker.mall.service.IPrivilegeManagerSvc;
import cn.iliker.mall.service.IadminManagerSvc;
import cn.iliker.mall.utils.EmailJob;
import cn.iliker.mall.utils.MD5Util;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.iliker.mall.utils.NetSteamUtil.isEmail;
import static cn.iliker.mall.utils.NetSteamUtil.isMobileNO;

//管理员相关
public class AdmininfoAction extends ActionSupport {
    private IadminManagerSvc adminSvc;
    private EmailJob testjob;
    private IPrivilegeManagerSvc privilegeManagerSvc;
    private PrivilegeGroup privilegeGroup;
    private String[] privilegeGroupIds;
    private String privilegeGroupId;
    private String[] systemPrivilegePK;
    // 管理员登录
    private String footprint;
    // 添加用户
    private Adminmanager adminmanager;
    private String regpassword = "";
    private String validateCode = "";
    private String imgcode;
    private long tamp;

    public String[] getPrivilegeGroupIds() {
        return privilegeGroupIds;
    }

    public void setPrivilegeGroupIds(String[] privilegeGroupIds) {
        this.privilegeGroupIds = privilegeGroupIds;
    }

    public String getPrivilegeGroupId() {
        return privilegeGroupId;
    }

    public void setPrivilegeGroupId(String privilegeGroupId) {
        this.privilegeGroupId = privilegeGroupId;
    }

    public PrivilegeGroup getPrivilegeGroup() {
        return privilegeGroup;
    }

    public void setPrivilegeGroup(PrivilegeGroup privilegeGroup) {
        this.privilegeGroup = privilegeGroup;
    }

    public String[] getSystemPrivilegePK() {
        return systemPrivilegePK;
    }

    public void setSystemPrivilegePK(String[] systemPrivilegePK) {
        this.systemPrivilegePK = systemPrivilegePK;
    }

    public void setPrivilegeManagerSvc(IPrivilegeManagerSvc privilegeManagerSvc) {
        this.privilegeManagerSvc = privilegeManagerSvc;
    }

    public Adminmanager getAdminmanager() {
        return adminmanager;
    }

    public void setAdminmanager(Adminmanager adminmanager) {
        this.adminmanager = adminmanager;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public long getTamp() {
        return tamp;
    }

    public void setTamp(long tamp) {
        this.tamp = tamp;
    }

    public String getImgcode() {
        return imgcode;
    }

    public void setImgcode(String imgcode) {
        this.imgcode = imgcode;
    }

    public void setAdminSvc(IadminManagerSvc adminSvc) {
        this.adminSvc = adminSvc;
    }

    public void setTestjob(EmailJob testjob) {
        this.testjob = testjob;
    }

    public String getFootprint() {
        return footprint;
    }

    public String getRegpassword() {
        return regpassword;
    }

    public void setRegpassword(String regpassword) {
        this.regpassword = regpassword;
    }

    /*
         * 管理员登陆，不管是新登陆用户还是重复登陆用户都让登陆
         */
    public String userLogin() {
        Map<String, Object> session = ActionContext.getContext().getSession();// session域容器
        Map<String, Object> application = ActionContext.getContext()
                .getApplication();// application容器

		/*-------------------登陆字段校验开始-------------------*/
        if (adminmanager == null) {
            return INPUT;
        }

        String checkCode = (String) session.get("checkCode");
        if (imgcode != null && !imgcode.equals(checkCode)) {
            ActionContext.getContext().put("error", "验证码不正确");
            return INPUT;
        }
        /*-------------------登陆字段校验结束-------------------*/

        Map<String, Adminmanager> managermap = (Map<String, Adminmanager>) application
                .get("managermap");// 用来存所有的用户,保存在application域中
        if (managermap == null) {
            managermap = new HashMap<>();
            application.put("managermap", managermap);
        }
        Adminmanager logininfo = adminSvc.loginSvc(adminmanager.getNickname(), MD5Util.getMD5Str(new String(MD5Util.decode(adminmanager.getPassword()))));//用户登录
        if (logininfo != null) {
            if (!logininfo.getStatus()) {
                ActionContext.getContext().put("error", "用户已被禁用");
                return ERROR;
            }
            Adminmanager info = managermap.get(adminmanager.getNickname());
            if (info != null) {
                managermap.remove(adminmanager.getNickname());// 把先前登陆过的用户从application域中去除
            }
            // 取登录时间的cookie
            HttpServletRequest request = ServletActionContext.getRequest();
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if ("footprint".equals(cookie.getName())) {
                    String dd = cookie.getValue();
                    footprint = cookie.getValue();
                }
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            // 写登录时间的cookie
            HttpServletResponse response = ServletActionContext.getResponse();
            Cookie loginCookie = new Cookie("footprint",
                    format.format(new Date()));
            loginCookie.setMaxAge(60 * 60 * 24 * 14); // 设置cookie有效期两周
            loginCookie.setPath("/");
            response.addCookie(loginCookie);
            managermap.put(adminmanager.getNickname(), logininfo);// 同一用户对象session域application域都存一份，用作拦截器比较
            session.put("user", logininfo);
            return SUCCESS;
        }
        ActionContext.getContext().put("error", "用户名或密码不正确");
        return INPUT;
    }

    // 用户退出登录
    public String userExit() {
        Map<String, Adminmanager> managermap = (Map<String, Adminmanager>) ActionContext
                .getContext().getApplication().get("managermap");
        Adminmanager user = (Adminmanager) ActionContext.getContext()
                .getSession().get("user");
        if (user != null) {
            ActionContext.getContext().getSession().remove("user");
            managermap.remove(user.getNickname());
            return SUCCESS;
        }
        return NONE;
    }

    public String queryAdmins() {
        List<Adminmanager> list = adminSvc.findAll();
        if (list != null) {
            ActionContext.getContext().put("managers", list);
            return SUCCESS;
        }
        return NONE;
    }

    @Permission(module = "employee", privilege = "insert")
    public String addManager() {
        if (adminmanager.getNickname() == null) {
            ActionContext.getContext().put("errorMessage", "账号不能为空");
            return ERROR;
        }
        /*客户端密码16位加密*/
        if (adminmanager.getPassword() == null || "".equals(adminmanager.getPassword().trim())) {
            ActionContext.getContext().put("errorMessage", "密码不能为空或长度不够");
            return ERROR;
        }
        if (!regpassword.equals(adminmanager.getPassword().trim())) {
            ActionContext.getContext().put("errorMessage", "密码不一致");
            return ERROR;
        }
        String pasePwd = new String(MD5Util.decode(adminmanager.getPassword().trim()));
        if (pasePwd.length() < 6) {
            ActionContext.getContext().put("errorMessage", "密码长度不够");
            return ERROR;
        }
        adminmanager.setPassword(MD5Util.getMD5Str(pasePwd));
        try {
            adminSvc.save(adminmanager);
        } catch (MyError e) {
            ActionContext.getContext().put("errorMessage", e.getMessage());
            return ERROR;
        } catch (ExistsError e) {
            ActionContext.getContext().put("errorMessage", e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    /*跳转管理员权限编辑视图*/
    public String privilege2ManagerView() {
        Adminmanager foundmanager = adminSvc.findById(adminmanager.getId());
        if (foundmanager != null) {
            ActionContext.getContext().put("manager", foundmanager);
            ActionContext.getContext().put("privilegeGroups", privilegeManagerSvc.findAllPrivilegeGroup());
            return SUCCESS;
        }
        return NONE;
    }

    /*管理员权限编辑方法*/
    @Permission(module = "employee", privilege = "privilegeGroupSet")
    public String privilege2Manager() {
        Adminmanager foundmanager = adminSvc.findById(adminmanager.getId());
        if (foundmanager != null && privilegeGroupIds != null) {
            foundmanager.getPrivilegeGroups().clear();
            for (String groupid : privilegeGroupIds) {
                foundmanager.getPrivilegeGroups().add(new PrivilegeGroup(groupid));
            }
            adminSvc.update(foundmanager);
            return SUCCESS;
        }
        return ERROR;
    }

    /*修改管理员视图*/
    public String editManagerView() {
        Adminmanager foundAdminmanger = adminSvc.findById(adminmanager.getId());
        if (foundAdminmanger != null) {
            ActionContext.getContext().put("manager", foundAdminmanger);
            return SUCCESS;
        }
        return NONE;
    }

    /**
     * 修改管理员资料
     */
    @Permission(module = "employee", privilege = "update")
    public String editManager() {
        if (adminmanager.getNickname() == null || adminmanager.getNickname().trim().length() == 0) {
            ActionContext.getContext().put("errorMessage", "用户名不能为空");
            return ERROR;
        }
        Adminmanager foundAdminmanger = adminSvc.findById(adminmanager.getId());
        if (foundAdminmanger == null) {
            ActionContext.getContext().put("errorMessage", "用户不存在");
            return ERROR;
        }
        if (adminmanager.getPassword() != null && !"".equals(regpassword.trim())) {
            if (!regpassword.equals(adminmanager.getPassword().trim()) || "undefined".equals(adminmanager.getPassword().trim().toLowerCase())) {
                ActionContext.getContext().put("errorMessage", "密码不一致");
                return ERROR;
            }
            String pasePwd = new String(MD5Util.decode(adminmanager.getPassword().trim()));
            if (pasePwd.length() < 6) {
                ActionContext.getContext().put("errorMessage", "密码长度不够6位");
                return ERROR;
            }
            foundAdminmanger.setPassword(MD5Util.getMD5Str(pasePwd));
        }
        foundAdminmanger.setNickname(adminmanager.getNickname().trim());
        foundAdminmanger.setGender(adminmanager.getGender());
        if (adminmanager.getEmail() != null && adminmanager.getEmail().trim().length() != 0) {
            if (isEmail(adminmanager.getEmail().trim())) {
                foundAdminmanger.setEmail(adminmanager.getEmail());
            }
        }
        if (adminmanager.getPhone() != null && adminmanager.getPhone().trim().length() != 0) {
            if (isMobileNO(adminmanager.getPhone().trim())) {
                foundAdminmanger.setPhone(adminmanager.getPhone().trim());
            }
        }
        foundAdminmanger.setNickname(adminmanager.getNickname());
        boolean isok = adminSvc.update(foundAdminmanger);
        if (isok) {
            return SUCCESS;
        }
        ActionContext.getContext().put("errorMessage", "添加失败");
        return ERROR;
    }

    public String findPwdFor2() {
        if (adminmanager != null) {
            Adminmanager admin = adminSvc.findByNickname(adminmanager.getNickname());
            if (admin != null) {
                if (admin.getEmail() != null) {
                    try {
                        long time = new Date().getTime();
                        testjob.sendTemplateMail(admin.getNickname(), MD5Util.getMD5Str(admin.getNickname()), time, admin.getEmail());
                        return SUCCESS;
                    } catch (Exception e) {
                        e.printStackTrace();
                        ActionContext.getContext().put("msg", "邮件系统出错，发送失败");
                        return ERROR;
                    }
                }
                ActionContext.getContext().put("msg", "该账户没有绑定邮箱地址");
            }
        }
        return ERROR;
    }

    public String validateUser() {
        if (adminmanager != null) {
            Adminmanager admin = adminSvc.findByNickname(adminmanager.getNickname());
            if (admin != null) {
                long nowTime = new Date().getTime();
                long bad = nowTime - tamp;
                if (validateCode.equals(MD5Util.getMD5Str(admin.getNickname())) && bad < 30 * 60 * 1000) {
                    ActionContext.getContext().put("uname", admin.getNickname());
                    ActionContext.getContext().put("validateCode", validateCode);
                    return SUCCESS;
                }
            }
        }
        return ERROR;
    }

    public String updatePwd() {
        Adminmanager admin = adminSvc.findByNickname(adminmanager.getNickname());
        if (admin != null) {
            if (validateCode.equals(MD5Util.getMD5Str(admin.getNickname()))) {
                String formpwd = adminmanager.getPassword();
                if (formpwd != null && !"".equals(formpwd.trim()) && formpwd.equals(regpassword)) {
                    admin.setPassword(MD5Util.getMD5Str(formpwd));
                    if (adminSvc.update(admin)) {
                        return SUCCESS;
                    }
                }
            }
        }
        return ERROR;
    }

    public String privilegegroupView() {
        ActionContext.getContext().put("privilegeGroups", privilegeManagerSvc.findAllPrivilegeGroup());
        return SUCCESS;
    }

    public String addPrivilegegroupView() {
        ActionContext.getContext().put("privileges", privilegeManagerSvc.findAllSystemPrivilege());
        return SUCCESS;
    }

    public String addPrivilegegroup() {
        if (systemPrivilegePK != null && systemPrivilegePK.length > 0) {
            for (String str : systemPrivilegePK) {
                String[] strings = str.split(",");
                privilegeGroup.getSystemPrivileges().add(new SystemPrivilege(new SystemPrivilegePK(strings[0], strings[1])));
            }
            privilegeManagerSvc.savePrivilegeGroup(privilegeGroup);
            return SUCCESS;
        }
        return ERROR;
    }

    public String editPrivilegeGroupView() {
        try {
            PrivilegeGroup foundPrivilegeGroup = privilegeManagerSvc.findPrivilegeGroupByGroupId(privilegeGroup.getGroupid());
            ActionContext.getContext().put("privilegeGroup", foundPrivilegeGroup);
            ActionContext.getContext().put("privileges", privilegeManagerSvc.findAllSystemPrivilege());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String updatePrivilegeGroup() {
        try {
            PrivilegeGroup foundPrivilegeGroup = privilegeManagerSvc.findPrivilegeGroupByGroupId(privilegeGroup.getGroupid());
            foundPrivilegeGroup.setName(privilegeGroup.getName());
            if (systemPrivilegePK != null && systemPrivilegePK.length > 0) {
                foundPrivilegeGroup.getSystemPrivileges().clear();
                for (String str : systemPrivilegePK) {
                    String[] strings = str.split(",");
                    foundPrivilegeGroup.getSystemPrivileges().add(new SystemPrivilege(new SystemPrivilegePK(strings[0], strings[1])));
                }
                privilegeManagerSvc.updatePrivilegeGroup(foundPrivilegeGroup);
                return SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        privilegeGroupId = privilegeGroup.getGroupid();
        return ERROR;
    }

    /*删除权限组*/
    public String delPrivilegeGroup() {
        try {
            PrivilegeGroup foundPrivilegeGroup = privilegeManagerSvc.findPrivilegeGroupByGroupId(privilegeGroup.getGroupid());
            privilegeManagerSvc.deletePrivilegeGroup(foundPrivilegeGroup);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    /*启用管理员*/
    @Permission(module = "employee", privilege = "leave")
    public String unlockingManager() {
        Adminmanager foundAdminmanager = adminSvc.findById(adminmanager.getId());
        if (foundAdminmanager != null) {
            foundAdminmanager.setStatus(true);
            adminSvc.update(foundAdminmanager);
        }
        return SUCCESS;
    }

    /*停用管理员*/
    @Permission(module = "employee", privilege = "leave")
    public String lockingManager() {
        Adminmanager foundAdminmanager = adminSvc.findById(adminmanager.getId());
        if (foundAdminmanager != null) {
            foundAdminmanager.setStatus(false);
            adminSvc.update(foundAdminmanager);
        }
        return SUCCESS;
    }

}
