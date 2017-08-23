package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
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
        return Arrays.asList("number","keysetCode", "keysetName");
    }

    /**
     * 根据编号number查询，以数组的形式传入数据进来["demo","test"]
     *
     * @param args     按照空格查询
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     * ["demo","test"]
     */
    @RequestMapping("/queryKeySet")
    public Object queryKeySet(String args, HttpServletRequest request, Locale locale) {
        List list = UtilsTool.jsonToObj(args, List.class);
        Map<Object, Object> map = new HashMap<>();
        for (Object li : list) {
            List lists = new ArrayList();
            lists.add(li);
            List<Map<String, Object>> result = getEntityService().singleInputSelect(Arrays.asList("number"), lists);
            map.put(li, result);
        }
        String toJson = UtilsTool.objToJson(map);
        if (map.size() == 0) {
            return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
        }
        return super.result(request, new ServiceResult(toJson, BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS), locale);
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
        List<Map<String, Object>> result = getEntityService().select(new FieldCondition("number", Operator.EQUAL, args));
        if (result.size() == 0) {
            return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
        }
        return super.result(request, new ServiceResult(result, BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS), locale);
    }

}
