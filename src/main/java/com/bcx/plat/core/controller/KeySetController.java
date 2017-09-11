package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.service.KeySetService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.dataSort;

/**
 * 键值集合Controller层
 * Created by Went on 2017/8/3.
 */
@RequestMapping(PLAT_SYS_PREFIX + "/core/keySet")
@RestController
public class KeySetController extends BaseController {

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
     * @param search 按照空格查询
     * @return PlatResult
     * ["demo","test"]
     */
    @RequestMapping("/queryKeySet")
    public PlatResult queryKeySet(String search) {
        if (UtilsTool.isValid(search)) {
            List list = UtilsTool.jsonToObj(search, List.class);
            ServerResult serverResult = keySetService.queryKeySet(list);
            return result(serverResult);
        } else {
            return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
        }
    }

    /**
     * 根据主表键值集合代码查询数据
     *
     * @param keyCode 按照空格查询
     * @return PlatResult
     */
    @RequestMapping("/queryKeyCode")
    public PlatResult queryKeyCode(String keyCode) {
        if (UtilsTool.isValid(keyCode)) {
            ServerResult serverResult = keySetService.queryKeyCode(keyCode);
            return result(serverResult);
        } else {
            return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
        }
    }

    /**
     * 根据键值集合主表查询从表详细信息
     *
     * @param rowId 唯一标识
     * @return PlatResult
     */
    @RequestMapping("/queryPro")
    public PlatResult queryPro(String rowId) {
        if (UtilsTool.isValid(rowId)) {
            ServerResult<List<Map>> listServerResult = keySetService.queryPro(rowId);
            return result(listServerResult);
        } else {
            return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
        }
    }


    /**
     * 根据业务对象rowId查找当前对象下的所有属性并分页显示
     *
     * @param search   按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @return PlatResult
     */
    @RequestMapping("/queryProPage")
    public PlatResult queryProPage(String rowId, String search,
                                   @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                   String order) {
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        if (UtilsTool.isValid(rowId)) {
            ServerResult serverResult = keySetService.queryProPage(search, rowId, pageNum, pageSize, orders);
            return result(serverResult);
        } else {
            return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
        }
    }


    /**
     * 删除键值集合数据
     *
     * @param rowId 业务对象rowId
     * @return serviceResult
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public PlatResult delete(String rowId) {
        int del;
        if (!rowId.isEmpty()) {
            KeySet keySet = new KeySet();
            del = keySet.deleteById(rowId);
            if (del != -1) {
                return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_SUCCESS));
            } else {
                return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_SUCCESS));
            }
        } else {
            return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
        }
    }

    /**
     * 键值集合新增数据
     *
     * @param param 接受一个实体参数
     * @return 返回操作信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public PlatResult insert(@RequestParam Map<String, Object> param) {
        KeySet keySet = new KeySet().buildCreateInfo().fromMap(param);
        int insert = keySet.insert();
        if (insert != -1) {
            return result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS));
        } else {
            return result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_FAIL));
        }
    }

    /**
     * 键值集合根据rowId修改数据
     *
     * @param param 接受一个实体参数
     * @return 返回操作信息
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public PlatResult update(@RequestParam Map<String, Object> param) {
        int update;
        if ((!param.get("rowId").equals("")) || param.get("rowId") != null) {
            KeySet keySet = new KeySet();
            KeySet modify = keySet.fromMap(param).buildModifyInfo();
            update = modify.updateById();
            if (update != -1) {
                return result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS));
            } else {
                return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL));
            }
        }
        return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }


    /**
     * 键值集合查询方法
     *
     * @param search   按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @return PlatResult
     */
    @RequestMapping("/queryPage")
    public PlatResult singleInputSelect(String search,
                                        @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                        String order) {
        LinkedList<Order> orders = dataSort(order);
        pageNum = search == null || search.isEmpty() ? 1 : pageNum;
        Or blankQuery;
        if (search.isEmpty()) {
            blankQuery = null;
        } else {
            blankQuery = UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
        }
        PageResult<Map<String,Object>> sysConfigPageResult = keySetService.selectPageMap(blankQuery, orders, pageNum, pageSize);
        Map map = adapterPageResult(sysConfigPageResult);
        ServerResult<Map> mapServerResult = new ServerResult<>(map);
        return result(mapServerResult);
    }


}
