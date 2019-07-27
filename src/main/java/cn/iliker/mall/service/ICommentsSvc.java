package cn.iliker.mall.service;

import java.util.List;

import cn.iliker.mall.entity.Comments;

public interface ICommentsSvc {

    void delete(Comments persistentInstance);

    List findAll(int shareId);

}