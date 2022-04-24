package com;

//Класс элемента (модели)
public class Item {

    //Тип
    private String name;
    //Поддержка
    private double supp;

    Item(String name){
        this.name = name;
        this.supp = 0;
    }

    public String getName() {
        return name;
    }

    public double getSupp() {
        return supp;
    }

    public void setSupp(double supp) {
        this.supp = supp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void plusSup() {
        this.supp++;
    }

    @Override
    public String toString() {
        return "com.Item{" +
                "name='" + name + '\'' +
                ", supp=" + supp +
                '}';
    }
}
