package cn.iliker.mall.dao;


import cn.iliker.mall.entity.ProtohuMan;
import cn.iliker.mall.entity.Userdata;
import cn.iliker.mall.entity.Userinfo;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IUserinfoDAO {

    int save(Userinfo transientInstance) throws RuntimeException;

    void update(Userinfo transientInstance) throws RuntimeException;

    void delete(Userinfo persistentInstance);

    Userinfo findById(Integer id);

    List findByProperty(String propertyName, Object value);

    Userinfo findByPhone(Object phone);

    List findAll();

    Userinfo findByNickName(String NickName);

    Set<Userdata> findByUId(int uid);

    List<Userinfo> getAllEmail();

    Userinfo faceLogin(String gId);

    void batchAddUser(List<ProtohuMan> list) throws SQLException;

    List<Userinfo> getSubclassesByUid(int uid) throws RuntimeException;

    void updateLevel(Userinfo userinfo);

    void saveSideData(Userdata userdata) throws RuntimeException;

    Userdata findBodyByUID(int uid);
}