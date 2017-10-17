package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.CurdController;
import com.bcx.plat.core.entity.Menu;
import com.bcx.plat.core.service.MenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

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
