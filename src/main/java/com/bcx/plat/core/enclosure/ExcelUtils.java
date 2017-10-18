package com.bcx.plat.core.enclosure;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>Title: ExcelUtils</p>
 * <p>Description: ExcelUtils 工具类</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>History: 2017/10/15  Wen TieHu Create </pre>
 */
public class ExcelUtils {

  /**
   * 转换单元格数据
   *
   * @param cell 接收类型
   * @return 数据
   */
  private static String dataConvert(Cell cell) {
    String cellValue;
    if (cell != null && cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
      cellValue = String.valueOf(cell.getBooleanCellValue());
    } else if (cell != null && cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
      cellValue = String.valueOf(cell.getNumericCellValue()).split("\\.")[0];
    } else {
      cellValue = cell == null ? "" : cell.getStringCellValue();
    }
    return cellValue;
  }


  /**
   * Excel 版本转换
   *
   * @param workbook 工作薄
   * @return List
   */
  static List<Map> ExcelVerConvert(Workbook workbook) {
    List<Map> list = new ArrayList<>();
    List rowNum = new LinkedList();
    //默认读取第一个工作表的sheet
    Sheet sheet = workbook.getSheetAt(0);
    //获取sheet中最后一行的行号
    int lastRowNum = sheet.getLastRowNum();
    for (int i = 0; i <= lastRowNum; i++) {
      Row row = sheet.getRow(i);
      //获取当前行最后一行的单元格列号
      int lastCellNum = row.getLastCellNum();
      Map<String, Object> map = new HashMap<>();
      for (int j = 0; j < lastCellNum; j++) {
        Cell cell = row.getCell(j);
        String convert = dataConvert(cell);
        if (i == 0) rowNum.add(convert);
        else {
          String of = String.valueOf(rowNum.get(j));
          String excelModuleVal = ExcelModule.getExcelModuleVal(of);
          if (excelModuleVal == null) {
            return list;
          }
          map.put(excelModuleVal, convert);
        }
      }
      if (map.size() != 0) list.add(map);
    }
    return list;
  }

  /**
   * 创建Excel模板
   *
   * @param cells     列头
   * @param workbook  工作薄
   * @param sheetPage sheet名称
   */
  static Workbook ExportExcel(String[] cells, Workbook workbook, String sheetPage) {
    // 在webbook中添加一个sheet,对应Excel文件中的sheet
    Sheet sheet = workbook.createSheet(sheetPage);
    // 在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
    Row row = sheet.createRow(0);
    // 创建单元格
    Cell cell;
    for (int i = 0; i < cells.length; i++) {
      cell = row.createCell((short) i);
      cell.setCellValue(cells[i]);
    }
    return workbook;
  }


  /**
   * 创建Excel模板下拉框
   *
   * @param cells     列头
   * @param workbook  工作薄
   * @param sheetPage sheet名称
   */
  static Workbook ExportExcelList(String[] cells, Workbook workbook, String sheetPage) {
    String[] list = {"男", "女"};
    // 在webbook中添加一个sheet,对应Excel文件中的sheet
    Sheet sheet = workbook.createSheet(sheetPage);
    // 在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
    Row row = sheet.createRow(0);
    // 创建单元格
    Cell cell;
    for (int i = 0; i < cells.length; i++) {
      if (Objects.equals(cells[i], "性别")) {
        cell = row.createCell((short) i);
        cell.setCellValue(cells[i]);
        createList(list, i, sheet);
      } else if (!Objects.equals(cells[i], "性别")) {
        cell = row.createCell((short) i);
        cell.setCellValue(cells[i]);
      }
    }
    return workbook;
  }

  /**
   * Excel下拉框封装
   *
   * @param list   下拉框参数
   * @param rowCol 列号
   * @param sheet  sheet
   */
  public static void createList(String[] list, int rowCol, Sheet sheet) {
    //生成下拉列表
    CellRangeAddressList regions = new CellRangeAddressList(1, 100, rowCol, rowCol);
    //生成下拉框内容
    DVConstraint constraint = DVConstraint.createExplicitListConstraint(list);
    //绑定下拉框和作用区域
    DataValidation data_validation = new HSSFDataValidation(regions, constraint);
    //对sheet页生效
    sheet.addValidationData(data_validation);
  }
}
