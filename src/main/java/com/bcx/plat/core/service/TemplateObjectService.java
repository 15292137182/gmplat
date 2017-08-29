package com.bcx.plat.core.service;

import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.entity.TemplateObject;
import org.springframework.stereotype.Service;

/**
 * <p>Title: TemplateObjectService</p>
 * <p>Description: 模板对象业务层</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy:
 *                2017/8/28  Wen TieHu Create
 *          </pre>
 */
@Service
public class TemplateObjectService extends BaseServiceTemplate<TemplateObject>{

    @Override
    public boolean isRemoveBlank() {
        return false;
    }
}
