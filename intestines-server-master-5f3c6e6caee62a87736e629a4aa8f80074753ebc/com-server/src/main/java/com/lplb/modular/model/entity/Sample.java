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
 * @Date（日期）： 2023-07-31 10:05:24
 * @Description（描述）：Sample（样本）(Sample)表实体类
 */
@Data
@ApiModel(value = "Sample（样本）(Sample)实体对象")
@TableName("in_sample")
public class Sample extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @TableField(value = "name")
    private String name;

    /**
     * 样本编号
     */
    @ApiModelProperty(value = "样本编号")
    @TableField(value = "sample_no")
    private String sampleNo;

    /**
     * 出现频次
     */
    @ApiModelProperty(value = "出现频次")
    @TableField(value = "frequency")
    private Integer frequency;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

