package com.bcx.plat.core.common;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public abstract class BaseControllerTemplate<T extends BaseServiceTemplate, Y extends BaseEntity<Y>> extends BaseController {

    @Autowired
    private T entityService;

    protected abstract List<String> blankSelectFields();

    protected List<Map<String, Object>> queryResultProcess(List<Map<String, Object>> result) {
        return UtilsTool.isValid(result) ? queryResultProcessAction(result) : result;
    }

    protected PageResult<Map<String, Object>> queryResultProcess(PageResult<Map<String, Object>> result) {
        result.setResult(queryResultProcess(result.getResult()));
        return result;
    }

    protected List<Map<String, Object>> queryResultProcessAction(List<Map<String, Object>> result) {
        return result;
    }

    public void setEntityService(T entityService) {
        this.entityService = entityService;
    }

    protected T getEntityService() {
        return entityService;
    }

    /**
     * 通用查询方法
     *
     * @param search  按照空格查询
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/query")
    public Object singleInputSelect(String search, HttpServletRequest request, Locale locale) {
        List<Map<String, Object>> result = entityService.select(UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search)));
        if (result.size() == 0) {
            return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
        }
        return super.result(request, commonServiceResult(queryResultProcess(result), Message.QUERY_SUCCESS), locale);
    }

    /**
     * 根据功能块rowId查询当前数据
     *
     * @param rowId   功能块rowId
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryById")
    public Object queryById(String rowId, HttpServletRequest request, Locale locale) {
        List result = entityService
                .select(new FieldCondition("rowId", Operator.EQUAL, rowId));
        result = queryResultProcess(result);
        if (result.size() == 0) {
            return result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL)), locale);
        }
        return result(request, ServiceResult.Msg(new PlatResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result)), locale);
    }

    /**
     * 通用查询方法
     *
     * @param search   按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @param request  request请求
     * @param locale   国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryPage")
    public Object singleInputSelect(String search, @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize, String order, HttpServletRequest request, Locale locale) {
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        if (search == null && search.isEmpty()) {
            pageNum = 1;
        }
        PageResult<Map<String, Object>> result = entityService
                .select(UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search)), Arrays.asList(QueryAction.ALL_FIELD), orders, pageNum, pageSize);
        return super.result(request, commonServiceResult(queryResultProcess(result), Message.QUERY_SUCCESS), locale);
    }


    /**
     * 通用新增方法
     *
     * @param entity  接受一个实体参数
     * @param request request请求
     * @param locale  国际化参数
     * @return
     */
    @RequestMapping("/add")
    public Object insert(Y entity, HttpServletRequest request, Locale locale) {
        int insert = entityService.insert(entity.buildCreateInfo().toMap());
        if (insert != 1) {
            return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL)), locale);
        }
        return super.result(request, commonServiceResult(entity, Message.NEW_ADD_SUCCESS), locale);
    }


    /**
     * 通过修改方法
     *
     * @param entity  接受一个实体参数
     * @param request request请求
     * @param locale  国际化参数
     * @return
     */
    @RequestMapping("/modify")
    public Object update(Y entity, HttpServletRequest request, Locale locale) {
        int update = entityService.update(entity.buildModifyInfo().toMap());
        if (update != 1) {
            return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL)), locale);
        }
        return super.result(request, commonServiceResult(entity, Message.UPDATE_SUCCESS), locale);
    }

    /**
     * 通用删除方法
     *
     * @param rowId   按照rowId查询
     * @param request request请求
     * @param locale  国际化参数
     * @return
     */
    @RequestMapping("/delete")
    public Object delete(String rowId, HttpServletRequest request, Locale locale) {
        if (UtilsTool.isValid(rowId)) {
            Map<String, Object> args = new HashMap<>();
            args.put("rowId", rowId);
            entityService.delete(args);
            return super.result(request, commonServiceResult(rowId, Message.DELETE_SUCCESS), locale);
        }
        return super.result(request, ServiceResult.Msg(PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL)), locale);

    }

    /**
     * 通用状态生效方法
     *
     * @param rowId   接受的唯一标示
     * @param request
     * @param locale
     * @return
     */
    @RequestMapping("/takeEffect")
    public Object updateTakeEffect(String rowId, HttpServletRequest request, Locale locale) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("rowId", rowId);
        map.put("status", BaseConstants.TAKE_EFFECT);
        map.put("modifyTime", UtilsTool.getDateTimeNow());
        entityService.update(map);
        return super.result(request, commonServiceResult(rowId, Message.UPDATE_SUCCESS), locale);
    }

    /**
     * 接受参数和消息进行封装
     *
     * @param content 接受的参数
     * @param msg     消息
     * @param <T>
     * @return
     */
    private <T> ServiceResult<T> commonServiceResult(T content, String msg) {
        return ServiceResult.Msg(new PlatResult<>(BaseConstants.STATUS_SUCCESS, msg, content));
    }


}