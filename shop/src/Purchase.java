import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Purchase {
    public boolean paid(String goods){//用于模拟结账
        ManagerFunction managerFunction=new ManagerFunction();
        UserFunction userFunction=new UserFunction();
        float sum=0;
        String account=userFunction.account;
        String[] productList = goods.split(" ");
        for (int i=0;i<productList.length;i++) {
            if(i%2==0){
                sum+=Integer.parseInt(productList[i+1])*getProductPrice(productList[i]);
                int num=managerFunction.getNum(productList[i]);
                if(Integer.parseInt(productList[i+1])>num){
                    System.out.println("商品"+productList[i]+"库存仅剩"+num+"购买失败！");
                    return false;
                }
            }

        }
        int choose;
        Scanner scanner=new Scanner(System.in);
        System.out.println("你购买的商品有:");
        for(int i=0;i<productList.length;i++){
            if(i%2==0){
                System.out.print(productList[i]+" ");
            }
        }
        System.out.println(" 总计"+sum+"元");
        System.out.println("1.确认支付\n2.取消购买");
        choose=scanner.nextInt();
        switch (choose){
            case 1:
                System.out.println("支付成功");
                writeShoppingHistory(goods,account);
                managerFunction.subCommodity(goods);
                Connection connection = null;
                PreparedStatement statement = null;
                try {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:data.db");
                    connection.setAutoCommit(false);
                    String sql = "SELECT sum FROM users WHERE account = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, account);
                    ResultSet rs =statement.executeQuery();
                    if (rs.next()) {
                        sum += rs.getInt("sum");
                        sql = "UPDATE users SET sum = ? WHERE account = ?";
                        statement = connection.prepareStatement(sql);
                        statement.setFloat(1, sum);
                        statement.setString(2, account);
                        statement.executeUpdate();
                        connection.commit();
                        if(sum>=3000){
                            sql = "UPDATE users SET level = ? WHERE account = ?";
                            statement = connection.prepareStatement(sql);
                            statement.setInt(1, 2);
                            statement.setString(2, account);
                            statement.executeUpdate();
                            connection.commit();
                        }
                        else if(sum>=2000){
                            sql = "UPDATE users SET level = ? WHERE account = ?";
                            statement = connection.prepareStatement(sql);
                            statement.setInt(1, 1);
                            statement.setString(2, account);
                            statement.executeUpdate();
                            connection.commit();
                        }
                    }
                    statement.close();
                    connection.commit();
                    connection.close();
                } catch (Exception e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);
                }
                break;
            case 2:return false;
        }
        return true;
    }

    public float getProductPrice(String name){//获取某一个商品的价格
        float price=0;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            String sql = "SELECT price FROM commodity WHERE name = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                price = resultSet.getFloat("price");
            } else {
                System.out.println("商品不存在!");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return price;
    }
    public void writeShoppingHistory(String goods,String account){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);

            String sql = "INSERT INTO shoppingHistory (account, goods,time) VALUES (?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, account);
            statement.setString(2, goods);
            statement.setString(3, formattedDateTime);
            statement.executeUpdate();
            statement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
        }
    }

    public void writeShoppingCart(String goods,String account){
        String[] productList = goods.split(" ");
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM shoppingCart WHERE name = ? AND account = ?";
            for (int i=0;i<productList.length;i++) {
                if(i%2==0){
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, productList[i]);
                    statement.setString(2, account);
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                    } else {
                        String sql1 = "INSERT INTO shoppingCart (account, name, price) VALUES (?, ?, ?)";
                        statement = connection.prepareStatement(sql1);
                        statement.setString(1, account);
                        statement.setString(2, productList[i]);
                        statement.setFloat(3, getProductPrice(productList[i]));
                        statement.executeUpdate();
                        connection.commit();
                    }
                    statement.close();
                }
            }
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void buy(){//购物的方法
        Menu menu=new Menu();
        Scanner scanner=new Scanner(System.in);
        ManagerFunction managerFunction=new ManagerFunction();
        managerFunction.commodityList(1);
        System.out.println("请输入要购买商品的名称（输入格式：商品名称 购买个数  ‘eg:computer 2’这代买两个computer）");
        String goods=scanner.nextLine();
        menu.shoppingMenu(goods);
    }

    public void deleteShoppingCart(String goods,int judge){
        UserFunction userFunction=new UserFunction();
        Scanner scanner=new Scanner(System.in);
        String[] productList = goods.split(" ");
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            String sql = "DELETE FROM shoppingCart WHERE name = ? AND account = ?";
            statement = connection.prepareStatement(sql);
            for (String product : productList) {
                statement.setString(1, product);
                statement.setString(2, userFunction.account);
                statement.executeUpdate();
            }
            if(judge==1)
                System.out.println("删除成功");
            connection.commit();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void buyShoppingCart(){
        UserFunction userFunction=new UserFunction();
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入要购买商品的名称（输入格式：商品名称 购买个数  ‘eg:computer 2’这代买两个computer）");
        String goods=scanner.nextLine();
        String[] productList = goods.split(" ");
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM shoppingCart WHERE name = ?";
            boolean isProductInCart = true; // 标记购物车中是否存在所有商品
            for (int i = 0; i < productList.length; i++) {
                if (i % 2 == 0) {
                    statement = connection.prepareStatement(sql);
                    statement.setString(1, productList[i]);
                    ResultSet rs = statement.executeQuery();
                    if (!rs.next()) {
                        isProductInCart = false;
                        System.out.println("购物车中不存在商品：" + productList[i]+" 购买失败!");
                    }
                    rs.close();
                }
            }
            statement.close();
            connection.close();
            if (!isProductInCart) {
                return;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        boolean judge=paid(goods);
        if(!judge)
            return;
        String str="";
        for(int i=0;i<productList.length;i++){
            if(i%2==0){
                str=str+productList[i]+" ";
            }
        }
        deleteShoppingCart(str,2);
    }
}
