package com.bcx.plat.core.common;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * @param str     按照空格查询
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/query")
    public Object singleInputSelect(String str, HttpServletRequest request, Locale locale) {
        List<Map<String, Object>> result = entityService
                .singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(str));
        if (result.size()==0) {
            return super.result(request,ServiceResult.Msg(BaseConstants.STATUS_FAIL,Message.QUERY_FAIL),locale);
        }
        return super.result(request, commonServiceResult(queryResultProcess(result), Message.QUERY_SUCCESS), locale);
    }


    /**
     * 通用查询方法
     *
     * @param args     按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @param request  request请求
     * @param locale   国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/queryPage")
    public Object singleInputSelect(String args, int pageNum, int pageSize,String order, HttpServletRequest request, Locale locale) {
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        PageResult<Map<String, Object>> result = entityService
                .singleInputSelect(blankSelectFields(), UtilsTool.collectToSet(args), pageNum, pageSize, Arrays.asList(QueryAction.ALL_FIELD),orders);
        return super.result(request, commonServiceResult(queryResultProcess(result), Message.QUERY_SUCCESS), locale);
    }

    /**
     * 通用查询方法
     *
     * @param args    按照空格查询
     * @param request request请求
     * @param locale  国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/select")
    public Object select(Map<String, Object> args, HttpServletRequest request, Locale locale) {
        List<Map<String, Object>> result = entityService.select(args);
        if (result.size()==0) {
            return result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL), locale);
        }
        return super.result(request, commonServiceResult(queryResultProcess(result), Message.QUERY_SUCCESS), locale);
    }

    /**
     * 通用查询方法
     *
     * @param args     按照空格查询
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @param request  request请求
     * @param locale   国际化参数
     * @return ServiceResult
     */
    @RequestMapping("/selectPage")
    public Object select(Map<String, Object> args, int pageNum, int pageSize, HttpServletRequest request, Locale locale) {
        PageResult<Map<String, Object>> result = entityService.select(args, pageNum, pageSize);

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
        entityService.insert(entity.buildCreateInfo().toMap());
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
        entityService.update(entity.buildModifyInfo().toMap());
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
        if (rowId!=null && rowId.length()>0) {
            Map<String, Object> args = new HashMap<>();
            args.put("rowId", rowId);
            entityService.delete(args);
            return super.result(request, commonServiceResult(rowId, Message.DELETE_SUCCESS), locale);
        }
        return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL,Message.DELETE_FAIL), locale);

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
        return new ServiceResult<>(content, BaseConstants.STATUS_SUCCESS, msg);
    }
}