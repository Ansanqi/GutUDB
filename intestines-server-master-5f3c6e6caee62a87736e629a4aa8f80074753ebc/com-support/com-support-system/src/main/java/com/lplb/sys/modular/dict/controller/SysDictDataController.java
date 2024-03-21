/*
Copyright [2020] [https://www.stylefeng.cn]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Guns源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns-separation
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns-separation
6.若您的项目无法满足以上几点，可申请商业授权，获取Guns商业授权许可，请在官网购买授权，地址为 https://www.stylefeng.cn
 */
package com.lplb.sys.modular.dict.controller;

import cn.hutool.core.util.ObjectUtil;
import com.lplb.core.annotion.BusinessLog;
import com.lplb.core.enums.LogAnnotionOpTypeEnum;
import com.lplb.core.pojo.base.param.BaseParam;
import com.lplb.core.pojo.response.ErrorResponseData;
import com.lplb.core.pojo.response.ResponseData;
import com.lplb.core.pojo.response.SuccessResponseData;
import com.lplb.sys.modular.dict.entity.SysDictData;
import com.lplb.sys.modular.dict.param.SysDictDataParam;
import com.lplb.sys.modular.dict.param.SysDictTypeParam;
import com.lplb.sys.modular.dict.service.SysDictDataService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统字典值控制器
 *
 * @author xuyuxiang, fengshuonan
 * @date 2020/3/31 20:49
 */
@RestController
public class SysDictDataController {

    @Resource
    private SysDictDataService sysDictDataService;

    /**
     * 查询系统字典值
     *
     * @author xuyuxiang, fengshuonan
     * @date 2020/3/31 20:50
     */
    @GetMapping("/system/dictionary-data/page")
//    @BusinessLog(title = "系统字典值_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysDictDataParam sysDictDataParam) {
        return new SuccessResponseData(sysDictDataService.page(sysDictDataParam));
    }

    /**
     * 某个字典类型下所有的字典
     *
     * @author xuyuxiang, fengshuonan
     * @date 2020/3/31 21:03
     */
    @GetMapping("/sysDictData/list")
//    @BusinessLog(title = "系统字典值_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(@Validated(SysDictDataParam.list.class) SysDictDataParam sysDictDataParam) {
        return new SuccessResponseData(sysDictDataService.list(sysDictDataParam));
    }


    /**
     * 根据字典类型编码查询所有的字典
     *
     * @author xuyuxiang, fengshuonan
     * @date 2020/3/31 21:03
     */
    @GetMapping("/system/dictionary-data")
//    @BusinessLog(title = "单条业务字典", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getDictDataByCode(@Validated(BaseParam.searchCode.class) SysDictTypeParam sysDictTypeParam) {
        List<SysDictData> dictDataByCode = sysDictDataService.getDictDataByCode(sysDictTypeParam);
        if (ObjectUtil.isEmpty(dictDataByCode)) {
            return ErrorResponseData.error("字典不存在");
        }
        return new SuccessResponseData(dictDataByCode);
    }

    /**
     * 查看系统字典值
     *
     * @author xuyuxiang, fengshuonan
     * @date 2020/3/31 20:51
     */
    @GetMapping("/sysDictData/detail")
//    @BusinessLog(title = "系统字典值_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(SysDictDataParam.detail.class) SysDictDataParam sysDictDataParam) {
        return new SuccessResponseData(sysDictDataService.detail(sysDictDataParam));
    }

    /**
     * 添加系统字典值
     *
     * @author xuyuxiang, fengshuonan
     * @date 2020/3/31 20:50
     */
    @PostMapping("/system/dictionary-data")
    @BusinessLog(title = "系统字典值_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(SysDictDataParam.add.class) SysDictDataParam sysDictDataParam) {
        sysDictDataService.add(sysDictDataParam);
        return new SuccessResponseData();
    }

    /**
     * 删除系统字典值
     *
     * @author xuyuxiang, fengshuonan
     * @date 2020/3/31 20:50
     */
    @DeleteMapping("/system/dictionary-data/{id}")
    @BusinessLog(title = "系统字典值_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@PathVariable("id") Long id) {
        SysDictDataParam sysDictDataParam = new SysDictDataParam();
        sysDictDataParam.setId(id);
        sysDictDataService.delete(sysDictDataParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑系统字典值
     *
     * @author xuyuxiang, fengshuonan
     * @date 2020/3/31 20:51
     */
    @PutMapping("/system/dictionary-data")
    @BusinessLog(title = "系统字典值_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(SysDictDataParam.edit.class) SysDictDataParam sysDictDataParam) {
        sysDictDataService.edit(sysDictDataParam);
        return new SuccessResponseData();
    }

    /**
     * 修改状态
     *
     * @author stylefeng
     * @date 2020/5/1 9:43
     */
    @PostMapping("/sysDictData/changeStatus")
    @BusinessLog(title = "系统字典值_修改状态", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData changeStatus(@RequestBody @Validated(SysDictDataParam.changeStatus.class) SysDictDataParam sysDictDataParam) {
        sysDictDataService.changeStatus(sysDictDataParam);
        return new SuccessResponseData();
    }
}
