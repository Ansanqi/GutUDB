package com.lplb.sys.modular.depart.model;

import com.lplb.sys.modular.depart.entity.SysDepart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 部门表 封装树结构的部门的名称的实体类
 * <p>
 * 
 * @Author Steve
 * @Since 2019-01-22 
 *
 */
public class DepartIdModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long key;

    /**
     * 主键ID
     */
    private Long value;

    /**
     * 部门名称
     */
    private String title;
    
    List<DepartIdModel> children = new ArrayList<>();
    
    /**
     * 将SysDepartTreeModel的部分数据放在该对象当中
     * @param treeModel
     * @return
     */
    public DepartIdModel convert(SysDepartTreeModel treeModel) {
        this.key = treeModel.getId();
        this.value = treeModel.getId();
        this.title = treeModel.getDepartName();
        return this;
    }
    
    /**
     * 该方法为用户部门的实现类所使用
     * @param sysDepart
     * @return
     */
    public DepartIdModel convertByUserDepart(SysDepart sysDepart) {
        this.key = sysDepart.getId();
        this.value = sysDepart.getId();
        this.title = sysDepart.getDepartName();
        return this;
    } 

    public List<DepartIdModel> getChildren() {
        return children;
    }

    public void setChildren(List<DepartIdModel> children) {
        this.children = children;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
