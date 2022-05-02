package com;

import java.util.ArrayList;

public class Rules {

    private ItemSet itemSet;
    private ArrayList<Item> listOfItems;
    private ItemSet first;
    private ItemSet second;
    private boolean selected;
    private double confidence;


    Rules (){
    }

    public void setItemSet(ItemSet itemSet) {
        this.itemSet = itemSet;
    }

    public void setListOfItems(ArrayList<Item> listOfItems) {
        this.listOfItems = listOfItems;
    }

    public void setFirst(ItemSet first) {
        this.first = first;
    }

    public void setSecond(ItemSet second) {
        this.second = second;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public ArrayList<Item> getListOfItems() {
        return listOfItems;
    }

    public ItemSet getItemSet() {
        return itemSet;
    }

    public ItemSet getFirst() {
        return first;
    }

    public Item getFirstItem(){ return first.getItemSet().get(0); }

    public ItemSet getSecond() {
        return second;
    }

    public boolean getSelected(){
        return selected;
    }

    public double getConfidence() {
        return confidence;
    }

    @Override
    public String toString() {
        return "Rules{" +
                ", first=" + first +
                ", second=" + second +
                ", selected=" + selected +
                ", confidence=" + confidence +
                '}';
    }
}
