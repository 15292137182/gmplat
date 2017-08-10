package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.SysConfig;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.service.SysConfigService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;
import static com.bcx.plat.core.utils.UtilsTool.isValid;


/**
 * Created by Wen Tiehu on 2017/8/4.
 */
@RestController
@RequestMapping("/core/sysConfig")
public class SysConfigController extends BaseControllerTemplate<SysConfigService,SysConfig> {

    @Autowired
    private SysConfigService sysConfigService;

    public void setSysConfigService(SysConfigService sysConfigService) {
        this.sysConfigService = sysConfigService;
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("confKey","confValue");
    }

    /**
     * 查询业务对象 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
     */
    @RequestMapping("/queryPage")
    public Object singleInputSelect(String args,int pageNum,int pageSize, HttpServletRequest request, Locale locale) {
        ServiceResult<PageResult<Map<String, Object>>> result = sysConfigService
                .singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(args), pageNum, pageSize);
        return super.result(request, result, locale);
    }
}
