package com.lplb.core.pojo.base.query;


import com.lplb.core.consts.CommonConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 通用查询字段
 *
 * @author
 * @version 1.0.1
 * @mail
 * @date 2021/8/25 0025 11:53
 */
@Data
public class BaseQuery implements Serializable {

    @ApiModelProperty(value = "页数(不传默认0)", required = false, position = -1, example = "1")
    private Long current = 1L;

    @ApiModelProperty(value = "每页数量(不传默认10)", required = false, position = -1, example = "20")
    private Long size = 10L;

    @ApiModelProperty(value = "导出页数(不传默认0)", required = false, position = -1, example = "1")
    private Long exportCurrent = 1L;

    @ApiModelProperty(value = "每页d导出数量(不传默认100000)", required = false, position = -1, example = "20")
    private Long exportSize = Long.valueOf(CommonConstant.MAX_EXPORT_COUNT);
}
