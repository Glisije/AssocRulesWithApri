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

    private void findeDoubleItemSupport() {
        //Массив с временными данными
        ArrayList<Item> tmp = new ArrayList<>();
        for (int i = 0; i < uniqueItems.size(); i++) {
            ArrayList<Item> items = new ArrayList<>();
            //Проверка на первое добавление в items
            if(i==0) {
                items.add(uniqueItems.get(i));
            }//Если не первый раз добавляем, то очищаем первый элемент и добавляем в начало следующий
            else {
                items.clear();
                items.add(uniqueItems.get(i));
            }
            for (int j = i; j < uniqueItems.size(); j++) {
                if (j > i) {
                    items.add(uniqueItems.get(j));
                    //добавляем в временный лист содержимое items
                    tmp.addAll(items);
                    itemSets.add(new ItemSet(tmp,i+j));
                    //обнуляем tmp
                    tmp = new ArrayList<>();
                    items.remove(1);

                }
            }
        }

        //Установление поддержки
        for (ItemSet itemSet : itemSets) {
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

    public void start(){
        Print.transactions(transactions);
        findSupport();
        Print.items(uniqueItems);
        deleteItemsWithoutMinSup();
        findeDoubleItemSupport();
        Print.itemSets(itemSets);
    }


}
