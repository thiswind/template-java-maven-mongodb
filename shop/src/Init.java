import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Init {
    public void initializeDatabase() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:data.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            // 创建commodity表格
            String createCommodityTable = "CREATE TABLE IF NOT EXISTS commodity (name TEXT NOT NULL, price REAL NOT NULL,factory TEXT NOT NULL,time TEXT NOT NULL,num INTEGER NOT NULL)";
            stmt.executeUpdate(createCommodityTable);

            // 创建user表格
            String createUserTable = "CREATE TABLE IF NOT EXISTS users (account TEXT NOT NULL, password TEXT NOT NULL, type INTEGER NOT NULL,id INTEGER PRIMARY KEY AUTOINCREMENT,level INTEGER NOT NULL,time TEXT NOT NULL,sum REAL NOT NULL,phone TEXT NOT NULL,email TEXT NOT NULL)";
            stmt.executeUpdate(createUserTable);

            // 检查是否已经存在管理员账户
            String checkAdminUser = "SELECT * FROM users WHERE type = 1";
            ResultSet adminUserResult = stmt.executeQuery(checkAdminUser);
            if (!adminUserResult.next()) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                String createAdminUser = "INSERT INTO users (account, password, type, level, time, sum, phone, email) VALUES ('admin', 'ynuinfo#777', 1, 0, '" + formattedDateTime + "', 0, '13379834673', '676864824@qq.com')";
                stmt.executeUpdate(createAdminUser);
            } else {
            }

            // 创建shoppingCart表格
            String createShoppingCart = "CREATE TABLE IF NOT EXISTS shoppingCart (account TEXT NOT NULL, name TEXT NOT NULL, price REAL NOT NULL)";
            stmt.executeUpdate(createShoppingCart);

            // 创建shoppingHistory表格
            String createShoppingHistory = "CREATE TABLE IF NOT EXISTS shoppingHistory (account TEXT NOT NULL, goods TEXT NOT NULL,time TEXT NOT NULL)";
            stmt.executeUpdate(createShoppingHistory);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
