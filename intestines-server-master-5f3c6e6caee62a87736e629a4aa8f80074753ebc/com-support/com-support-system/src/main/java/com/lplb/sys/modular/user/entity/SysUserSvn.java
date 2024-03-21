package com.lplb.sys.modular.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户SVN账号信息(SysUserSvn)实体类
 *
 * @author Leiziyu
 * @since 2021-07-19 09:32:40
 */
@Data
@TableName("sys_user_svn")
public class SysUserSvn implements Serializable {

    private static final long serialVersionUID = -60278190573672009L;

    /**
     * 主键
     */
    @TableId(value = "account_id", type = IdType.ASSIGN_ID)
    private Long accountId;
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    /**
     * svn用户名
     */
    @TableField("svn_name")
    private String svnName;
    /**
     * svn密码
     */
    @TableField("svn_pwd")
    private String svnPwd;
}
