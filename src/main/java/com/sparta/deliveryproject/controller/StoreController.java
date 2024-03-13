package com.sparta.deliveryproject.controller;

import com.sparta.deliveryproject.requestDto.StoreRequestDto;
import com.sparta.deliveryproject.responseDto.StoreProjection;
import com.sparta.deliveryproject.responseDto.StoreResponseDto;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreController {

    private final StoreService storeService;

    // 카테고리별 메장 조회
    @GetMapping("/public/stores/category/{categoryId}")
    public ResponseEntity<Page<StoreProjection>> getStoreListByCategory(
            @PathVariable Long categoryId,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") Boolean isAsc
    ) {
        Page<StoreProjection> storeList = storeService.getStoreListByCategory(categoryId, page, size, sortBy, isAsc);
        return ResponseEntity.status(200).body(storeList);
    }

    @Secured("ROLE_ENTRE")
    @GetMapping("/stores/sales-top-three")
    public ResponseEntity<List<StoreResponseDto>> getTopSalesStoreList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<StoreResponseDto> storeList = storeService.getTopSalesStoreList(userDetails.getUser());
        return ResponseEntity.status(200).body(storeList);
    }

    @Secured("ROLE_ENTRE")
    @GetMapping("/stores/counts-top-three")
    public ResponseEntity<List<StoreResponseDto>> getTopCountStoreList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<StoreResponseDto> storeList = storeService.getTopCountStoreList(userDetails.getUser());
        return ResponseEntity.status(200).body(storeList);
    }

    // 매장 등록
    @Secured("ROLE_ENTRE")
    @PostMapping("/stores")
    public void createStore(@RequestBody StoreRequestDto storeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        storeService.createStore(storeRequestDto, userDetails.getUser());
    }

    // 매장 수정
    @Secured("ROLE_ENTRE")
    @PutMapping("/stores/{storeId}")
    public void editStore(@PathVariable Long storeId, @RequestBody StoreRequestDto storeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        storeService.editStore(storeId, storeRequestDto, userDetails.getUser());
    }

    // 매장 삭제
    @Secured("ROLE_ENTRE")
    @DeleteMapping("/stores/{storeId}")
    public void deleteStore(@PathVariable Long storeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        storeService.deleteStore(storeId, userDetails.getUser());
    }
}
