package com.company;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;





public class Excel {




    public void create(){
        String fileName = "Alldata.xlsx";
        String sheetName = "user";
        String[] columnHeaders = {"用户ID", "用户名", "用户密码", "类型", "用户级别", "用户注册时间", "客户累计消费总金额", "用户手机号", "用户邮箱"};

        Workbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                // 创建新的Excel文件
                workbook = new XSSFWorkbook();
            } else {
                // 打开现有的Excel文件
                FileInputStream fileInputStream = new FileInputStream(file);
                workbook = new XSSFWorkbook(fileInputStream);
                fileInputStream.close();
            }

            // 获取或创建表格
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
                // 写入列头
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < columnHeaders.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columnHeaders[i]);
                }
            }

            String sheetName1 = "commodity";
            String[] columnHeaders1 = {"商品编号", "商品名称", "商品厂家", "生产日期", "型号", "进货价", "零售价", "数量"};
            Sheet sheet1 = workbook.getSheet(sheetName1);
            if (sheet1 == null) {
                sheet1 = workbook.createSheet(sheetName1);
                // 写入列头
                Row headerRow = sheet1.createRow(0);
                for (int i = 0; i < columnHeaders1.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columnHeaders1[i]);
                }
            }


            String sheetName2 = "shopcart";
            String[] columnHeaders2 = {"用户名", "商品", "价格", "数量"};
            Sheet sheet2 = workbook.getSheet(sheetName2);
            if (sheet2 == null) {
                sheet2 = workbook.createSheet(sheetName2);
                // 写入列头
                Row headerRow = sheet2.createRow(0);
                for (int i = 0; i < columnHeaders2.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columnHeaders2[i]);
                }
            }



            String sheetName3 = "history";
            String[] columnHeaders3 = {"用户名", "商品", "数量", "金额","购买时间"};
            Sheet sheet3 = workbook.getSheet(sheetName3);
            if (sheet3 == null) {
                sheet3 = workbook.createSheet(sheetName3);
                // 写入列头
                Row headerRow = sheet3.createRow(0);
                for (int i = 0; i < columnHeaders3.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columnHeaders3[i]);
                }
            }



            // 写入数据
          //  Row dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
          //  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
          //  dataRow.createCell(0).setCellValue("123");
         //   dataRow.createCell(1).setCellValue("John Doe");
         //   dataRow.createCell(2).setCellValue("password");
         //   dataRow.createCell(3).setCellValue("普通用户");
         //   dataRow.createCell(4).setCellValue("Silver");
         //   dataRow.createCell(5).setCellValue(LocalDateTime.now().format(dtf));
         //   dataRow.createCell(6).setCellValue(5000);
          //  dataRow.createCell(7).setCellValue("1234567890");
         //   dataRow.createCell(8).setCellValue("john.doe@example.com");

            // 保存Excel文件
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
          //  System.out.println("Excel文件保存成功！");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
