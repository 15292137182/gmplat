package com.bcx.plat.core.controller;

import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.MaintDBTables;
import com.bcx.plat.core.service.MaintDBTablesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Wen Tiehu on 2017/8/11.
 */
@RestController
@RequestMapping("/core/maintTable")
public class MaintDBTablesController extends BaseControllerTemplate<MaintDBTablesService,MaintDBTables>{

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("tableSchema","tableEname","tableCname");
    }
}
