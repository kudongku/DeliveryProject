package com.sparta.deliveryproject.responseDto;

import com.sparta.deliveryproject.entity.Orders;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private Long allTotalPrice = 0L;
    private List<OrderDetailsDto> ordersList;

    public OrderResponseDto(List<Orders> ordersList){
        this.ordersList = ordersList.stream()
                .map(OrderDetailsDto::new)
                .collect(Collectors.toList());
        this.allTotalPrice = this.ordersList.stream()
                .mapToLong(OrderDetailsDto::getTotalPrice)
                .sum();
    }
}
