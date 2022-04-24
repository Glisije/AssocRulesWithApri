package com;

import java.util.ArrayList;

public class ItemSet{

    private ArrayList<Item> items;

    ItemSet(ArrayList<Item> items){
        this.items = items;
    }

    ItemSet(){
        this.items = new ArrayList<>();
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

    @Override
    public String toString() {
        return "com.ItemSet{" +
                "items=" + items +
                '}';
    }
}
