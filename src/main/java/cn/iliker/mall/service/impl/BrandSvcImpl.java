package cn.iliker.mall.service.impl;

import java.util.List;

import cn.iliker.mall.dao.IBrandDAO;
import cn.iliker.mall.entity.Brand;
import cn.iliker.mall.error.BrandSaveError;
import cn.iliker.mall.error.DelError;
import cn.iliker.mall.service.IBrandSvc;

public class BrandSvcImpl implements IBrandSvc {
    private IBrandDAO brandDAO;

    public void setBrandDAO(IBrandDAO brandDAO) {
        this.brandDAO = brandDAO;
    }

    @Override
    public void save(Brand transientInstance) throws BrandSaveError {
        brandDAO.save(transientInstance);
    }

    @Override
    public void delete(int brandId) throws DelError {
        brandDAO.delete(brandId);
    }

    @Override
    public Brand findById(Integer id) {
        return brandDAO.findById(id);
    }

    @Override
    public boolean updateBrand(Brand brand) {
        return brandDAO.updateBrand(brand);
    }

    @Override
    public List findAll() {
        return brandDAO.findAll();
    }

    @Override
    public List findBrands() throws RuntimeException {
        return brandDAO.findBrands();
    }
}
