package com.company;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import java.time.format.DateTimeFormatter;


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
            }
        }
    }


    public void login(){

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
        String filePath = "user.txt";

        // 查找操作
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("用户名: ")) {
                    String name = line.substring(5);
                   // System.out.println(line.length());
                  //  System.out.println(name);

                    //String name = line.substring(9);
                    // 读取密码
                    String passwordLine = reader.readLine();
                    String password = passwordLine.substring(4);

                  //  System.out.println(passwordLine.length());
                  //  System.out.println(password);
                  //  String password = passwordLine.substring(7);
                    // 检查用户名和密码是否匹配
                    if (name.equals(username) && password.equals(userps)) {
                        found = true;
                        break;
                    }
                }
            }

            if (found) {
                System.out.println("登录成功");
                reader.close();

                usermenu (username,userps);
            } else {
                reader.close();
                System.out.println("用户名密码错误,请重新登录");
                login();
            }
        } catch (IOException e) {
            System.out.println("读取文件时出现错误。");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
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


        String userID="123-"+username1;
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
        // 文件路径
        String filePath = "user.txt";
        String customerId="123-"+username1;
        // 写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
            String userInfo = "客户ID: " + customerId + "\n" +
                    "用户名: " + username1 + "\n" +
                    "密码: "+ userps1 +"\n" +
                    "类型: "+ 0 +"\n"+
                    "用户级别: " + "铜牌客户" + "\n" +
                    "用户注册时间: " + formattedDateTime1 + "\n" +
                    "客户累计消费总金额: " + totalAmountSpent + "\n" +
                    "用户手机号: " + phonenunber + "\n" +
                    "用户邮箱: " + mail + "\n";

            writer.write(userInfo);
            System.out.println("用户ID为"+"123-"+username1);
            System.out.println("客户信息已成功写入文件。");

        } catch (IOException e) {
            System.out.println("写入文件时出现错误。");
            e.printStackTrace();
        }


        //    System.out.println("Records created successfully");
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
                changePassword("user.txt",username,newPassword);
            }
            if(choose2==2) {


                int ii=0;
                String filePath = "commodity.txt";

                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        i++;
                        if(i%8!=6)
                        System.out.println(line);

                        if(i%8==0){
                            System.out.println("");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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

                        String PRODUCTIdToFind = "商品名称: "+product;
                        boolean found = false;

                        try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath))) {
                            String line2;

                            while ((line2 = reader1.readLine()) != null) {
                                if (line2.equals(PRODUCTIdToFind)) {
                                    found = true;
                                    reader1.readLine();
                                    reader1.readLine();
                                    reader1.readLine();
                                    reader1.readLine();
                                    String priceLine = reader1.readLine();
                                    String price1 =priceLine.substring(5);
                                    price = Integer.parseInt(price1);
                                    //System.out.println(price);

                                    String amountLine=reader1.readLine();
                                    String amount1=amountLine.substring(4);
                                    amount=Integer.parseInt(amount1);
                                    //System.out.println(amount);

                                }
                            }
                            if(!found){
                                System.out.println("未查询到该商品");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(found) {
                            System.out.print("请输入要加入的商品的数量:");
                            int numss = input.nextInt();


                            String filePath1 = "shopcart.txt";
                            int a = 0;
                            boolean founnd = false;

                            try (BufferedReader reader = new BufferedReader(new FileReader(filePath1));
                                 BufferedWriter writer = new BufferedWriter(new FileWriter(filePath1, true))) {

                                founnd = false;
                                String line;
                                String nn;
                                String nums = "";


                                while ((line = reader.readLine()) != null) {
                                    if (line.equals("用户名: " + username) && reader.readLine().equals("商品: " + product)) {
                                        reader.readLine();
                                        nums = reader.readLine();
                                        String numsss = nums.substring(4);
                                        a = Integer.parseInt(numsss);
                                        founnd = true;
                                        reader.close();

                                        break;
                                    }
                                }
                                if(!founnd){

                                    if (numss > amount) {
                                        System.out.println("商品库存不足，加入购车失败");
                                    } else {
                                        String userInfo = "用户名: " + username + "\n" +
                                                "商品: " + product + "\n" +
                                                "价格: " + price + "\n" +
                                                "数量: " + numss + "\n";

                                        writer.write(userInfo);

                                        System.out.println("加入购物车成功");
                                    }
                                }



                            } catch (IOException e) {
                                e.printStackTrace();
                            }




                            if (founnd) {
                                if (a + numss > amount) {
                                    System.out.println("商品库存不足，加入购物车失败");
                                } else {

                                    File inputFile = new File("shopcart.txt");
                                    File tempFile = new File("shopcarttemp.txt");

                                    try (BufferedReader rreader = new BufferedReader(new FileReader(inputFile));
                                         BufferedWriter rwriter = new BufferedWriter(new FileWriter(tempFile))) {

                                        String rline;
                                        while ((rline = rreader.readLine()) != null) {
                                            if (rline.equals("用户名: " + username)) {
                                                String nextLine = rreader.readLine();
                                                if (nextLine.equals("商品: " + product)) {
                                                    rwriter.write("用户名: " + username);
                                                    rwriter.newLine();
                                                    rwriter.write("商品: " + product);
                                                    rwriter.newLine();
                                                    rwriter.write("价格: " + price);
                                                    rwriter.newLine();
                                                    int total = a + numss;
                                                    rwriter.write("数量: " + total);
                                                    rwriter.newLine();
                                                    System.out.println("商品数量更新成功");
                                                    rreader.readLine(); // Skip the next line
                                                    rreader.readLine(); // Skip the next line
                                                }else{
                                                    rwriter.write("用户名: " + username);
                                                    rwriter.newLine();
                                                    rwriter.write(nextLine);
                                                    rwriter.newLine();


                                                }
                                            }else
                                            {
                                                rwriter.write(rline);
                                                rwriter.newLine();
                                            }

                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    if (inputFile.delete()) {
                                        if (tempFile.renameTo(inputFile)) {
                                            System.out.println("原文件删除成功！");
                                        } else {
                                            System.out.println("文件重命名失败！");
                                        }
                                    } else {
                                        System.out.println("删除原文件失败！");
                                    }


                                }


                            }




                        }
                    }







                    if (choose8 == 2) {
                        System.out.print("请输入要移除的商品名称:");
                        String deletename = input.next();


                        System.out.print("请确定是否移除(Y/N)");
                        String ch=input.next();
                        if(Objects.equals(ch, "Y")){

                            String PRODUCTToFind = "商品: "+deletename;
                            boolean found = false;

                            try (BufferedReader reader1 = new BufferedReader(new FileReader("shopcart.txt"));
                                 BufferedWriter writer1 = new BufferedWriter(new FileWriter("tempFile.txt"))) {
                                String line2;


                                while ((line2 = reader1.readLine()) != null) {
                                    if (line2.equals("用户名: " + username)) {
                                        String nextLine = reader1.readLine();
                                        if (nextLine.equals(PRODUCTToFind)) {

                                            System.out.println("商品删除成功");
                                            reader1.readLine(); // Skip the next line
                                            reader1.readLine(); // Skip the next line
                                            found=true;

                                        }else{
                                            writer1.write("用户名: " + username);
                                            writer1.newLine();
                                            writer1.write(nextLine);
                                            writer1.newLine();


                                        }
                                    }else
                                    {
                                        writer1.write(line2);
                                        writer1.newLine();
                                    }

                                }
                                if(!found){
                                    System.out.println("未查询到该商品,移除失败");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            File inputFile = new File("shopcart.txt");
                            File tempFile = new File("tempFile.txt");
                            if (inputFile.delete()) {
                                if (tempFile.renameTo(inputFile)) {
                                    System.out.println("原文件删除成功！");
                                } else {
                                    System.out.println("文件重命名失败！");
                                }
                            } else {
                                System.out.println("删除原文件失败！");
                            }


                        }
                        else{
                            System.out.println("取消移除操作");
                        }








                    }

                    if(choose8==3){

                        int Num=1;
                        System.out.print("请输入要修改的商品名称:");
                        String changename = input.next();


                        String PRODUCTToFind = "商品: "+changename;
                        boolean found = false;

                        try (BufferedReader reader1 = new BufferedReader(new FileReader("shopcart.txt"));
                             BufferedWriter writer1 = new BufferedWriter(new FileWriter("tempFile.txt"))) {
                            String line2=null;


                            while ((line2 = reader1.readLine()) != null) {
                                if (line2.equals("用户名: " + username)) {
                                    String nextLine = reader1.readLine();
                                    if (nextLine.equals(PRODUCTToFind)) {
                                        System.out.print("已找到该商品，商品数量修改为(小于原来的数量):");
                                         Num=input.nextInt();
                                        if(Num>=1){

                                            writer1.write("用户名: " + username);
                                            writer1.newLine();
                                            writer1.write(nextLine);
                                            writer1.newLine();
                                            String aa= reader1.readLine();
                                            writer1.write(aa);
                                            writer1.newLine();
                                            writer1.write("数量: " + Num);
                                            writer1.newLine();
                                            // Skip the next line
                                            reader1.readLine(); // Skip the next line
                                            System.out.println("商品数量修改成功");

                                        }else{
                                            writer1.write("用户名: " + username);
                                            writer1.newLine();
                                            writer1.write(nextLine);
                                            writer1.newLine();
                                        }

                                        found=true;


                                    }else{
                                        writer1.write("用户名: " + username);
                                        writer1.newLine();
                                        writer1.write(nextLine);
                                        writer1.newLine();


                                    }
                                }else
                                {
                                    writer1.write(line2);
                                    writer1.newLine();
                                }

                            }
                            if(!found){
                                System.out.println("未查询到该商品,移除失败");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        File inputFile = new File("shopcart.txt");
                        File tempFile = new File("tempFile.txt");
                        if (inputFile.delete()) {
                            if (tempFile.renameTo(inputFile)) {
                               // System.out.println("原文件删除成功！");
                            } else {
                                System.out.println("文件重命名失败！");
                            }
                        } else {
                            System.out.println("删除原文件失败！");
                        }


                        if(Num<1){


                            String PRODUCTToFind1 = "商品: "+changename;
                            boolean found1 = false;

                            try (BufferedReader reader1 = new BufferedReader(new FileReader("shopcart.txt"));
                                 BufferedWriter writer1 = new BufferedWriter(new FileWriter("tempFile.txt"))) {
                                String line2;


                                while ((line2 = reader1.readLine()) != null) {
                                    if (line2.equals("用户名: " + username)) {
                                        String nextLine = reader1.readLine();
                                        if (nextLine.equals(PRODUCTToFind1)) {

                                            System.out.println("数量小于1，商品删除成功");
                                            reader1.readLine(); // Skip the next line
                                            reader1.readLine(); // Skip the next line
                                            found1=true;

                                        }else{
                                            writer1.write("用户名: " + username);
                                            writer1.newLine();
                                            writer1.write(nextLine);
                                            writer1.newLine();


                                        }
                                    }else
                                    {
                                        writer1.write(line2);
                                        writer1.newLine();
                                    }

                                }
                                if(!found1){
                                    System.out.println("未查询到该商品,移除失败");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            File inputFile1 = new File("shopcart.txt");
                            File tempFile1 = new File("tempFile.txt");
                            if (inputFile1.delete()) {
                                if (tempFile1.renameTo(inputFile1)) {
                              //      System.out.println("原文件删除成功！");
                                } else {
                                    System.out.println("文件重命名失败！");
                                }
                            } else {
                                System.out.println("删除原文件失败！");
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



                        try (BufferedReader reader1 = new BufferedReader(new FileReader("shopcart.txt"));
                             BufferedWriter writer1 = new BufferedWriter(new FileWriter("temp.txt"))) {
                            String line2;




                            while ((line2 = reader1.readLine()) != null) {
                                if (line2.equals("用户名: " + username)) {

                                    String Product = reader1.readLine();
                                    String product =Product.substring(4);

                                    String Price = reader1.readLine();
                                    String price = Price.substring(4);
                                   int price0 = Integer.parseInt(price);

                                    String Amount = reader1.readLine();
                                    String amount = Amount.substring(4);
                                    int amount0 = Integer.parseInt(amount);
                                    int pricee=price0*amount0;
                                    total=total+price0*amount0;



                                    try (BufferedReader reader = new BufferedReader(new FileReader("commodity.txt"));
                                         BufferedWriter writer = new BufferedWriter(new FileWriter("tempFile.txt"))) {
                                        String line;


                                        while ((line = reader.readLine()) != null) {
                                            if (line.equals("商品名称: " +product)) {
                                                writer.write("商品名称: " + product);
                                                writer.newLine();

                                                String nextLine = reader.readLine();
                                                writer.write(nextLine);
                                                writer.newLine();

                                                String nextLine1 = reader.readLine();
                                                writer.write(nextLine1);
                                                writer.newLine();

                                                String nextLine2 = reader.readLine();
                                                writer.write(nextLine2);
                                                writer.newLine();

                                                String nextLine3 = reader.readLine();
                                                writer.write(nextLine3);
                                                writer.newLine();

                                                String nextLine4 = reader.readLine();
                                                writer.write(nextLine4);
                                                writer.newLine();

                                                String nextLine5 = reader.readLine();
                                                String amm = nextLine5.substring(4);
                                                int amountt = Integer.parseInt(amm);
                                                int nowamount = amountt-amount0;
                                                String nownum="数量: "+nowamount;
                                                writer.write(nownum);
                                                writer.newLine();

                                            }else
                                            {
                                                writer.write(line);
                                                writer.newLine();
                                            }

                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    File inputFile1 = new File("commodity.txt");
                                    File tempFile1 = new File("tempFile.txt");
                                    if (inputFile1.delete()) {
                                        if (tempFile1.renameTo(inputFile1)) {
                                      //      System.out.println("原文件删除成功！");
                                        } else {
                                            System.out.println("文件重命名失败！");
                                        }
                                    } else {
                                        System.out.println("删除原文件失败！");
                                    }

                                    // 获取当前时间
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String purchaseTime = dateFormat.format(new Date());

                                    // 构建购物记录
                                    String shoppingRecord = "用户名: " + username + "\n" +
                                            "商品: " + product + "\n" +
                                            "数量: " + amount0 + "\n" +
                                            "金额: " + pricee + "\n" +
                                            "购买时间: " + purchaseTime + "\n";

                                    // 写入history.txt表
                                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("history.txt", true))) {
                                        writer.write(shoppingRecord);
                                        writer.newLine();
                                        System.out.println("购物记录已写入history.txt表");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                            System.out.println("共花费 "+ total + "元，使用 "+way+" 支付成功");

                            int nowamount=0;

                            try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"));
                                 BufferedWriter writer = new BufferedWriter(new FileWriter("tempFile.txt"))) {
                                String line;


                                while ((line = reader.readLine()) != null) {
                                    if (line.equals("用户名: " +username)) {
                                        writer.write("用户名: " + username);
                                        writer.newLine();

                                        String nextLine = reader.readLine();
                                        writer.write(nextLine);
                                        writer.newLine();

                                        String nextLine1 = reader.readLine();
                                        writer.write(nextLine1);
                                        writer.newLine();

                                        String nextLine2 = reader.readLine();
                                        writer.write(nextLine2);
                                        writer.newLine();

                                        String nextLine3 = reader.readLine();
                                        writer.write(nextLine3);
                                        writer.newLine();



                                        String nextLine5 = reader.readLine();
                                        String amm = nextLine5.substring(11);
                                        int amountt = Integer.parseInt(amm);
                                         nowamount = amountt+total;
                                        String nownum="客户累计消费总金额: "+nowamount;
                                        writer.write(nownum);
                                        writer.newLine();


                                        String nextLine4 = reader.readLine();
                                        writer.write(nextLine4);
                                        writer.newLine();

                                        String nextLine6 = reader.readLine();
                                        writer.write(nextLine6);
                                        writer.newLine();
                                    }else
                                    {
                                        writer.write(line);
                                        writer.newLine();
                                    }

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            File inputFile1 = new File("user.txt");
                            File tempFile1 = new File("tempFile.txt");
                            if (inputFile1.delete()) {
                                if (tempFile1.renameTo(inputFile1)) {
                                  //  System.out.println("原文件删除成功！");
                                } else {
                                    System.out.println("文件重命名失败！");
                                }
                            } else {
                                System.out.println("删除原文件失败！");
                            }



                            try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"));
                                 BufferedWriter writer = new BufferedWriter(new FileWriter("tempFile.txt"))) {
                                String line;


                                while ((line = reader.readLine()) != null) {
                                    if (line.equals("用户名: " +username)) {
                                        writer.write("用户名: " + username);
                                        writer.newLine();

                                        String nextLine = reader.readLine();
                                        writer.write(nextLine);
                                        writer.newLine();

                                        String nextLine1 = reader.readLine();
                                        writer.write(nextLine1);
                                        writer.newLine();



                                        String nextLine2 = reader.readLine();
                                        if(nowamount>10){
                                            nextLine2="用户级别: 银牌客户";
                                        }
                                        if(nowamount>100){
                                            nextLine2="用户级别: 金牌客户";
                                        }


                                        writer.write(nextLine2);
                                        writer.newLine();



                                        String nextLine3 = reader.readLine();
                                        writer.write(nextLine3);
                                        writer.newLine();



                                        String nextLine5 = reader.readLine();

                                        writer.write(nextLine5);
                                        writer.newLine();


                                        String nextLine4 = reader.readLine();
                                        writer.write(nextLine4);
                                        writer.newLine();

                                        String nextLine6 = reader.readLine();
                                        writer.write(nextLine6);
                                        writer.newLine();
                                    }else
                                    {
                                        writer.write(line);
                                        writer.newLine();
                                    }

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            File inputFile11 = new File("user.txt");
                            File tempFile11 = new File("tempFile.txt");
                            if (inputFile11.delete()) {
                                if (tempFile11.renameTo(inputFile11)) {
                                   // System.out.println("原文件删除成功！");
                                } else {
                                    System.out.println("文件重命名失败！");
                                }
                            } else {
                                System.out.println("删除原文件失败！");
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        File inputFile1 = new File("shopcart.txt");
                        File tempFile1 = new File("temp.txt");
                        if (inputFile1.delete()) {
                            if (tempFile1.renameTo(inputFile1)) {
                                //  System.out.println("原文件删除成功！");
                            } else {
                                System.out.println("文件重命名失败！");
                            }
                        } else {
                            System.out.println("删除原文件失败！");
                        }



                    }
                    if(choose8==5){
                        int count=0;
                        String filePath11 = "shopcart.txt";

                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath11))) {
                            String line;

                            while ((line = reader.readLine()) != null) {

                                if (line.equals("用户名: " + username)) {
                                    System.out.println(line);

                                    line = reader.readLine();
                                    System.out.println(line);

                                    line = reader.readLine();
                                    System.out.println(line);

                                    line = reader.readLine();
                                    System.out.println(line);

                                    System.out.println("");
                                }else {
                                    continue;
                                }

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    if(choose8==6){
                        System.out.println("****购物历史如下****");
                        String filePath111 = "history.txt";

                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath111))) {
                            String line;

                            while ((line = reader.readLine()) != null) {

                                if (line.equals("用户名: " + username)) {
                                    System.out.println(line);

                                    line = reader.readLine();
                                    System.out.println(line);

                                    line = reader.readLine();
                                    System.out.println(line);

                                    line = reader.readLine();
                                    System.out.println(line);

                                    line = reader.readLine();
                                    System.out.println(line);

                                    System.out.println("");
                                }else {
                                    continue;
                                }

                            }
                        }catch (IOException e) {
                            e.printStackTrace();
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






