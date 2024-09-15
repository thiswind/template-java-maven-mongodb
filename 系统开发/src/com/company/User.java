package com.company;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.sql.*;
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
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
          //  System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String query = "SELECT * FROM USER WHERE NAME='" + username + "' AND PASSWORD='" + userps + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                // 用户名和密码匹配
                System.out.println("登陆成功，进入用户菜单界面");
                stmt.close();
                c.commit();
                c.close();
                usermenu(username,userps);

            } else {
                // 用户名和密码不匹配
                System.out.println("用户名密码错误，请重新输入");
                stmt.close();
                c.commit();
                c.close();
                login();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
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

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
      //      System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String query = "SELECT NAME FROM USER WHERE NAME='" + username1 + "'";
            ResultSet rs = stmt.executeQuery(query);



            if (rs.next()) {
                // 用户名已存在，不执行插入操作
                System.out.println("用户名已经存在,注册失败");
            } else {
                // 用户名不存在，执行插入操作
                LocalDateTime localDateTime1 = LocalDateTime.now();
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedDateTime1 = localDateTime1.format(formatter1);

                String sql = "INSERT INTO USER (ID,NAME,PASSWORD,TYPE,LEVEL,RETIME,MONEY,PHONE,MAILBOX) " +
                        "VALUES ('" + userID + "','" + username1 + "','" + userps1 +"','" + 0 + "','" + level + "','" + formattedDateTime1 + "','" + type + "','" + phonenunber + "','" + mail + "');";
                stmt.executeUpdate(sql);
                System.out.println("用户ID为"+"123-"+username1);
                System.out.println("用户注册成功，数据已存到表中");
            }

            stmt.close();
            c.commit();
            c.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
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

    public void usermenu (String username,String password) throws SQLException {
        for(int i=0;;i++){
            boolean validInput1 = false;
            int choose2=0;
            while (!validInput1) {
                try {
                    System.out.println("");
                    System.out.println("****欢迎进入 用户菜单 界面****");
                    System.out.print("请选择要进行的操作——" + '\n' + "1.密码管理" + '\n' + "2.购物" + '\n' + "3.退出登录" + '\n' + ">");
                     choose2 = input.nextInt();

                    // 根据choose2的值来执行相应的操作

                    validInput1 = true; // 输入合法，跳出循环
                } catch (InputMismatchException e) {
                    System.out.println("输入格式错误，请输入合法的操作编号！");
                    input.nextLine(); // 清空输入缓冲区
                    // 可以选择继续循环提示用户重新输入，或者直接结束程序
                }
            }

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
                Connection c = null;
                Statement stmt = null;
                try {
                    Class.forName("org.sqlite.JDBC");
                    c = DriverManager.getConnection("jdbc:sqlite:test.db");
                    c.setAutoCommit(false);
                  //  System.out.println("Opened database successfully");

                    stmt = c.createStatement();
                    String query = "UPDATE USER SET PASSWORD = '" + newPassword + "' WHERE NAME = '" + username + "'";
                    int rowsAffected = stmt.executeUpdate(query);

                    if (rowsAffected > 0) {
                        System.out.println("密码已更新为'" + newPassword + "'.");
                    } else {
                        System.out.println("No user found with username '" + username + "'.");
                    }

                    stmt.close();
                    c.commit();
                    c.close();
                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }
            }
            if(choose2==2) {

                Connection cc = null;
                Statement stmtc = null;
                try {
                    Class.forName("org.sqlite.JDBC");
                    cc = DriverManager.getConnection("jdbc:sqlite:test.db");
                    cc.setAutoCommit(false);
                    //  System.out.println("Opened database successfully");

                    stmtc = cc.createStatement();

                    String query = "SELECT * FROM COMMODITY";
                    ResultSet rs = stmtc.executeQuery(query);

                    while (rs.next()) {


                        String ID= rs.getString("ID");
                        String product = rs.getString("PRODUCT");
                        String factory = rs.getString("FACTORY");
                        String date=rs.getString("DATE");
                        String type = rs.getString("TYPE");
                        int inprice=rs.getInt("INPRICE");

                        int outprice=rs.getInt("OUTPRICE");
                        int nums =rs.getInt("NUMS");

                        System.out.println("商品编号:" + ID);
                        System.out.println("商品名称:" + product);

                        System.out.println("生产厂家:" + factory);
                        System.out.println("生产日期:" + date);
                        System.out.println("型号:" + type);

                        System.out.println("零售价:" + outprice);
                        System.out.println("数量:" + nums);
                        System.out.println("");
                        // 处理商品信息，例如打印或存储到变量中
                    }
                    stmtc.close();
                    cc.commit();
                    cc.close();
                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }





                for (int m = 0; ; m++) {

                    System.out.println("");
                    System.out.println("****欢迎进入 购物 界面****");
                    System.out.print("请选择要进行的操作——" + '\n' + "1.将商品加入购物车" + '\n' + "2.从购物车中移除商品" + '\n' + "3.修改购物车中的商品" + '\n' + "4.结账（清空购物车）" + '\n' + "5.查看购物车" + '\n' + "6.查看购物历史" + '\n' + "7.返回" + '\n' + ">");
                    int choose8 = input.nextInt();

                    if (choose8 == 1) {

                        System.out.print("请输入要加入的商品名称或者编号:");
                        String product = input.next();

                        System.out.print("请输入要加入的商品的数量:");
                        int numss = input.nextInt();

                        Connection c = null;
                        Statement stmt = null;
                        ResultSet productRs = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            c = DriverManager.getConnection("jdbc:sqlite:test.db");
                            c.setAutoCommit(false);

                            stmt = c.createStatement();

                            // 检查商品是否存在于COMMODITY表中

                            String selectQuery = "SELECT * FROM COMMODITY WHERE PRODUCT = ? OR ID = ?";
                            PreparedStatement ps = c.prepareStatement(selectQuery);
                            ps.setString(1, product);
                            ps.setString(2, product);

                            productRs = ps.executeQuery();

                            int price = productRs.getInt("OUTPRICE");

                            if (productRs.next()) {
                                int numssss = productRs.getInt("NUMS");
                                String name5 = productRs.getString("PRODUCT");

                                // 检查是否存在相同的USERNAME和PRODUCT
                                String checkQuery = "SELECT AMOUNT FROM SHOPCART WHERE USERNAME = '" + username + "' AND PRODUCT = '" + name5 + "'";
                                ResultSet rs = stmt.executeQuery(checkQuery);

                                if (rs.next()) {
                                    // 如果存在相同的USERNAME和PRODUCT，则将AMOUNT值累加
                                    int amount = rs.getInt("AMOUNT");

                                    if (numssss < numss + amount) {
                                        System.out.println("商品库存不足，加入购物车失败");
                                    } else {
                                        amount += numss;
                                        System.out.println("购物车中已存在相同的商品，AMOUNT值累加");

                                        String updateQuery = "UPDATE SHOPCART SET AMOUNT = " + amount +
                                                " WHERE USERNAME = '" + username + "' AND PRODUCT = '" + name5 + "'";
                                        stmt.executeUpdate(updateQuery);
                                        c.commit();
                                        System.out.println("购物车已更新");
                                    }
                                } else {
                                    // 如果不存在相同的USERNAME和PRODUCT，则执行插入操作
                                    if (numssss < numss) {
                                        System.out.println("商品库存不足，加入购物车失败");
                                    } else {
                                        int a = numss;
                                        System.out.println("商品信息已成功添加到购物车");

                                        String insertQuery = "INSERT INTO SHOPCART (USERNAME, PRODUCT, PRICE, AMOUNT) " +
                                                "VALUES ('" + username + "', '" + name5 + "', " + price + ", " + a + ")";
                                        stmt.executeUpdate(insertQuery);
                                        c.commit();
                                        System.out.println("购物车已更新");
                                    }
                                }

                                rs.close();
                                productRs.close();
                                stmt.close();
                                c.close();
                            } else {
                                // 商品不存在于COMMODITY表中，无法加入购物车
                                System.out.println("该商品不存在，请选择其他商品");

                                productRs.close();
                                stmt.close();
                                c.close();
                            }
                        } catch (Exception e) {

                            System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            System.exit(0);
                        }

                    }
                    if (choose8 == 2) {
                        System.out.print("请输入要移除的商品:");
                        String deletename = input.next();

                        Connection c = null;
                        Statement stmt = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            c = DriverManager.getConnection("jdbc:sqlite:test.db");
                            c.setAutoCommit(false);

                            stmt = c.createStatement();


                            String checkQuery = "SELECT * FROM SHOPCART WHERE PRODUCT = ?";
                            PreparedStatement ps = c.prepareStatement(checkQuery);
                            ps.setString(1, deletename);
                            ResultSet rs = ps.executeQuery();

                            if (rs.next()) {
                                // 商品存在
                                System.out.print("购物车中存在该商品，是否要移除？(Y/N)");

                                String confirmation = input.next();

                                if (confirmation.equalsIgnoreCase("Y")) {
                                    // 执行移除操作
                                    String deleteQuery = "DELETE FROM SHOPCART WHERE PRODUCT = ?";
                                    PreparedStatement deletePs = c.prepareStatement(deleteQuery);
                                    deletePs.setString(1, deletename);
                                    int rowsAffected = deletePs.executeUpdate();

                                    if (rowsAffected > 0) {
                                        System.out.println("商品信息已从购物车中移除");
                                        c.commit();
                                    } else {
                                        System.out.println("无法移除商品信息");
                                    }

                                    deletePs.close();
                                } else {
                                    System.out.println("取消移除操作");
                                }
                            } else {
                                System.out.println("购物车中不存在该商品");
                            }

                            rs.close();
                            ps.close();
                            c.close();
                        } catch (Exception e) {
                            System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            System.exit(0);
                        }
                    }
                    if (choose8 == 3) {

                        Connection c = null;
                        Statement stmt = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            c = DriverManager.getConnection("jdbc:sqlite:test.db");
                            c.setAutoCommit(false);

                            stmt = c.createStatement();

                            String selectQuery = "SELECT * FROM SHOPCART";
                            ResultSet rs = stmt.executeQuery(selectQuery);

                            List<String> productsToDelete = new ArrayList<>(); // 用于存储待删除的商品

                            while (rs.next()) {
                                String username1 = rs.getString("USERNAME");
                                String product = rs.getString("PRODUCT");
                                int price = rs.getInt("PRICE");
                                int amount = rs.getInt("AMOUNT");

                                System.out.println("用户名: " + username1);
                                System.out.println("商品名称: " + product);
                                System.out.println("价格: " + price);
                                System.out.println("数量: " + amount);
                                System.out.println();

                                System.out.print("是否修改该商品？(Y/N): ");
                                String choice = input.next();

                                if (choice.equalsIgnoreCase("Y")) {


                                    System.out.print("请输入修改后的商品的数量: ");
                                    int amountt = input.nextInt();

                                    if (amountt < 1) {
                                        // 数量小于1，执行删除操作
                                        String deleteQuery = "DELETE FROM SHOPCART WHERE PRODUCT = ?";
                                        PreparedStatement deletePs = c.prepareStatement(deleteQuery);
                                        deletePs.setString(1, product);
                                        int rowsAffected = deletePs.executeUpdate();

                                        if (rowsAffected > 0) {
                                            System.out.println("商品信息已从购物车中移除");
                                            c.commit();
                                        } else {
                                            System.out.println("无法移除商品信息");
                                        }

                                        deletePs.close();
                                    } else {
                                        // 数量大于等于1，执行更新操作
                                        String updateQuery = "UPDATE SHOPCART SET AMOUNT = ? WHERE PRODUCT = ?";
                                        PreparedStatement ps = c.prepareStatement(updateQuery);
                                        ps.setInt(1, amountt);
                                        ps.setString(2, product);
                                        int rowsAffected = ps.executeUpdate();

                                        if (rowsAffected == 0) {
                                            System.out.println("未找到该商品，修改失败");
                                        } else {
                                            System.out.println("修改成功");
                                        }

                                        ps.close();
                                    }
                                }
                            }
                            // 执行待删除的商品操作

                            c.commit();
                            rs.close();
                            stmt.close();
                            c.close();
                            System.out.println("商品修改已完成");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                    if(choose8==4){
                        int total=0;
                        int totalamount=0;
                        Connection c = null;
                        Statement stmt = null;
                        int chose=0;
                        String func=null;

                        while (true) {
                            try {
                                System.out.println("");
                                System.out.print("请选择支付渠道--1.支付宝   2.微信   3.银行卡 >");
                                chose = input.nextInt();


                                if (chose == 1) {
                                    func = "支付宝";
                                } else if (chose == 2) {
                                    func = "微信";
                                } else if (chose == 3) {
                                    func = "银行卡";
                                } else {
                                    throw new IllegalArgumentException("无效的选择"); // 抛出自定义异常
                                }

                                // 执行其他操作

                                break; // 如果没有异常，跳出循环
                            } catch (Exception e) {
                                System.out.println("发生异常：" + e.getMessage());
                                continue; // 如果发生异常，重新开始循环
                            }
                        }

                        try {
                            Class.forName("org.sqlite.JDBC");
                            c = DriverManager.getConnection("jdbc:sqlite:test.db");
                            c.setAutoCommit(false);
                            stmt = c.createStatement();
                            String selectQuery = "SELECT * FROM SHOPCART WHERE USERNAME = ?";
                            PreparedStatement pstmt11 = c.prepareStatement(selectQuery);
                            pstmt11.setString(1, username);
                            ResultSet rs = pstmt11.executeQuery();

                            while (rs.next()) {

                                LocalDateTime localDateTime2 = LocalDateTime.now();
                                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                String formattedDateTime2 = localDateTime2.format(formatter2);

                                String username1 = rs.getString("USERNAME");
                                String product = rs.getString("PRODUCT");
                                int price = rs.getInt("PRICE");
                                int amount = rs.getInt("AMOUNT");

                                int totall=amount*price;

                                totalamount++;
                                total = total+price * amount;

                                String insertQuery = "INSERT INTO HISTORY (USERNAME, PRODUCT, AMOUNT,TOTALPRICE, TIME) " +
                                        "VALUES ('" + username1 + "','" + product + "'," + amount + ",'" + totall + "','" + formattedDateTime2 + "')";

                                stmt.executeUpdate(insertQuery);
                                System.out.println("");
                                System.out.println("购物历史添加成功，数据已存到表中");

                                String selectQuery1 = "SELECT * FROM COMMODITY WHERE PRODUCT = ?";
                                PreparedStatement pstmt = c.prepareStatement(selectQuery1);
                                pstmt.setString(1, product);
                                ResultSet rss = pstmt.executeQuery();
                                if (rss.next()) {
                                    int num = rss.getInt("NUMS");
                                    int newnum = num - amount;

                                    String updateQuery = "UPDATE COMMODITY SET NUMS = ? WHERE PRODUCT = ?";
                                    PreparedStatement pstmt1 = c.prepareStatement(updateQuery);
                                    pstmt1.setInt(1, newnum);
                                    pstmt1.setString(2, product);
                                    pstmt1.executeUpdate();
                                } else {
                                   System.out.println("未找到");
                                }


                                String selectQuery2 = "SELECT * FROM USER WHERE NAME = ?";
                                PreparedStatement pstmtt = c.prepareStatement(selectQuery2);
                                pstmtt.setString(1, username1);
                                ResultSet rsss = pstmtt.executeQuery();

                                int money = 0;
                                int level = 1;

                                if (rsss.next()) {
                                    money = rsss.getInt("MONEY");
                                    int newmoney = money + total;

                                    if (newmoney >= 10) {
                                        level = 2;
                                    }
                                    if (newmoney >= 100) {
                                        level = 3;
                                    }

                                    // 更新用户的MONEY和LEVEL字段
                                    String updateQuery = "UPDATE USER SET MONEY = ?, LEVEL = ? WHERE NAME = ?";
                                    PreparedStatement updateStmt = c.prepareStatement(updateQuery);
                                    updateStmt.setInt(1, newmoney);
                                    updateStmt.setInt(2, level);
                                    updateStmt.setString(3, username1);
                                    int rowsAffected = updateStmt.executeUpdate();

                                    if (rowsAffected > 0) {
                                        System.out.println("用户消费总金额和等级更新成功"+"\n");
                                    } else {
                                        System.out.println("修改失败");
                                    }
                                }

                            }

                            System.out.println("总价为:"+total+"元  用 "+func+" 支付成功");

                            String deleteQuery = "DELETE FROM SHOPCART";
                            stmt.executeUpdate(deleteQuery);


                            c.commit();
                            stmt.close();
                            rs.close();
                            c.close();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                if(choose8==5){

                    Connection c = null;
                    Statement stmt = null;
                    try {
                        Class.forName("org.sqlite.JDBC");
                        c = DriverManager.getConnection("jdbc:sqlite:test.db");
                        c.setAutoCommit(false);

                        stmt = c.createStatement();

                        String selectQuery = "SELECT * FROM SHOPCART";
                        ResultSet rs = stmt.executeQuery(selectQuery);

                        while (rs.next()) {
                            String username1 = rs.getString("USERNAME");
                            String product = rs.getString("PRODUCT");
                            int price = rs.getInt("PRICE");
                            int amount = rs.getInt("AMOUNT");

                              System.out.println("用户名: " + username1);
                              System.out.println("商品名称: " + product);
                              System.out.println("价格: " + price);
                              System.out.println("数量: " + amount);
                              System.out.println();
                        }
                        rs.close();
                        stmt.close();
                        c.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                if(choose8==6){
                    Connection c = null;
                    PreparedStatement preparedStatement = null;
                    try {
                        Class.forName("org.sqlite.JDBC");
                        c = DriverManager.getConnection("jdbc:sqlite:test.db");
                        c.setAutoCommit(false);

                        String selectQuery = "SELECT * FROM HISTORY WHERE USERNAME = ?";
                        preparedStatement = c.prepareStatement(selectQuery);
                        preparedStatement.setString(1, username);
                        ResultSet rs = preparedStatement.executeQuery(); // 使用preparedStatement来执行查询

                        while (rs.next()) {

                            String name1 = rs.getString("USERNAME");
                            String productt = rs.getString("PRODUCT");
                            int amountt = rs.getInt("AMOUNT");
                            int pricee = rs.getInt("TOTALPRICE");
                            String time = rs.getString("TIME");

                            System.out.println(time + " 用户 " + name1 + " 购买了 " + amountt + " 个 " + productt + " 商品，花费" + pricee + "元");

                            System.out.println("");
                        }
                        rs.close();
                        preparedStatement.close();
                        c.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
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






