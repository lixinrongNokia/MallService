package cn.iliker.mall.service;

import cn.iliker.mall.entity.Comments;

import java.util.List;

public interface ICommentsSvc {

    void delete(Comments persistentInstance);

    List findAll(int shareId);

}