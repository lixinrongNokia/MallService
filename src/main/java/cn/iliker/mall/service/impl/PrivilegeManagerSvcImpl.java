package cn.iliker.mall.service.impl;

import java.util.List;

import cn.iliker.mall.dao.IPrivilegeManagerDAO;
import cn.iliker.mall.privilege.PrivilegeGroup;
import cn.iliker.mall.privilege.SystemPrivilege;
import cn.iliker.mall.service.IPrivilegeManagerSvc;

public class PrivilegeManagerSvcImpl implements IPrivilegeManagerSvc {
    private IPrivilegeManagerDAO privilegeManagerDAO;

    public void setPrivilegeManagerDAO(IPrivilegeManagerDAO privilegeManagerDAO) {
        this.privilegeManagerDAO = privilegeManagerDAO;
    }

    @Override
    public List findAllSystemPrivilege() {
        return privilegeManagerDAO.findAllSystemPrivilege();
    }

    @Override
    public void savePrivilegeGroup(PrivilegeGroup privilegeGroup) {
        privilegeManagerDAO.savePrivilegeGroup(privilegeGroup);
    }

    @Override
    public void saveSystemPrivilege(SystemPrivilege systemPrivilege) {
        privilegeManagerDAO.saveSystemPrivilege(systemPrivilege);
    }

    @Override
    public void deletePrivilegeGroup(PrivilegeGroup privilegeGroup) {
        privilegeManagerDAO.deletePrivilegeGroup(privilegeGroup);
    }

    @Override
    public void updatePrivilegeGroup(PrivilegeGroup privilegeGroup) {
        privilegeManagerDAO.updatePrivilegeGroup(privilegeGroup);
    }

    @Override
    public void batchSave(List<SystemPrivilege> systemPrivileges) {
        for (SystemPrivilege systemPrivilege : systemPrivileges) {
            this.saveSystemPrivilege(systemPrivilege);
        }
    }

    @Override
    public List findAllPrivilegeGroup() {
        return privilegeManagerDAO.findAllPrivilegeGroup();
    }

    @Override
    public PrivilegeGroup findPrivilegeGroupByGroupId(String groupId) throws Exception{
        return privilegeManagerDAO.findPrivilegeGroupByGroupId(groupId);
    }
}
