package com;

import java.util.ArrayList;

public class ItemSet{

    private ArrayList<Item> items;
    //Поддержка
    private double supp;
    //Номер
    private int num;
    //ПодСеты
    private ArrayList<ItemSet> subItemsSets = new ArrayList<>();



    ItemSet(ArrayList<Item> items, int num){
        this.items = items;
        supp = 0;
        this.num = num;
    }

    ItemSet(){
        this.items = new ArrayList<>();
        supp = 0;
    }

    ItemSet(ItemSet itemSet){
        this.items = itemSet.getItemSet();
        this.supp = itemSet.getSupp();
        this.subItemsSets = itemSet.getSubItemsSets();
    }

    public void plusSup() {
        this.supp++;
    }

    public double getSupp() {
        return supp;
    }

    public void removeLast(){
        this.items.remove(items.size()-1);
    }

    public ArrayList<Item> getItemSet() {
        return items;
    }

    public void setItemSet(ArrayList<Item> itemSet) {
        itemSet = itemSet;
    }

    public void addItem(Item item){items.add(item);}

    public int getNum() {
        return num;
    }

    public void addSubItems(ItemSet subItemsSet){
        subItemsSets.add(subItemsSet);
    }

    public ArrayList<ItemSet> getSubItemsSets() {
        return subItemsSets;
    }

    public void setSupp(double supp) {
        this.supp = supp;
    }

    @Override
    public String toString() {
        return "ItemSet{" +
                "items=" + items +
                "supp=" + supp +
                '}';
    }
}
