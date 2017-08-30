package com.bcx.plat.core.controller;

import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.entity.KeySetPro;
import com.bcx.plat.core.service.KeySetProService;
import com.bcx.plat.core.service.KeySetService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * <p>Title: KeySetProController</p>
 * <p>Description: 键值集合属性明细控制层</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy:
 *                2017/8/30  Wen TieHu Create
 *          </pre>
 */
@RequestMapping("/core/keySetPro")
@RestController
public class KeySetProController extends BaseControllerTemplate<KeySetProService, KeySetPro> {

    protected List<String> blankSelectFields() {
        return Arrays.asList("confKey", "confValue");
    }

}
