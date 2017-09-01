package com.bcx.plat.core.controller;

import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.SysConfig;
import com.bcx.plat.core.service.SysConfigService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;


/**
 * 系统资源配置controller层
 * Created by Wen Tiehu on 2017/8/4.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/sysConfig")
public class SysConfigController extends BaseControllerTemplate<SysConfigService,SysConfig> {
    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("confKey","confValue");
    }
}
