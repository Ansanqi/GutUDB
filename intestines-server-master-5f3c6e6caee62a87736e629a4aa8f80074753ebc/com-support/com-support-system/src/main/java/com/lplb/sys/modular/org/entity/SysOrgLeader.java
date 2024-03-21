package com.lplb.sys.modular.org.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门领导表(SysOrgLeader)实体类
 *
 * @author Leiziyu
 * @since 2021-09-17 13:20:59
 */
@Data
@TableName("sys_org_leader")
public class SysOrgLeader implements Serializable {

    private static final long serialVersionUID = -40248811880940075L;

    /**
     * 主键
     */
    @TableId(value = "leader_id", type = IdType.ASSIGN_ID)
    private Long leaderId;
    /**
     * 部门ID
     */
    @TableField("org_id")
    private Long orgId;
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 删除标识 0=未删除 1=已删除
     */
    @TableField("deleted")
    private String deleted;
}
