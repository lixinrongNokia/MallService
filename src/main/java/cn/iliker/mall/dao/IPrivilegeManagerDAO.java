package cn.iliker.mall.dao;

import java.util.List;

import cn.iliker.mall.privilege.PrivilegeGroup;
import cn.iliker.mall.privilege.SystemPrivilege;

public interface IPrivilegeManagerDAO {
    List findAllSystemPrivilege();

    List findAllPrivilegeGroup();

    void savePrivilegeGroup(PrivilegeGroup privilegeGroup);

    void saveSystemPrivilege(SystemPrivilege systemPrivilege);

    void deletePrivilegeGroup(PrivilegeGroup privilegeGroup);

    void updatePrivilegeGroup(PrivilegeGroup privilegeGroup);

    PrivilegeGroup findPrivilegeGroupByGroupId(String groupId) throws Exception;
}