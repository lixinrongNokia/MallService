package cn.iliker.mall.entity;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * 一级商品类别
 * Crowd entity. @author MyEclipse Persistence Tools
 */
@Indexed
@Analyzer(impl = SmartChineseAnalyzer.class)//分词器
public class Crowd implements java.io.Serializable {

    // Fields
    @DocumentId
    private Integer id;
    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES)
    private String name;//名称
    // Constructors

    /**
     * default constructor
     */
    public Crowd() {
    }

    public Crowd(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}