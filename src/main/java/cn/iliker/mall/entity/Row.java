package cn.iliker.mall.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFPictureData;

/**
 * excel行数据封装
 * Created by LIXINRONG on 2016/9/11.
 */
public class Row {
    private List<String> picNames=new ArrayList<>(200);
    private List<HSSFPictureData> pictureDatas = new ArrayList<>(200);

    public List<String> getPicNames() {
        return picNames;
    }

    public void setPicNames(List<String> picNames) {
        this.picNames = picNames;
    }

    public List<HSSFPictureData> getPictureDatas() {
        return pictureDatas;
    }

    public void setPictureDatas(List<HSSFPictureData> pictureDatas) {
        this.pictureDatas = pictureDatas;
    }
}
