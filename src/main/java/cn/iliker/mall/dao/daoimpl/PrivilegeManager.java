package cn.iliker.mall.dao.daoimpl;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.IPrivilegeManagerDAO;
import cn.iliker.mall.entity.Adminmanager;
import cn.iliker.mall.privilege.PrivilegeGroup;
import cn.iliker.mall.privilege.SystemPrivilege;

public class PrivilegeManager extends HibernateDaoSupport implements IPrivilegeManagerDAO {
    private static final Logger log = LoggerFactory.getLogger(PrivilegeManager.class);

    /**
     * 查询所有的系统权限
     */
    @Override
    public List findAllSystemPrivilege() {
        log.debug("finding all Ps instances");
        try {
            return getHibernateTemplate().find("from cn.iliker.mall.privilege.SystemPrivilege");
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }
    /**
     * 查询所有的系统权限组
     */
    @Override
    public List findAllPrivilegeGroup() {
        log.debug("finding all Ps instances");
        try {
            return getHibernateTemplate().find("from cn.iliker.mall.privilege.PrivilegeGroup");
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }
    /**
     * 保存权限组
     */
    @Override
    public void savePrivilegeGroup(PrivilegeGroup privilegeGroup) {
        log.debug("saving Ps instance");
        try {
            privilegeGroup.setGroupid(UUID.randomUUID().toString());
            getHibernateTemplate().save(privilegeGroup);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    /**
     * 保存权限
     */
    @Override
    public void saveSystemPrivilege(SystemPrivilege systemPrivilege) {
        log.debug("saving Ps instance");
        try {
            getHibernateTemplate().save(systemPrivilege);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    /**
     * 删除权限组
     */
    @Override
    public void deletePrivilegeGroup(PrivilegeGroup privilegeGroup) {
        log.debug("deleting Ps instance");
        try {
            Set<Adminmanager> adminmanagerSet = privilegeGroup.getAdminmanagers();
            if (!adminmanagerSet.isEmpty()) {
                for (Adminmanager adminmanager : adminmanagerSet) {
                    adminmanager.setPrivilegeGroups(null);
                }
            }
            getHibernateTemplate().delete(privilegeGroup);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    /**
     * 修改权限组
     */
    @Override
    public void updatePrivilegeGroup(PrivilegeGroup privilegeGroup) {
        log.debug("attaching dirty Ps instance");
        try {
            getHibernateTemplate().saveOrUpdate(privilegeGroup);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Override
    public PrivilegeGroup findPrivilegeGroupByGroupId(String groupId) throws Exception{
        return getHibernateTemplate().get(PrivilegeGroup.class,groupId);
    }

}
