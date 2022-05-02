package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

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
    //Список Правил
    private final ArrayList<Rules> rules = new ArrayList<>();
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
        for (ItemSet itemSet3 : itemSets3WithMinSup) {

            //Добавляем элементы в subItemSet
            ItemSet tmp = new ItemSet();
            tmp.addItem(itemSet3.getItemSet().get(0));
            tmp.addItem(itemSet3.getItemSet().get(1));
            //Сравниваем уже имеющиеся ItemSet со временным и если они сходятся, то мы приравиниваем их
            for (ItemSet itemSet2 : itemSets2){
                int tmpInt = 0;
                for (Item subItem : tmp.getItemSet()){
                   for (Item item : itemSet2.getItemSet()){
                       if (Objects.equals(subItem.getName(), item.getName())){
                           tmpInt ++;
                       }
                   }
                }
                if (tmpInt == 2) {
                    //tmp.setSupp(itemSet2.getSupp());
                    tmp = itemSet2;
                    break;
                }
            }
            itemSet3.getSubItemsSets().add(tmp);

            ItemSet tmp2 = new ItemSet();
            tmp2.addItem(itemSet3.getItemSet().get(0));
            tmp2.addItem(itemSet3.getItemSet().get(2));
            //Сравниваем уже имеющиеся ItemSet со временным и если они сходятся, то мы приравиниваем их
            for (ItemSet itemSet2 : itemSets2){
                int tmpInt = 0;
                for (Item subItem : tmp2.getItemSet()){
                    for (Item item : itemSet2.getItemSet()){
                        if (Objects.equals(subItem.getName(), item.getName())){
                            tmpInt ++;
                        }
                    }
                }
                if (tmpInt == 2) {
                    //tmp.setSupp(itemSet2.getSupp());
                    tmp2 = itemSet2;
                    break;
                }
            }
            itemSet3.getSubItemsSets().add(tmp2);

            ItemSet tmp3 = new ItemSet();
            tmp3.addItem(itemSet3.getItemSet().get(1));
            tmp3.addItem(itemSet3.getItemSet().get(2));
            //Сравниваем уже имеющиеся ItemSet со временным и если они сходятся, то мы приравиниваем их
            for (ItemSet itemSet2 : itemSets2){
                int tmpInt = 0;
                for (Item subItem : tmp3.getItemSet()){
                    for (Item item : itemSet2.getItemSet()){
                        if (Objects.equals(subItem.getName(), item.getName())){
                            tmpInt ++;
                        }
                    }
                }
                if (tmpInt == 2) {
                    //tmp.setSupp(itemSet2.getSupp());
                    tmp3 = itemSet2;
                    break;
                }
            }
            itemSet3.getSubItemsSets().add(tmp3);

            itemSet3.getSubItemsSets().add(itemToItemSet(itemSet3.getItemSet().get(0)));
            for (Item element : uniqueItems) {
                if (Objects.equals(element.getName(), itemSet3.getSubItemsSets().get(3).getItemSet().get(0).getName())) {
                    itemSet3.getSubItemsSets().get(3).setSupp(element.getSupp());
                    break;
                }
            }


            itemSet3.getSubItemsSets().add(itemToItemSet(itemSet3.getItemSet().get(1)));
            for (Item element : uniqueItems) {
                if (Objects.equals(element.getName(), itemSet3.getSubItemsSets().get(4).getItemSet().get(0).getName())) {
                    itemSet3.getSubItemsSets().get(4).setSupp(element.getSupp());
                    break;
                }

            }


            itemSet3.getSubItemsSets().add(itemToItemSet(itemSet3.getItemSet().get(2)));
            for (Item element : uniqueItems) {
                if (Objects.equals(element.getName(), itemSet3.getSubItemsSets().get(5).getItemSet().get(0).getName())) {
                    itemSet3.getSubItemsSets().get(5).setSupp(element.getSupp());
                    break;
                }

            }

        }
    }


    //Не работает
    //Генерация правил
    public void rulesGeneration(){
        //Для каждого ItemSet3, прошедшего мин поддержку
        ArrayList <ItemSet> itemSets3WithMinSupCLONE = new ArrayList<>(itemSets3WithMinSup.size());
        for (ItemSet itemSet : itemSets3WithMinSup) itemSets3WithMinSupCLONE.add(new ItemSet(itemSet));

        for (ItemSet mainItemset : itemSets3WithMinSupCLONE){
            ItemSet tmp = mainItemset;
            //Для каждого подсета
            for (ItemSet subItemSet : mainItemset.getSubItemsSets()){
                Rules rule = new Rules();
                tmp.getItemSet().clear();
                //Если подсет размером с один эелемент
                if (subItemSet.getItemSet().size() == 1){
                    rule.setFirst(subItemSet);
                    double Confidence = 0;

                    rule.setSecond(tmp);
                    rule.getSecond().getItemSet().remove(rule.getFirstItem());


                    Confidence = rule.getFirst().getSupp()/ rule.getSecond().getSupp();
                    rule.setConfidence(Confidence);

                    rule.setSelected(Confidence > 0.60);
                    rules.add(rule);
                }
                //иначе может быть только когда в подсете два элемента!
                else {}
            }
        }
    }

    //Работа с правилами
    public void rulesWork(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите элемент для которого хотите найти правила ( ");
        for (Item element : uniqueItems){
            System.out.print(element.getName()+" ");
        }
        System.out.print("): \n");
        String itemName = sc.nextLine();
        Item item = null;
        for (Item element : uniqueItems){
            if (Objects.equals(element.getName(), itemName)){
                item = element;
                break;
            }
        }
        if (item == null){
            System.out.println("Вы ошиблись при вводе :(");
            rulesWork();
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
        rulesGeneration();
        rulesWork();

    }


}
