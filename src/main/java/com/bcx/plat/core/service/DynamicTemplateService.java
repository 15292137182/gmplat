package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.entity.DataSetConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 数据集配置信息维护 service层
 * Created by Wen Tiehu on 2017/8/8.
 */
@Service
public class DynamicTemplateService extends BaseService<DataSetConfig> {
    @Autowired
    private DBTableColumnService dbTableColumnService;

    public List<String> blankSelectFields() {
        return Arrays.asList("objectCode", "objectName");
    }



//    /**
//     * 数据集查询全部数据并分页显示
//     * @param blankSearch   空白查询
//     * @param pageNum   当前页码
//     * @param pageSize  一页显示条数
//     * @param orders    排序
//     * @return  ServerResult
//     */
//    public ServerResult queryPage(String blankSearch, int pageNum, int pageSize, LinkedList<Order> orders) {
////        pageNum = !UtilsTool.isValid(blankSearch) ? 1 : pageNum;
//        PageResult<Map<String, Object>> result;
//        result = selectPageMap(createBlankQuery(blankSelectFields(), collectToSet(blankSearch)),
//                orders, pageNum, pageSize);
//        if (result.get).size() == 0) {
//            return new Server).setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
//        } else {
//            return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result);
//        }
//    }


}

