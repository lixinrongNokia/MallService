package cn.iliker.mall.dao;

import java.util.List;

import cn.iliker.mall.entity.Comments;

public interface ICommentsDAO {

    void delete(Comments persistentInstance);

    List findAll(int shareId);

}