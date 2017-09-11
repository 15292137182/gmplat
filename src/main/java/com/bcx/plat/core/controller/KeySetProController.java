package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.KeySetPro;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * <p>Title: KeySetProController</p>
 * <p>Description: 键值集合属性数据属性明细控制层</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy:
 *                         2017/8/30  Wen TieHu Create
 *                   </pre>
 */
@RequestMapping(PLAT_SYS_PREFIX + "/core/keySetProPro")
@RestController
public class KeySetProController extends BaseController {

    protected List<String> blankSelectFields() {
        return Arrays.asList("confKey", "confValue");
    }


    /**
     * 键值集合属性数据新增数据
     *
     * @param param 接受一个实体参数
     * @return 返回操作信息
     */
    @RequestMapping("/add")
    public PlatResult insert(@RequestParam Map<String, Object> param) {
        KeySetPro KeySetPro = new KeySetPro().buildCreateInfo().fromMap(param);
        int insert = KeySetPro.insert();
        if (insert != -1) {
            return super.result(ServerResult.setMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS));
        } else {
            return super.result(ServerResult.setMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_FAIL));
        }
    }

    /**
     * 键值集合属性数据根据rowId修改数据
     *
     * @param param 接受一个实体参数
     * @return 返回操作信息
     */
    @RequestMapping("/modify")
    public PlatResult update(@RequestParam Map<String, Object> param) {
        int update;
        if ((!param.get("rowId").equals("")) || param.get("rowId") != null) {
            KeySetPro keySetPro = new KeySetPro();
            KeySetPro modify = keySetPro.fromMap(param).buildModifyInfo();
            update = modify.updateById();
            if (update != -1) {
                return super.result(ServerResult.setMessage(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS));
            } else {
                return super.result(ServerResult.setMessage(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL));
            }
        }
        return super.result(ServerResult.setMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
    }

    /**
     * 删除键值集合属性数据属性数据
     *
     * @param rowId 业务对象rowId
     * @return serviceResult
     */
    @RequestMapping("/delete")
    public PlatResult delete(String rowId) {
        int del;
        if (!rowId.isEmpty()) {
            KeySetPro keySetPro = new KeySetPro();
            del = keySetPro.deleteById(rowId);
            if (del != -1) {
                return super.result(ServerResult.setMessage(BaseConstants.STATUS_FAIL, Message.DELETE_SUCCESS));
            } else {
                return super.result(ServerResult.setMessage(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL));
            }
        } else {
            return super.result(ServerResult.setMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
        }
    }

}
