package com.chen.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Order {
    @JSONField(name = "id")
    private String id;

    @JSONField(name = "CreatedAt")
    private String createdAt;

    @JSONField(name = "goods")
    private String goods;

    @JSONField(name = "total_price")
    private String totalPrice;

    @JSONField(name = "school_name")
    private String schoolName;

    @JSONField(name = "rest_name")
    private String restName;

    @JSONField(name = "order_number")
    private String orderNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
