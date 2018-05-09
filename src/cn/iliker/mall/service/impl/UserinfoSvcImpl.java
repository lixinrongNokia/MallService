/**
 *
 */
package cn.iliker.mall.service.impl;

import cn.iliker.mall.dao.IUserAccountDAO;
import cn.iliker.mall.dao.IUserinfoDAO;
import cn.iliker.mall.entity.*;
import cn.iliker.mall.service.IAliYunAccountSvc;
import cn.iliker.mall.service.IUserinfoSvc;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * @author lixinrong
 */
public class UserinfoSvcImpl implements IUserinfoSvc {
    private IUserinfoDAO dao;
    private IAliYunAccountSvc aliYunAccountSvc;
    private IUserAccountDAO userAccountDAO;

    public void setUserAccountDAO(IUserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    public void setAliYunAccountSvc(IAliYunAccountSvc aliYunAccountSvc) {
        this.aliYunAccountSvc = aliYunAccountSvc;
    }

    public void setDao(IUserinfoDAO dao) {
        this.dao = dao;
    }

    @Override
    public Userinfo faceLogin(String gId) {
        return dao.faceLogin(gId);
    }

    /*@Override
    public void save(Userinfo transientInstance) throws Exception {
        if (aliYunAccountSvc.getAccount(transientInstance.getPhone()))
            aliYunAccountSvc.delAccount(transientInstance.getPhone());
        if (aliYunAccountSvc.addAccount(transientInstance.getPhone(), transientInstance.getPassword(), transientInstance.getNickname())) {
            dao.save(transientInstance);
            Wallet wallet = new Wallet(transientInstance.getPhone());
            GiveAccount giveAccount = new GiveAccount(transientInstance.getPhone());
            IntegralAccount integralAccount = new IntegralAccount(transientInstance.getPhone());
            userAccountDAO.saveWallet(wallet);
            userAccountDAO.saveGiveAccount(giveAccount);
            userAccountDAO.saveIntegral(integralAccount);
        }
    }*/

    @Override
    public int save(Userinfo transientInstance) throws Exception {
        if (aliYunAccountSvc.getAccount(transientInstance.getPhone()))
            aliYunAccountSvc.delAccount(transientInstance.getPhone());
        if (aliYunAccountSvc.addAccount(transientInstance.getPhone(), transientInstance.getPassword(), transientInstance.getNickname())) {
            return dao.save(transientInstance);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Userinfo transientInstance) throws Exception {
        if (aliYunAccountSvc.getAccount(transientInstance.getPhone())) {
            aliYunAccountSvc.updateAccount(transientInstance.getPhone(), null, transientInstance.getPassword(), null);
        } else {
            aliYunAccountSvc.addAccount(transientInstance.getPhone(), transientInstance.getPassword(), transientInstance.getNickname());
        }
        dao.update(transientInstance);
    }

    @Override
    public void delete(Userinfo persistentInstance) {
        dao.delete(persistentInstance);
    }

    /*
     * (non-Javadoc)
     *
     * @see cn.iliker.serivce.IUserinfoSvc#findAll()
     */
    @Override
    public List<?> findAll() {
        return dao.findAll();
    }

    @Override
    public Userinfo findByNickName(String NickName) {
        return dao.findByNickName(NickName);
    }

    @Override
    public Set<Userdata> findByUId(int uid) {
        return dao.findByUId(uid);
    }

    @Override
    public Userinfo findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public List<Userinfo> getAllEmail() {
        return dao.getAllEmail();
    }

    @Override
    public Userinfo findByPhone(Object phone) {
        return dao.findByPhone(phone);
    }

    @Override
    public List findByProperty(String propertyName, Object value) {
        return dao.findByProperty(propertyName, value);
    }

    @Override
    public Userinfo login(String loginType, String logtext, String pwd) {
        List<Userinfo> list = dao.findByProperty(loginType, logtext);
        if (!list.isEmpty()) {
            Userinfo userinfo = list.get(0);
            if (pwd.equals(userinfo.getPassword())) {
                return userinfo;
            }
        }
        return null;
    }

    @Override
    public void batchAddUser(List<ProtohuMan> list) throws SQLException {
        dao.batchAddUser(list);
    }

    @Override
    public List<Userinfo> getSubclassesByUid(int uid) throws RuntimeException {
        return dao.getSubclassesByUid(uid);
    }

    @Override
    public void updateLevel(Userinfo userinfo) {

    }

    @Override
    public void saveSideData(Userdata userdata) throws RuntimeException{
        dao.saveSideData(userdata);
    }

    @Override
    public Userdata findBodyByUID(int uid) {
        return dao.findBodyByUID(uid);
    }
}
