package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.TemplateObjectPro;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * <p>Title: TemplateObjectProController</p>
 * <p>Description: 模板对象属性控制层</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre> Histroy:
 *          2017/8/28  Wen TieHu Create
 *          </pre>
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/templateObjPro")
public class TemplateObjectProController extends BaseController {


    /**
     * 模板对象属性方法
     *
     * @return 返回操作信息
     */
    @RequestMapping("/add")
    public PlatResult addDataSet(@RequestParam Map<String, Object> param) {
        TemplateObjectPro templateObjectPro = new TemplateObjectPro().buildCreateInfo().fromMap(param);
        int insert = templateObjectPro.insert();
        if (insert != -1) {
            return result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS));
        } else {
            return result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_FAIL));
        }
    }

    /**
     * 模板对象属性修改方法
     *
     * @param param 接受一个实体参数
     * @return 返回操作信息
     */
    @RequestMapping("/modify")
    public PlatResult modifyDataSet(@RequestParam Map<String, Object> param) {
        int update;
        if (UtilsTool.isValid(param.get("rowId"))) {
            TemplateObjectPro templateObjectPro = new TemplateObjectPro().buildModifyInfo().fromMap(param);
            update = templateObjectPro.updateById();
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
    @RequestMapping("/delete")
    public Object delete(String rowId) {
        int del;
        if (!rowId.isEmpty()) {
            TemplateObjectPro templateObjectPro = new TemplateObjectPro();
            del = templateObjectPro.deleteById(rowId);
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
