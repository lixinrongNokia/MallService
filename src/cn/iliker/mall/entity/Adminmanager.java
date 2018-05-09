package cn.iliker.mall.entity;

//import cn.iliker.mall.privilege.PrivilegeGroup;

import cn.iliker.mall.entity.stateattr.Gender;
import cn.iliker.mall.privilege.PrivilegeGroup;

import java.util.HashSet;
import java.util.Set;

/**
 * Adminmanager entity. @author MyEclipse Persistence Tools
 */

public class Adminmanager implements java.io.Serializable {

    // Fields
    private Integer id;
    private String nickname;
    private Gender gender = Gender.WOMEN;
    private String password;
    private String email;
    private String phone;
    private Boolean status = false;//状态
    private Set<PrivilegeGroup> privilegeGroups = new HashSet<>();

    /**
     * default constructor
     */
    public Adminmanager() {
    }

    public Adminmanager(String nickname) {
        this.nickname = nickname;
    }

    /**
     * full constructor
     */
    public Adminmanager(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return this.password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<PrivilegeGroup> getPrivilegeGroups() {
        return privilegeGroups;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPrivilegeGroups(Set<PrivilegeGroup> privilegeGroups) {
        this.privilegeGroups = privilegeGroups;
    }
}