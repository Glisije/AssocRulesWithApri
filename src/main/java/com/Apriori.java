package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static com.Parser.*;

public class Apriori {

    private final ArrayList<Item> uniqueItems;
    private final ArrayList<Transaction> transactions;
    private final ArrayList<Item> uniqueItemsWithMinSup;
    private final ArrayList<ItemSet> itemSets = new ArrayList<>();
    private final int minSup;

    Apriori(String path, int minSup) throws IOException {
        this.minSup = minSup;
        this.uniqueItems = getUniqueItems(path);
        this.transactions = getTransactions(path, this.uniqueItems);
        this.uniqueItemsWithMinSup = new ArrayList<>();
    }

    private void deleteItemsWithoutMinSup() {
        for (Item uniqueItem : this.uniqueItems) {
            if (uniqueItem.getSupp() >= this.minSup) {
                this.uniqueItemsWithMinSup.add(uniqueItem);
            }
        }
    }

    private void findSupport(){
        for (Item uniqueItem : uniqueItems) {
            for (Transaction transaction : transactions) {
                for (int h = 0; h < transaction.getNumOfModels(); h++) {
                    if (Objects.equals(uniqueItem.getName(), transaction.getModels().get(h).getName())) {
                        uniqueItem.plusSup();
                    }
                }
            }
        }
    }

    //НЕ реализовано!
    private void findeDoubleItemSupport(){
        for (int i = 0; i < uniqueItems.size(); i++){
            ArrayList <Item> items = new ArrayList<>();
            items.add(uniqueItems.get(i));
            for (int j = i; j < uniqueItems.size(); j++){
                items.add(uniqueItems.get(j));
                itemSets.add(new ItemSet(items));
//                items.remove(1);
            }
        }
    }

    public void start(){
        System.out.println(uniqueItems);
        System.out.println(transactions);
        findSupport();
        System.out.println(uniqueItems);
        System.out.println(transactions);
        deleteItemsWithoutMinSup();
        findeDoubleItemSupport();
        System.out.println(itemSets);

    }


}
