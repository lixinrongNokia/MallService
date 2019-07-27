package cn.iliker.mall.control;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.iliker.mall.entity.Brand;
import cn.iliker.mall.error.BrandSaveError;
import cn.iliker.mall.interceptor.Permission;
import cn.iliker.mall.service.IBrandSvc;
import cn.iliker.mall.utils.Rename_PIC;
import cn.iliker.mall.utils.UrlTools;

public class BrandAction extends ActionSupport {
    private IBrandSvc brandSvc;
    private String brandName;
    private File brandImg;
    private String brandImgFileName;
    private String sendmsg;

    public String getSendmsg() {
        return sendmsg;
    }

    public void setSendmsg(String sendmsg) {
        this.sendmsg = sendmsg;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public File getBrandImg() {
        return brandImg;
    }

    public void setBrandImg(File brandImg) {
        this.brandImg = brandImg;
    }

    public String getBrandImgFileName() {
        return brandImgFileName;
    }

    public void setBrandImgFileName(String brandImgFileName) {
        this.brandImgFileName = brandImgFileName;
    }

    public void setBrandSvc(IBrandSvc brandSvc) {
        this.brandSvc = brandSvc;
    }

    @Permission(module = "brand", privilege = "insert")
    public String execute() {

        if (null == brandImgFileName || "".equals(brandImgFileName)) {
            ActionContext.getContext().put("saveerror", "品牌名不能为空");
            return ERROR;
        }
        if (brandImg == null) {
            ActionContext.getContext().put("saveerror", "图片不能为空");
            return ERROR;
        }
        String nameName = Rename_PIC.renameUtil(brandImgFileName);
        Brand brand = new Brand();
        brand.setBrandName(brandName);
        brand.setImgUrl(nameName);
        try {
            brandSvc.save(brand);
        } catch (BrandSaveError brandSaveError) {
            ActionContext.getContext().put("saveerror", "添加不成功");
            return ERROR;
        }

        try {
            FileUtils.copyFile(brandImg, new File(UrlTools.BRANDDIR, nameName));
        } catch (IOException e) {
            ActionContext.getContext().put("saveerror", "添加不成功");
            return ERROR;
        } catch (Exception e) {
            ActionContext.getContext().put("saveerror", "系统出现异常");
            return ERROR;
        }
        sendmsg = "添加成功";
        ActionContext.getContext().put("sendmsg", sendmsg);
        return SUCCESS;
    }
}
