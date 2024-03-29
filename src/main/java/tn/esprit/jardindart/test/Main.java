package tn.esprit.jardindart.test;

import tn.esprit.jardindart.utils.MyDataBase;

public class Main {
    public static void main(String[] args){

        MyDataBase db=MyDataBase.getInstance();
        MyDataBase db1=MyDataBase.getInstance();
        System.out.println(db);
        System.out.println(db1);
    }
}
