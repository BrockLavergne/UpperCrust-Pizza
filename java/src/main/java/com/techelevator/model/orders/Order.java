package com.techelevator.model.orders;

import com.techelevator.model.menu.MenuItem;
import com.techelevator.model.menu.Pizza;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private int orderId;
    private int dataId;
    private String status;
    private String email;
    private String phone;
    private String address;
    private boolean isDelivery;

    private BigDecimal total = new BigDecimal(0);
    private List<MenuItem> menuItems;
    private List<Pizza> customPizzas;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public void setDelivery(boolean delivery) {
        isDelivery = delivery;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<Pizza> getCustomPizzas() {
        return customPizzas;
    }

    public void setCustomPizzas(List<Pizza> customPizzas) {
        this.customPizzas = customPizzas;
    }
}
