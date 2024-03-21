package com.lplb.modular.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lplb.core.pojo.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-08-06 15:02:07
 * @Description（描述）：搜索记录(SearchHistory)表实体类
 */
@Data
@ApiModel(value = "搜索记录(SearchHistory)实体对象")
@TableName("in_search_history")
public class SearchHistory extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 搜索文本
     */
    @ApiModelProperty(value = "搜索文本")
    @TableField(value = "search_text")
    private String searchText;

    /**
     * 搜索次数
     */
    @ApiModelProperty(value = "搜索次数")
    @TableField(value = "search_count")
    private Integer searchCount;

    /**
     * 关联基因
     */
    @ApiModelProperty(value = "关联基因")
    @TableField(value = "about_gene")
    private String aboutGene;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

