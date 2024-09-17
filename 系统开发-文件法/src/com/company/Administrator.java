package com.company;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;


public class Administrator {
    Scanner input = new Scanner(System.in);
    Start mm= new Start();
    public void administratorLogin(){
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
        String filePath = "administrator.txt";

        // 查找操作
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("用户名: ")) {
                    String name = line.substring(5);
                    //System.out.println(line.length());
                   // System.out.println(name);

                    //String name = line.substring(9);
                    // 读取密码
                    String passwordLine = reader.readLine();
                    String password = passwordLine.substring(4);

                   // System.out.println(passwordLine.length());
                   // System.out.println(password);
                    //  String password = passwordLine.substring(7);
                    // 检查用户名和密码是否匹配
                    if (name.equals(adname) && password.equals(adps)) {
                        found = true;
                        break;
                    }
                }
            }

            if (found) {
                System.out.println("登录成功");
                reader.close();

                administratormenu (adname,adps);
            } else {
                reader.close();
                System.out.println("用户名密码错误,请重新登录");
                administratorLogin();
            }
        } catch (IOException e) {
            System.out.println("读取文件时出现错误。");
            e.printStackTrace();
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
    private static boolean isProIDExist(String proID, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("商品编号: " + proID)) {
                    return true; // 商品编号已存在
                }
            }
        } catch (IOException e) {
            System.out.println("读取文件时出现错误。");
            e.printStackTrace();
        }
        return false; // 商品编号不存在
    }



    private static boolean changePassword(String fileName, String name, String newPassword) {


        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            File tempFile = new File("temp.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("用户名: ") && line.substring(line.indexOf(":") + 1).trim().equals(name)) {
                    writer.write(line + System.lineSeparator());
                    String nextLine = reader.readLine();
                    if (nextLine.startsWith("密码: ")) {
                        writer.write("密码: " + newPassword + System.lineSeparator());
                        found = true;
                    }
                } else {
                    writer.write(line + System.lineSeparator());
                }
            }
            writer.close();
            reader.close();

            if (found) {
                Path originalPath = Path.of(fileName);
                Path tempPath = Path.of(tempFile.getAbsolutePath());
                Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("修改成功！");
            } else {
                Files.deleteIfExists(tempFile.toPath());
                System.out.println("未找到匹配的记录！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }







    public void administratormenu(String ADSname,String ADSpsA) {

        for(int i=0;;i++){

            int choose5=0;
            boolean validInput = false;

            while (!validInput) {
                try {
                    System.out.println("");
                    System.out.println("****欢迎进入 管理员菜单 界面****");
                    System.out.print("请选择要进行的操作——"+'\n'+"1.密码管理"+'\n'+ "2.客户管理"+'\n'+  "3.商品管理"+'\n'+"4.退出登录"+'\n'+">");
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
                for(int j=0;;j++) {
                    System.out.println("");
                    System.out.println("****欢迎进入 管理员密码管理 界面****");
                    System.out.print("请输入要进行的操作——" + '\n' + "1.修改自身密码" + '\n' + "2.重置用户密码(重置为Yn123456@)" + '\n' + "3.返回" +'\n' + ">");
                    int choose4 = input.nextInt();

                    if(choose4==3)
                        administratormenu(ADSname,ADSpsA);
                    String newPassword = null;
                    String userName = null;
                    if (choose4 == 2) {

                        System.out.print("请输入要重置密码的用户的name:");
                        userName = input.next();
                        newPassword = "Yn123456@";

                        changePassword("user.txt",userName,newPassword);

                    }
                    if (choose4 == 1) {
                        for(int s=0;;s++){
                            System.out.print("请输入修改后的password:");
                            newPassword = input.next();
                            if (isValidPassword(newPassword)) {
                                break;
                            } else {
                                System.out.println("密码不符合要求，需重新设置,密码长度大于8个字符，必须是大小写字母、数字和标点符号的组合");
                            }
                        }

                        changePassword("administrator.txt",ADSname,newPassword);
                    }

                }
            }
            if(choose5==2){
                for(int k=0;;k++) {

                    System.out.println("");
                    System.out.println("****欢迎进入 客户管理 界面****");
                    System.out.print("请选择要进行的操作——" + '\n' + "1.列出所有客户信息" + '\n' + "2.删除客户信息" + '\n' + "3.查询客户信息" + '\n' + "4.返回" + '\n'+ ">");
                    int choose6 = input.nextInt();

                    if (choose6 == 1) {
                        int ii=0;
                        String filePath = "user.txt";

                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                            String line;

                            while ((line = reader.readLine()) != null) {

                                System.out.println(line);
                                i++;
                                if(i%9==0){
                                    System.out.println("");
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                    if (choose6 == 2) {
                        System.out.print("请输入要删除的客户的ID:");
                        String deleteName = input.next();



                        System.out.print("请确定是否移除(Y/N)");
                        String ch=input.next();
                        if(Objects.equals(ch, "Y")) {


                            String filePath = "user.txt";
                            String tempFilePath = "temp.txt";
                            String customerIdToFind = "客户ID: " + deleteName;
                            int linesToDelete = 9;

                            try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFilePath))) {
                                String line;
                                boolean found = false;
                                int count = 0;

                                while ((line = reader.readLine()) != null) {
                                    if (line.equals(customerIdToFind)) {
                                        System.out.println("删除成功");
                                        found = true;
                                    }

                                    if (!found || count >= linesToDelete) {
                                        writer.write(line);
                                        writer.newLine();
                                    } else {
                                        count++;
                                    }
                                }
                                if (!found) {
                                    System.out.println("未查询到此客户，删除失败");
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            // 删除原文件并将临时文件重命名为原文件名
                            File file = new File(filePath);
                            file.delete();
                            File tempFile = new File(tempFilePath);
                            tempFile.renameTo(file);
                        }
                        else{
                                System.out.println("取消移除操作");
                            }




                    }
                    if(choose6==3){

                        System.out.print("请输入要查询的客户的ID:");
                        String selectName = input.next();

                        String filePath = "user.txt";
                        String customerIdToFind = "客户ID: "+selectName;
                        int linesToOutput = 9;
                        int count = 0;
                        boolean found = false;

                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                            String line;

                            while ((line = reader.readLine()) != null) {
                                if (line.equals(customerIdToFind)) {
                                    found = true;


                                }

                                if (found && count < linesToOutput) {
                                    System.out.println(line);
                                    count++;
                                }

                                if (count == linesToOutput) {

                                    break;

                                }
                            }
                            if(!found){
                                System.out.println("未查询到此客户");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(choose6==4)
                        administratormenu(ADSname,ADSpsA);
                }
            }
            if(choose5==3){
                for(int h=0;;h++) {
                    System.out.println("");
                    System.out.println("****欢迎进入 商品管理 界面****");
                    System.out.print("请选择要进行的操作——" + '\n' + "1.列出所有商品信息" + '\n' + "2.添加商品信息" + '\n' + "3.修改商品信息" + '\n' + "4.删除商品信息" + '\n' + "5.查询商品信息"+ '\n' + "6.返回" + '\n' +">");
                    int choose7 = input.nextInt();
                    if(choose7==1){
                        int ii=0;
                        String filePath = "commodity.txt";

                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                            String line;

                            while ((line = reader.readLine()) != null) {

                                System.out.println(line);
                                i++;
                                if(i%8==0){
                                    System.out.println("");
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                    }

                    if(choose7 == 2) {

                        System.out.print("请输入要添加的商品的编号: ");
                        String ProID = input.next();

                        // 检查商品编号是否已存在
                        if (isProIDExist(ProID, "commodity.txt")) {
                            System.out.println("商品编号已存在，无法添加商品信息。");
                        } else {
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

                            String filePath = "commodity.txt";

                            // 写入文件
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                                String userInfo = "商品编号: " + ProID + "\n" +
                                        "商品名称: " + Pro + "\n" +
                                        "商品厂家: " + factory + "\n" +
                                        "生产日期: " + date + "\n" +
                                        "型号: " + type + "\n" +
                                        "进货价: " + intoprice + "\n" +
                                        "零售价: " + outprice + "\n" +
                                        "数量: " + nums + "\n";

                                writer.write(userInfo);

                                writer.close();
                                System.out.println("商品信息已成功写入文件。");

                            } catch (IOException e) {
                                System.out.println("写入文件时出现错误。");
                                e.printStackTrace();
                            }
                        }
                    }
                    if(choose7==3){
                        String filePath = "commodity.txt";

                        System.out.print("请输入要修改的商品的编号: ");
                        String commodityIdToModify = input.next();


                        String PRODUCTIdToFind = "商品编号: "+commodityIdToModify;
                        boolean found = false;

                        try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath))) {
                            String line2;

                            while ((line2 = reader1.readLine()) != null) {
                                if (line2.equals(PRODUCTIdToFind)) {
                                    found = true;
                                }
                            }
                            if(!found){
                                System.out.println("未查询到该商品");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }




                        if(found){


                            System.out.print("请输入修改后的商品名称: ");
                            String addPro = input.next();
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

                            try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                                 BufferedWriter writer = new BufferedWriter(new FileWriter("commodity_temp.txt"))) {

                                String line;
                                while ((line = reader.readLine()) != null) {
                                    if (line.startsWith("商品编号: " + commodityIdToModify)) {
                                        // 写入找到的商品信息
                                        writer.write(line);
                                        writer.newLine();
                                        writer.write("商品名称: " + addPro);
                                        writer.newLine();
                                        writer.write("商品厂家: " + factory);
                                        writer.newLine();
                                        writer.write("生产日期: " + date);
                                        writer.newLine();
                                        writer.write("型号: " + type);
                                        writer.newLine();
                                        writer.write("进货价: " + intoprice);
                                        writer.newLine();
                                        writer.write("零售价: " + outprice);
                                        writer.newLine();
                                        writer.write("数量: " + nums);
                                        writer.newLine();
                                        System.out.println("修改成功");
                                        reader.readLine();
                                        reader.readLine();
                                        reader.readLine();
                                        reader.readLine();
                                        reader.readLine();
                                        reader.readLine();
                                        reader.readLine();



                                    } else {
                                        writer.write(line);
                                        writer.newLine();
                                    }
                                }




                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            File file = new File(filePath);
                            file.delete();
                            File tempFile = new File("commodity_temp.txt");

                            try {
                                if (tempFile.renameTo(file)) {
                                    System.out.println("修改成功");
                                } else {
                                    System.out.println("文件重命名失败");
                                }
                            } catch (SecurityException e) {
                                System.out.println("安全权限异常：" + e.getMessage());
                            } catch (NullPointerException e) {
                                System.out.println("空指针异常：" + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("其他异常：" + e.getMessage());
                                e.printStackTrace();
                            }

                        }



                    }

                    if(choose7==4){
                        System.out.print("请输入要删除的商品编号:");
                        String deletePro = input.next();


                        String filePath = "commodity.txt";
                        String tempFilePath = "temp.txt";
                        String proIdToFind = "商品编号: "+deletePro;
                        int linesToDelete = 8;

                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFilePath))) {
                            String line;
                            boolean found = false;
                            int count = 0;

                            while ((line = reader.readLine()) != null) {
                                if (line.equals(proIdToFind)) {
                                    System.out.println("删除成功");
                                    found = true;
                                }

                                if (!found || count >= linesToDelete) {
                                    writer.write(line);
                                    writer.newLine();
                                } else {
                                    count++;
                                }
                            }
                            if(!found){
                                System.out.println("未找到，删除失败");
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // 删除原文件并将临时文件重命名为原文件名
                        File file = new File(filePath);
                        file.delete();
                        File tempFile = new File(tempFilePath);
                        tempFile.renameTo(file);






                    }
                    if(choose7==5){
                        System.out.print("请输入要查询的商品编号:");
                        String selectValue = input.next();

                        String filePath = "commodity.txt";
                        String customerIdToFind = "商品编号: "+selectValue;
                        int linesToOutput = 8;
                        int count = 0;
                        boolean found = false;

                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                            String line;

                            while ((line = reader.readLine()) != null) {
                                if (line.equals(customerIdToFind)) {
                                    found = true;
                                }

                                if (found && count < linesToOutput) {
                                    System.out.println(line);
                                    count++;
                                }

                                if (count == linesToOutput) {

                                    break;

                                }
                            }
                            if(!found){
                                System.out.println("未查询到此商品");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    if(choose7==6)
                        administratormenu(ADSname,ADSpsA);
                }
            }
            if(choose5==4){
                System.out.println("返回START页面");
                mm.start();
            }
        }
    }
}
