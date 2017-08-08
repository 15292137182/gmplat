package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.service.KeySetService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * Created by Went on 2017/8/3.
 */
@RequestMapping("/core/keySet")
@RestController
public class KeySetController extends BaseController {

    @Autowired
    private KeySetService keySetService;

    /**
     * @param str     根据条件查询数据
     * @param rowId   根据ID查询数据
     * @param request
     * @param locale
     * @return
     */
    @RequestMapping("/query")
    public Object select(String str, String rowId, HttpServletRequest request, Locale locale) {
        Map<String, Object> map = new HashMap<>();
        map.put("strArr", collectToSet(str));
        map.put("rowId", rowId);
        ServiceResult<KeySet> result = keySetService.selete(map);
        return super.result(request, result, locale);

    }

    /**
     * 新增键值集合信息
     *
     * @param keySet  键值集合信息实体
     * @param request
     * @param locale
     * @return
     */
    @RequestMapping("/add")
    public Object insert(KeySet keySet, HttpServletRequest request, Locale locale) {
        ServiceResult<KeySet> result = keySetService.insert(keySet);
        return super.result(request, result, locale);

    }

    /**
     * 修改键值集合信息
     *
     * @param keySet  键值集合信息实体
     * @param request
     * @param locale
     * @return
     */
    @RequestMapping("/modify")
    public Object update(KeySet keySet, HttpServletRequest request, Locale locale) {
        ServiceResult<KeySet> result = keySetService.update(keySet);
        return super.result(request, result, locale);

    }

    /**
     * 删除键值集合信息
     *
     * @param keySet  键值集合信息实体
     * @param request
     * @param locale
     * @return
     */
    @RequestMapping("/delete")
    public Object delete(KeySet keySet, HttpServletRequest request, Locale locale) {
        ServiceResult<KeySet> result = keySetService.delete(keySet);
        return super.result(request, result, locale);

    }
}
