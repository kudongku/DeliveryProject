package com.sparta.deliveryproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "category")
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String introduce;

    public Category(String name, String introduce) {
        this.name = name;
        this.introduce = introduce;
    }

    public void update(String name, String introduce) {
        this.name = name;
        this.introduce = introduce;
    }
}
