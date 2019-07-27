package cn.iliker.mall.service.impl;

import java.util.List;

import cn.iliker.mall.dao.IClothesType;
import cn.iliker.mall.entity.Clothestype;
import cn.iliker.mall.error.MyError;
import cn.iliker.mall.service.IClothesTypeSvc;

public class ClothesTypeSvcImpl implements IClothesTypeSvc {
    private IClothesType clothesType;

    public void setClothesType(IClothesType clothesType) {
        this.clothesType = clothesType;
    }

    @Override
    public void save(Clothestype transientInstance) throws MyError {
        clothesType.save(transientInstance);
    }

    @Override
    public void delete(Clothestype persistentInstance) {
        clothesType.delete(persistentInstance);
    }

    @Override
    public Clothestype findById(Integer id) {
        return clothesType.findById(id);
    }

    @Override
    public List findAll() {
        return clothesType.findAll();
    }

    @Override
    public List findAll(int crowdid) {
        return clothesType.findAll(crowdid);
    }

    @Override
    public List<Object[]> findAllType() {
        return clothesType.findAllType();
    }
}
