package ru.nonamecompany.delivery.Model.Cart;

import com.google.firebase.Timestamp;

public class OrderInfo {
    private String user;

    private Timestamp orderTime;

    private String phone;
    private String deliveryTime;
    private String city;
    private String street;
    private String house;
    private String flat;
    private String comment;


    @Override
    public String toString() {
        return "OrderInfo{" +
                "user='" + user + '\'' +
                ", orderTime=" + orderTime +
                ", phone='" + phone + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", flat='" + flat + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public String getUser() {
        return user;
    }

    public OrderInfo setUser(String user) {
        this.user = user;
        return this;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public OrderInfo setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public OrderInfo setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public OrderInfo setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
        return this;
    }

    public String getCity() {
        return city;
    }

    public OrderInfo setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public OrderInfo setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getHouse() {
        return house;
    }

    public OrderInfo setHouse(String house) {
        this.house = house;
        return this;
    }

    public String getFlat() {
        return flat;
    }

    public OrderInfo setFlat(String flat) {
        this.flat = flat;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public OrderInfo setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
