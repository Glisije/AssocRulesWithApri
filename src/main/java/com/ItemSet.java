package com;

import java.util.ArrayList;

public class ItemSet{

    private final ArrayList<Item> items;

    ItemSet(ArrayList<Item> items){
        this.items = items;
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
