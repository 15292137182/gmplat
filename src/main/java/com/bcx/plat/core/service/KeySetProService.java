package com.bcx.plat.core.service;

import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.entity.KeySetPro;
import org.springframework.stereotype.Service;

/**
 * <p>Title: KeySetProService</p>
 * <p>Description:键值集合明细业务层 </p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy:
 *                2017/8/30  Wen TieHu Create
 *          </pre>
 */
@Service
public class KeySetProService extends BaseServiceTemplate<KeySetPro>{
    @Override
    public boolean isRemoveBlank() {
        return false;
    }
}
