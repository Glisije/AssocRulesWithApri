package com;

import java.util.ArrayList;

//Класс транзакции
public class Transaction {

    //Номер транзакции
    private int num;
    //Список моделей(элементов) в транзакции
    private ArrayList<Item> models;
    //Количество моделей(элементов)
    private int numOfModels;

    public Transaction(){
        models = new ArrayList<>();
        this.numOfModels = models.size();
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
