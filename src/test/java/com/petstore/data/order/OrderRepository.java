package com.petstore.data.order;

public class OrderRepository {

    public static IOrder getValidOrder() {
        return Order.get()
                .setId(54321)
                .setPetId(12345)
                .setQuantity(1)
                .setStatus("placed")
                .setComplete(true)
                .build();
    }
}
