package com.sparta.deliveryproject.controller;

import com.sparta.deliveryproject.requestDto.OrderRequestDto;
import com.sparta.deliveryproject.responseDto.OrderResponseDto;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<OrderResponseDto> getOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        OrderResponseDto responseDto = orderService.getOrders(userDetails.getUser());
        return ResponseEntity.status(200).body(responseDto);
    }

    @PostMapping("/{menuId}")
    public void createOrders(@PathVariable Long menuId, @RequestBody OrderRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.createOrders(menuId, requestDto, userDetails.getUser());
    }

    @PutMapping("/{menuId}")
    public void updateOrders(@PathVariable Long menuId, @RequestBody OrderRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.updateOrders(menuId, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/{menuId}")
    public void deleteOrders(@PathVariable Long menuId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.deleteOrders(menuId, userDetails.getUser());
    }

    @DeleteMapping()
    public void clearOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.clearOrders(userDetails.getUser());
    }

    @GetMapping("/purchase")
    public void purchaseOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.purchaseOrders(userDetails.getUser());
    }
}
