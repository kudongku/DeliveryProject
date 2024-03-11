package com.sparta.deliveryproject.service;

import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.requestDto.OrderRequestDto;
import com.sparta.deliveryproject.responseDto.OrderResponseDto;

public interface OrderService {
    void createOrders(Long menuId, OrderRequestDto requestDto, User user);
    OrderResponseDto getOrders(User user);
    void updateOrders(Long menuId, OrderRequestDto requestDto, User user);
    void deleteOrders(Long menuId, User user);
    void clearOrders(User user);
    void purchaseOrders(User user);
}
