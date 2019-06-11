package com.tjrac.topicselect.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import com.tjrac.topicselect.bean.CreateUserFromExcel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author myd
 * @Date 2018年12月28日
 */
public class PoiReadUtils {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    /**
     * 判断Excel的版本,获取Workbook工作簿
     * @param inputStream: 输入流, file: 文件流
     * @param file: filename
     * @throws IOException e
     */
    public static Workbook getWorkbook(InputStream inputStream,File file) throws IOException{
        Workbook wb = null;
        //Excel 2003
        if(file.getName().endsWith(EXCEL_XLS)){
            wb = new HSSFWorkbook(inputStream);

        }
        // Excel 2007+
        else if(file.getName().endsWith(EXCEL_XLSX)){
            wb = new XSSFWorkbook(inputStream);
        }
        return wb;
    }

    /**
     * 判断是否是Excel文件
     * @throws Exception e
     */
    public static void checkExcelVaild(File file) throws Exception{
        if(!file.exists()){
            throw new Exception("文件不存在");
        }
        if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))){
            throw new Exception("文件不是标准Excel格式！请上传.xls或.xlsx的文件！");
        }
    }

    /**
     * 读取Excel
     * @param excelFile
     * @Description 调用时捕捉NULL_POINTER_EXCEPTION
     * @return CreateUserFromExcel[]
     */
    public CreateUserFromExcel[] readExcel(File excelFile) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 文件流
            FileInputStream in = new FileInputStream(excelFile);
            checkExcelVaild(excelFile);
            Workbook workbook = getWorkbook(in,excelFile);
            //如果Sheet数不等于1则跳出
            if (workbook.getNumberOfSheets()!=1){
                return null;
            }
            /**
             * 设置当前excel中sheet的下标：0开始
             * 本Util仅为固定格式设计，只读一个sheet
             */
            Sheet sheet = workbook.getSheetAt(0);
            //初始化结果数组
            CreateUserFromExcel[] createUserFromExcels=new CreateUserFromExcel[sheet.getLastRowNum()];
            for (int i=0;i<createUserFromExcels.length;i++){
                createUserFromExcels[i]=new CreateUserFromExcel();
            }
            // 为跳过第一行目录设置count
            int count = 0;
            for (Row row : sheet) {
                try {
                    // 跳过第一和第二行的目录
                    if(count < 1 ) {
                        count++;
                        continue;
                    }
                    //如果当前行没有数据，跳出循环
                    if(row.getCell(0).toString().equals("")){
                        break;
                    }
                    int end = row.getLastCellNum();
                    for (int i = 0; i < end; i++) {
                        Cell cell = row.getCell(i);
                        if(cell == null) {
                            System.out.print("null" + "\n");
                            continue;
                        }
                        Object obj = getValue(cell);
                        //因为少读了第一行所以getRowNum-1
                        switch (i){
                            default:break;
                            case 0:
                                createUserFromExcels[row.getRowNum()-1].setUsername((String) obj);
                                System.out.print(obj + ",");
                                break;
                            case 1:
                                createUserFromExcels[row.getRowNum()-1].setTruename((String) obj);
                                System.out.print(obj + ",");
                                break;
                            case 2:
                                createUserFromExcels[row.getRowNum()-1].setPermission((String) obj);
                                System.out.print(obj + "");
                                break;
                        }
                    }
                    System.out.println();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return createUserFromExcels;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Object getValue(Cell cell) {
        Object obj = null;
        switch (cell.getCellType()) {
            case BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case ERROR:
                obj = cell.getErrorCellValue();
                break;
            case NUMERIC:
                obj = NumberToTextConverter.toText(cell.getNumericCellValue());
                break;
            case STRING:
                obj = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return obj;
    }
}
