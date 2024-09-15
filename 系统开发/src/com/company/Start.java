package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Start {

    public void start(){

        Scanner input = new Scanner(System.in);

        User user=new User();
        Administrator administrator=new Administrator();

        boolean mis = true;


        while (mis) {
            try {
                System.out.println("***************START***************");
                System.out.print("请选择身份————1.管理员 2.用户  (按‘0’退出系统)>");
                int choose = input.nextInt();

                if (choose == 1) {
                    administrator.administratorLogin();
                    mis = false;
                } else if (choose == 2) {
                    user.userLogin();
                    mis = false;
                } else if (choose == 0) {
                    System.exit(1);
                } else {
                    System.out.println("输入有误，请重新输入");
                }
            } catch (InputMismatchException e) {
                System.out.println("输入有误，请重新输入");
                input.nextLine(); // 清空输入缓冲区
            }
        }


    }



}
