package com.tjrac.topicselect;

import com.tjrac.topicselect.bean.ExportTopicResult;
import org.apache.poi.hssf.usermodel.*;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WriteTest {
    public static void main(String[] args){
        final String UPLOADED_FOLDER = "%temp%";
        List<ExportTopicResult> exportTopicResultList = new ArrayList<>();
        ExportTopicResult ex1 = new ExportTopicResult();
        ExportTopicResult ex2 = new ExportTopicResult();
        ex1.setStuusername("6015203078");
        ex1.setStutruename("学生1");
        ex1.setTeachername("教师1");
        ex1.setTopictitle("课题1");
        ex2.setStuusername("6015203079");
        ex2.setStutruename("学生2");
        ex2.setTeachername("教师2");
        ex2.setTopictitle("课题2");
        exportTopicResultList.add(ex1);
        exportTopicResultList.add(ex2);
        //
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
        try {
            String filename = "E:"+"\\"+ System.currentTimeMillis() +".xls";
            FileOutputStream fout = new FileOutputStream(filename);
            wb.write(fout);
            fout.close();
            wb.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
