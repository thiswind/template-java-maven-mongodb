package com.company;
import java.io.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;


import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;





public class User {
    Scanner input = new Scanner(System.in);
    Start m= new Start();
    int type=0;
    int level=1;
    public void userLogin(){
        boolean mis=true;
        while(mis){
            System.out.println("");
            System.out.println("****欢迎进入用户 登录/注册 界面****");
            System.out.print("请选择要进行的操作——1.登录  2.注册  3.返回>");
            try {
                int choose1 = input.nextInt();
                if (choose1 == 1) {
                    login();
                    mis = false;
                } else if (choose1 == 2) {
                    register();
                    mis = false;
                } else if (choose1 == 3) {
                    m.start();
                } else {
                    System.out.println("输入有误，请重新输入");
                }
            } catch (InputMismatchException e) {
                System.out.println("输入有误，请重新输入");
                input.nextLine(); // 清除输入缓冲区中的无效输入
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void login() throws SQLException, IOException {

        System.out.println("");
        System.out.println("****欢迎进入 用户登录 界面****");
        System.out.print("请输入name:");
        String username = input.next();

        System.out.print("请输入password:");
        String userps = input.next();

        if (username.equals("0") || userps.equals("0")) {
            System.out.println("返回 用户 登录/注册 界面");
            userLogin();
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
                    if (username.equals(cellUsername) && userps.equals(cellPassword) && type == 0) {
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
            usermenu (username,userps);
        }
        else{
            System.out.println("用户名密码错误，登陆失败");login();
        }


    }

    public void register() {
        System.out.println("");
        System.out.println("****欢迎进入 用户注册 界面****");
        String username1=null;
        for(int h=0;;h++){
            System.out.print("请输入name:");
            username1 = input.next();
            if (username1.length() < 5) {
                System.out.print("长度不能小于5，请重新输入");
            }
            else
                break;
        }

        String userps1=null;
        for(int i=0;;i++){
            System.out.print("请输入password:");
            userps1 = input.next();
            if (isValidPassword(userps1)) {
                break;
            } else {
                System.out.println("密码不符合要求，需重新设置,密码长度大于8个字符，必须是大小写字母、数字和标点符号的组合");
            }
        }


        System.out.print("请输入手机号:");
        String phonenunber = input.next();

        System.out.print("请输入邮箱:");
        String mail = input.next();
        int userLevel=1;
        LocalDateTime localDateTime1 = LocalDateTime.now();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime1 = localDateTime1.format(formatter1);

        int totalAmountSpent=0;

        String fileName = "Alldata.xlsx";
        String sheetName = "user";
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


            // 写入数据
            Row dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            dataRow.createCell(0).setCellValue("123-"+username1);
            dataRow.createCell(1).setCellValue(username1);
            dataRow.createCell(2).setCellValue(userps1);
            dataRow.createCell(3).setCellValue(0);
            dataRow.createCell(4).setCellValue("铜牌客户");
            dataRow.createCell(5).setCellValue(LocalDateTime.now().format(dtf));
            dataRow.createCell(6).setCellValue(0);
            dataRow.createCell(7).setCellValue(phonenunber);
            dataRow.createCell(8).setCellValue(mail);

            // 保存Excel文件
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("用户ID为"+"123-"+username1);
            System.out.println("注册成功,Excel文件保存成功！");

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
        userLogin();
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

    public void usermenu (String username,String password) throws SQLException, IOException {
        for(int i=0;;i++){
            int choose2=0;

            do {
                try {

                    System.out.println("");
                    System.out.println("****欢迎进入 用户菜单 界面****");
                    System.out.print("请选择要进行的操作——" + '\n' + "1.密码管理" + '\n' + "2.购物" + '\n' + "3.退出登录" + '\n' + ">");
                    choose2 = input.nextInt();

                    // 在这里根据用户的选择执行相应的操作

                } catch (Exception e) {
                    System.out.println("输入异常，请重新输入。");
                    input.nextLine(); // 清空输入流
                    choose2 = 0; // 将choose2重置为0，以便重新循环
                }
            } while (choose2 < 1 || choose2 > 3);

            if (choose2 == 1) {
                boolean validInput = false;
                int choose4=0;
                while (!validInput) {
                    try {
                        System.out.println("");
                        System.out.println("****欢迎进入 用户密码管理 界面****");
                        System.out.print("请输入要进行的操作——" + '\n' + "1.修改密码" + '\n' + "2.重置密码(重置为Yn123456@)" + '\n' + ">");
                        choose4 = input.nextInt();

                        // 在这里根据choose4的值来执行对应的操作

                        validInput = true; // 输入合法，跳出循环
                    } catch (InputMismatchException e) {
                        System.out.println("输入格式错误，请输入合法的操作编号！");
                        input.nextLine(); // 清空输入缓冲区
                        // 可以选择继续循环提示用户重新输入，或者直接结束程序
                    }
                }

                String newPassword = null;
                if (choose4 == 2){
                    newPassword = "Yn123456@";
                }

                if (choose4 == 1) {
                    System.out.println("");
                    for(int s=0;;s++){
                        System.out.print("请输入修改后的password:");
                        newPassword = input.next();
                        if (isValidPassword(newPassword)) {
                            break;
                        } else {
                            System.out.println("密码不符合要求，需重新设置,密码长度大于8个字符，必须是大小写字母、数字和标点符号的组合");
                        }

                    }
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
                            if (username.equals(cellUsername) && type == 0) {
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


            if(choose2==2) {

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

                for (int m = 0; ; m++) {

                    System.out.println("");
                    System.out.println("****欢迎进入 购物 界面****");
                    System.out.print("请选择要进行的操作——" + '\n' + "1.将商品加入购物车" + '\n' + "2.从购物车中移除商品" + '\n' + "3.修改购物车中的商品" + '\n' + "4.结账（清空购物车）" + '\n' + "5.查看购物车" + '\n' + "6.查看购物历史" + '\n' + "7.返回" + '\n' + ">");
                    int choose8 = input.nextInt();

                    if (choose8 == 1) {

                        int price=0;
                        int amount=0;
                        System.out.print("请输入要加入的商品名称:");
                        String product = input.next();


                        String fileName1 = "Alldata.xlsx";
                        String sheetName1 = "commodity";
                        boolean foundMatch = false;
                        Workbook workbook1 = null;

                        try {
                            File file = new File(fileName1);
                            if (!file.exists()) {
                                // 创建新的Excel文件
                                workbook1 = new XSSFWorkbook();
                            } else {
                                // 打开现有的Excel文件
                                FileInputStream fileInputStream = new FileInputStream(file);
                                workbook1 = new XSSFWorkbook(fileInputStream);
                                fileInputStream.close();
                            }

                            // 获取或创建表格
                            Sheet sheet = workbook1.getSheet(sheetName1);

                            // 查找匹配的用户名和密码
                            int startRow = 1;


                            for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);

                                Cell proname = row.getCell(1);

                                if(proname != null){
                                    String cellproname = proname.getStringCellValue();
                                    if(cellproname.equals(product)){

                                        foundMatch=true;
                                        Cell Price = row.getCell(6);
                                        Cell num = row.getCell(7);

                                        double cellnum = num.getNumericCellValue();
                                        int cellnume = (int) cellnum;
                                        amount = cellnume;
                                        double cellprice = Price.getNumericCellValue();
                                        int cellpricee = (int) cellprice;
                                        price=cellpricee;

                                    }
                                }

                                //  Cell celll = row.getCell(2);
                                //  celll.setCellValue(newPassword);
                            }
                            // 保存Excel文件
                            FileOutputStream fileOut = new FileOutputStream(fileName1);
                            workbook1.write(fileOut);
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
                            int nums=0;
                            boolean isValidInput = false;
                            while (!isValidInput) {
                                try {
                                    System.out.print("请输入要添加的商品数量：");
                                    nums = input.nextInt();

                                    if (nums > 0) {
                                        isValidInput = true;
                                    } else {
                                        throw new IllegalArgumentException("商品数量必须大于0，请重新输入");
                                    }
                                } catch (Exception e) {
                                    System.out.println("输入无效，请重新输入整数");
                                    input.nextLine(); // 清空输入缓冲区
                                }
                            }


                            String fileName2 = "Alldata.xlsx";
                            String sheetName2 = "shopcart";
                            boolean foundMatch2 = false;
                            Workbook workbook2 = null;

                            try {
                                File file = new File(fileName2);
                                if (!file.exists()) {
                                    // 创建新的Excel文件
                                    workbook2 = new XSSFWorkbook();
                                } else {
                                    // 打开现有的Excel文件
                                    FileInputStream fileInputStream = new FileInputStream(file);
                                    workbook2 = new XSSFWorkbook(fileInputStream);
                                    fileInputStream.close();
                                }

                                // 获取或创建表格
                                Sheet sheet = workbook2.getSheet(sheetName2);

                                // 查找匹配的用户名和密码
                                int startRow = 1;


                                for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                    Row row = sheet.getRow(j);
                                    Cell usernamee=row.getCell(0);
                                    Cell productt = row.getCell(1);
                                    Cell pricee = row.getCell(2);
                                    Cell amountt = row.getCell(3);


                                    if(usernamee != null && productt!= null){
                                        String cellproname = productt.getStringCellValue();
                                        String cellusername = usernamee.getStringCellValue();
                                        if(cellproname.equals(product)&&cellusername.equals(username)){
                                            foundMatch2=true;

                                            double cellprice = pricee.getNumericCellValue();
                                            int cellinpricee = (int) cellprice;

                                            double cellamountt = amountt.getNumericCellValue();
                                            int cellamount = (int) cellamountt;

                                            if(cellamount+nums>amount){
                                                System.out.println("商品库存不足，加入购物车失败");
                                            }
                                            else{
                                                  int a = nums+cellamount;
                                                  Cell celll = row.getCell(3);
                                                  celll.setCellValue(a);
                                                  System.out.println("购物车中已有该商品，商品数量已更新");

                                            }

                                        }

                                    }

                                    //  Cell celll = row.getCell(2);
                                    //  celll.setCellValue(newPassword);

                                }

                                if (!foundMatch2) {

                                    Row dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

                                    dataRow.createCell(0).setCellValue(username);
                                    dataRow.createCell(1).setCellValue(product);
                                    dataRow.createCell(2).setCellValue(price);
                                    dataRow.createCell(3).setCellValue(nums);

                                    System.out.println("添加成功");

                                }


                                // 保存Excel文件
                                FileOutputStream fileOut = new FileOutputStream(fileName2);
                                workbook2.write(fileOut);
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
                        else{
                            System.out.println("未找到该商品，添加购物车失败");
                        }

                    }

                    if (choose8 == 2) {
                        System.out.print("请输入要移除的商品名称:");
                        String deletename = input.next();


                        String fileName3 = "Alldata.xlsx";
                        String sheetName3 = "shopcart";
                        boolean foundMatch3 = false;
                        boolean t = false;
                        Workbook workbook3 = null;

                        try {
                            File file = new File(fileName3);
                            if (!file.exists()) {
                                // 创建新的Excel文件
                                workbook3 = new XSSFWorkbook();
                            } else {
                                // 打开现有的Excel文件
                                FileInputStream fileInputStream = new FileInputStream(file);
                                workbook3 = new XSSFWorkbook(fileInputStream);
                                fileInputStream.close();
                            }

                            // 获取或创建表格
                            Sheet sheet = workbook3.getSheet(sheetName3);

                            // 查找匹配的用户名和密码
                            int startRow = 1;

// 查找匹配的用户名和密码
                            for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);
                                Cell useename=row.getCell(0);
                                Cell productname=row.getCell(1);



                                if ( productname != null&&useename!=null) {
                                    String productnameStringCellValue = productname.getStringCellValue();
                                    String usernameStringCellValue = useename.getStringCellValue();
                                    if (productnameStringCellValue.equals(deletename)&&usernameStringCellValue.equals(username)) {

                                        String confirmation;

                                        boolean isValidInput = false;
                                        while (!isValidInput) {
                                            try {
                                                System.out.print("已查询到商品信息，确定要移除吗？(Y/N) > ");
                                                confirmation = input.next().toUpperCase();

                                                if (confirmation.equals("Y")) {
                                                    // 执行删除操作
                                                    System.out.println("执行移除操作...");
                                                    foundMatch3=true;

                                                    sheet.removeRow(row);
                                                    isValidInput = true;
                                                } else if (confirmation.equals("N")) {
                                                    // 取消删除操作
                                                    System.out.println("取消移除操作...");
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
                            FileOutputStream fileOut = new FileOutputStream(fileName3);
                            workbook3.write(fileOut);
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
                        if (foundMatch3) {
                            System.out.println("移除成功");

                        }
                        else{
                            if(!t)
                                System.out.println("未找到该商品，移除失败");
                        }

                    }

                    if(choose8==3){

                        int Num=1;
                        boolean yesno=false;
                        boolean t=false;
                        System.out.print("请输入要修改的商品名称:");
                        String changename = input.next();


                        String fileName4 = "Alldata.xlsx";
                        String sheetName4 = "shopcart";
                        Workbook workbook4 = null;

                        try {
                            File file = new File(fileName4);
                            if (!file.exists()) {
                                // 创建新的Excel文件
                                workbook4 = new XSSFWorkbook();
                            } else {
                                // 打开现有的Excel文件
                                FileInputStream fileInputStream = new FileInputStream(file);
                                workbook4 = new XSSFWorkbook(fileInputStream);
                                fileInputStream.close();
                            }

                            // 获取或创建表格
                            Sheet sheet = workbook4.getSheet(sheetName4);

                            int startRow = 1;
                            for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);
                                Cell user=row.getCell(0);

                                Cell productname=row.getCell(1);

                                if (user != null&&productname!=null) {
                                    String uservalue = user.getStringCellValue();
                                    String productnameStringCellValue = productname.getStringCellValue();
                                    if (uservalue.equals(username)&&productnameStringCellValue.equals(changename)) {
                                        yesno=true;

                                        boolean isValidInput = false;
                                        while (!isValidInput) {
                                            try {
                                                System.out.print("请输入修改后的商品数量(往小了改): ");
                                                Num = input.nextInt();
                                                isValidInput = true;
                                            } catch (Exception e) {
                                                System.out.println("输入无效，请重新输入");
                                                input.nextLine(); // 清空输入缓冲区
                                            }
                                        }
                                        if(Num>=1){

                                            Cell cell6 = row.getCell(3);
                                            cell6.setCellValue(Num);
                                            System.out.println("商品信息修改成功");
                                        }

                                        if(Num<1){
                                            sheet.removeRow(row);
                                            System.out.println("商品数量小于1，已移除");
                                        }

                                    }
                                }
                            }

                            for (int ii = sheet.getLastRowNum(); ii >= 0; ii--) {
                                Row row = sheet.getRow(ii);
                                if (rowIsEmpty(row)) {
                                    sheet.shiftRows(ii + 1, sheet.getLastRowNum(), -1);
                                }
                            }

                            if(!yesno){
                                System.out.println("未找到该商品，修改失败");
                            }


                            // 保存Excel文件
                            FileOutputStream fileOut = new FileOutputStream(fileName4);
                            workbook4.write(fileOut);
                            fileOut.close();


                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (workbook4 != null) {
                                try {
                                    workbook4.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }

                    if(choose8==4){

                        int total=0;
                        int totalamount=0;
                        String way = null;
                        while (true) {
                            System.out.print("请选择支付方式： 1.支付宝  2.微信  3.银行卡 ");
                            try {
                                int cho = input.nextInt();
                                if (cho == 1) {
                                    way = "支付宝";
                                    break;
                                } else if (cho == 2) {
                                    way = "微信";
                                    break;
                                } else if (cho == 3) {
                                    way = "银行卡";
                                    break;
                                } else {
                                    System.out.println("请输入有效的选项！");
                                }
                            } catch (Exception e) {
                                System.out.println("输入错误，请重新输入！");
                                input.nextLine(); // 清除输入缓冲区
                            }
                        }

                        String fileName3 = "Alldata.xlsx";
                        String sheetName3 = "shopcart";
                        String sheetName4 = "history";
                        String sheetName5 = "user";
                        String sheetName6 = "commodity";

                        boolean foundMatch3 = false;
                        boolean t = false;
                        Workbook workbook3 = null;

                        try {
                            File file = new File(fileName3);
                            if (!file.exists()) {
                                // 创建新的Excel文件
                                workbook3 = new XSSFWorkbook();
                            } else {
                                // 打开现有的Excel文件
                                FileInputStream fileInputStream = new FileInputStream(file);
                                workbook3 = new XSSFWorkbook(fileInputStream);
                                fileInputStream.close();
                            }

                            // 获取或创建表格
                            Sheet sheet = workbook3.getSheet(sheetName3);
                            Sheet sheet4 = workbook3.getSheet(sheetName4);
                            Sheet sheet5 = workbook3.getSheet(sheetName5);
                            Sheet sheet6 = workbook3.getSheet(sheetName6);

                            // 查找匹配的用户名和密码
                            int startRow = 1;

                          int amount=0;
                            for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);
                                Cell useename=row.getCell(0);

                                if (useename!=null) {

                                    String usernameStringCellValue = useename.getStringCellValue();
                                    if (usernameStringCellValue.equals(username)) {
                                        Cell productcell=row.getCell(1);
                                        Cell pricecell=row.getCell(2);
                                        Cell amountcell=row.getCell(3);
                                        String productname=productcell.getStringCellValue();
                                        double priceee = pricecell.getNumericCellValue();
                                        int price=(int) priceee;

                                        double am = amountcell.getNumericCellValue();
                                         amount=(int) am;

                                        int thistotal = amount*price;

                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        String purchaseTime = dateFormat.format(new Date());

                                        total=total+thistotal;

                                        Row dataRow = sheet4.createRow(sheet4.getLastRowNum() + 1);

                                        dataRow.createCell(0).setCellValue(username);
                                        dataRow.createCell(1).setCellValue(productname);
                                        dataRow.createCell(2).setCellValue(amount);
                                        dataRow.createCell(3).setCellValue(thistotal);
                                        dataRow.createCell(4).setCellValue(purchaseTime);

                                        sheet.removeRow(row);
                                        System.out.println("1条记录成功写入购物历史");



                                        for (int e = startRow; e <= sheet6.getLastRowNum(); e++) {
                                            Row roww = sheet6.getRow(e);
                                            Cell productnamee=roww.getCell(1);

                                            if (productnamee!=null) {

                                                String productnameeStringCellValue = productnamee.getStringCellValue();
                                                if (productnameeStringCellValue.equals(productname)) {
                                                    Cell productnum=roww.getCell(7);


                                                    double productnumm = productnum.getNumericCellValue();
                                                    int oldnum=(int) productnumm;

                                                    int newnum=oldnum-amount;

                                                    Cell cell = roww.getCell(7);
                                                    cell.setCellValue(newnum);
                                                    System.out.println("仓库商品数量更新成功");

                                                }
                                            }
                                        }



                                    }
                                }
                            }

                            for (int n = startRow; n <= sheet5.getLastRowNum(); n++) {
                                Row roww = sheet5.getRow(n);
                                Cell usernamee=roww.getCell(1);
                                Cell typee = roww.getCell(3);

                                if (usernamee!=null&typee!=null) {
                                    double tp = typee.getNumericCellValue();
                                    int type=(int) tp;

                                    String usernameeStringCellValue = usernamee.getStringCellValue();
                                    if (usernameeStringCellValue.equals(username)&&type==0) {
                                        Cell moneynum=roww.getCell(6);

                                        double moneynumm = moneynum.getNumericCellValue();
                                        int oldnum=(int) moneynumm;
                                        int newmoney= total+oldnum;


                                        Cell cell = roww.getCell(6);
                                        cell.setCellValue(newmoney);
                                        System.out.println("用户累计消费金额更新成功");

                                        if(newmoney>=10&&newmoney<100){
                                            Cell cell1 = roww.getCell(4);
                                            cell1.setCellValue("银牌客户");
                                           // System.out.println("用户等级更新成功");
                                        }

                                        if(newmoney>=100){
                                            Cell cell2 = roww.getCell(4);
                                            cell2.setCellValue("金牌客户");
                                           // System.out.println("用户等级更新成功");
                                        }


                                    }
                                }
                            }


                            for (int ii = sheet.getLastRowNum(); ii >= 0; ii--) {
                                Row row = sheet.getRow(ii);
                                if (rowIsEmpty(row)) {
                                    sheet.shiftRows(ii + 1, sheet.getLastRowNum(), -1);
                                }
                            }
                            System.out.println("共需支付 "+ total +" 元，用 "+way+" 支付成功");
                            // 保存Excel文件
                            FileOutputStream fileOut = new FileOutputStream(fileName3);
                            workbook3.write(fileOut);
                            fileOut.close();

                            // System.out.println("Excel文件保存成功！");

                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (workbook3 != null) {
                                try {
                                    workbook3.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }


                    }






                    if(choose8==5){
                        System.out.println("****购物车内容如下****");
                        String fileName1 = "Alldata.xlsx";
                        String sheetName1 = "shopcart";

                        Workbook workbook1 = null;

                        try {
                            File file = new File(fileName1);
                            if (!file.exists()) {
                                // 创建新的Excel文件
                                workbook1 = new XSSFWorkbook();
                            } else {
                                // 打开现有的Excel文件
                                FileInputStream fileInputStream = new FileInputStream(file);
                                workbook1 = new XSSFWorkbook(fileInputStream);
                                fileInputStream.close();
                            }

                            // 获取或创建表格
                            Sheet sheet = workbook1.getSheet(sheetName1);

                            int startRow = 1;

                            for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);
                                Cell usernameee=row.getCell(0);

                                if(usernameee!=null){
                                    String celluserid = usernameee.getStringCellValue();
                                    if(celluserid.equals(username)){
                                        Cell proname = row.getCell(1);
                                        Cell priceee = row.getCell(2);
                                        Cell amountt = row.getCell(3);
                                        String cellproname = proname.getStringCellValue();

                                        double cellprice = priceee.getNumericCellValue();
                                        int cellpricee = (int) cellprice;

                                        double cellnum = amountt.getNumericCellValue();
                                        int cellnume = (int) cellnum;

                                        System.out.println("");
                                        System.out.println("用户名: "+username);
                                        System.out.println("商品: "+cellproname);
                                        System.out.println("价格: "+cellpricee);
                                        System.out.println("数量: "+cellnume);

                                    }

                                }

                            }

                            // 保存Excel文件
                            FileOutputStream fileOut = new FileOutputStream(fileName1);
                            workbook1.write(fileOut);
                            fileOut.close();
                            // System.out.println("Excel文件保存成功！");

                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (workbook1 != null) {
                                try {
                                    workbook1.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }

                    if(choose8==6){
                        System.out.println("****购物历史如下****");
                        String fileName1 = "Alldata.xlsx";
                        String sheetName1 = "history";

                        Workbook workbook1 = null;

                        try {
                            File file = new File(fileName1);
                            if (!file.exists()) {
                                // 创建新的Excel文件
                                workbook1 = new XSSFWorkbook();
                            } else {
                                // 打开现有的Excel文件
                                FileInputStream fileInputStream = new FileInputStream(file);
                                workbook1 = new XSSFWorkbook(fileInputStream);
                                fileInputStream.close();
                            }

                            // 获取或创建表格
                            Sheet sheet = workbook1.getSheet(sheetName1);

                            int startRow = 1;

                            for (int j = startRow; j <= sheet.getLastRowNum(); j++) {
                                Row row = sheet.getRow(j);
                                Cell usernameee=row.getCell(0);

                                if(usernameee!=null){
                                    String celluserid = usernameee.getStringCellValue();
                                    if(celluserid.equals(username)){

                                        Cell proname = row.getCell(1);
                                        Cell priceee = row.getCell(3);
                                        Cell amountt = row.getCell(2);
                                        Cell time = row.getCell(4);
                                        String cellproname = proname.getStringCellValue();
                                        String timme = time.getStringCellValue();
                                        double cellprice = priceee.getNumericCellValue();
                                        int cellpricee = (int) cellprice;

                                        double cellnum = amountt.getNumericCellValue();
                                        int cellnume = (int) cellnum;

                                        System.out.println("");
                                        System.out.println("用户名: "+username);
                                        System.out.println("商品: "+cellproname);
                                        System.out.println("数量: "+cellnume);
                                        System.out.println("金额: "+cellpricee);
                                        System.out.println("购物时间: "+timme);
                                    }

                                }

                            }

                            // 保存Excel文件
                            FileOutputStream fileOut = new FileOutputStream(fileName1);
                            workbook1.write(fileOut);
                            fileOut.close();
                            // System.out.println("Excel文件保存成功！");

                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (workbook1 != null) {
                                try {
                                    workbook1.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }


                    }






                    if(choose8==7){
                        usermenu (username,password);
                    }
                }

            }
            if(choose2==3)
                m.start();

        }

    }

}






