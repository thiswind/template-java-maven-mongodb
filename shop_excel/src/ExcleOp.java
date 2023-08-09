import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcleOp {

    //用户文件---------------------------------------------------
    public String getLineUsers(String account){//返回account当前行的所有数据
        String line = null;
        boolean judge = true;
        File file = new File("users.xlsx");
        if (file.length() == 0) {
            return null;
        }
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream("users.xlsx"));
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                line = "";
                for (Cell cell : row) {
                    line += cell.getStringCellValue() + "|";
                }
                String[] parts = line.split("\\|");
                if (parts.length > 0 && !parts[0].equals(account))
                    judge = false;
                if (judge) {
                    return line;
                }
                judge = true;
            }
            workbook.close(); // 关闭Workbook对象
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        return null;
    }


    public String getPassword(String account){//返回account的密码
        String line=getLineUsers(account);
        String[] parts=line.split("\\|");
        return parts[1];
    }


    public char getType(String account){
        String line=getLineUsers(account);
        String[] parts=line.split("\\|");
        return parts[2].charAt(0);
    }

    public void writeAccount(String account,String password,String phone,String email){//将用户数据写入文件
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        File file = new File("users.xlsx");
        try (InputStream is = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet("Users");
            if(sheet==null)
                sheet=workbook.createSheet("Users");
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);

            Cell accountCell = newRow.createCell(0);
            accountCell.setCellValue(account);

            Cell passwordCell = newRow.createCell(1);
            passwordCell.setCellValue(password);

            Cell typeCell = newRow.createCell(2);
            typeCell.setCellValue("0");

            Cell idCell = newRow.createCell(3);
            idCell.setCellValue(Integer.toString(UserFunction.RecNum++));

            Cell levelCell = newRow.createCell(4);
            levelCell.setCellValue("0");

            Cell timeCell = newRow.createCell(5);
            timeCell.setCellValue(formattedDateTime);

            Cell sumCell = newRow.createCell(6);
            sumCell.setCellValue("0");

            Cell phoneCell = newRow.createCell(7);
            phoneCell.setCellValue(phone);

            Cell emailCell = newRow.createCell(8);
            emailCell.setCellValue(email);

            try (FileOutputStream fos = new FileOutputStream("users.xlsx")) {
                workbook.write(fos);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing the workbook.");
            e.printStackTrace();
        }
    }

    public void writeAccount(String account,String password,String id,String level,String time,String sum,String phone,String email){//将用户数据写入文件
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        File file = new File("users.xlsx");
        try (InputStream is = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet("Users");
            if(sheet==null)
                sheet=workbook.createSheet("Users");
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);

            Cell accountCell = newRow.createCell(0);
            accountCell.setCellValue(account);

            Cell passwordCell = newRow.createCell(1);
            passwordCell.setCellValue(password);

            Cell typeCell = newRow.createCell(2);
            typeCell.setCellValue("0");

            Cell idCell = newRow.createCell(3);
            idCell.setCellValue(id);

            Cell levelCell = newRow.createCell(4);
            levelCell.setCellValue(level);

            Cell timeCell = newRow.createCell(5);
            timeCell.setCellValue(time);

            Cell sumCell = newRow.createCell(6);
            sumCell.setCellValue(sum);

            Cell phoneCell = newRow.createCell(7);
            phoneCell.setCellValue(phone);

            Cell emailCell = newRow.createCell(8);
            emailCell.setCellValue(email);

            try (FileOutputStream fos = new FileOutputStream("users.xlsx")) {
                workbook.write(fos);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing the workbook.");
            e.printStackTrace();
        }
    }

    public void addManager(String account,String password){//将管理员数据写入文件
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        File file = new File("users.xlsx");
        try (InputStream is = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet("Users");
            if(sheet==null)
                sheet=workbook.createSheet("Users");
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);

            Cell accountCell = newRow.createCell(0);
            accountCell.setCellValue(account);

            Cell passwordCell = newRow.createCell(1);
            passwordCell.setCellValue(password);

            Cell typeCell = newRow.createCell(2);
            typeCell.setCellValue("1");

            Cell idCell = newRow.createCell(3);
            idCell.setCellValue(Integer.toString(UserFunction.RecNum++));

            Cell levelCell = newRow.createCell(4);
            levelCell.setCellValue("0");

            Cell timeCell = newRow.createCell(5);
            timeCell.setCellValue(formattedDateTime);

            Cell sumCell = newRow.createCell(6);
            sumCell.setCellValue("0");

            Cell phoneCell = newRow.createCell(7);
            phoneCell.setCellValue("13379834673");

            Cell emailCell = newRow.createCell(8);
            emailCell.setCellValue("676864824@qq.com");

            try (FileOutputStream fos = new FileOutputStream("users.xlsx")) {
                workbook.write(fos);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing the workbook.");
            e.printStackTrace();
        }
    }

    public boolean judgeExistUsers(String account){//判断账户是否存在,存在返回false，不存在返回true
        String line=getLineUsers(account);
        if(line==null)
            return true;
        return false;
    }

    public void writeCounter(int count){
        String data = Integer.toString(count);
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Counter");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue(data);

            try (FileOutputStream fos = new FileOutputStream("counter.xlsx")) {
                workbook.write(fos);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the workbook.");
            e.printStackTrace();
        }
    }
    public void modifyPassword(String account,String password){
        File file = new File("users.xlsx");
        try (InputStream is = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet("Users");
            if (sheet != null) {
                int lastRowNum = sheet.getLastRowNum();
                for (int i = 0; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        Cell accountCell = row.getCell(0);
                        if (accountCell != null && account.equals(accountCell.getStringCellValue())) {
                            Cell passwordCell = row.getCell(1);
                            passwordCell.setCellValue(password);
                            break;
                        }
                    }
                }
            }
            try (FileOutputStream fos = new FileOutputStream("users.xlsx")) {
                workbook.write(fos);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing the workbook.");
            e.printStackTrace();
        }
    }

    public void deleteAccountUsers(String account){//删除用户
        File file = new File("users.xlsx");
        try (InputStream is = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet("Users");
            if (sheet != null) {
                int lastRowNum = sheet.getLastRowNum();
                for (int i = 0; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        Cell accountCell = row.getCell(0);
                        if (accountCell != null && account.equals(accountCell.getStringCellValue())) {
                            sheet.removeRow(row);
                            break;
                        }
                    }
                }
            }
            try (FileOutputStream fos = new FileOutputStream("users.xlsx")) {
                workbook.write(fos);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing the workbook.");
            e.printStackTrace();
        }
    }

    public void modifyLevel(String account,int level){
        String line=getLineUsers(account);
        deleteAccountUsers(account);
        String[] parts=line.split("\\|");
        writeAccount(account,parts[1],parts[3],Integer.toString(level),parts[5],parts[6],parts[7],parts[8]);
    }

    public void modifySum(String account,float sum){
        String line=getLineUsers(account);
        deleteAccountUsers(account);
        String[] parts=line.split("\\|");
        writeAccount(account,parts[1],parts[3],parts[4],parts[5],Float.toString(sum),parts[7],parts[8]);
    }
    //商品文件----------------------------------------
    public void writeCommodity(String name,double price,String factory,String time,int num){//将商品数据写入文件
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        File file = new File("commodity.xlsx");
        try (InputStream is = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet("Commodity");
            if(sheet==null)
                sheet=workbook.createSheet("Commodity");
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);

            Cell accountCell = newRow.createCell(0);
            accountCell.setCellValue(name);

            Cell passwordCell = newRow.createCell(1);
            passwordCell.setCellValue(Double.toString(price));

            Cell typeCell = newRow.createCell(2);
            typeCell.setCellValue(factory);

            Cell idCell = newRow.createCell(3);
            idCell.setCellValue(time);

            Cell levelCell = newRow.createCell(4);
            levelCell.setCellValue(Integer.toString(num));
            try (FileOutputStream fos = new FileOutputStream("commodity.xlsx")) {
                workbook.write(fos);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing the workbook.");
            e.printStackTrace();
        }
    }

    public String getLineCommodity(String name){//返回name当前行的所有数据
        String line = null;
        boolean judge = true;
        File file = new File("commodity.xlsx");
        if (file.length() == 0) {
            return null;
        }
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream("commodity.xlsx"));
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                line = "";
                for (Cell cell : row) {
                    line += cell.getStringCellValue() + "|";
                }
                String[] parts = line.split("\\|");
                if (parts.length > 0 && !parts[0].equals(name))
                    judge = false;
                if (judge) {
                    return line;
                }
                judge = true;
            }
            workbook.close(); // 关闭Workbook对象
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        return null;
    }

    public void modifyCommodity(String name,float price,String factory,String time,int num){
        File file = new File("commodity.xlsx");
        try (InputStream is = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet("Commodity");
            if (sheet != null) {
                int lastRowNum = sheet.getLastRowNum();
                for (int i = 0; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        Cell nameCell = row.getCell(0);
                        if (nameCell != null && name.equals(nameCell.getStringCellValue())) {
                            Cell priceCell = row.getCell(1);
                            priceCell.setCellValue(Float.toString(price));
                            Cell factoryCell = row.getCell(2);
                            factoryCell.setCellValue(factory);
                            Cell timeCell = row.getCell(3);
                            timeCell.setCellValue(time);
                            Cell numCell = row.getCell(4);
                            numCell.setCellValue(Integer.toString(num));
                            break;
                        }
                    }
                }
            }
            try (FileOutputStream fos = new FileOutputStream("commodity.xlsx")) {
                workbook.write(fos);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing the workbook.");
            e.printStackTrace();
        }
    }

    public void deleteCommodity(String name){//删除商品
        File file = new File("commodity.xlsx");
        try (InputStream is = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet("Commodity");
            if (sheet != null) {
                int lastRowNum = sheet.getLastRowNum();
                for (int i = 0; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        Cell nameCell = row.getCell(0);
                        if (nameCell != null && name.equals(nameCell.getStringCellValue())) {
                            sheet.removeRow(row);
                            break;
                        }
                    }
                }
            }
            try (FileOutputStream fos = new FileOutputStream("commodity.xlsx")) {
                workbook.write(fos);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing the workbook.");
            e.printStackTrace();
        }
    }

    public void modifyCommodityNum(String name,int num){
        String line=getLineCommodity(name);
        String[] parts=line.split("\\|");
        modifyCommodity(parts[0],Float.parseFloat(parts[1]),parts[2],parts[3],num);
    }
    //shoppingHistroy---------------------------
    public void writeShoppingHistory(String account,String goods,String time){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        File file = new File("shoppingHistory.xlsx");
        try (InputStream is = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet("ShoppingHistory");
            if(sheet==null)
                sheet=workbook.createSheet("ShoppingHistory");
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);

            Cell accountCell = newRow.createCell(0);
            accountCell.setCellValue(account);

            Cell passwordCell = newRow.createCell(1);
            passwordCell.setCellValue(goods);

            Cell typeCell = newRow.createCell(2);
            typeCell.setCellValue(time);
            try (FileOutputStream fos = new FileOutputStream("shoppingHistory.xlsx")) {
                workbook.write(fos);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing the workbook.");
            e.printStackTrace();
        }
    }

    public List<String> getLinesShoppingHistory(String account){
        List<String> lines=new ArrayList<>();
        String line = null;
        boolean judge = true;
        File file = new File("shoppingHistory.xlsx");
        if (file.length() == 0) {
            return null;
        }
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream("shoppingHistory.xlsx"));
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                line = "";
                for (Cell cell : row) {
                    line += cell.getStringCellValue() + "|";
                }
                String[] parts = line.split("\\|");
                if (parts.length > 0 && !parts[0].equals(account))
                    judge = false;
                if (judge) {
                    lines.add(line);
                }
                judge = true;
            }
            workbook.close(); // 关闭Workbook对象
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        return lines;
    }
    //shoppingCart-------------------------------
    public void writeShoppingCart(String account,String name,float price){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        File file = new File("shoppingCart.xlsx");
        try (InputStream is = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet("ShoppingCart");
            if(sheet==null)
                sheet=workbook.createSheet("ShoppingCart");
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);

            Cell accountCell = newRow.createCell(0);
            accountCell.setCellValue(account);

            Cell passwordCell = newRow.createCell(1);
            passwordCell.setCellValue(name);

            Cell typeCell = newRow.createCell(2);
            typeCell.setCellValue(Float.toString(price));
            try (FileOutputStream fos = new FileOutputStream("shoppingCart.xlsx")) {
                workbook.write(fos);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing the workbook.");
            e.printStackTrace();
        }
    }

    public List<String> getLinesShoppingCart(String account){
        List<String> lines=new ArrayList<>();
        String line = null;
        boolean judge = true;
        File file = new File("shoppingCart.xlsx");
        if (file.length() == 0) {
            return null;
        }
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream("shoppingCart.xlsx"));
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                line = "";
                for (Cell cell : row) {
                    line += cell.getStringCellValue() + "|";
                }
                String[] parts = line.split("\\|");
                if (parts.length > 0 && !parts[0].equals(account))
                    judge = false;
                if (judge) {
                    lines.add(line);
                }
                judge = true;
            }
            workbook.close(); // 关闭Workbook对象
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        return lines;
    }

    public void deleteShoppingCart(String account,String goods){//删除商品
        File file = new File("shoppingCart.xlsx");
        try (InputStream is = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet("ShoppingCart");
            if (sheet != null) {
                int lastRowNum = sheet.getLastRowNum();
                for (int i = 0; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        Cell accountCell = row.getCell(0);
                        Cell nameCell=row.getCell(1);
                        if (accountCell != null && account.equals(accountCell.getStringCellValue())&&judgeGoodsAndName(goods,nameCell.getStringCellValue())) {
                            sheet.removeRow(row);
                        }
                    }
                }
            }
            try (FileOutputStream fos = new FileOutputStream("shoppingCart.xlsx")) {
                workbook.write(fos);
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing the workbook.");
            e.printStackTrace();
        }
    }

    public boolean judgeGoodsAndName(String goods,String name){//name若有goods中一个商品同名就返回true
        String[] productList = goods.split(" ");
        for(int i=0;i<productList.length;i++){
            if(name.equals(productList[i]))
                return true;
        }
        return false;
    }

    public boolean judgeExistShoppingCart(String name){
        ExcleOp excleOp=new ExcleOp();
        boolean judge=false;
        List<String> linesList = excleOp.getLinesShoppingCart(UserFunction.account);
        String[] lines = linesList.toArray(new String[linesList.size()]);
        for(int i=0;i<lines.length;i++){
            String[] parts=lines[i].split("\\|");
            if(parts[1].equals(name)){
                judge=true;
                break;
            }
        }
        return judge;
    }
}

