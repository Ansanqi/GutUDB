package com.lplb.sys.modular.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lplb.sys.modular.file.entity.SysFileChunk;
import com.lplb.sys.modular.file.mapper.SysFileChunkMapper;
import com.lplb.sys.modular.file.service.SysFileChunkService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 *   @author Ray-zy
 *   @since 2021/9/27 17:59
 **/
@Service
public class SysFileChunkServiceImpl extends ServiceImpl<SysFileChunkMapper, SysFileChunk> implements SysFileChunkService {

    @Override
    public ArrayList<Integer> checkChunk(SysFileChunk chunk) {

        return null;
    }
}
