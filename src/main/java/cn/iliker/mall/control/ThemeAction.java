package cn.iliker.mall.control;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.ActionSupport;

import cn.iliker.mall.entity.Theme;
import cn.iliker.mall.service.IThemeSvc;
import cn.iliker.mall.utils.Rename_PIC;
import cn.iliker.mall.utils.UrlTools;

public class ThemeAction extends ActionSupport {
    private Theme theme;
    private File themeURL;
    private String themeURLFileName;
    private IThemeSvc themeSvc;

    public File getThemeURL() {
        return themeURL;
    }

    public void setThemeURL(File themeURL) {
        this.themeURL = themeURL;
    }

    public String getThemeURLFileName() {
        return themeURLFileName;
    }

    public void setThemeURLFileName(String themeURLFileName) {
        this.themeURLFileName = themeURLFileName;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public void setThemeSvc(IThemeSvc themeSvc) {
        this.themeSvc = themeSvc;
    }

    public String createTheme() {
        String imgUrl = Rename_PIC.renameUtil(themeURLFileName);
        theme.setThemeURL(imgUrl);
        try {
            themeSvc.save(theme);
            FileUtils.copyFile(themeURL, new File(UrlTools.THEMEDIR, imgUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return SUCCESS;
    }
}
