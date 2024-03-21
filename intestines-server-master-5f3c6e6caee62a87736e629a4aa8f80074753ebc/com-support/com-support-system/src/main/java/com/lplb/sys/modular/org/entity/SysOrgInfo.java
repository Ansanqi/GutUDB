package com.lplb.sys.modular.org.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;

/**
 * 机构信息表(SysOrgInfo)实体类
 *
 * @author Ray
 * @since 2021-12-13 14:07:40
 */
@Data
@TableName("sys_org_info")
public class SysOrgInfo implements Serializable {

    private static final long serialVersionUID = -29768351009576539L;
    
    /**
    /*主键
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)    
    private Long id;
    /**
    /*机构ID
    */
    @TableField("org_uuid")
    private Long orgUuid;
    /**
    /*开放平台应用ID
    */
    @TableField("open_app_id")
    private Long openAppId;

    /**
     * 广告管家账号
     */
    @TableField("advertiser_id")
    private Long advertiserId;
}
