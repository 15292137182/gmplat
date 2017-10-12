package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.base.CurdController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.Menu;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.MenuService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.constants.Message.NEW_ADD_FAIL;
import static com.bcx.plat.core.constants.Message.NEW_ADD_SUCCESS;
import static com.bcx.plat.core.constants.Message.PRIMARY_KEY_CANNOT_BE_EMPTY;
import static com.bcx.plat.core.constants.Message.UPDATE_FAIL;
import static com.bcx.plat.core.constants.Message.UPDATE_SUCCESS;
import static com.bcx.plat.core.utils.UtilsTool.dataSort;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * <p>Title: MenuController</p>
 * <p>Description: 菜单控制层</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy: 2017/10/10  Wen TieHu Create </pre>
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/menu")
public class MenuController extends CurdController<MenuService, Menu> {

  @Override
  protected List<String> blankSelectFields() {
    return Arrays.asList("parentNumber", "number", "name", "category", "sort", "url", "icon", "grantAuth");
  }

}
