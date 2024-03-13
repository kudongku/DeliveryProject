package com.sparta.deliveryproject.service;

import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.exception.DuplicatedMenuException;
import com.sparta.deliveryproject.requestDto.MenuRequestDto;
import com.sparta.deliveryproject.responseDto.MenuResponseDto;

import java.util.List;

public interface MenuService {
    List<MenuResponseDto> getMenuListByStore(Long storeId, int page, int size, String sortBy, Boolean isAsc);

    List<MenuResponseDto> getTopThreeSalesMenuListByStore(Long storeId);

    List<MenuResponseDto> getTopThreeCountsMenuListByStore(Long storeId);

    void createMenu(Long storeId, MenuRequestDto menuRequestDto, User userDetails) throws DuplicatedMenuException;

    void editMenu(Long menuId, MenuRequestDto menuRequestDto, User userDetails) throws DuplicatedMenuException;

    void deleteMenu(Long menuId, User userDetails);
}
