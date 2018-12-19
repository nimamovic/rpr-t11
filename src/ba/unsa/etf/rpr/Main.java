package ba.unsa.etf.rpr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Gradovi su:\n" + ispisiGradove());
        glavniGrad();

    }
    private static void glavniGrad() {
        System.out.println("Unesite naziv drzave: ");
        Scanner sc = new Scanner(System.in);
        String ime = sc.nextLine();

    }

    public static String ispisiGradove() {
        return "";
    }
}
