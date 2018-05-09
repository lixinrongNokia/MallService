package cn.iliker.mall.dao;

import cn.iliker.mall.entity.Comments;

import java.util.List;

public interface ICommentsDAO {

    void delete(Comments persistentInstance);

    List findAll(int shareId);

}