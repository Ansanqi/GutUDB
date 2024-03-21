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
 * @Date（日期）： 2023-07-31 10:05:20
 * @Description（描述）：样本出现频次(SampleSeq)表实体类
 */
@Data
@ApiModel(value = "样本出现频次(SampleSeq)实体对象")
@TableName("in_sample_seq")
public class SampleSeq extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "ID",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 样本ID
     */
    @ApiModelProperty(value = "样本ID")
    @TableField(value = "sample_id")
    private Long sampleId;

    /**
     * 组学ID
     */
    @ApiModelProperty(value = "组学ID")
    @TableField(value = "omics_id")
    private Long omicsId;

    /**
     * 实体名称
     */
    @ApiModelProperty(value = "实体名称")
    @TableField(value = "entity_name")
    private String entityName;

    /**
     * 表名称
     */
    @ApiModelProperty(value = "表名称")
    @TableField(value = "table_name")
    private String tableName;

    /**
     * 文件位置
     */
    @ApiModelProperty(value = "文件位置")
    @TableField(value = "file_url")
    private String fileUrl;

    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 数据项ID
     */
    @ApiModelProperty(value = "数据项ID")
    @TableField(value = "data_id")
    private Long dataId;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @ApiModelProperty(value = "状态（字典 0正常 1停用 2删除）")
    @TableField(value = "status")
    private Integer status;

}

