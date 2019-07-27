package cn.iliker.mall.privilege;

import java.util.HashSet;
import java.util.Set;

public class SystemPrivilege {
    private SystemPrivilegePK id;
    private String name;
    private Set<PrivilegeGroup> privilegeGroups = new HashSet(0);

    public SystemPrivilege() {
    }

    public SystemPrivilege(SystemPrivilegePK id) {
        this.id = id;
    }

    public SystemPrivilege(String module, String privilege, String name) {
        this.id = new SystemPrivilegePK(module, privilege);
        this.name = name;
    }

    public SystemPrivilegePK getId() {
        return id;
    }

    public void setId(SystemPrivilegePK id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PrivilegeGroup> getPrivilegeGroups() {
        return privilegeGroups;
    }

    public void setPrivilegeGroups(Set<PrivilegeGroup> privilegeGroups) {
        this.privilegeGroups = privilegeGroups;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        final SystemPrivilege other = (SystemPrivilege) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
