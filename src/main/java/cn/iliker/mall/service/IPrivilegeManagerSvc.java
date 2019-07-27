package cn.iliker.mall.service;

import java.util.List;

import cn.iliker.mall.privilege.PrivilegeGroup;
import cn.iliker.mall.privilege.SystemPrivilege;

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