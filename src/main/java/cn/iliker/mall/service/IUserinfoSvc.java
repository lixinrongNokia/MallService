package cn.iliker.mall.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import cn.iliker.mall.entity.ProtohuMan;
import cn.iliker.mall.entity.Userdata;
import cn.iliker.mall.entity.Userinfo;

public interface IUserinfoSvc {
    Userinfo faceLogin(String gId);
    int save(Userinfo transientInstance) throws Exception;
    void update(Userinfo transientInstance) throws Exception;
    void delete(Userinfo persistentInstance);

    List findAll();

    List findByProperty(String propertyName, Object value);

    Userinfo findByNickName(String NickName);

    Set<Userdata> findByUId(int uid);
    Userinfo findById(Integer id);
    List<Userinfo> getAllEmail();

    Userinfo findByPhone(Object phone);

    Userinfo login(String loginType, String logtext, String pwd);

    void batchAddUser(List<ProtohuMan> list) throws SQLException;
    List<Userinfo> getSubclassesByUid(int uid) throws RuntimeException;

    void updateLevel(Userinfo userinfo);

    void saveSideData(Userdata userdata) throws RuntimeException;

    Userdata findBodyByUID(int uid);
}