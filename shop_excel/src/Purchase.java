import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Purchase {
    public boolean paid(String goods){//用于模拟结账
        ExcleOp excleOp =new ExcleOp();
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
                String line= excleOp.getLineUsers(account);
                String[] parts=line.split("\\|");
                sum=Float.parseFloat(parts[6])+sum;
                excleOp.modifySum(account,sum);
                if(sum>=3000)
                    excleOp.modifyLevel(account,2);
                else if(sum>=2000)
                    excleOp.modifyLevel(account,1);
                break;
            case 2:return false;
        }
        return true;
    }

    public float getProductPrice(String name){//获取某一个商品的价格
        ExcleOp excleOp =new ExcleOp();
        float price=0;
        String line= excleOp.getLineCommodity(name);
        String[] parts=line.split("\\|");
        price=Float.parseFloat(parts[1]);
        return price;
    }
    public void writeShoppingHistory(String goods,String account){
        ExcleOp excleOp =new ExcleOp();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        Connection connection = null;
        PreparedStatement statement = null;
        //shoppingHistory文件写入
        excleOp.writeShoppingHistory(account,goods,formattedDateTime);
    }

    public void writeShoppingCart(String goods,String account){
        ExcleOp excleOp =new ExcleOp();
        String[] productList = goods.split(" ");
        for(int i=0;i<productList.length;i++){
            if(i%2==0){
                excleOp.writeShoppingCart(account,productList[i],getProductPrice(productList[i]));
            }
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
        ExcleOp excleOp =new ExcleOp();
        UserFunction userFunction=new UserFunction();
        Scanner scanner=new Scanner(System.in);
        String[] productList = goods.split(" ");
       //购物车删除操作
        excleOp.deleteShoppingCart(UserFunction.account,goods);
        if(judge==1)
            System.out.println("删除成功");
    }

    public void buyShoppingCart(){
        UserFunction userFunction=new UserFunction();
        ExcleOp excleOp =new ExcleOp();
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入要购买商品的名称（输入格式：商品名称 购买个数  ‘eg:computer 2’这代买两个computer）");
        String goods=scanner.nextLine();
        String[] productList = goods.split(" ");
        boolean isProductInCart = true; // 标记购物车中是否存在所有商品
        //判断购物车中的商品是否存在
        for(int i=0;i<productList.length;i++){
            if(i%2==0){
                boolean judge= excleOp.judgeExistShoppingCart(productList[i]);
                if(!judge){
                    isProductInCart=false;
                    System.out.println("购物车中不存在商品：" + productList[i]+" 购买失败!");
                }
            }
        }
        if(!isProductInCart)
            return;
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
