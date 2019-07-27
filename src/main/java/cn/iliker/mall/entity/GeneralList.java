package cn.iliker.mall.entity;

import java.util.List;

//分页通用封装类
public class GeneralList<T> {
    private List<T> list;//根据页码与显示条数返回的集合,不是总数据
    private int totalSize;//总条目
    private int totalPage;//总页码

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
