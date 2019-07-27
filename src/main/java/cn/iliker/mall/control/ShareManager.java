package cn.iliker.mall.control;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.iliker.mall.entity.Comments;
import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.Share;
import cn.iliker.mall.service.ICommentsSvc;
import cn.iliker.mall.service.IShareSvc;
import cn.iliker.mall.utils.UrlTools;

//处理分享
public class ShareManager extends ActionSupport {
    private int shareid;//分享编号
    private IShareSvc shareSvc;
    private int offset;//当前页码
    private int totalPage;//总页码
    private int totalSize;//总个数
    private int node;//几个月前参数
    private GeneralList<Share> sharelist = null;//按条件筛选返回的集合
    private ICommentsSvc commentsSvc;

    public void setCommentsSvc(ICommentsSvc commentsSvc) {
        this.commentsSvc = commentsSvc;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }


    public int getShareid() {
        return shareid;
    }

    public void setShareid(int shareid) {
        this.shareid = shareid;
    }

    private final Map<Integer, Integer> map = new LinkedHashMap<>();

    public Map<Integer, Integer> getMap() {
        return map;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setShareSvc(IShareSvc shareSvc) {
        this.shareSvc = shareSvc;
    }

    public String showShare() {
        if (offset <= 0) {
            offset = 1;
        }
        try {
            if (sharelist != null) {
                sharelist.getList().clear();
                sharelist.setTotalPage(0);
                sharelist = null;
            }
            sharelist = shareSvc
                    .findAll(offset, 5, node);
            if (sharelist != null) {
                for (Share share : sharelist.getList()) {
                    List<Comments> comm = commentsSvc.findAll(share.getShareId());
                    map.put(share.getShareId(), comm.size());
                }
                totalPage = sharelist.getTotalPage();
                totalSize = sharelist.getTotalSize();
                ActionContext.getContext().put("shares", sharelist);
                return SUCCESS;
            }
        } catch (Exception e) {
        }
        return NONE;
    }

    public String delShare() {
        Share share = shareSvc.findById(shareid);
        if (share != null) {
            List<Comments> commentses = commentsSvc.findAll(share.getShareId());
            boolean isdel = shareSvc.delShare(shareid);
            if (isdel) {
                commentses.stream().filter(comm -> !"".equals(comm.getCommaudiopath())).forEach(comm -> {
                    File delFile = new File(UrlTools.SOUNDDIR + "/" + comm.getCommaudiopath());
                    delFile.delete();
                });
                String[] imgPath = share.getPic().split("#");
                for (String imgUrl : imgPath) {
                    File delFile = new File(UrlTools.SHAREDIR + "/" + imgUrl);
                    delFile.delete();
                }
                return SUCCESS;
            }
        }
        return ERROR;
    }

}
