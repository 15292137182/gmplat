package com.bcx.plat.core.enclosure;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.utils.UtilsTool;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy: 2017/10/12  Wen TieHu Create </pre>
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/file/excel")
public class ExcelModuleExport extends BaseController {


  /**
   * 导出用户信息模板2003版
   *
   * @param response 响应
   */
  @GetMapping("/exportXls")
  public void ExcelModuleExportXLS(HttpServletResponse response) {
    ExcelModule(response,".xls");
  }

  /**
   * 导出用户信息模板2007版
   *
   * @param response 响应
   */
  @GetMapping("/exportXlsx")
  public void ExcelModuleExportXLSX(HttpServletResponse response) {
    ExcelModule(response,".xlsx");
  }

  private static void ExcelModule(HttpServletResponse response, String suffix) {
    String[] cells = {"工号", "姓名", "昵称", "性别", "所属部门", "身份证", "移动电话", "办公电话"
        , "邮箱", "职务", "入职日期", "密码更新日期", "上次登录时间", "账号锁定日期", "说明", "备注"};
    try {
      response.reset();
      response.setContentType("application/x-download");
      response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("用户模板" + UtilsTool.lengthUUID(5) + suffix, "UTF-8"));
      ServletOutputStream outputStream = response.getOutputStream();
      if (suffix.equals(".xls")) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("用户模板");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        HSSFCell cell;
        for (int i = 0; i < cells.length; i++) {
          cell = row.createCell((short) i);
          cell.setCellValue(cells[i]);
          cell.setCellStyle(style);
        }
        wb.write(outputStream);
        outputStream.close();
      } else if (suffix.equals(".xlsx")) {
        // 声明一个工作薄
        XSSFWorkbook workBook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workBook.createSheet();
        workBook.setSheetName(0, "用户模板");
        // 创建表格标题行 第一行
        XSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < cells.length; i++) {
          titleRow.createCell(i).setCellValue(cells[i]);
        }
        workBook.write(outputStream);
        outputStream.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
