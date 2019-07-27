package cn.iliker.mall.privilege;

import java.util.HashSet;
import java.util.Set;

import cn.iliker.mall.entity.Adminmanager;

public class PrivilegeGroup implements java.io.Serializable {
    private String groupid;
    private String name;
    private Set<SystemPrivilege> systemPrivileges = new HashSet(0);
    private Set<Adminmanager> adminmanagers = new HashSet(0);

    public PrivilegeGroup() {
    }

    public PrivilegeGroup(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SystemPrivilege> getSystemPrivileges() {
        return systemPrivileges;
    }

    public void setSystemPrivileges(Set<SystemPrivilege> systemPrivileges) {
        this.systemPrivileges = systemPrivileges;
    }

    public Set<Adminmanager> getAdminmanagers() {
        return adminmanagers;
    }

    public void setAdminmanagers(Set<Adminmanager> adminmanagers) {
        this.adminmanagers = adminmanagers;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((groupid == null) ? 0 : groupid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PrivilegeGroup other = (PrivilegeGroup) obj;
        if (groupid == null) {
            if (other.groupid != null)
                return false;
        } else if (!groupid.equals(other.groupid))
            return false;
        return true;
    }
}
