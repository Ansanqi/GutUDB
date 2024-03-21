package com.lplb.sys.modular.org.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;

/**
 * 机构关联表(SysOrgRelation)实体类
 *
 * @author Ray
 * @since 2021-12-24 13:16:02
 */
@Data
@TableName("sys_org_relation")
public class SysOrgRelation implements Serializable {

    private static final long serialVersionUID = -26285875295843280L;
    
    /**
    /*主键
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)    
    private Long id;
    /**
    /*主机构ID
    */
    @TableField("master_org_id")
    private Long masterOrgId;
    /**
    /*从结构ID
    */
    @TableField("salve_org_id")
    private Long salveOrgId;
    /**
    /*创建时间
    */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
}
