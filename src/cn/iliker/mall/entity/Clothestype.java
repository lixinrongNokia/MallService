package cn.iliker.mall.entity;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.hibernate.search.annotations.*;

import java.util.HashSet;
import java.util.Set;
@Indexed(index = "clothestype")
@Analyzer(impl = SmartChineseAnalyzer.class)//分词器
public class Clothestype implements java.io.Serializable {

    // Fields
    @DocumentId
    private Integer id;
    private Crowd crowd;//一级类别
    @Field(index= Index.YES, analyze= Analyze.NO,store= Store.YES)
    private String name;//类别名称
    private String typeimg;//图标
//    private Set goodses = new HashSet(0);

    // Constructors

    /**
     * default constructor
     */
    public Clothestype() {
    }

    /**
     * minimal constructor
     */
    public Clothestype(Integer id, String typeimg) {
        this.id = id;
        this.typeimg = typeimg;
    }

    /**
     * minimal constructor
     */
    public Clothestype(Crowd crowd, String typeimg) {
        this.crowd = crowd;
        this.typeimg = typeimg;
    }

    /**
     * full constructor
     */
    public Clothestype(Crowd crowd, String name, String typeimg) {
        this.crowd = crowd;
        this.name = name;
        this.typeimg = typeimg;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Crowd getCrowd() {
        return this.crowd;
    }

    public void setCrowd(Crowd crowd) {
        this.crowd = crowd;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeimg() {
        return this.typeimg;
    }

    public void setTypeimg(String typeimg) {
        this.typeimg = typeimg;
    }

}