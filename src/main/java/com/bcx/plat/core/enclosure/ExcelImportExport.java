package com.bcx.plat.core.enclosure;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.controller.UserController;
import com.bcx.plat.core.entity.BaseOrg;
import com.bcx.plat.core.entity.User;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.UserService;
import com.bcx.plat.core.utils.PlatResult;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * <p>Title:ExcelModuleExport </p>
 * <p>Description: ExcelModuleExport</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>History: 2017/10/12  Wen TieHu Create </pre>
 */
@Controller
@RequestMapping(PLAT_SYS_PREFIX + "/core/excel")
public class ExcelImportExport extends BaseController {


  @Resource
  private UserController userController;
  @Resource
  private UserService userService;

  /**
   * 导出用户信息模板
   *
   * @param response 响应
   * @param suffix   文件后缀
   */
  @GetMapping("/exportModule")
  public void excelExport(HttpServletResponse response, String suffix) {
    if (suffix == null) {
      suffix = "xls";
    }
    String[] cells = {"工号", "姓名", "昵称", "性别", "所属部门", "身份证", "移动电话", "办公电话"
        , "邮箱", "职务", "入职日期", "说明", "备注"};
    try {
      String fileName = "用户模板";
      response.reset();
      response.setContentType("application/x-download");
      response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName + "." + suffix, "UTF-8"));
      ServletOutputStream outputStream = response.getOutputStream();
      Workbook workbook = null;
      if (suffix.equals("xls")) {
        // 第一步，创建一个webbook，对应一个Excel文件
        workbook = ExcelUtils.ExportExcelList(cells, new HSSFWorkbook(), "用户模板");
      } else if (suffix.equals("xlsx")) {
        workbook = ExcelUtils.ExportExcelList(cells, new XSSFWorkbook(), "用户模板");
      }
      if (workbook != null) {
        workbook.write(outputStream);
      }
      outputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * 上传Excel
   *
   * @param request 请求
   * @return PlatResult
   */
  @PostMapping("/excelUpload")
  @ResponseBody
  @SuppressWarnings("unchecked")
  public PlatResult excelUpload(HttpServletRequest request) {
    try {
      List<Map> data;
      FileItemFactory factory = new DiskFileItemFactory();
      ServletFileUpload upload = new ServletFileUpload(factory);
      FileItemIterator itemIterator = upload.getItemIterator(request);
      FileItemStream next = itemIterator.next();
      String fileName = next.getName();
      String suffix = fileName.split("\\.")[1];
      InputStream inputStream = next.openStream();
      if ("xls".equals(suffix)) {
        data = ExcelUtils.ExcelVerConvert(new HSSFWorkbook(inputStream));
      } else if ("xlsx".equals(suffix)) {
        data = ExcelUtils.ExcelVerConvert(new XSSFWorkbook(inputStream));
      } else {
        return fail(Message.PLEASE_ENTER_THE_CORRECT_FORMAT);
      }
      if (data != null && data.size() == 0) {
        return fail(Message.PARSING_EXCEL_FAIL);
      } else if (data != null) {
        for (Map li : data) {
          String id = String.valueOf(li.get("id"));
          String name = String.valueOf(li.get("name"));
          if (null != id && !"".equals(id.trim())
              && null != name && !"".equals(name.trim())) {//工号和姓名不能为空
            //根据工号查询是否已存在该工号的记录
            Condition validCondition = new ConditionBuilder(User.class).and().equal("id", id.trim()).endAnd().buildDone();
            List<User> list = userService.select(validCondition);
            if (list.size() != 0) return fail(Message.DATA_CANNOT_BE_DUPLICATED);
          } else {
            return fail(Message.DATA_CANNOT_BE_EMPTY);
          }
        }
        PlatResult add = null;
        for (Map li : data) {
          List<BaseOrg> list = new BaseOrg().selectAll();
          for (BaseOrg baseOrg : list) {
            if (baseOrg.getOrgName().equals(li.get("belongOrg"))) {
              String rowId = baseOrg.getRowId();
              li.put("belongOrg", rowId);
            }
          }
          add = userController.add(li);
        }
        return add;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


}
