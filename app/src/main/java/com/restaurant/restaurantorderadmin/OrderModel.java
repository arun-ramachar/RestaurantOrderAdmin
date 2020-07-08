package com.restaurant.restaurantorderadmin;

public class OrderModel {

    String id;
    String username;
    String order_detail;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    String orderId;
    public OrderModel() {
    }
    public OrderModel(String orderId,String id,String username,String order_detail) {
        this.id=id;
        this.username=username;
        this.order_detail=order_detail;
        this.orderId=orderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(String order_detail) {
        this.order_detail = order_detail;
    }
}
