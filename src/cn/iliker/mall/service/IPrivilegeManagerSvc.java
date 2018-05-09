package cn.iliker.mall.service;

import cn.iliker.mall.privilege.PrivilegeGroup;
import cn.iliker.mall.privilege.SystemPrivilege;

import java.util.List;

public interface IPrivilegeManagerSvc {
    List findAllSystemPrivilege();

    List findAllPrivilegeGroup();

    void savePrivilegeGroup(PrivilegeGroup privilegeGroup);

    void saveSystemPrivilege(SystemPrivilege systemPrivilege);

    void deletePrivilegeGroup(PrivilegeGroup privilegeGroup);

    void updatePrivilegeGroup(PrivilegeGroup privilegeGroup);

    void batchSave(List<SystemPrivilege> systemPrivileges);

    PrivilegeGroup findPrivilegeGroupByGroupId(String groupId) throws Exception;
}