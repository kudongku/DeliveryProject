package com.sparta.deliveryproject.responseDto;

import com.sparta.deliveryproject.entity.Orders;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDetailsDto {
    private Long orderId;
    private String menuName;
    private Long quantity;
    private Long totalPrice;

    public OrderDetailsDto(Orders orders) {
        this.orderId = orders.getId();
        this.menuName = orders.getMenu().getName();
        this.quantity = orders.getQuantity();
        this.totalPrice = orders.getTotalPrice();
    }
}
