package com.sparta.deliveryproject.entity;

import com.sparta.deliveryproject.requestDto.OrderRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Orders(Menu menu, OrderRequestDto requestDto, User user){
        this.quantity = requestDto.getQuantity();
        this.totalPrice = menu.getPrice() * quantity;
        this.menu = menu;
        this.user = user;
    }

    public void update(OrderRequestDto requestDto){
        this.quantity = requestDto.getQuantity();
        this.totalPrice = menu.getPrice() * quantity;
    }

    public void add(OrderRequestDto requestDto) {
        this.quantity += requestDto.getQuantity();
        this.totalPrice = menu.getPrice() * quantity;
    }
}
