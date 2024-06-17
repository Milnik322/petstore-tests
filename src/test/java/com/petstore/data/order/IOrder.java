package com.petstore.data.order;

import com.petstore.data.pet.IPet;

import java.util.Date;

public interface IOrder {

    int getId();

    int getPetId();

    int getQuantity();

    Date getShipDate();

    String getStatus();

    boolean getComplete();

    IOrder setId(int id);

    IOrder setPetId(int petId);

    IOrder setQuantity(int quantity);

    IOrder setShipDate(Date shipDate);

    IOrder setStatus(String status);

    IOrder setComplete(boolean complete);

    IOrder build();
}
