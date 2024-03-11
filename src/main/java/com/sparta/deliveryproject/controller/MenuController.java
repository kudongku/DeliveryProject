package com.sparta.deliveryproject.controller;

import com.sparta.deliveryproject.exception.DuplicatedMenuException;
import com.sparta.deliveryproject.requestDto.MenuRequestDto;
import com.sparta.deliveryproject.responseDto.MenuResponseDto;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/public/menus/{storeId}")
    public ResponseEntity<List<MenuResponseDto>> getMenuListByStore(@PathVariable Long storeId) {
        List<MenuResponseDto> menuList = menuService.getMenuListByStore(storeId);
        return ResponseEntity.status(200).body(menuList);
    }

    @Secured("ROLE_ENTRE")
    @GetMapping("/menus/{storeId}/sales")
    public ResponseEntity<List<MenuResponseDto>> getTopThreeSalesMenuListByStore(@PathVariable Long storeId) {
        List<MenuResponseDto> menuList = menuService.getTopThreeSalesMenuListByStore(storeId);
        return ResponseEntity.status(200).body(menuList);
    }

    @Secured("ROLE_ENTRE")
    @GetMapping("/menus/{storeId}/counts")
    public ResponseEntity<List<MenuResponseDto>> getTopThreeCountsMenuListByStore(@PathVariable Long storeId) {
        List<MenuResponseDto> menuList = menuService.getTopThreeCountsMenuListByStore(storeId);
        return ResponseEntity.status(200).body(menuList);
    }

    @Secured("ROLE_ENTRE")
    @PostMapping("/menus/{storeId}")
    public void createMenu(@PathVariable Long storeId, @RequestBody MenuRequestDto menuRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws DuplicatedMenuException {
        menuService.createMenu(storeId, menuRequestDto, userDetails.getUser());
    }

    @Secured("ROLE_ENTRE")
    @PutMapping("/menus/{menuId}")
    public void editMenu(@PathVariable Long menuId, @RequestBody MenuRequestDto menuRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws DuplicatedMenuException {
        menuService.editMenu(menuId, menuRequestDto, userDetails.getUser());
    }

    @Secured("ROLE_ENTRE")
    @DeleteMapping("/menus/{menuId}")
    public void deleteStore(@PathVariable Long menuId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        menuService.deleteMenu(menuId, userDetails.getUser());
    }
}
