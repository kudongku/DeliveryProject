package com.sparta.deliveryproject.service;

import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.requestDto.StoreRequestDto;
import com.sparta.deliveryproject.responseDto.StoreResponseDto;

import java.util.List;

public interface StoreService {
    List<StoreResponseDto> getStoreListByCategory(Long categoryId);
    List<StoreResponseDto> getTopCountStoreList(User user);
    List<StoreResponseDto> getTopSalesStoreList(User user);
    void createStore(StoreRequestDto storeRequestDto, User user);
    void editStore(Long id, StoreRequestDto storeRequestDto, User userDetails);
    void deleteStore(Long id, User userDetails);
}
