package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.DataSetConfig;
import com.bcx.plat.core.service.DataSetConfigService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


/**
 * 数据集controller层
 * Created by Wen Tiehu on 2017/8/8.
 */
@RestController
@RequestMapping("/core/dataSetConfig")
public class DataSetConfigController extends BaseControllerTemplate<DataSetConfigService, DataSetConfig> {

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("datasetCode", "datasetName", "datasetType");
    }




//    /**
//     * 通用新增方法
//     *
//     * @param entity  接受一个实体参数
//     * @param request request请求
//     * @param locale  国际化参数
//     * @return
//     */
//    @RequestMapping("/add")
//    public Object insert(@Validated DataSetConfig entity, BindingResult result, HttpServletRequest request, Locale locale) {
//        if (result.hasErrors()) {
//            List<ObjectError> allErrors = result.getAllErrors();
//            for (ObjectError all : allErrors){
//                String defaultMessage = all.getDefaultMessage();
//                try {
//                    byte[] bytes = defaultMessage.getBytes("ISO8859-1");
//                    defaultMessage=  new String(bytes,"UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("------------------++++++++++++++="+defaultMessage);
//            }
//
//        }
//        int insert = getEntityService().insert(entity.buildCreateInfo().toMap());
//        if (insert!=1) {
//            return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL),locale);
//        }
//        return super.result(request, new ServiceResult(insert,BaseConstants.STATUS_SUCCESS,Message.NEW_ADD_SUCCESS), locale);
//    }

}
