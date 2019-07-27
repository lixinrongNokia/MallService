package cn.iliker.mall.entity;


import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Indexed(index = "brand")
public class Brand {
    @DocumentId
    private Integer brandId;
    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES)
    private String BrandName;
    private String imgUrl;
    /*@OneToMany
    private Set<Goods> goods = new HashSet<>(0);*/

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = Integer.parseInt(brandId);
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }
}
