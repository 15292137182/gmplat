package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.service.KeySetService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    protected List<String> blankSelectFields() {
        return Arrays.asList("keysetCode", "keysetName");
    }

    @Autowired
    KeySetService keySetService;

    /**
     * 根据keysetCode查询，以数组的形式传入数据进来["demo","test"]
     *
     * @param search    按照空格查询
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     * ["demo","test"]
     */
    @RequestMapping("/queryKeySet")
    public Object queryKeySet(String search, HttpServletRequest request, Locale locale) {
        List list = UtilsTool.jsonToObj(search, List.class);
        return super.result(request,ServiceResult.Msg(keySetService.queryKeySet(list)),locale);
    }

    /**
     * 根据编号查询数据
     *
     * @param search    按照空格查询
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryNumber")
    public Object queryNumber(String search, HttpServletRequest request, Locale locale) {
         return super.result(request,ServiceResult.Msg(keySetService.queryNumber(search)),locale);
    }

    /**
     * 根据键值集合主表查询从表详细信息
     * @param rowId 唯一标识
     * @param request   请求
     * @param locale    国际化
     * @return
     */
    @RequestMapping("/queryPro")
    public Object queryPro(String rowId, HttpServletRequest request, Locale locale){
        return super.result(request,ServiceResult.Msg(keySetService.queryPro(rowId)),locale);
    }



}
