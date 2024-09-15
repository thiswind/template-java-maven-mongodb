package com.company;


import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
	// write your code here
        SqLite mysql=new SqLite();
        mysql.create();
        Start s=new Start();
        s.start();

    }

}
