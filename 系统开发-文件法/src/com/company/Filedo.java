package com.company;
import java.io.File;
import java.io.IOException;


public class Filedo {


    public void create(){

        String fileName = "user.txt";

        try {
            File file = new File(fileName);

            if (file.createNewFile()) {
                System.out.println("文件已创建: " + file.getAbsolutePath());
            } else {
                //System.out.println("文件已存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String fileName1 = "administrator.txt";

        try {
            File file = new File(fileName1);

            if (file.createNewFile()) {
                System.out.println("文件已创建: " + file.getAbsolutePath());
            } else {
               // System.out.println("文件已存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String fileName2 = "commodity.txt";

        try {
            File file = new File(fileName2);

            if (file.createNewFile()) {
                System.out.println("文件已创建: " + file.getAbsolutePath());
            } else {
               // System.out.println("文件已存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String fileName3 = "shopcart.txt";

        try {
            File file = new File(fileName3);

            if (file.createNewFile()) {
                System.out.println("文件已创建: " + file.getAbsolutePath());
            } else {
               // System.out.println("文件已存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileName4 = "history.txt";

        try {
            File file = new File(fileName4);

            if (file.createNewFile()) {
                System.out.println("文件已创建: " + file.getAbsolutePath());
            } else {
               // System.out.println("文件已存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }















    }










}




