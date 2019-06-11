package com.tjrac.topicselect.util;

import com.tjrac.topicselect.bean.ExportTopicResult;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * @author myd
 * @param List<ExportTopicResult>
 * @return Workbook
 */
public class ExportTopicResultUtil {
    public static Workbook outWorkBook(List<ExportTopicResult> exportTopicResultList){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("获取excel测试表格");
        HSSFRow row = null;
        row = sheet.createRow(0);
        row.createCell(0).setCellValue("学号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("课题名称");
        row.createCell(3).setCellValue("教师姓名");
        for (int i = 0; i < exportTopicResultList.size(); i++) {
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(exportTopicResultList.get(i).getStuusername());
            row.createCell(1).setCellValue(exportTopicResultList.get(i).getStutruename());
            row.createCell(2).setCellValue(exportTopicResultList.get(i).getTopictitle());
            row.createCell(3).setCellValue(exportTopicResultList.get(i).getTeachername());
        }
        sheet.setDefaultRowHeight((short) (16.5 * 20));
        //列宽自适应
        for (int i = 0; i <= 13; i++) {
            sheet.autoSizeColumn(i);
        }
        return wb;
    }
}
