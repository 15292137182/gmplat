package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.MaintDBTables;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.MaintDBTablesService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 数据库表信息Controller层
 * Created by Wen Tiehu on 2017/8/11.
 */
@RestController
@RequestMapping("/core/maintTable")
public class MaintDBTablesController extends BaseControllerTemplate<MaintDBTablesService,MaintDBTables>{

    /**
     *根据数据库表信息rowId查询出当前对应的一条记录
     *
     * @param rowId     rowId查找当前记录
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryById")
    public Object queryById(String rowId, HttpServletRequest request, Locale locale) {
        List<Map<String, Object>> result = getEntityService()
                .select(new And(new FieldCondition("rowId", Operator.EQUAL, rowId)));
        if (result.size()==0) {
            return result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
        }
        return super.result(request, new ServiceResult(BaseConstants.STATUS_SUCCESS,Message.QUERY_SUCCESS,result), locale);
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("tableSchema","tableEname","tableCname");
    }
}
