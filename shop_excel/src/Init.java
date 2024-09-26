import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Init {

    private String counterFile = "counter.xlsx";
    private String usersFile = "users.xlsx";
    private String commodityFile = "commodity.xlsx";
    private String shoppingHistoryFile = "shoppingHistory.xlsx";
    private String shoppingCartFile = "shoppingCart.xlsx";
    public void checkAndCreateFiles() {
        String[] name={"Counter","Users","Commodity","ShoppingHistory","ShoppingCart"};
        String[] files = {counterFile, usersFile, commodityFile, shoppingHistoryFile, shoppingCartFile};

        for (int i=0;i<5;i++) {
            File f = new File(files[i]);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                    try (Workbook workbook = new XSSFWorkbook()) {
                        Sheet sheet = workbook.createSheet(name[i]);
                        try (FileOutputStream fos = new FileOutputStream(files[i])) {
                            workbook.write(fos);
                        } catch (IOException e) {
                            System.out.println("An error occurred while writing to the file.");
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred while creating the workbook.");
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
            }
        }
    }
    public void initialization(){
        checkAndCreateFiles();
        UserFunction userFunction=new UserFunction();
        ExcleOp excleOp =new ExcleOp();
        String line= excleOp.getLineUsers("admin");
        if(line==null){
            excleOp.addManager("admin","ynuinfo#777");
            excleOp.writeCounter(2);
        }
        try (Workbook workbook = new XSSFWorkbook(new File(counterFile))) {
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(0);
            Cell cell = row.getCell(0);

            if(cell.getCellType() == CellType.STRING) {
                String counterStr = cell.getStringCellValue();
                int counter = Integer.parseInt(counterStr);
                userFunction.RecNum = counter;
            } else if (cell.getCellType() == CellType.NUMERIC) {
                int counter = (int) cell.getNumericCellValue();
                userFunction.RecNum = counter;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
