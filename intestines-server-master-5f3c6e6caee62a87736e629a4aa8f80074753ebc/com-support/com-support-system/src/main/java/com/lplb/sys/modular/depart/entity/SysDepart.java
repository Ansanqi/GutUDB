package com.lplb.sys.modular.depart.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
/**
 * 组织机构表(SysDepart)表实体类
 *
 * @author Leiziyu
 * @since 2022-04-21 17:45:33
 */
@Data
@TableName("sys_depart")
public class SysDepart {
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID,value = "id")
    private Long id;
    /**
     * 父机构ID
     */
    @TableField(value = "parent_id",insertStrategy = FieldStrategy.IGNORED)
    private Long parentId;
    /**
     * 机构ID
     */
    @TableField("org_id")
    private Long orgId;
    /**
     * 机构/部门名称
     */
    @TableField("depart_name")
    private String departName;
    /**
     * 英文名
     */
    @TableField("depart_name_en")
    private String departNameEn;
    /**
     * 缩写
     */
    @TableField("depart_name_abbr")
    private String departNameAbbr;
    /**
     * 排序
     */
    @TableField("depart_order")
    private Integer departOrder;
    /**
     * 描述
     */
    @TableField("description")
    private String description;
    /**
     * 机构类别 1公司，2组织机构，2岗位
     */
    @TableField("org_category")
    private String orgCategory;
    /**
     * 机构类型 1一级部门 2子部门
     */
    @TableField("org_type")
    private String orgType;
    /**
     * 机构编码
     */
    @TableField("org_code")
    private String orgCode;
    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;
    /**
     * 传真
     */
    @TableField("fax")
    private String fax;
    /**
     * 地址
     */
    @TableField("address")
    private String address;
    /**
     * 备注
     */
    @TableField("memo")
    private String memo;
    /**
     * 状态（1启用，0不启用）
     */
    @TableField("status")
    private String status;
    /**
     * 删除状态（0，正常，1已删除）
     */
    @TableField("del_flag")
    private String delFlag;
    /**
     * 创建人
     */
    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private Long createUser;
    /**
     * 创建日期
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新人
     */
    @TableField(value = "update_user",fill = FieldFill.UPDATE)
    private Long updateUser;
    /**
     * 更新日期
     */
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;
}

