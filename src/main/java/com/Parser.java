package com;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Parser {

    public static ArrayList<Item> getUniqueItems(String file) throws IOException {
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet myExcelSheet = myExcelBook.getSheet("Transactions");
        ArrayList<String> allItems = new ArrayList<>();

        for (int i = 0; i < myExcelSheet.getLastRowNum(); i++){
            XSSFRow row = myExcelSheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++){
                allItems.add(row.getCell(j).getStringCellValue());
            }
        }

        Set<String> s = new LinkedHashSet<>(allItems);
        ArrayList<String> namesOfUniqueItems = new ArrayList<>(s);
        ArrayList<Item> uniqueItems =  new ArrayList<>();

        myExcelBook.close();

        for (String namesOfUniqueItem : namesOfUniqueItems) {
            uniqueItems.add(new Item(namesOfUniqueItem));
        }

        return uniqueItems;
    }

    public static ArrayList<Transaction> getTransactions(String file, ArrayList<Item> uniqueItems) throws IOException {
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet myExcelSheet = myExcelBook.getSheet("Transactions");
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i <= myExcelSheet.getLastRowNum(); i++){
            XSSFRow row = myExcelSheet.getRow(i);
            Transaction nTrans = new Transaction(i);
            for (int j = 0; j < row.getLastCellNum(); j++){
                for (int h = 0; h < uniqueItems.size(); h++) {
                    if (Objects.equals(uniqueItems.get(h).getName(), row.getCell(j).getStringCellValue()))
                    nTrans.addModel(uniqueItems.get(h));
                }
            }
            transactions.add(nTrans);
        }
        myExcelBook.close();

        return transactions;
    }

}
