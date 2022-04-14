package com;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        String path = "src/main/resources/transations.xlsx";
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите минимальную поддержку: ");
        int minSup = sc.nextInt();

        Apriori apri = new Apriori(path, minSup);
        apri.start();


    }

}
