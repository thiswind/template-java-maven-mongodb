import java.util.Scanner;

public class Menu {
    public void selectRole(){
        Scanner scanner = new Scanner(System.in);
        UserFunction userFunction =new UserFunction();
        int choose;
        boolean judge=true;
        while(judge){
            System.out.print("1.普通用户\n2.管理员\n3.退出\n");
            choose=scanner.nextInt();
            switch (choose){
                case 1:userLoginMenu();break;
                case 2:managerLoginMenu();break;
                case 3:judge=false;
            }
        }
    }

    public void managerLoginMenu(){
        Scanner scanner = new Scanner(System.in);
        UserFunction userFunction =new UserFunction();
        int choose;
        boolean judge=true;
        while(judge){
            System.out.print("1.登录\n2.返回上级菜单\n");
            choose=scanner.nextInt();
            switch (choose){
                case 1:
                    userFunction.login();break;
                case 2:judge=false;
            }
        }
    }

    public void userLoginMenu(){
        Scanner scanner = new Scanner(System.in);
        UserFunction userFunction =new UserFunction();
        int choose;
        boolean judge=true;
        while(judge){
            System.out.print("1.登录\n2.注册\n3.返回上级菜单\n");
            choose=scanner.nextInt();
            switch (choose){
                case 1:
                    userFunction.login();break;
                case 2:
                    userFunction.register();break;
                case 3:judge=false;
            }
        }
    }

    public void userMenu(){
        Purchase purchase=new Purchase();
        Scanner scanner = new Scanner(System.in);
        UserFunction userFunction =new UserFunction();
        int choose;
        boolean judge=true;
        while(judge){
            System.out.print("1.购物\n2.查看购物车\n3.修改密码\n4.重置密码（重置后为123456）\n5.查看购买记录\n6.退出登陆\n");
            choose=scanner.nextInt();
            switch (choose){
                case 1:purchase.buy();break;
                case 2:userFunction.shoppingCartList();break;
                case 3:userFunction.changePassword();break;
                case 4:userFunction.resetPassword();break;
                case 5:userFunction.shoppingHistoryList();break;
                case 6:judge=false;
            }
        }
    }

    public void managerMenu(){
        Scanner scanner = new Scanner(System.in);
        ManagerFunction managerFunction=new ManagerFunction();
        UserFunction userFunction =new UserFunction();
        int choose;
        boolean judge=true;
        while(judge){
            System.out.print("1.用户信息\n2.删除用户\n3.查询用户\n4.修改密码\n5.重置用户密码\n6.商品信息\n7.添加商品\n8.修改商品\n9.删除商品\n10.查询商品\n11.退出登陆\n");
            choose=scanner.nextInt();
            switch (choose){
                case 1:managerFunction.accountList();break;
                case 2:managerFunction.deleteAccount();break;
                case 3:managerFunction.selectAccount();break;
                case 4:userFunction.changePassword();break;
                case 5:managerFunction.resetPassword();break;
                case 6:managerFunction.commodityList(2);break;
                case 7:managerFunction.addCommodity();break;
                case 8:managerFunction.modifyCommodity();break;
                case 9:managerFunction.deleteCommodity();break;
                case 10:managerFunction.selectCommodityList();break;
                case 11:judge=false;
            }
        }
    }

    public void shoppingcartMenu(){
        Scanner scanner = new Scanner(System.in);
        UserFunction userFunction =new UserFunction();
        Purchase purchase=new Purchase();
        int choose;
        boolean judge=true;
        System.out.print("1.删除购物车中的商品\n2.选择购物车中的商品购买\n3.退出\n");
        choose=scanner.nextInt();
        scanner.nextLine();
        switch (choose){
            case 1:
                System.out.println("请输入要删除的商品（商品之间用空格隔开）");
                String goods=scanner.nextLine();
                System.out.println("确定删除此商品吗(Y/N)");
                char choose1=scanner.next().charAt(0);
                if(choose1=='N'||choose1=='n')
                    return;
                purchase.deleteShoppingCart(goods,1);
                break;
            case 2:purchase.buyShoppingCart();break;
            case 3:break;
        }
    }

    public void shoppingMenu(String goods){
        Purchase purchase=new Purchase();
        Scanner scanner = new Scanner(System.in);
        UserFunction userFunction =new UserFunction();
        int choose;
        System.out.print("1.购买\n2.加入购物车\n3.退出\n");
        choose=scanner.nextInt();
        switch (choose){
            case 1:purchase.paid(goods);break;
            case 2:purchase.writeShoppingCart(goods,userFunction.account);break;
            case 3:break;
        }
    }
}
