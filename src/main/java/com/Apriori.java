package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static com.Parser.*;

//Класс релизующий Априори
public class Apriori {

    //Список уникальных элементов
    private final ArrayList<Item> uniqueItems;
    //Список всех транзакций
    private final ArrayList<Transaction> transactions;
    //Список всех ункиальных элементов, проходящих минимальную поддержку
    private final ArrayList<Item> uniqueItemsWithMinSup;
    //Сприсок всех ItemSets
    private final ArrayList<ItemSet> itemSets = new ArrayList<>();
    //Минимальная поддержка
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
        //дергаем первый элемент
        for (int i = 0; i < uniqueItems.size(); i++){
            ItemSet tmpSet = new ItemSet();
//          ArrayList <Item> items = new ArrayList<>();
//          items.add(uniqueItems.get(i));
            tmpSet.addItem(uniqueItems.get(i));
            //дергаем второй элемент
            for (int j = i; j < uniqueItems.size(); j++){
                //Item tmpItem = uniqueItems.get(j);
                //Проверка на повторы
                if (j > i){
                    //items.add(tmpItem);
                    tmpSet.addItem(uniqueItems.get(j));
                    //itemSets.add(new ItemSet(items));
                    itemSets.add(tmpSet);
                    tmpSet.removeLast();

                }
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
