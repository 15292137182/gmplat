package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.TemplateObject;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.TemplateObjectProService;
import com.bcx.plat.core.service.TemplateObjectService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Title: TemplateObjectController</p>
 * Description: 模板对象控制层
 * Copyright: Shanghai BatchSight GMP Information of management platform, Inc. Copyright(c) 2017
 *
 * @author Wen TieHu
 *         <pre>History:
 *                         2017/8/28  Wen TieHu Create
 *                         </pre>
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/templateObj")
public class TemplateObjectController extends BaseController {

    @Autowired
    private TemplateObjectProService templateObjectProService;
    @Autowired
    private TemplateObjectService templateObjectService;

    /**
     * @return 参与空格查询的字段
     */
    public List<String> blankSelectFields() {
        return Arrays.asList("templateCode", "templateCode", "templateName");
    }

    /**
     * 分页方式查询对象属性
     *
     * @param rowId    这个rowId指模版对象的RowId
     * @param search   空格查询的字符串
     * @param pageNum  页面号
     * @param pageSize 页面大小
     * @param order    排序信息（字符串）
     * @return 返回查询结果
     */
    @RequestMapping("/queryProPage")
    public PlatResult queryPropertiesPage(String rowId,
                                          String search,
                                          @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                          String order) {
        List<Condition> ors = new ArrayList<>();
        if (isValid(rowId)) {
            ors.add(new FieldCondition("templateObjRowId", Operator.EQUAL, rowId));
            if (isValid(search)) {
                ors.add(createBlankQuery(blankSelectFields(), collectToSet(search)));
            }
            Condition condition = new Or(ors);
            List<Order> orders = dataSort(order);
            PageResult<Map<String, Object>> result = templateObjectProService.selectPageMap(condition, orders, pageNum, pageSize);
            return result(new ServerResult<>(STATUS_FAIL, Message.QUERY_FAIL, result));
        }
        return result(new ServerResult<>(STATUS_FAIL, Message.QUERY_FAIL, null));
    }

    /**
     * 查询信息
     *
     * @param search 空格查询的值
     * @return 返回
     */
    @RequestMapping("/query")
    public PlatResult singleInputSelect(String search) {
        Map<String, Object> responseMap = new HashMap<>();
        List<Map> maps = templateObjectService.selectMap(createBlankQuery(blankSelectFields(), collectToSet(search)));
        responseMap.put("data", maps);
        return result(new ServerResult<>(responseMap));
    }

    /**
     * 根据功能块rowId查询当前数据
     *
     * @param rowId 功能块rowId
     * @return PlatResult
     */
    @RequestMapping("/queryById")
    public Object queryById(String rowId) {
        Map<String, Object> responseMap = new HashMap<>();
        List<Map> maps = templateObjectService.selectMap(new FieldCondition("rowId", Operator.EQUAL, rowId));
        responseMap.put("data", maps);
        return result(new ServerResult<>(responseMap));
    }

    /**
     * 通用查询方法
     *
     * @param search   按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @return PlatResult
     */
    @RequestMapping("/queryPage")
    public Object singleInputSelect(String search,
                                    @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                    String order) {
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        Or blankQuery = search.isEmpty() ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
        PageResult<Map<String, Object>> result = templateObjectService.selectPageMap(blankQuery, orders, pageNum, pageSize);
        Map map = adapterPageResult(result);
        ServerResult<Map> mapServerResult = new ServerResult<>(map);
        return result(mapServerResult);
    }


    /**
     * 通用新增方法
     *
     * @return 返回操作信息
     */
    @RequestMapping(value = "/add", method = POST)
    public PlatResult addDataSet(@RequestParam Map<String, Object> param) {
        TemplateObject templateObject = new TemplateObject().buildCreateInfo().fromMap(param);
        int insert = templateObject.insert();
        if (insert != -1) {
            return result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS));
        } else {
            return result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_FAIL));
        }
    }

    /**
     * 模板对象修改方法
     *
     * @param param 接受一个实体参数
     * @return 返回操作信息
     */
    @RequestMapping(value = "/modify", method = POST)
    public PlatResult modifyDataSet(@RequestParam Map<String, Object> param) {
        int update;
        if (UtilsTool.isValid(param.get("rowId"))) {
            TemplateObject templateObject = new TemplateObject().buildModifyInfo().fromMap(param);
            update = templateObject.updateById();
            if (update != -1) {
                return result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS));
            } else {
                return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL));
            }
        } else {
            return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
        }
    }

    /**
     * 通用删除方法
     *
     * @param rowId 按照rowId查询
     * @return 返回操作信息
     */
    @RequestMapping(value = "/delete", method = POST)
    public Object delete(String rowId) {
        int del;
        if (!rowId.isEmpty()) {
            TemplateObject templateObject = new TemplateObject();
            del = templateObject.deleteById(rowId);
            if (del != -1) {
                return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_SUCCESS));
            } else {
                return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_SUCCESS));
            }
        } else {
            return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
        }
    }

}
