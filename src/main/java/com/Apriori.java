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
    private final ArrayList<Item> uniqueItemsWithMinSup  = new ArrayList<>();
    //Сприсок всех ItemSets состоящих из двух элементов
    private final ArrayList<ItemSet> itemSets2 = new ArrayList<>();
    //Список ItemSet из двух элементов, прошедших минимальную поддержку
    private final ArrayList<ItemSet> itemSets2WithMinSup = new ArrayList<>();
    //Сприсок всех ItemSets состоящих из трех элементов
    private final ArrayList<ItemSet> itemSets3 = new ArrayList<>();
    //Список ItemSet из трех элементов, прошедших минимальную поддержку
    private final ArrayList<ItemSet> itemSets3WithMinSup = new ArrayList<>();
    //Минимальная поддержка
    private final int minSup;

    Apriori(String path, int minSup) throws IOException {
        this.minSup = minSup;
        this.uniqueItems = getUniqueItems(path);
        this.transactions = getTransactions(path, this.uniqueItems);
    }

    public ItemSet itemToItemSet(Item item){
        ItemSet itemSet = new ItemSet();
        itemSet.addItem(item);
        return itemSet;
    }

    //Поиск поддержки элементов
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

    //Удаление элементов, не проходящих минимальную поддержку
    private void deleteItemsWithoutMinSup() {
        for (Item uniqueItem : this.uniqueItems) {
            if (uniqueItem.getSupp() >= this.minSup) {
                this.uniqueItemsWithMinSup.add(uniqueItem);
            }
        }
    }

    //Поиск поддержки ItemSet сосотящих из двух элементов
    private void findDoubleItemSupport() {
        //Массив с временными данными
        ArrayList<Item> tmp = new ArrayList<>();
        for (int i = 0; i < uniqueItemsWithMinSup.size(); i++) {
            ArrayList<Item> items = new ArrayList<>();
            //Проверка на первое добавление в items
            if(i==0) {
                items.add(uniqueItemsWithMinSup.get(i));
            }//Если не первый раз добавляем, то очищаем первый элемент и добавляем в начало следующий
            else {
                items.clear();
                items.add(uniqueItemsWithMinSup.get(i));
            }
            for (int j = i; j < uniqueItemsWithMinSup.size(); j++) {
                if (j > i) {
                    items.add(uniqueItemsWithMinSup.get(j));
                    //добавляем в временный лист содержимое items
                    tmp.addAll(items);
                    itemSets2.add(new ItemSet(tmp,i+j));
                    //обнуляем tmp
                    tmp = new ArrayList<>();
                    items.remove(1);
                }
            }
        }

        //Установление поддержки
        for (ItemSet itemSet : itemSets2) {
            //Смотрим каждую траназакцию для каждого ItemSet
            for (Transaction transaction : transactions) {
                //Смотрим каждый элемент транзакции
                for (int i = 0; i < transaction.getNumOfModels(); i++) {
                    //Если первый элемент ItemSet совпадает, проверяем второй
                    if (Objects.equals(itemSet.getItemSet().get(0).getName(), transaction.getModels().get(i).getName())){
                        for (int j = 0; j < transaction.getNumOfModels(); j++) {
                            if (Objects.equals(itemSet.getItemSet().get(1).getName(), transaction.getModels().get(j).getName())){
                                itemSet.plusSup();
                            }
                        }
                    }
                }
            }
        }
    }

    //Удаление ItemsSet сосотящих из двух элементов, не проходящих минимальную поддержку
    private void deleteItemSets2WithoutMinSup() {
        for (ItemSet itemSet : this.itemSets2) {
            if (itemSet.getSupp() >= this.minSup) {
                this.itemSets2WithMinSup.add(itemSet);
            }
        }
    }

    //Поиск поддержки для ItemSet из трех элеметов
    private void findTripleItemSupport() {
        //Массив с временными данными
        ArrayList<Item> tmp = new ArrayList<>();
        for (int i = 0; i < uniqueItemsWithMinSup.size(); i++) {
            ArrayList<Item> items = new ArrayList<>();
            //Проверка на первое добавление в items
            if(i==0) {
                items.add(uniqueItemsWithMinSup.get(i));
            }//Если не первый раз добавляем, то очищаем первый элемент и добавляем в начало следующий
            else {
                items.clear();
                items.add(uniqueItemsWithMinSup.get(i));
            }
            for (int j = i; j < uniqueItemsWithMinSup.size(); j++) {
                if (j > i) {
                    items.add(uniqueItemsWithMinSup.get(j));
                    for (int k = j; k < uniqueItemsWithMinSup.size(); k++) {
                        if (k > j) {
                            items.add(uniqueItemsWithMinSup.get(k));
                            //добавляем в временный лист содержимое items
                            tmp.addAll(items);
                            itemSets3.add(new ItemSet(tmp,i+j));
                            //обнуляем tmp
                            tmp = new ArrayList<>();
                            items.remove(2);
                        }
                    }
                    items.remove(1);
                }
            }
        }

        //Установление поддержки
        for (ItemSet itemSet : itemSets3) {
            //Смотрим каждую траназакцию для каждого ItemSet
            for (Transaction transaction : transactions) {
                //Смотрим каждый элемент транзакции
                for (int i = 0; i < transaction.getNumOfModels(); i++) {
                    //Если первый элемент ItemSet совпадает, проверяем второй
                    if (Objects.equals(itemSet.getItemSet().get(0).getName(), transaction.getModels().get(i).getName())){
                        for (int j = 0; j < transaction.getNumOfModels(); j++) {
                            //Если второй элемент ItemSet совпадает, проверяем третий
                            if (Objects.equals(itemSet.getItemSet().get(1).getName(), transaction.getModels().get(j).getName())){
                                for (int k = 0; k < transaction.getNumOfModels(); k++) {
                                    if (Objects.equals(itemSet.getItemSet().get(2).getName(), transaction.getModels().get(k).getName())){
                                        itemSet.plusSup();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //Удаление ItemsSet сосотящих из трех элементов, не проходящих минимальную поддержку
    private void deleteItemSets3WithoutMinSup() {
        for (ItemSet itemSet : this.itemSets3) {
            if (itemSet.getSupp() >= this.minSup) {
                this.itemSets3WithMinSup.add(itemSet);
            }
        }
    }

    //Добавление подсетов для ItemSet из трех элементов
    private void subSetAdd(){

        //Для каждого ItemSet из трех элементов, прошедшего minSup
        for (ItemSet itemSet : itemSets3WithMinSup) {

            ItemSet tmp = new ItemSet();
            tmp.addItem(itemSet.getItemSet().get(0));
            tmp.addItem(itemSet.getItemSet().get(1));
            itemSet.getSubItemsSets().add(tmp);

            ItemSet tmp2 = new ItemSet();
            tmp2.addItem(itemSet.getItemSet().get(0));
            tmp2.addItem(itemSet.getItemSet().get(2));
            itemSet.getSubItemsSets().add(tmp2);

            ItemSet tmp3 = new ItemSet();
            tmp3.addItem(itemSet.getItemSet().get(1));
            tmp3.addItem(itemSet.getItemSet().get(2));
            itemSet.getSubItemsSets().add(tmp3);

            itemSet.getSubItemsSets().add(itemToItemSet(itemSet.getItemSet().get(0)));
            itemSet.getSubItemsSets().add(itemToItemSet(itemSet.getItemSet().get(1)));
            itemSet.getSubItemsSets().add(itemToItemSet(itemSet.getItemSet().get(2)));

        }

    }

    public void start(){
        findSupport();
        deleteItemsWithoutMinSup();
        findDoubleItemSupport();
        deleteItemSets2WithoutMinSup();
        findTripleItemSupport();
        deleteItemSets3WithoutMinSup();
        subSetAdd();


    }


}
