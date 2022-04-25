package com;

import org.apache.commons.codec.binary.StringUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Formatter;

public class Print {

    public static void itemSet(ItemSet items){
        System.out.print("Itemset: {");
        for (int i = 0; i < items.getItemSet().size(); i++) {
            if (i!= items.getItemSet().size()-1) System.out.print(items.getItemSet().get(i).getName()+", ");
            else System.out.print(items.getItemSet().get(i).getName());
        }
        System.out.print("}, supp = " + items.getSupp() + ";");

    }

    public static void itemSets(ArrayList<ItemSet> itemSetsArr){

        NumberFormat size = NumberFormat.getInstance();
        size.setMinimumIntegerDigits(3);
        size.setMaximumIntegerDigits(3);
        NumberFormat supp = NumberFormat.getInstance();
        supp.setMaximumFractionDigits(3);
        supp.setMinimumFractionDigits(3);


        System.out.print("\n----------------------------------------");
        System.out.print("\n|  Номер    |\tЭлементы   | Поддержка |");
        System.out.print("\n----------------------------------------\n");
        for (ItemSet itemSet : itemSetsArr) {
            System.out.print("|\t" + size.format(itemSet.getNum()) + "\t");
            for (int j = 0; j < itemSet.getItemSet().size(); j++) {
                if (j == 0)
                    System.out.print("\t| " + itemSet.getItemSet().get(j).getPrintName() + " |\t" + supp.format(itemSet.getSupp()) + "  |");
                else System.out.print("\n|\t\t\t| " + itemSet.getItemSet().get(j).getPrintName() + " |\t\t   |");
            }
            System.out.print("\n----------------------------------------\n");
        }
    }

    public static void item(Item item){
        System.out.print("Item: " + item.getName() + ", sup = " + item.getSupp() + "; ");
    }

    public static void items(ArrayList<Item> itemsArr){
        NumberFormat size = NumberFormat.getInstance();
        size.setMinimumIntegerDigits(3);
        size.setMaximumIntegerDigits(3);
        NumberFormat supp = NumberFormat.getInstance();
        supp.setMaximumFractionDigits(3);
        supp.setMinimumFractionDigits(3);


        System.out.print("\n----------------------------------------");
        System.out.print("\n|  Номер    |\tНазвание   | Поддержка |");
        System.out.print("\n----------------------------------------\n");
        for (Item item : itemsArr) {
            System.out.print("|\t" + size.format(item.getNum()) + "\t");
            System.out.print("\t| " + item.getPrintName() + " |\t" + size.format(item.getSupp()) + "\t   |");
            System.out.print("\n----------------------------------------\n");
        }
    }

    public static void transaction(Transaction trans){
        System.out.print("Transaction №" + trans.getNum() + " : {");
        for (int i = 0; i < trans.getNumOfModels(); i++) {
            if (i!= trans.getNumOfModels()-1) System.out.print(trans.getModel(i).getName()+", ");
            else System.out.print(trans.getModel(i).getName()+"};");
        }
    }

    public static void transactions(ArrayList<Transaction> transArr){
        NumberFormat size = NumberFormat.getInstance();
        size.setMinimumIntegerDigits(3);
        size.setMaximumIntegerDigits(3);
        NumberFormat supp = NumberFormat.getInstance();
        supp.setMaximumFractionDigits(3);
        supp.setMinimumFractionDigits(3);


        System.out.print("\n----------------------------");
        System.out.print("\n|  Номер    |\tЭлементы   |");
        System.out.print("\n----------------------------\n");
        for (Transaction transaction : transArr) {
            System.out.print("|\t" + size.format(transaction.getNumOfModels()) + "\t");
            for (int j = 0; j < transaction.getNumOfModels(); j++) {
                if (j == 0)
                    System.out.print("\t| " + transaction.getModel(j).getPrintName() + " |\n");
                else System.out.print("|\t\t\t| " + transaction.getModel(j).getPrintName() + " |\n");
            }
            System.out.print("----------------------------\n");
        }
    }

}

