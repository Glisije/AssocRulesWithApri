package com;

import java.util.ArrayList;

public class Transaction {

    private int num;
    private ArrayList<Item> models;
    private int numOfModels;

    public Transaction(){
        models = new ArrayList<>();
    }

    public Transaction (int num) {
        this.num = num;
        models = new ArrayList<>();
    }

    public void addModel(Item model){
        models.add(model);
        this.numOfModels = models.size();
    }

    public int getNum() {
        return num;
    }

    public ArrayList<Item> getModels(){
        return models;
    }

    public Item getModel(int i){
        return models.get(i);
    }

    public int getNumOfModels() {
        return numOfModels;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setModels(ArrayList<Item> models) {
        this.models = models;
        this.numOfModels = models.size();
    }

    public void setNumOfModels(int numOfModels) {
        this.numOfModels = numOfModels;
    }

    @Override
    public String toString() {
        return "com.Transaction{" +
                "num=" + num +
                ", models=" + models.toString() +
                ", numOfModels=" + numOfModels +
                '}';
    }
}
