package com.lplb.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.modular.mapper.SingleCellGeneExpressItemMapper;
import com.lplb.modular.model.entity.SingleCellGeneExpressItem;
import com.lplb.modular.service.SingleCellGeneExpressItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author（作者）： BealHe
 * @Date（日期）： 2023-07-27 18:35:44
 * @Description（描述）：单细胞组学基因表达数据项(SingleCellGeneExpressItem)表服务实现类
 */
@Service
@Transactional
public class SingleCellGeneExpressItemServiceImpl extends ServiceImpl<SingleCellGeneExpressItemMapper, SingleCellGeneExpressItem> implements SingleCellGeneExpressItemService {

}

