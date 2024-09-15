package com.company;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class Administrator {
    Scanner input = new Scanner(System.in);
    Start mm = new Start();

    public void administratorLogin() {
        System.out.println("");
        System.out.println("****欢迎进入 管理员登录 界面****");
        System.out.print("请输入name:");
        String adname = input.next();

        System.out.print("请输入password:");
        String adps = input.next();

        if (adname.equals("0") || adps.equals("0")) {
            System.out.println("返回 START 界面");
           mm.start();
        }


        String fileName = "Alldata.xlsx";
        String sheetName = "user";

        boolean foundMatch = false;

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

            // 查找匹配的用户名和密码
            int startRow = 1;

// 查找匹配的用户名和密码
            for (int i = startRow; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Cell usernameCell = row.getCell(1);
                Cell passwordCell = row.getCell(2);
                Cell typeCell = row.getCell(3);
                if (usernameCell != null && passwordCell != null && typeCell != null) {
                    String cellUsername = usernameCell.getStringCellValue();
                    String cellPassword = passwordCell.getStringCellValue();
                    double type0 = typeCell.getNumericCellValue();


                    int type = (int) type0;
                    if (adname.equals(cellUsername) && adps.equals(cellPassword) && type == 1) {
                        foundMatch = true;
                        break;
                    }
                }
            }

            // 保存Excel文件
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
            // System.out.println("Excel文件保存成功！");

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
        if (foundMatch) {
            System.out.println("登陆成功");
            administratormenu (adname,adps);
        }
        else{
            System.out.println("用户名密码错误，登陆失败");administratorLogin();
        }


    }

    public static boolean isValidPassword(String password) {
        if (password.length() <= 8) {
            return false;
        }

        // 使用正则表达式进行验证
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).+$";
        return password.matches(pattern);
    }

    private static boolean rowIsEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (Cell cell : row) {
            if (cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
    public void administratormenu(String ADSname, String ADSpsA) {

        for (int i = 0; ; i++) {

            int choose5 = 0;
            boolean validInput = false;

            while (!validInput) {
                try {
                    System.out.println("");
                    System.out.println("****欢迎进入 管理员菜单 界面****");
                    System.out.print("请选择要进行的操作——" + '\n' + "1.密码管理" + '\n' + "2.客户管理" + '\n' + "3.商品管理" + '\n' + "4.退出登录" + '\n' + ">");
                    choose5 = input.nextInt();

                    // 在这里编写处理菜单选项的代码

                    validInput = true; // 如果没有发生异常，则标记输入为有效
                } catch (InputMismatchException e) {
                    System.out.println("输入无效，请重新输入数字选项。");
                    // 清空输入缓冲区
                    input.nextLine();
                }
            }


            if (choose5 == 1) {
                for (int j = 0; ; j++) {
                    System.out.println("");
                    System.out.println("****欢迎进入 管理员密码管理 界面****");
                    System.out.print("请输入要进行的操作——" + '\n' + "1.修改自身密码" + '\n' + "2.重置用户密码(重置为Yn123456@)" + '\n' + "3.返回" + '\n' + ">");
                    int choose4 = input.nextInt();

                    if (choose4 == 3)
                        administratormenu(ADSname, ADSpsA);
                    String newPassword = null;
                    String userName = null;
                    int typee=0;
                    if (choose4 == 2) {

                        System.out.print("请输入要重置密码的用户的name:");
                        userName = input.next();
                        newPassword = "Yn123456@";


                    }
                    if (choose4 == 1) {
                        for (int s = 0; ; s++) {
                            System.out.print("请输入修改后的password:");
                            newPassword = input.next();
                            if (isValidPassword(newPassword)) {
                                break;
                            } else {
                                System.out.println("密码不符合要求，需重新设置,密码长度大于8个字符，必须是大小写字母、数字和标点符号的组合");
                            }
                        }
                        typee=1;
                        userName=ADSname;
                    }

                    String fileName = "Alldata.xlsx";
                    String sheetName = "user";
                    boolean foundMatch = false;
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

                        // 查找匹配的用户名和密码
                        int startRow = 1;

// 查找匹配的用户名和密码
                        for (int ii = startRow; ii <= sheet.getLastRowNum(); ii++) {
                            Row row = sheet.getRow(ii);
                            Cell usernameCell = row.getCell(1);

                            Cell typeCell = row.getCell(3);
                            if (usernameCell != null && typeCell != null) {
                                String cellUsername = usernameCell.getStringCellValue();

                                double type0 = typeCell.getNumericCellValue();

                                int type = (int) type0;
                                if (userName.equals(cellUsername) && type == typee) {
                                    foundMatch = true;
                                    Cell celll = row.getCell(2);
                                    celll.setCellValue(newPassword);

                                    break;
                                }
                            }
                        }

                        // 保存Excel文件
                        FileOutputStream fileOut = new FileOutputStream(fileName);
                        workbook.write(fileOut);
                        fileOut.close();
                        // System.out.println("Excel文件保存成功！");

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
                    if (foundMatch) {
                        System.out.println("修改成功");

                    }
                    else{
                        System.out.println("修改失败");
                    }

                }
            }
            if (choose5 == 2) {
                for (int k = 0; ; k++) {

                    System.out.println("");
                    System.out.println("****欢迎进入 客户管理 界面****");
                    System.out.print("请选择要进行的操作——" + '\n' + "1.列出所有客户信息" + '\n' + "2.删除客户信息" + '\n' + "3.查询客户信息" + '\n' + "4.返回" + '\n' + ">");
                    int choose6 = input.nextInt();

                    if (choose6 == 1) {
                        int ii = 0;

                        String fileName = "Alldata.xlsx";
                        String sheetName = "user";
                        boolean foundMatch = false;
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

                            // 查找匹配的用户名和密码
                            int startRow = 1;

// 查找匹配的用户名和密码
                            for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);
                                Cell userId=row.getCell(0);
                                Cell usernameCell = row.getCell(1);
                                Cell typeCell = row.getCell(3);
                                Cell levelCell = row.getCell(4);
                                Cell timeCell = row.getCell(5);
                                Cell moneyCell = row.getCell(6);
                                Cell phoneCell = row.getCell(7);
                                Cell mailCell = row.getCell(8);

                                if (typeCell != null) {
                                    double type0 = typeCell.getNumericCellValue();
                                    int type = (int) type0;
                                    if (type == 0) {
                                        String cellUserId = userId.getStringCellValue();
                                        String cellUsername = usernameCell.getStringCellValue();
                                        String celllevel = levelCell.getStringCellValue();
                                        String celltime = timeCell.getStringCellValue();

                                        double cellmoney = moneyCell.getNumericCellValue();
                                        int cellmoneyy = (int) cellmoney;

                                        String cellphone = phoneCell.getStringCellValue();
                                        String cellmail = mailCell.getStringCellValue();

                                        System.out.println("");
                                        System.out.println("客户ID: "+cellUserId);
                                        System.out.println("用户名: "+cellUsername);
                                        System.out.println("用户级别: "+celllevel);
                                        System.out.println("用户注册时间: "+celltime);
                                        System.out.println("客户累计消费金额: "+cellmoneyy);
                                        System.out.println("用户手机号: "+cellphone);
                                        System.out.println("用户邮箱: "+cellmail);
                                      //  Cell celll = row.getCell(2);
                                      //  celll.setCellValue(newPassword);


                                    }
                                }
                            }

                            // 保存Excel文件
                            FileOutputStream fileOut = new FileOutputStream(fileName);
                            workbook.write(fileOut);
                            fileOut.close();
                            // System.out.println("Excel文件保存成功！");

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
                    if (choose6 == 2) {
                        System.out.print("请输入要删除的客户的ID:");
                        String deleteName = input.next();

                        String fileName = "Alldata.xlsx";
                        String sheetName = "user";
                        boolean foundMatch = false;
                        boolean t = false;
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

                            // 查找匹配的用户名和密码
                            int startRow = 1;

// 查找匹配的用户名和密码
                            for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);

                                Cell userId=row.getCell(0);

                                Cell typeCell = row.getCell(3);

                                if (typeCell != null&&userId != null) {


                                    double type0 = typeCell.getNumericCellValue();
                                    int type = (int) type0;
                                    String cellUserId = userId.getStringCellValue();

                                    if (type == 0 && cellUserId.equals(deleteName)) {

                                        String confirmation;

                                        boolean isValidInput = false;
                                        while (!isValidInput) {
                                            try {
                                                System.out.print("已查询到客户信息，确定要删除吗？(Y/N) > ");
                                                confirmation = input.next().toUpperCase();

                                                if (confirmation.equals("Y")) {
                                                    // 执行删除操作
                                                    System.out.println("执行删除操作...");
                                                    foundMatch=true;

                                                    sheet.removeRow(row);
                                                    isValidInput = true;
                                                } else if (confirmation.equals("N")) {
                                                    // 取消删除操作
                                                    System.out.println("取消删除操作...");
                                                    isValidInput = true;
                                                   t=true;
                                                } else {
                                                    throw new IllegalArgumentException("无效的选项，请重新输入");
                                                }
                                            } catch (IllegalArgumentException e) {
                                                System.out.println(e.getMessage());
                                            }
                                        }


                                        System.out.println("");

                                        //  Cell celll = row.getCell(2);
                                        //  celll.setCellValue(newPassword);

                                    }
                                }
                            }
                            for (int ii = sheet.getLastRowNum(); ii >= 0; ii--) {
                                Row row = sheet.getRow(ii);
                                if (rowIsEmpty(row)) {
                                    sheet.shiftRows(ii + 1, sheet.getLastRowNum(), -1);
                                }
                            }

                            // 保存Excel文件
                            FileOutputStream fileOut = new FileOutputStream(fileName);
                            workbook.write(fileOut);
                            fileOut.close();
                            // System.out.println("Excel文件保存成功！");

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
                        if (foundMatch) {
                            System.out.println("删除成功");

                        }
                        else{
                            if(!t)
                            System.out.println("未找到该客户，删除失败");
                        }

                    }

                    if (choose6 == 3) {

                            System.out.print("请输入要查询的客户的ID:");
                            String selectName = input.next();


                        String fileName = "Alldata.xlsx";
                        String sheetName = "user";
                        boolean foundMatch = false;
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

                            // 查找匹配的用户名和密码
                            int startRow = 1;

// 查找匹配的用户名和密码
                            for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);
                                Cell userId=row.getCell(0);
                                Cell usernameCell = row.getCell(1);
                                Cell typeCell = row.getCell(3);
                                Cell levelCell = row.getCell(4);
                                Cell timeCell = row.getCell(5);
                                Cell moneyCell = row.getCell(6);
                                Cell phoneCell = row.getCell(7);
                                Cell mailCell = row.getCell(8);

                                if (typeCell != null&&userId != null) {


                                    double type0 = typeCell.getNumericCellValue();
                                    int type = (int) type0;
                                    String cellUserId = userId.getStringCellValue();

                                    if (type == 0 && cellUserId.equals(selectName)) {
                                        foundMatch=true;

                                        String cellUsername = usernameCell.getStringCellValue();
                                        String celllevel = levelCell.getStringCellValue();
                                        String celltime = timeCell.getStringCellValue();

                                        double cellmoney = moneyCell.getNumericCellValue();
                                        int cellmoneyy = (int) cellmoney;

                                        String cellphone = phoneCell.getStringCellValue();
                                        String cellmail = mailCell.getStringCellValue();

                                        System.out.println("");
                                        System.out.println("客户ID: "+cellUserId);
                                        System.out.println("用户名: "+cellUsername);
                                        System.out.println("用户级别: "+celllevel);
                                        System.out.println("用户注册时间: "+celltime);
                                        System.out.println("客户累计消费金额: "+cellmoneyy);
                                        System.out.println("用户手机号: "+cellphone);
                                        System.out.println("用户邮箱: "+cellmail);
                                        //  Cell celll = row.getCell(2);
                                        //  celll.setCellValue(newPassword);


                                    }
                                }
                            }

                            // 保存Excel文件
                            FileOutputStream fileOut = new FileOutputStream(fileName);
                            workbook.write(fileOut);
                            fileOut.close();
                            // System.out.println("Excel文件保存成功！");

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
                        if (foundMatch) {
                            System.out.println("查询成功");

                        }
                        else{
                            System.out.println("查询失败");
                        }


                    }
                    if (choose6 == 4)
                            administratormenu(ADSname, ADSpsA);
                    }
                }

            if (choose5 == 3) {
                for (int h = 0; ; h++) {
                    System.out.println("");
                    System.out.println("****欢迎进入 商品管理 界面****");
                    System.out.print("请选择要进行的操作——" + '\n' + "1.列出所有商品信息" + '\n' + "2.添加商品信息" + '\n' + "3.修改商品信息" + '\n' + "4.删除商品信息" + '\n' + "5.查询商品信息" + '\n' + "6.返回" + '\n' + ">");
                    int choose7 = input.nextInt();

                    if (choose7 == 1) {
                        String fileName = "Alldata.xlsx";
                        String sheetName = "commodity";

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

                            int startRow = 1;

                            for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);
                                Cell proid=row.getCell(0);
                                Cell proname = row.getCell(1);
                                Cell factory = row.getCell(2);
                                Cell date = row.getCell(3);
                                Cell type = row.getCell(4);
                                Cell inprice = row.getCell(5);
                                Cell outprice = row.getCell(6);
                                Cell num = row.getCell(7);


                                String cellproid = proid.getStringCellValue();
                                String cellproname = proname.getStringCellValue();
                                String cellfactory = factory.getStringCellValue();
                                String celldate = date.getStringCellValue();
                                String celltype = type.getStringCellValue();

                                double cellinprice = inprice.getNumericCellValue();
                                int cellinpricee = (int) cellinprice;

                                double celloutprice = outprice.getNumericCellValue();
                                int celloutpricee = (int) celloutprice;

                                double cellnum = num.getNumericCellValue();
                                int cellnume = (int) cellnum;


                                System.out.println("");
                                System.out.println("商品编号: "+cellproid);
                                System.out.println("商品: "+cellproname);
                                System.out.println("生产厂家: "+cellfactory);
                                System.out.println("生产日期: "+celldate);
                                System.out.println("型号: "+celltype);
                                System.out.println("进货价: "+cellinpricee);
                                System.out.println("零售价: "+celloutpricee);
                                System.out.println("数量: "+cellnume);
                                //  Cell celll = row.getCell(2);
                                //  celll.setCellValue(newPassword);


                            }

                            // 保存Excel文件
                            FileOutputStream fileOut = new FileOutputStream(fileName);
                            workbook.write(fileOut);
                            fileOut.close();
                            // System.out.println("Excel文件保存成功！");

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

                    if (choose7 == 2) {
                        boolean yesno = true;
                        System.out.print("请输入要添加的商品的编号: ");
                        String ProID = input.next();

                        // 检查商品编号是否已存在

                        System.out.print("请输入商品名称: ");
                        String Pro = input.next();
                        System.out.print("请输入商品的厂家: ");
                        String factory = input.next();
                        System.out.print("请输入商品的生产日期: ");
                        String date = input.next();
                        System.out.print("请输入商品的型号: ");
                        String type = input.next();
                        System.out.print("请输入商品的进货价: ");
                        int intoprice = input.nextInt();
                        System.out.print("请输入商品的零售价: ");
                        int outprice = input.nextInt();
                        System.out.print("请输入商品的数量: ");
                        int nums = input.nextInt();


                        String fileName = "Alldata.xlsx";
                        String sheetName = "commodity";
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

                            int startRow = 1;
                            for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);
                                Cell PROId=row.getCell(0);
                                Cell PROnameCell = row.getCell(1);

                                if (PROId != null&&PROnameCell != null) {


                                    String cellproId = PROId.getStringCellValue();
                                    String cellnameCell = PROnameCell.getStringCellValue();

                                    if ( cellnameCell.equals(Pro) && cellproId.equals(ProID)) {
                                        yesno=false;
                                    }
                                }
                            }

                            if(yesno){
                                Row dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

                                dataRow.createCell(0).setCellValue(ProID);
                                dataRow.createCell(1).setCellValue(Pro);
                                dataRow.createCell(2).setCellValue(factory);
                                dataRow.createCell(3).setCellValue(date);
                                dataRow.createCell(4).setCellValue(type);
                                dataRow.createCell(5).setCellValue(intoprice);
                                dataRow.createCell(6).setCellValue(outprice);
                                dataRow.createCell(7).setCellValue(nums);
                                System.out.println("商品添加成功");
                            }
                            else{
                                System.out.println("商品已经添加过，不能重复添加");
                            }

                            // 保存Excel文件
                            FileOutputStream fileOut = new FileOutputStream(fileName);
                            workbook.write(fileOut);
                            fileOut.close();


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

                    if (choose7 == 3) {
                        boolean yesno = false;
                        System.out.print("请输入要修改的商品编号:");
                        String changePro = input.next();

                        String fileName = "Alldata.xlsx";
                        String sheetName = "commodity";
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

                            int startRow = 1;
                            for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);
                                Cell PROId=row.getCell(0);

                                if (PROId != null) {
                                    String cellproId = PROId.getStringCellValue();
                                    if (cellproId.equals(changePro)) {
                                        yesno=true;
                                        System.out.print("请输入修改后的商品名称: ");
                                        String Pro = input.next();
                                        System.out.print("请输入修改后的商品的厂家: ");
                                        String factory = input.next();
                                        System.out.print("请输入修改后的商品的生产日期: ");
                                        String date = input.next();
                                        System.out.print("请输入修改后的商品的型号: ");
                                        String type = input.next();
                                        System.out.print("请输入修改后的商品的进货价: ");
                                        int intoprice = input.nextInt();
                                        System.out.print("请输入修改后的商品的零售价: ");
                                        int outprice = input.nextInt();
                                        System.out.print("请输入修改后的商品的数量: ");
                                        int nums = input.nextInt();

                                        Cell celll = row.getCell(1);
                                        celll.setCellValue(Pro);

                                        Cell cell1 = row.getCell(2);
                                        cell1.setCellValue(factory);

                                        Cell cell2 = row.getCell(3);
                                        cell2.setCellValue(date);

                                        Cell cell3 = row.getCell(4);
                                        cell3.setCellValue(type);

                                        Cell cell4 = row.getCell(5);
                                        cell4.setCellValue(intoprice);

                                        Cell cell5 = row.getCell(6);
                                        cell5.setCellValue(outprice);

                                        Cell cell6 = row.getCell(7);
                                        cell6.setCellValue(nums);

                                    }
                                }
                            }

                            if(yesno){
                                System.out.println("商品信息修改成功");
                            }
                            else{
                                System.out.println("未找到该商品，修改失败");
                            }

                            // 保存Excel文件
                            FileOutputStream fileOut = new FileOutputStream(fileName);
                            workbook.write(fileOut);
                            fileOut.close();


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
                    if (choose7 == 4) {
                        System.out.print("请输入要删除的商品编号:");
                        String deletePro = input.next();

                        String fileName = "Alldata.xlsx";
                        String sheetName = "commodity";
                        boolean foundMatch = false;
                        boolean t = false;
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


                            int startRow = 1;

                            for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);

                                Cell proId=row.getCell(0);


                                if (proId != null) {

                                    String cellproId = proId.getStringCellValue();

                                    if (cellproId.equals(deletePro)) {

                                        String confirmation;
                                        boolean isValidInput = false;
                                        while (!isValidInput) {
                                            try {
                                                System.out.print("已查询到商品信息，确定要删除吗？(Y/N) > ");
                                                confirmation = input.next().toUpperCase();

                                                if (confirmation.equals("Y")) {
                                                    // 执行删除操作
                                                    System.out.println("执行删除操作...");
                                                    foundMatch=true;

                                                    sheet.removeRow(row);
                                                    isValidInput = true;
                                                } else if (confirmation.equals("N")) {
                                                    // 取消删除操作
                                                    System.out.println("取消删除操作...");
                                                    isValidInput = true;
                                                    t=true;
                                                } else {
                                                    throw new IllegalArgumentException("无效的选项，请重新输入");
                                                }
                                            } catch (IllegalArgumentException e) {
                                                System.out.println(e.getMessage());
                                            }
                                        }

                                        System.out.println("");

                                        //  Cell celll = row.getCell(2);
                                        //  celll.setCellValue(newPassword);
                                    }
                                }
                            }
                            for (int ii = sheet.getLastRowNum(); ii >= 0; ii--) {
                                Row row = sheet.getRow(ii);
                                if (rowIsEmpty(row)) {
                                    sheet.shiftRows(ii + 1, sheet.getLastRowNum(), -1);
                                }
                            }

                            // 保存Excel文件
                            FileOutputStream fileOut = new FileOutputStream(fileName);
                            workbook.write(fileOut);
                            fileOut.close();
                            // System.out.println("Excel文件保存成功！");

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
                        if (foundMatch) {
                            System.out.println("删除成功");

                        }
                        else{
                            if(!t)
                                System.out.println("未找到该商品，删除失败");
                        }

                    }

                    if (choose7 == 5) {
                        System.out.print("请输入要查询的商品编号:");
                        String selectValue = input.next();

                        String fileName = "Alldata.xlsx";
                        String sheetName = "commodity";
                        boolean foundMatch = false;
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

                            // 查找匹配的用户名和密码
                            int startRow = 1;


                            for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);
                                Cell proid=row.getCell(0);
                                Cell proname = row.getCell(1);
                                Cell factory = row.getCell(2);
                                Cell date = row.getCell(3);
                                Cell type = row.getCell(4);
                                Cell inprice = row.getCell(5);
                                Cell outprice = row.getCell(6);
                                Cell num = row.getCell(7);

                                if(proid != null){
                                    String cellproid = proid.getStringCellValue();
                                    if(cellproid.equals(selectValue)){
                                        foundMatch=true;

                                        String cellproname = proname.getStringCellValue();
                                        String cellfactory = factory.getStringCellValue();
                                        String celldate = date.getStringCellValue();
                                        String celltype = type.getStringCellValue();

                                        double cellinprice = inprice.getNumericCellValue();
                                        int cellinpricee = (int) cellinprice;

                                        double celloutprice = outprice.getNumericCellValue();
                                        int celloutpricee = (int) celloutprice;

                                        double cellnum = num.getNumericCellValue();
                                        int cellnume = (int) cellnum;


                                        System.out.println("");
                                        System.out.println("商品编号: "+cellproid);
                                        System.out.println("商品: "+cellproname);
                                        System.out.println("生产厂家: "+cellfactory);
                                        System.out.println("生产日期: "+celldate);
                                        System.out.println("型号: "+celltype);
                                        System.out.println("进货价: "+cellinpricee);
                                        System.out.println("零售价: "+celloutpricee);
                                        System.out.println("数量: "+cellnume);
                                        System.out.println("");


                                    }
                                }




                                //  Cell celll = row.getCell(2);
                                //  celll.setCellValue(newPassword);

                            }



                            // 保存Excel文件
                            FileOutputStream fileOut = new FileOutputStream(fileName);
                            workbook.write(fileOut);
                            fileOut.close();
                            // System.out.println("Excel文件保存成功！");

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
                        if (foundMatch) {
                            System.out.println("查询成功");

                        }
                        else{
                            System.out.println("查询失败");
                        }









                    }
                    if (choose7 == 6)
                        administratormenu(ADSname, ADSpsA);
                }
            }


            if (choose5 == 4) {
                System.out.println("返回START页面");
                mm.start();
            }

        }
    }
}