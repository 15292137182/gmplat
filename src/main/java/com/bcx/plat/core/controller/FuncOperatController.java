package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.CurdController;
import com.bcx.plat.core.entity.FuncOperat;
import com.bcx.plat.core.service.FuncOperatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * <p>Title: FuncOperatController</p>
 * <p>Description: 功能接口控制层</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy: 2017/10/12  Wen TieHu Create </pre>
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/funcOperat")
public class FuncOperatController extends CurdController<FuncOperatService, FuncOperat> {

  @Override
  protected List<String> blankSelectFields() {
    return Arrays.asList("operatNumber", "operatName", "interceptUrl", "avail", "desc");
  }
}
