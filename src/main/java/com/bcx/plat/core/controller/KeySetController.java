package com.bcx.plat.core.controller;

import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.service.KeySetService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 键值集合Controller层
 * Created by Went on 2017/8/3.
 */
@RequestMapping("/core/keySet")
@RestController
public class KeySetController extends BaseControllerTemplate<KeySetService, KeySet> {

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("number", "keysetCode", "keysetName");
    }

    /**
     * 根据编号number查询，以数组的形式传入数据进来["demo","test"]
     *
     * @param args    按照空格查询
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     * ["demo","test"]
     */
    @RequestMapping("/queryKeySet")
    public Object queryKeySet(String args, HttpServletRequest request, Locale locale) {
        List list = UtilsTool.jsonToObj(args, List.class);
        ServiceResult result = getEntityService().queryKeySet(list);
        return super.result(request,result,locale);
    }

    /**
     * 根据编号查询数据
     *
     * @param args    按照空格查询
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryNumber")
    public Object queryNumber(String args, HttpServletRequest request, Locale locale) {
        ServiceResult result = getEntityService().queryNumber(args);
         return super.result(request,result,locale);
    }

}
