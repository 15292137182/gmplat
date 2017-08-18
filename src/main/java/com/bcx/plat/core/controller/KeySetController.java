package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.service.KeySetService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.json.JSONArray;
import org.json.JSONObject;
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

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("number","keysetCode", "keysetName");
    }


    /**
     * 通用查询方法
     *
     * @param args     按照空格查询
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     * ["demo","test"]
     */
    @RequestMapping("/queryKeySey")
    public Object queryKeySet(String args, HttpServletRequest request, Locale locale) {
        List list = UtilsTool.jsonToObj(args, List.class);
        List lists = new ArrayList();
        for (Object l : list) {
            lists.add(l);
        }
        List<Map<String, Object>> result = getEntityService()
                .singleInputSelect(Arrays.asList("number"), lists);
        for (Map<String,Object> results : result){
            Object number = results.get("number");
//            if (number==A){
//
//            }
        }
        if (result.size() == 0) {
            return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
        }
        return super.result(request, new ServiceResult(result, BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS), locale);
    }


    /**
     * 通用查询方法
     *
     * @param args     按照空格查询
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryTest")
    public Object query(String args, HttpServletRequest request, Locale locale) {
//        List list = UtilsTool.jsonToObj(args, List.class);
//        for (Object l: list){
//            System.out.println(l);
//        }

        HashMap hashMap = UtilsTool.jsonToObj(args, HashMap.class);
        for (Object ha : hashMap.keySet()){
            Object o = hashMap.get(ha);
            System.out.println(o);
        }
//        List<Map<String, Object>> result = getEntityService()
//                .singleInputSelect(Arrays.asList("number"), list);
//        if (result.size() == 0) {
//            return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
//        }
        return null;
    }

}
