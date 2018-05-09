package cn.iliker.mall.control;

import cn.iliker.mall.entity.Comments;
import cn.iliker.mall.service.ICommentsSvc;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

//评论
public class CommSvcAction extends ActionSupport {
    private ICommentsSvc commentsSvc;
    private int shareid;

    public void setCommentsSvc(ICommentsSvc commentsSvc) {
        this.commentsSvc = commentsSvc;
    }

    public int getShareid() {
        return shareid;
    }


    public void setShareid(int shareid) {
        this.shareid = shareid;
    }


    public String findCommByShareId() {
        List<Comments> comms = commentsSvc.findAll(shareid);
        if (comms != null) {
            ActionContext.getContext().put("comms", comms);
            return SUCCESS;
        }
        return NONE;
    }
}
