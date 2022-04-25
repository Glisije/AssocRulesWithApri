package com;

//Класс элемента (модели)
public class Item {

    //Тип
    private String name;
    //Имя для печати
    private String printName;
    //Поддержка
    private double supp;
    //Номер
    private int num;

    Item(String name){
        this.name = name;
        this.supp = 0;
        this.printName = name;
        while (printName.length() != "Architecture".length()){
            printName+=" ";
        }
    }

    Item(String name, int num){
        this.name = name;
        this.supp = 0;
        this.printName = name;
        while (printName.length() != "Architecture".length()){
            printName+=" ";
        }
        this.num = num;
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

    public String getPrintName() {
        return printName;
    }

    public int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "com.Item{" +
                "name='" + name + '\'' +
                ", supp=" + supp +
                '}';
    }
}
