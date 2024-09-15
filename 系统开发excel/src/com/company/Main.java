package com.company;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;






import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
        // write your code here

        Excel f=new Excel();
        f.create();


        Start s=new Start();
        s.start();

    }

}
