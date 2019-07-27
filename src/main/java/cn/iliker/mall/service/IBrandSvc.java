package cn.iliker.mall.service;


import java.util.List;

import cn.iliker.mall.entity.Brand;
import cn.iliker.mall.error.BrandSaveError;
import cn.iliker.mall.error.DelError;

public interface IBrandSvc {
    void save(Brand transientInstance) throws BrandSaveError;

    /*
         * (non-Javadoc)
         *
         * @see cn.iliker.daoimpl.IGoods#delete(cn.iliker.entity.Goods)
         */
    void delete(int brandId) throws DelError;

    /*
         * (non-Javadoc)
         *
         * @see cn.iliker.daoimpl.IGoods#findById(java.lang.Integer)
         */
    Brand findById(Integer id);

    boolean updateBrand(Brand brand);

    List findAll();
    List findBrands() throws RuntimeException;

}
