package com.bcx.plat.core.controller;

import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.TemplateObject;
import com.bcx.plat.core.service.TemplateObjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Title: TemplateObjectController</p>
 * <p>Description: 模板对象控制层</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy:
 *                2017/8/28  Wen TieHu Create
 *          </pre>
 */
@RestController
@RequestMapping("/core/templateObj")
public class TemplateObjectController extends BaseControllerTemplate<TemplateObjectService,TemplateObject>{


    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("templateCode","templateCode","templateName");
    }
}
