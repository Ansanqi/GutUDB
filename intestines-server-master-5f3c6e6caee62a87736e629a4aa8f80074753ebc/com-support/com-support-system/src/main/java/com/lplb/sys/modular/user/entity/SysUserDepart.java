package com.lplb.sys.modular.user.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
/**
 * (SysUserDepart)表实体类
 *
 * @author Leiziyu
 * @since 2022-04-25 15:09:52
 */
@Data
@TableName("sys_user_depart")
public class SysUserDepart {
    private static final long serialVersionUID = -63185262625288759L;
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID,value = "id")
    private Long id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 部门id
     */
    @TableField("dep_id")
    private Long depId;
}

