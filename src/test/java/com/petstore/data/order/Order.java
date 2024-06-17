package com.petstore.data.order;

import java.util.Date;

public class Order implements IOrder {

    private int id;
    private int petId;
    private int quantity;
    private Date shipDate;
    private String status;
    private boolean complete;

    public static IOrder get() {
        return new Order();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getPetId() {
        return petId;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public Date getShipDate() {
        return shipDate;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public boolean getComplete() {
        return complete;
    }

    @Override
    public IOrder setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public IOrder setPetId(int petId) {
        this.petId = petId;
        return this;
    }

    @Override
    public IOrder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public IOrder setShipDate(Date shipDate) {
        this.shipDate = shipDate;
        return this;
    }

    @Override
    public IOrder setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public IOrder setComplete(boolean complete) {
        this.complete = complete;
        return this;
    }

    @Override
    public IOrder build() {
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", petId=" + petId +
                ", quantity=" + quantity +
                ", shipDate=" + shipDate +
                ", status='" + status + '\'' +
                ", complete=" + complete +
                '}';
    }
}
