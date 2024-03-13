package com.sparta.deliveryproject.service;

import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.requestDto.StoreRequestDto;
import com.sparta.deliveryproject.responseDto.StoreProjection;
import com.sparta.deliveryproject.responseDto.StoreResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StoreService {
    Page<StoreProjection> getStoreListByCategory(Long categoryId, int page, int size, String sortBy, Boolean isAsc);
    List<StoreResponseDto> getTopCountStoreList(User user);
    List<StoreResponseDto> getTopSalesStoreList(User user);
    void createStore(StoreRequestDto storeRequestDto, User user);
    void editStore(Long id, StoreRequestDto storeRequestDto, User userDetails);
    void deleteStore(Long id, User userDetails);
}
