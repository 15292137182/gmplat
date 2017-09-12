package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.entity.KeySetPro;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.KeySetProService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.SpringContextHolder;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.dataSort;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <p>Title: KeySetProController</p>
 * <p>Description: 键值集合属性数据属性明细控制层</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy:
 *                                                    2017/8/30  Wen TieHu Create
 *                                              </pre>
 */
@RequestMapping(PLAT_SYS_PREFIX + "/core/keySetPro")
@RestController
public class KeySetProController extends BaseController {

    @Autowired
    private KeySetProService keySetProService;

    protected List<String> blankSelectFields() {
        return Arrays.asList("confKey", "confValue");
    }


    /**
     * 键值集合属性数据新增数据
     *
     * @param param 接受一个实体参数
     * @return 返回操作信息
     */
    @RequestMapping(value = "/add", method = POST)
    public PlatResult insert(@RequestParam Map<String, Object> param) {
        ServerResult result = new ServerResult();
        KeySetPro KeySetPro = new KeySetPro().buildCreateInfo().fromMap(param);
        int insert = KeySetPro.insert();
        if (insert != -1) {
            return super.result(result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS));
        } else {
            return super.result(result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_FAIL));
        }
    }

    /**
     * 键值集合属性数据根据rowId修改数据
     *
     * @param param 接受一个实体参数
     * @return 返回操作信息
     */
    @RequestMapping(value = "/modify", method = POST)
    public PlatResult update(@RequestParam Map<String, Object> param) {
       MoreBatis moreBatis =  (MoreBatis)SpringContextHolder.getApplicationContext().getBean("moreBatis");
        ServerResult result = new ServerResult();
        int update;
        if ((!param.get("rowId").equals("")) || param.get("rowId") != null) {
            KeySetPro keySetPro = new KeySetPro();
//            KeySetPro modify = keySetPro.fromMap(param).buildModifyInfo();
            update = moreBatis.update(KeySetPro.class, param).where(new FieldCondition("rowId", Operator.EQUAL, param.get("rowId"))).execute();
//            update = modify.updateById();
            if (update != -1) {
                return super.result(result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS));
            } else {
                return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL));
            }
        }
        moreBatis.update(KeySetPro.class,param);
        return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }

    /**
     * 删除键值集合属性数据属性数据
     *
     * @param rowId 业务对象rowId
     * @return serviceResult
     */
    @RequestMapping(value = "/delete", method = POST)
    public PlatResult delete(String rowId) {
        ServerResult result = new ServerResult();
        int del;
        if (!rowId.isEmpty()) {
            KeySetPro keySetPro = new KeySetPro();
            del = keySetPro.deleteById(rowId);
            if (del != -1) {
                return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_SUCCESS));
            } else {
                return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL));
            }
        } else {
            return super.result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
        }
    }


    /**
     * 根据rowId查询数据
     *
     * @param rowId 唯一标识
     * @return PlatResult
     */
    @RequestMapping("/queryById")
    public PlatResult queryById(String rowId) {
        ServerResult serverResult = new ServerResult();
        if (!rowId.isEmpty()) {
            Condition condition = new ConditionBuilder(KeySetPro.class).and().equal("rowId", rowId).endAnd().buildDone();
            List<KeySetPro> select = keySetProService.select(condition);
            return result(new ServerResult<>(select));
        } else {
            return result(serverResult.setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
        }
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
        Or blankQuery = search.isEmpty() ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
        PageResult<Map<String, Object>> keysetPro = keySetProService.selectPageMap(blankQuery, orders, pageNum, pageSize);
        if (keysetPro.getResult()!=null) {
            return result(new ServerResult<>(keysetPro));
        }
        return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL,Message.QUERY_FAIL));
    }


}
