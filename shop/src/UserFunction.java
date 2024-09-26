import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserFunction {

    static String account;//设置为静态变量，用于记录当前登录用户的账号

    public void login(){
        Menu menu=new Menu();
        Scanner scanner = new Scanner(System.in);
        String password;
        System.out.println("请输入用户名");
        account = scanner.nextLine();
        System.out.println("请输入密码");
        password = scanner.nextLine();

        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String sql = "SELECT * FROM users WHERE ACCOUNT = '" + account + "' AND PASSWORD = '" + password + "'";
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                int type = rs.getInt("TYPE");
                if (type == 0) {
                    rs.close();
                    statement.close();
                    connection.close();
                    menu.userMenu();
                } else {
                    rs.close();
                    statement.close();
                    connection.close();
                    menu.managerMenu();
                }
            } else {
                rs.close();
                statement.close();
                connection.close();
                System.out.println("账号或密码错误");
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void register(){
        Scanner scanner=new Scanner(System.in);
        String account,password;
        while (true) {
            System.out.println("请输入用户名（长度不少于5个字符）");
            account = scanner.nextLine();
            if (account.length() >= 5) {
                break;
            } else {
                System.out.println("用户名长度不符合要求，请重新输入");
            }
        }
        while (true) {
            System.out.println("请输入密码（长度大于8个字符，必须是大小写字母、数字和标点符号的组合）");
            password = scanner.nextLine();
            if (password.length() > 8 && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$")) {
                break;
            } else {
                System.out.println("密码长度或字符组合不符合要求，请重新输入");
            }
        }
        System.out.println("请输入手机号");
        String phone=scanner.nextLine();
        System.out.println("请输入邮箱");
        String email=scanner.nextLine();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            String checkQuery = "SELECT COUNT(*) FROM users WHERE account=?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, account);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    System.out.println("账户已存在，注册失败！");
                    return;
                }
            }
            String sql = "INSERT INTO users (account, password,type,level,time,sum,phone,email) VALUES (?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, account);
            statement.setString(2, password);
            statement.setInt(3, 0);
            statement.setInt(4, 0);
            statement.setString(5, formattedDateTime);
            statement.setFloat(6, 0);
            statement.setString(7, phone);
            statement.setString(8,email);
            statement.executeUpdate();

            statement.close();
            connection.commit();
            connection.close();
            System.out.println("注册成功！");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void changePassword(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要修改的密码：");
        String password = scanner.nextLine();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            String sql = "UPDATE users SET password = ? WHERE account = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, password);
            statement.setString(2, account);
            statement.executeUpdate();
            connection.commit();
            statement.close();
            connection.close();
            System.out.println("密码修改成功！");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void resetPassword(){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            String sql = "UPDATE users SET password = ? WHERE account = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, "123456");
            statement.setString(2, account);
            statement.executeUpdate();
            connection.commit();
            statement.close();
            connection.close();
            System.out.println("密码重置成功！");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void shoppingHistoryList(){
        Purchase purchase=new Purchase();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);

            String sql = "SELECT * FROM shoppingHistory WHERE account=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, account);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String goods = rs.getString("goods");
                String time = rs.getString("time");
                String[] productList = goods.split(" ");
                float sum=0;

                for(int i=0;i<productList.length;i++){
                    if(i%2==0){
                        if(i==0)
                            System.out.println("时间:"+time);
                        System.out.println("商品:"+productList[i]+"*"+productList[i+1]+" 累计:"+Integer.parseInt(productList[i+1])*purchase.getProductPrice(productList[i])+"元");
                        sum+=Integer.parseInt(productList[i+1])*purchase.getProductPrice(productList[i]);
                    }
                }
                System.out.println("总计:"+sum+"元");
                System.out.println("------------------------");
            }

            rs.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void shoppingCartList(){
        ManagerFunction managerFunction=new ManagerFunction();
        int count=0;
        Menu menu=new Menu();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM shoppingCart WHERE account=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, account);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                float price = rs.getFloat("price");
                int num=managerFunction.getNum(name);
                System.out.println("商品名称: " + name);
                System.out.println("商品价格: " + price);
                System.out.println("商品数量:"+num);
                System.out.println("------------------------");
                count++;
            }
            rs.close();
            statement.close();
            connection.close();
            if(count!=0)
                menu.shoppingcartMenu();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
