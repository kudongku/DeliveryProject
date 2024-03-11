package com.sparta.deliveryproject.entity;

import com.sparta.deliveryproject.requestDto.MenuRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "menu")
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String introduce;

    @Column(nullable = false)
    private Long salesCount = 0L;

    @Column(nullable = false)
    private Long totalSales = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public Menu(Store store, String name, int priceInt, String introduce) {
        this.store = store;
        this.name = name;
        this.price = priceInt;
        this.introduce = introduce;
    }

    public void update(MenuRequestDto menuRequestDto, int priceInt) {
        this.name = menuRequestDto.getName();
        this.price = priceInt;
        this.introduce = menuRequestDto.getIntroduce();
    }

    public void incrementSales(Long quantity){
        this.salesCount += quantity;
        this.totalSales += this.price * quantity;
    }
}
