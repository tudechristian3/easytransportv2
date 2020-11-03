package com.example.easytransportation;

public class vehicleServiceList {

    private String image,id,name,price,kilogram;

    public vehicleServiceList(String image, String id, String name, String price, String kilogram){
        super();
        this.image = image;
        this.id = id;
        this.name = name;
        this.price = price;
        this.kilogram = kilogram;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getKilogram() {
        return kilogram;
    }

    public void setKilogram(String kilogram) {
        this.kilogram = kilogram;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
