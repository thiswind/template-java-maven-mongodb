import java.sql.*;
import java.util.Scanner;

public class ManagerFunction {

    public void addCommodity(){
        Scanner scanner=new Scanner(System.in);
        String name;
        float price;
        System.out.println("请输入商品名称");
        name=scanner.nextLine();
        System.out.println("请输入商品价格");
        price=scanner.nextFloat();
        scanner.nextLine();
        System.out.println("请输入生产厂家");
        String factory=scanner.nextLine();
        System.out.println("请输入生产时间(格式为yyyy-mm-dd)");
        String time=scanner.nextLine();
        System.out.println("请输入商品数量");
        int num=scanner.nextInt();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);

            String sql = "INSERT INTO commodity (name, price,factory,time,num) VALUES (?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setFloat(2, price);
            statement.setString(3, factory);
            statement.setString(4, time);
            statement.setInt(5, num);
            statement.executeUpdate();

            statement.close();
            connection.commit();
            connection.close();
            System.out.println("商品添加成功！");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void deleteCommodity(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要删除的商品名称：");
        String name = scanner.nextLine();
        System.out.println("确定删除此商品吗(Y/N)");
        char choose=scanner.next().charAt(0);
        if(choose=='N'||choose=='n')
            return;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);

            String sql = "DELETE FROM commodity WHERE name = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                connection.commit();
                System.out.println("商品删除成功！");
            } else {
                System.out.println("未找到指定的商品名称，删除失败！");
            }

            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void modifyCommodity(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要修改的商品名称：");
        String name = scanner.nextLine();
        System.out.println("请输入修改后的商品价格：");
        float price = scanner.nextFloat();
        scanner.nextLine();
        System.out.println("请输入修改后的商品厂家");
        String factory=scanner.nextLine();
        System.out.println("请输入修改后的生产时间(格式为yyyy-mm-dd)");
        String time=scanner.nextLine();
        System.out.println("请输入修改后的商品数量");
        int num=scanner.nextInt();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);

            String sql = "UPDATE commodity SET price = ?,factory=?,time=?,num=?  WHERE name = ?";
            statement = connection.prepareStatement(sql);
            statement.setFloat(1, price);
            statement.setString(2, factory);
            statement.setString(3, time);
            statement.setInt(4,num);
            statement.setString(5, name);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                connection.commit();
                System.out.println("商品修改成功！");
            } else {
                System.out.println("未找到指定的商品名称，修改失败！");
            }
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void selectCommodityList(){
        float price;
        int num;
        String factory=null,time=null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.根据商品名称查找商品\n2.根据商品价格查找商品\n3.根据生产厂家查找商品\n4.根据生产时间查找商品");
        int choose=scanner.nextInt();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql=null;
        boolean judge=false;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            switch (choose){
                case 1:
                    System.out.println("请输入商品名称");
                    scanner.nextLine();
                    String nameInput=scanner.nextLine();
                    sql = "SELECT * FROM commodity WHERE name = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, nameInput);
                    rs = statement.executeQuery();
                    break;
                case 2:
                    System.out.println("请输入商品价格");
                    float priceInput=scanner.nextFloat();
                    sql = "SELECT * FROM commodity WHERE price = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setFloat(1, priceInput);
                    rs = statement.executeQuery();
                    break;
                case 3:
                    System.out.println("请输入生产厂家");
                    scanner.nextLine();
                    String factoryInput=scanner.nextLine();
                    sql = "SELECT * FROM commodity WHERE factory = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, factoryInput);
                    rs = statement.executeQuery();
                    break;
                case 4:
                    System.out.println("请输入生产时间");
                    scanner.nextLine();
                    String timeInput=scanner.nextLine();
                    sql = "SELECT * FROM commodity WHERE time = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, timeInput);
                    rs = statement.executeQuery();
                    break;
            }

            while (rs.next()) {
                judge=true;
                String name=rs.getString("name");
                price = rs.getFloat("price");
                factory=rs.getString("factory");
                time=rs.getString("time");
                num=rs.getInt("num");
                System.out.println("商品名称:"+name+"\n价格："+price+"\n生产厂家:"+factory+"\n生产时间:"+time+"\n商品数量:"+num);
                System.out.println("------------------------");
            }
            if(!judge) {
                System.out.println("商品不存在!");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void commodityList(int judge){//查看商品列表
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            String sql = "SELECT * FROM commodity";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("name");
                float price = rs.getFloat("price");
                String factory=rs.getString("factory");
                String time=rs.getString("time");
                int num=rs.getInt("num");
                if(num==0&&judge==1)
                    continue;
                System.out.println("商品名称:"+name+"\n价格："+price+"\n生产厂家:"+factory+"\n生产时间:"+time+"\n商品数量:"+num);
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



    public void accountList(){//查看用户列表
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            String sql = "SELECT * FROM users";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String account = rs.getString("account");
                int id=rs.getInt("id");
                int level=rs.getInt("level");
                String time=rs.getString("time");
                float sum=rs.getFloat("sum");
                String phone=rs.getString("phone");
                String email=rs.getString("email");

                System.out.println("id: " + id);
                System.out.println("用户名: " + account);
                switch (level){
                    case 0:System.out.println("用户等级:铜牌客户" );break;
                    case 1:System.out.println("用户等级:银牌客户" );break;
                    case 2:System.out.println("用户等级:金牌客户" );break;
                }
                System.out.println("注册时间: " + time);
                System.out.println("用户总消费: " + sum);
                System.out.println("手机号: " + phone);
                System.out.println("邮箱: " + email);
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

    public void deleteAccount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要删除的用户账户名：");
        String account = scanner.nextLine();
        System.out.println("确认删除此用户吗(Y/N)");
        char choose=scanner.next().charAt(0);
        if(choose=='N'||choose=='n')
            return;
        Connection connection = null;
        PreparedStatement statement = null;
        boolean deleted = false;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            String sql = "DELETE FROM users WHERE account = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, account);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                deleted = true;
            }
            sql = "DELETE FROM shoppingHistory WHERE account = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, account);
            statement.executeUpdate();
            sql = "DELETE FROM shoppingCart WHERE account = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, account);
            statement.executeUpdate();
            connection.commit();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        if (deleted) {
            System.out.println("成功删除用户：" + account);
        } else {
            System.out.println("未找到指定的用户账户！");
        }
    }

    public void selectAccount(){
        String account,sql;
        int id;
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.根据id查询用户\n2.根据账户查询用户");
        int choose=scanner.nextInt();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            switch (choose){
                case 1:
                    System.out.println("请输入要查询的用户的id：");
                    id = scanner.nextInt();
                    sql = "SELECT * FROM users WHERE id = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, id);
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.println("请输入要查询的用户账户名：");
                    account = scanner.nextLine();
                    sql = "SELECT * FROM users WHERE account = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, account);
                    break;
            }
            rs = statement.executeQuery();
            if (rs.next()) {
                int userId=rs.getInt("id");
                String userAcconut=rs.getString("account");
                int level=rs.getInt("level");
                String time=rs.getString("time");
                float sum=rs.getFloat("sum");
                String phone=rs.getString("phone");
                String email=rs.getString("email");

                System.out.println("id: " + userId);
                System.out.println("用户名: " + userAcconut);
                switch (level){
                    case 0:System.out.println("用户等级:铜牌客户" );break;
                    case 1:System.out.println("用户等级:银牌客户" );break;
                    case 2:System.out.println("用户等级:金牌客户" );break;
                }
                System.out.println("注册时间: " + time);
                System.out.println("用户总消费: " + sum);
                System.out.println("手机号: " + phone);
                System.out.println("邮箱: " + email);
                System.out.println("------------------------");
            } else {
                System.out.println("未找到指定的用户账户！");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void resetPassword(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入要重置密码的用户");
        String account=scanner.nextLine();
        Connection connection = null;
        PreparedStatement statement = null;
        boolean reset=false;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            String sql = "UPDATE users SET password = ? WHERE account = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, "123456");
            statement.setString(2, account);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                reset = true;
            }
            statement.close();
            connection.commit();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        if(reset)
            System.out.println("密码重置成功");
        else
            System.out.println("找不到指定用户");
    }

    public int getNum(String name) {
        int num = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            String sql = "SELECT num FROM commodity WHERE name = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                num = rs.getInt("num");
            }
            statement.close();
            connection.commit();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return num;
    }

    public void subCommodity(String goods){
        String[] productList = goods.split(" ");
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            String sql = "UPDATE commodity SET num = ? WHERE name = ?";
            for(int i=0;i<productList.length;i++){
                if(i%2==0){
                    int num=getNum(productList[i]);
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, num-Integer.parseInt(productList[i+1]));
                    statement.setString(2,productList[i]);
                    statement.executeUpdate();
                }
            }
            statement.close();
            connection.commit();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {

        }
    }
}
