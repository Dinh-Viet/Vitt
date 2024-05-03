package com.example.Employee.Entity;

public class Employee {
    private int id;
    private String cmnd;
    private String name;
    private String bird;
    private String address;
    private String position;
    private String work_room;

    public Employee() {}

    public Employee(String cmnd, String name, String bird, String address, String position, String work_room) {
        this.cmnd = cmnd;
        this.name = name;
        this.bird = bird;
        this.address = address;
        this.position = position;
        this.work_room = work_room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBird() {
        return bird;
    }

    public void setBird(String bird) {
        this.bird = bird;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWork_room() {
        return work_room;
    }

    public void setWork_room(String work_room) {
        this.work_room = work_room;
    }
}