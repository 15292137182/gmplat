package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.service.KeySetService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * 键值集合Controller层
 * Created by Went on 2017/8/3.
 */
@RequestMapping(PLAT_SYS_PREFIX + "/core/keySet")
@RestController
public class KeySetController extends BaseControllerTemplate<KeySetService, KeySet> {

    private final KeySetService keySetService;

    @Autowired
    public KeySetController(KeySetService keySetService) {
        this.keySetService = keySetService;
    }

    protected List<String> blankSelectFields() {
        return Arrays.asList("keysetCode", "keysetName");
    }

    /**
     * 根据keysetCode查询，以数组的形式传入数据进来["demo","test"]
     *
     * @param search  按照空格查询
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     * ["demo","test"]
     */
    @RequestMapping("/queryKeySet")
    public Object queryKeySet(String search, HttpServletRequest request, Locale locale) {
        if (UtilsTool.isValid(search)) {
            List list = UtilsTool.jsonToObj(search, List.class);
            return super.result(request, ServiceResult.Msg(keySetService.queryKeySet(list)), locale);
        } else {
            return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL,Message.QUERY_FAIL)), locale);
        }
    }

    /**
     * 根据主表键值集合代码查询数据
     *
     * @param keyCode 按照空格查询
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryKeyCode")
    public Object queryKeyCode(String keyCode, HttpServletRequest request, Locale locale) {
        if (UtilsTool.isValid(keyCode)) {
            return super.result(request, ServiceResult.Msg(keySetService.queryKeyCode(keyCode)), locale);
        } else {
            return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
        }
    }

    /**
     * 根据键值集合主表查询从表详细信息
     *
     * @param rowId   唯一标识
     * @param request 请求
     * @param locale  国际化
     * @return
     */
    @RequestMapping("/queryPro")
    public Object queryPro(String rowId, HttpServletRequest request, Locale locale) {
        if (UtilsTool.isValid(rowId)) {
            return super.result(request, ServiceResult.Msg(keySetService.queryPro(rowId)), locale);
        } else {
            return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
        }
    }


    /**
     * 根据业务对象rowId查找当前对象下的所有属性并分页显示
     *
     * @param search   按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @param request  request请求
     * @param locale   国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryProPage")
    public Object queryProPage(String rowId, String search,
                               @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                               String order, HttpServletRequest request, Locale locale) {
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        if (UtilsTool.isValid(rowId)) {
            return super.result(request, ServiceResult.Msg(keySetService.queryProPage(search, rowId, pageNum, pageSize, orders)), locale);
        } else {
            return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
        }
    }


    /**
     * 判断当前业务对象下是否有业务对象属性数据,有就全部删除
     *
     * @param rowId   业务对象rowId
     * @param request request请求
     * @param locale  国际化参数
     * @return serviceResult
     */
    @RequestMapping("/delete")
    @Override
    public Object delete(String rowId, HttpServletRequest request, Locale locale) {
        if (UtilsTool.isValid(rowId)) {
            return super.result(request, ServiceResult.Msg(keySetService.delete(rowId)), locale);
        } else {
            return super.result(request, ServiceResult.Msg(null), locale);
        }
    }

}
