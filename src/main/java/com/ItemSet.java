package com;

import java.util.ArrayList;

public class ItemSet{

    private ArrayList<Item> items;
    //Поддержка
    private double supp;
    //Номер
    private int num;



    ItemSet(ArrayList<Item> items, int num){
        this.items = items;
        supp = 0;
        this.num = num;
    }

    ItemSet(){
        this.items = new ArrayList<>();
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

    @Override
    public String toString() {
        return "ItemSet{" +
                "items=" + items +
                "supp=" + supp +
                '}';
    }
}
