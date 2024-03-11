package com.sparta.deliveryproject.responseDto;

import com.sparta.deliveryproject.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreResponseDto {
    private String name;
    private String address;
    private String categoryName;

    public StoreResponseDto(Store store) {
        this.name = store.getName();
        this.address = store.getAddress();
        this.categoryName = store.getCategory().getName();
    }
}
