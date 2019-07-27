package cn.iliker.mall.control;

import com.opensymphony.xwork2.ActionSupport;

import cn.iliker.mall.entity.FlashSale;
import cn.iliker.mall.service.IFlashsaleSvc;

public class FlashSaleAction extends ActionSupport {
    private IFlashsaleSvc fixme;

    private FlashSale flashsale;

    public FlashSale getFlashsale() {
        return flashsale;
    }

    public void setFlashsale(FlashSale flashsale) {
        this.flashsale = flashsale;
    }

    public void setFixme(IFlashsaleSvc fixme) {
        this.fixme = fixme;
    }

    @Override
    public String execute() throws Exception {
        flashsale.setStatus(true);
        fixme.save(flashsale);
        return SUCCESS;
    }
}
