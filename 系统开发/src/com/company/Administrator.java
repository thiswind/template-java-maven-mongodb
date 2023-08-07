package com.company;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.*;

public class Administrator {
    Scanner input = new Scanner(System.in);
    Start mm= new Start();
    public void administratorLogin(){
        System.out.println("");
        System.out.println("****欢迎进入 管理员登录 界面****");
        System.out.print("请输入name:");
        String ADSname = input.next();

        System.out.print("请输入password:");
        String ADSpsA = input.next();

        if (ADSname.equals("0") || ADSpsA.equals("0")) {
            System.out.println("返回 Start 界面");
           mm.start();
        }
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
         //   System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String query = "SELECT * FROM USER WHERE NAME='" + ADSname + "' AND PASSWORD='" + ADSpsA + "' AND TYPE=1";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                // 用户名和密码匹配
                System.out.println("登陆成功，进入管理员菜单界面");
                stmt.close();
                c.commit();
                c.close();
                administratormenu(ADSname,ADSpsA);

            } else {
                // 用户名和密码不匹配
                System.out.println("用户名密码错误，请重新输入");
                stmt.close();
                c.commit();
                c.close();
                administratorLogin();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
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
                    System.out.print("请输入要进行的操作——" + '\n' + "1.修改自身密码" + '\n' + "2.重置用户密码(重置为Yn111111@)" + '\n' + "3.返回" +'\n' + ">");
                    int choose4 = input.nextInt();

                    if(choose4==3)
                        administratormenu(ADSname,ADSpsA);
                    String newPassword = null;
                    String userName = null;
                    if (choose4 == 2) {
                        System.out.print("请输入要重置密码的用户的name:");
                        userName = input.next();
                        newPassword = "Yn111111@";
                    }
                    if (choose4 == 1) {
                        userName=ADSname;
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

                        String query = "UPDATE USER SET PASSWORD = '" + newPassword + "' WHERE NAME = '" + userName + "'";
                        int rowsAffected = stmt.executeUpdate(query);

                        if (rowsAffected > 0) {
                            System.out.println(userName + "的密码已更新为" + newPassword );
                        } else {
                            System.out.println("未找到该用户");
                        }

                        stmt.close();
                        c.commit();
                        c.close();
                    } catch (Exception e) {
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
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

                        Connection c = null;
                        Statement stmt = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            c = DriverManager.getConnection("jdbc:sqlite:test.db");
                            c.setAutoCommit(false);
                            //  System.out.println("Opened database successfully");

                            stmt = c.createStatement();

                            String query = "SELECT * FROM USER WHERE TYPE = 0";
                            ResultSet rs = stmt.executeQuery(query);
                            System.out.println("***所有用户信息如下***");
                            System.out.println("");
                            while (rs.next()) {
                                String userID= rs.getString("ID");
                                String name1 = rs.getString("NAME");
                                String password1 = rs.getString("PASSWORD");
                                int type = rs.getInt("TYPE");
                                int level=rs.getInt("LEVEL");
                                String time=rs.getString("RETIME");
                                int money=rs.getInt("MONEY");
                                String phone=rs.getString("PHONE");
                                String mail=rs.getString("MAILBOX");

                                System.out.println("客户ID:" + userID);
                                System.out.println("用户名:" + name1);
                                //System.out.println("password:" + password1);

                                if (level == 1) {
                                    System.out.println("客户级别: 铜牌客户");
                                }
                                if (level == 2) {
                                    System.out.println("客户级别: 银牌客户");
                                }
                                if (level == 3) {
                                    System.out.println("客户级别: 金牌客户");
                                }
                                System.out.println("用户注册时间:" + time);
                                System.out.println("用户累计消费金额:" + money+"元");
                                System.out.println("用户手机号:" + phone);
                                System.out.println("用户邮箱:" + mail);
                                System.out.println("");
                                // 处理用户信息，例如打印或存储到变量中
                            }

                            stmt.close();
                            c.commit();
                            c.close();
                        } catch (Exception e) {
                            System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            System.exit(0);
                        }
                    }
                    if (choose6 == 2) {
                        System.out.print("请输入要删除的客户的name:");
                        String deleteName = input.next();

                        Connection c = null;
                        Statement stmt = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            c = DriverManager.getConnection("jdbc:sqlite:test.db");
                            c.setAutoCommit(false);
                            //  System.out.println("Opened database successfully");

                            stmt = c.createStatement();

                            // 替换为你要删除的用户名


                            String query = "SELECT * FROM USER WHERE NAME = '" + deleteName + "' AND TYPE = 0";
                            ResultSet resultSet = stmt.executeQuery(query);

                            if (resultSet.next()) {
                                System.out.print("找到该用户信息，是否继续进行删除操作？(y/n)");
                                String option = input.next();
                                if (option.equalsIgnoreCase("y")) {
                                    query = "DELETE FROM USER WHERE NAME = '" + deleteName + "' AND TYPE = 0";
                                    int rowsAffected = stmt.executeUpdate(query);

                                    if (rowsAffected > 0) {
                                        System.out.println("删除成功，返回客户管理菜单界面");
                                    } else {
                                        System.out.println("删除失败");
                                    }
                                } else {
                                    System.out.println("未执行删除操作，返回客户管理菜单界面");
                                }
                            } else {
                                System.out.println("未查询到该用户或此账号是管理员账号，返回客户管理菜单界面");
                            }
                            stmt.close();
                            c.commit();
                            c.close();
                        } catch (Exception e) {
                            System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            System.exit(0);
                        }
                    }
                    if(choose6==3){

                        System.out.print("请输入要查询的客户的用户名或ID:");
                        String selectName = input.next();
                        Connection c = null;
                        Statement stmt = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            c = DriverManager.getConnection("jdbc:sqlite:test.db");
                            c.setAutoCommit(false);

                            stmt = c.createStatement();
                            String username = ""; // 设置要查询的用户名
                            String query = "SELECT * FROM USER WHERE ID = '" + selectName + "' OR NAME = '" + selectName + "'";
                            ResultSet rs = stmt.executeQuery(query);

                            boolean hasRecords = false;

                            while (rs.next()) {
                                hasRecords = true;
                                String userID= rs.getString("ID");
                                String name1 = rs.getString("NAME");
                                String password1 = rs.getString("PASSWORD");
                                int type = rs.getInt("TYPE");
                                int level=rs.getInt("LEVEL");
                                String time=rs.getString("RETIME");
                                int money=rs.getInt("MONEY");
                                String phone=rs.getString("PHONE");
                                String mail=rs.getString("MAILBOX");

                                System.out.println("客户ID:" + userID);
                                System.out.println("用户名:" + name1);
                                //System.out.println("password:" + password1);

                                if (level == 1) {
                                    System.out.println("客户级别: 铜牌客户");
                                }
                                if (level == 2) {
                                    System.out.println("客户级别: 银牌客户");
                                }
                                if (level == 3) {
                                    System.out.println("客户级别: 金牌客户");
                                }
                                System.out.println("用户注册时间:" + time);
                                System.out.println("用户累计消费金额:" + money+"元");
                                System.out.println("用户手机号:" + phone);
                                System.out.println("用户邮箱:" + mail);
                                System.out.println("");
                                // 处理用户信息，例如打印或存储到变量中
                            }

                            if (!hasRecords) {
                                System.out.println("查询失败：无匹配记录");
                            }
                            stmt.close();
                            c.commit();
                            c.close();
                        } catch (Exception e) {
                            System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            System.exit(0);
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
                        Connection c = null;
                        Statement stmt = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            c = DriverManager.getConnection("jdbc:sqlite:test.db");
                            c.setAutoCommit(false);
                            //  System.out.println("Opened database successfully");

                            stmt = c.createStatement();

                            String query = "SELECT * FROM COMMODITY";
                            ResultSet rs = stmt.executeQuery(query);

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
                                System.out.println("进货价:" + inprice);
                                System.out.println("零售价:" + outprice);
                                System.out.println("数量:" + nums);
                                System.out.println("");
                                // 处理商品信息，例如打印或存储到变量中
                            }
                            stmt.close();
                            c.commit();
                            c.close();
                        } catch (Exception e) {
                            System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            System.exit(0);
                        }
                    }
                    if(choose7==2){
                        System.out.print("请输入要添加的商品的编号: ");
                        String addProID = input.next();
                        System.out.print("请输入商品的名称: ");
                        String addPro = input.next();
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

                        Connection c = null;
                        Statement stmt = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            c = DriverManager.getConnection("jdbc:sqlite:test.db");
                            c.setAutoCommit(false);

                            stmt = c.createStatement();

                            String query = "SELECT PRODUCT FROM COMMODITY WHERE PRODUCT='" + addPro + "'";
                            ResultSet rs = stmt.executeQuery(query);

                            if (rs.next()) {
                                // 商品已存在，不执行插入操作
                                System.out.println("商品已经存在,注册失败");
                            } else {
                                // 商品不存在，执行插入操作
                                 String insertQuery = "INSERT INTO COMMODITY (ID,PRODUCT,FACTORY, DATE,TYPE,INPRICE,OUTPRICE,NUMS) " +
                                         "VALUES ('" + addProID + "', '" + addPro + "','" + factory + "','" + date + "','" + type + "'," + intoprice + "," + outprice + "," + nums + ")";
                                stmt.executeUpdate(insertQuery);

                                System.out.println("商品添加成功，数据已存到表中");
                            }

                            stmt.close();
                            c.commit();
                            c.close();
                        } catch (Exception e) {
                            System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            System.exit(0);
                        }
                    }
                    if(choose7==3){

                        System.out.print("请输入要修改的商品的编号: ");
                        String addProID = input.next();




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

                        Connection c = null;
                        Statement stmt = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            c = DriverManager.getConnection("jdbc:sqlite:test.db");
                            c.setAutoCommit(false);

                            stmt = c.createStatement();

                            String updateQuery = "UPDATE COMMODITY SET PRODUCT = ?, FACTORY = ?, DATE = ?, TYPE = ?, INPRICE = ?, OUTPRICE = ?, NUMS = ? WHERE ID = ?";
                            PreparedStatement ps = c.prepareStatement(updateQuery);

// 设置参数值
                            ps.setString(1, addPro);
                            ps.setString(2, factory);
                            ps.setString(3, date);
                            ps.setString(4, type);
                            ps.setInt(5, intoprice);
                            ps.setInt(6, outprice);
                            ps.setInt(7, nums);
                            ps.setString(8, addProID);

// 执行更新操作
                            int rowsAffected=ps.executeUpdate();



                            if (rowsAffected == 0) {
                                System.out.println("未找到该商品，修改失败");
                            } else {
                                System.out.println("修改成功");
                            }

                            stmt.close();
                            c.commit();
                            c.close();
                        } catch (Exception e) {
                            System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            System.exit(0);
                        }

                    }

                    if(choose7==4){
                        System.out.print("请输入要删除的商品名称或商品编号（任意一个）:");
                        String deletePro = input.next();

                        Connection c = null;
                        Statement stmt = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            c = DriverManager.getConnection("jdbc:sqlite:test.db");
                            c.setAutoCommit(false);

                            stmt = c.createStatement();

                            System.out.print("确认要删除该商品吗？删除后无法恢复。请输入 'y' 继续删除，或输入其他内容取消删除操作: ");
                            String confirmation = input.next();

                            if (confirmation.equalsIgnoreCase("y")) {
                                String deleteQuery = "DELETE FROM COMMODITY WHERE ID = ? OR PRODUCT = ?";
                                PreparedStatement ps = c.prepareStatement(deleteQuery);
                                ps.setString(1, deletePro);
                                ps.setString(2, deletePro);

                                int rowsAffected = ps.executeUpdate();

                                if (rowsAffected > 0) {
                                    System.out.println("删除成功");
                                } else {
                                    System.out.println("未查询到该商品，删除失败");
                                }

                                ps.close();
                            } else {
                                System.out.println("已取消删除操作");
                            }

                            stmt.close();
                            c.commit();
                            c.close();
                        } catch (Exception e) {
                            System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            System.exit(0);
                        }
                    }
                    if(choose7==5){
                        System.out.print("请输入要查询的商品名称 或 商品编号 或 生产厂家 或 零售价格: ");
                        String selectValue = input.next();

                        Connection c = null;
                        Statement stmt = null;
                        try {
                            Class.forName("org.sqlite.JDBC");
                            c = DriverManager.getConnection("jdbc:sqlite:test.db");
                            c.setAutoCommit(false);

                            stmt = c.createStatement();

                            String selectQuery = "SELECT * FROM COMMODITY WHERE PRODUCT = ? OR ID = ? OR FACTORY=? OR OUTPRICE=?";
                            PreparedStatement ps = c.prepareStatement(selectQuery);
                            ps.setString(1, selectValue);
                            ps.setString(2, selectValue);
                            ps.setString(3, selectValue);
                            ps.setString(4, selectValue);

                            ResultSet rs = ps.executeQuery();

                            if (rs.next()) {
                                String ID = rs.getString("ID");
                                String product = rs.getString("PRODUCT");
                                String factory = rs.getString("FACTORY");
                                String date = rs.getString("DATE");
                                String type = rs.getString("TYPE");
                                int inprice = rs.getInt("INPRICE");
                                int outprice = rs.getInt("OUTPRICE");
                                int nums = rs.getInt("NUMS");

                                System.out.println();
                                System.out.println("商品编号: " + ID);
                                System.out.println("商品名称: " + product);
                                System.out.println("生产厂家: " + factory);
                                System.out.println("生产日期: " + date);
                                System.out.println("型号: " + type);
                                System.out.println("进货价: " + inprice);
                                System.out.println("零售价: " + outprice);
                                System.out.println("数量: " + nums);
                                System.out.println();

                                // 进行其他操作
                            } else {
                                System.out.println("未找到该商品");
                            }

                            rs.close();
                            ps.close();
                            stmt.close();
                            c.close();
                        } catch (Exception e) {
                            System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            System.exit(0);
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
