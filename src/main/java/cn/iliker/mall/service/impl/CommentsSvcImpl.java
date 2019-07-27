package cn.iliker.mall.service.impl;

import java.util.List;

import cn.iliker.mall.dao.ICommentsDAO;
import cn.iliker.mall.entity.Comments;
import cn.iliker.mall.service.ICommentsSvc;

public class CommentsSvcImpl implements ICommentsSvc {
    private ICommentsDAO dao;

    public void setDao(ICommentsDAO dao) {
        this.dao = dao;
    }

    @Override
    public void delete(Comments persistentInstance) {
        dao.delete(persistentInstance);
    }

    @Override
    public List findAll(int shareId) {
        return dao.findAll(shareId);
    }

}
