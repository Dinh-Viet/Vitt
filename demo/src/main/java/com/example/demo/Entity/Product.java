package com.example.demo.Entity;

public class Product {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private Double price;

    public Product(){}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Product(Long id, String name, String phone, String address, Double price) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.price = price;
    }
}
