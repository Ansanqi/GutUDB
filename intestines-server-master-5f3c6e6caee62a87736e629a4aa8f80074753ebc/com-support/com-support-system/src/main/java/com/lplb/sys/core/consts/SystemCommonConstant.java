package com.lplb.sys.core.consts;

import com.lplb.core.context.constant.ConstantContextHolder;
import com.lplb.framework.util.OSUtil;

/**
 *   @author Ray-zy
 *   @since 2021/10/19 15:45
 **/
public interface SystemCommonConstant {

    /**
     * 顶级部门ID
     */
    Long TOP_ORG_ID = 1410139203211223042L;

    String uploadFolder = OSUtil.isWin ? ConstantContextHolder.getDefaultFileUploadPathForWindows() : ConstantContextHolder.getDefaultFileUploadPathForLinux();

    String DELETED = "1";
    String NOT_DELETED = "0";

}
