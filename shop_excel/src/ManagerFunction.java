import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.Scanner;

public class ManagerFunction {

    public void addCommodity(){
        ExcleOp excleOp =new ExcleOp();
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
        excleOp.writeCommodity(name,price,factory,time,num);
    }

    public void deleteCommodity(){
        ExcleOp excleOp =new ExcleOp();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要删除的商品名称：");
        String name = scanner.nextLine();
        System.out.println("确定删除此商品吗(Y/N)");
        char choose=scanner.next().charAt(0);
        if(choose=='N'||choose=='n')
            return;
        excleOp.deleteCommodity(name);
    }

    public void modifyCommodity(){
        ExcleOp excleOp =new ExcleOp();
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
        excleOp.modifyCommodity(name,price,factory,time,num);
    }

    public void selectCommodityList(){
        ExcleOp excleOp =new ExcleOp();
        float price;
        int num;
        String factory=null,time=null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要查询的商品名称：");
        String name = scanner.nextLine();
        String line= excleOp.getLineCommodity(name);
        if(line==null){
            System.out.println("商品不存在");
            return;
        }
        String[] parts=line.split("\\|");
        System.out.println("商品名称:"+parts[0]+"\n价格："+parts[1]+"\n生产厂家:"+parts[2]+"\n生产时间:"+parts[3]+"\n商品数量:"+parts[4]);
        System.out.println("------------------------");
    }

    public void commodityList(int judge){//查看商品列表
        String line = null;
        File file = new File("commodity.xlsx");
        if (file.length() == 0) {
            return;
        }
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream("commodity.xlsx"));
            Sheet sheet = workbook.getSheet("Commodity");

            for (Row row : sheet) {
                line = "";
                for (Cell cell : row) {
                    line += cell.getStringCellValue() + "|";
                }
                String[] parts = line.split("\\|");
                if(Integer.parseInt(parts[4])==0&&judge==1)
                    continue;
                System.out.println("商品名称:"+parts[0]+"\n价格："+parts[1]+"\n生产厂家:"+parts[2]+"\n生产时间:"+parts[3]+"\n商品数量:"+parts[4]);
                System.out.println("------------------------");
            }
            workbook.close(); // 关闭Workbook对象
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }



    public void accountList(){//查看用户列表
        String line = null;
        File file = new File("users.xlsx");
        if (file.length() == 0) {
            return;
        }
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream("users.xlsx"));
            Sheet sheet = workbook.getSheetAt(0);

            // 检查文件是否为空
            if (sheet.getLastRowNum() == 0) {
                return;
            }

            for (Row row : sheet) {
                line = "";
                for (Cell cell : row) {
                    line += cell.getStringCellValue() + "|";
                }
                String[] parts = line.split("\\|");
                System.out.println("id: " + parts[3]);
                System.out.println("用户名: " + parts[0]);
                switch (Integer.parseInt(parts[4])){
                    case 0:System.out.println("用户等级:铜牌客户" );break;
                    case 1:System.out.println("用户等级:银牌客户" );break;
                    case 2:System.out.println("用户等级:金牌客户" );break;
                }
                System.out.println("注册时间: " +parts[5] );
                System.out.println("用户总消费: " + parts[6]);
                System.out.println("手机号: " + parts[7]);
                System.out.println("邮箱: " + parts[8]);
                System.out.println("------------------------");
            }
            workbook.close(); // 关闭Workbook对象
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    public void deleteAccount(){
        ExcleOp excleOp =new ExcleOp();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要删除的用户账户名：");
        String account = scanner.nextLine();
        if(excleOp.judgeExistUsers(account)){
            System.out.println("用户不存在");
            return;
        }
        System.out.println("确认删除此用户吗(Y/N)");
        char choose=scanner.next().charAt(0);
        if(choose=='N'||choose=='n')
            return;
        excleOp.deleteAccountUsers(account);
        System.out.println("删除成功");
    }

    public void selectAccount(){
        ExcleOp excleOp =new ExcleOp();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要查询的用户账户名：");
        String account = scanner.nextLine();
        String line= excleOp.getLineUsers(account);
        if(line==null){
            System.out.println("用户不存在");
            return;
        }
        String[] parts=line.split("\\|");
        System.out.println("id: " + parts[3]);
        System.out.println("用户名: " + parts[0]);
        switch (Integer.parseInt(parts[4])){
            case 0:System.out.println("用户等级:铜牌客户" );break;
            case 1:System.out.println("用户等级:银牌客户" );break;
            case 2:System.out.println("用户等级:金牌客户" );break;
        }
        System.out.println("注册时间: " +parts[5] );
        System.out.println("用户总消费: " + parts[6]);
        System.out.println("手机号: " + parts[7]);
        System.out.println("邮箱: " + parts[8]);
        System.out.println("------------------------");
    }

    public void resetPassword(){
        ExcleOp excleOp =new ExcleOp();
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入要重置密码的用户");
        String account=scanner.nextLine();
        if(excleOp.judgeExistUsers(account)){
            System.out.println("账户不存在");
            return;
        }
        excleOp.modifyPassword(account,"123456");
        System.out.println("重置成功");
    }

    public int getNum(String name) {
        ExcleOp excleOp =new ExcleOp();
        int num = 0;
        String line= excleOp.getLineCommodity(name);
        String[] parts=line.split("\\|");
        num=Integer.parseInt(parts[4]);
        return num;
    }

    public void subCommodity(String goods){
        ExcleOp excleOp =new ExcleOp();
        String[] productList = goods.split(" ");
        //商品数量减少操作
        for(int i=0;i<productList.length;i++){
            if(i%2==0){
                excleOp.modifyCommodityNum(productList[i],getNum(productList[i])-Integer.parseInt(productList[i+1]));
            }
        }
    }
}
