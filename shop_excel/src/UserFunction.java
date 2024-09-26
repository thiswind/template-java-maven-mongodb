import java.util.List;
import java.util.Scanner;

public class UserFunction {

    static String account;//设置为静态变量，用于记录当前登录用户的账号
    static int RecNum;

    public void login(){
        Menu menu=new Menu();
        ExcleOp excleOp =new ExcleOp();
        Scanner scanner = new Scanner(System.in);
        String password,passRec,line;
        System.out.println("请输入用户名");
        account = scanner.nextLine();
        System.out.println("请输入密码");
        password = scanner.nextLine();
        line= excleOp.getLineUsers(account);
        if(line==null){
            System.out.println("账户不存在");
        }
        else {
            passRec= excleOp.getPassword(account);
            if(passRec.equals(password)){
                if(excleOp.getType(account)=='0')
                    menu.userMenu();
                else
                    menu.managerMenu();
            }
            else
                System.out.println("密码错误");
        }
    }

    public void register(){
        ExcleOp excleOp =new ExcleOp();
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
        if(excleOp.judgeExistUsers(account)){
            excleOp.writeAccount(account,password,phone,email);
            System.out.println("注册成功");
            excleOp.writeCounter(RecNum++);
        }
        else {
            System.out.println("账户已存在");
        }
    }

    public void changePassword(){
        ExcleOp excleOp =new ExcleOp();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要修改的密码：");
        String password = scanner.nextLine();
        excleOp.modifyPassword(account,password);
        System.out.println("修改成功");
    }

    public void resetPassword(){
        ExcleOp excleOp =new ExcleOp();
        excleOp.modifyPassword(account,"123456");
        System.out.println("重置成功");
    }

    public void shoppingHistoryList(){
        ExcleOp excleOp =new ExcleOp();
        Purchase purchase=new Purchase();
        List<String> linesList = excleOp.getLinesShoppingHistory(UserFunction.account);
        if (linesList==null)
            return;
        String[] lines = linesList.toArray(new String[linesList.size()]);
        for(int i=0;i<lines.length;i++){
            String[] parts=lines[i].split("\\|");
            String[] productList = parts[1].split(" ");
            float sum=0;
            for(int j=0;j<productList.length;j++){
                if(j%2==0){
                    if(j==0)
                        System.out.println("时间:"+parts[2]);
                    System.out.println("商品:"+productList[j]+"*"+productList[j+1]+" 累计:"+Integer.parseInt(productList[j+1])*purchase.getProductPrice(productList[j])+"元");
                    sum+=Integer.parseInt(productList[j+1])*purchase.getProductPrice(productList[j]);
                }
            }
            System.out.println("总计:"+sum+"元");
            System.out.println("------------------------");
        }
        
    }

    public void shoppingCartList(){
        ManagerFunction managerFunction=new ManagerFunction();
        ExcleOp excleOp =new ExcleOp();
        int count=0;
        Menu menu=new Menu();
        List<String> linesList = excleOp.getLinesShoppingCart(UserFunction.account);
        if(linesList==null)
            return;
        String[] lines = linesList.toArray(new String[linesList.size()]);
        if(lines==null)
            return;
        for(int i=0;i<lines.length;i++){
            String line=lines[i];
            String[] parts=line.split("\\|");
            String name = parts[1];
            float price = Float.parseFloat(parts[2]);
            int num=managerFunction.getNum(name);
            System.out.println("商品名称: " + name);
            System.out.println("商品价格: " + price);
            System.out.println("商品数量:"+num);
            System.out.println("------------------------");
            count++;
        }
        if(count!=0)
            menu.shoppingcartMenu();
    }
}
