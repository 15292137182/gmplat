package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.service.KeySetService;
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
 * Created by Went on 2017/8/3.
 */
@RequestMapping("/core/keySet")
@RestController
public class KeySetController extends BaseControllerTemplate<KeySetService, KeySet> {
    @Autowired
    private KeySetService keySetService;

    @Override
    public void setEntityService(KeySetService keySetService) {
        super.setEntityService(keySetService);
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("keysetCode", "keysetName");
    }

    /**
     * 查询业务对象 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
     *
     * @param args
     * @param pageNum
     * @param pageSize
     * @param request
     * @param locale
     */
    @RequestMapping("/queryPage")
    @Override
    public Object singleInputSelect(String args, int pageNum, int pageSize, HttpServletRequest request, Locale locale) {
        ServiceResult<PageResult<Map<String, Object>>> result = keySetService
                .singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(args), pageNum, pageSize);
        return super.result(request, result, locale);
    }
}
