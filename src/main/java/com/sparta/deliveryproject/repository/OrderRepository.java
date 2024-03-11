package com.sparta.deliveryproject.repository;

import com.sparta.deliveryproject.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByMenuIdAndUserUserID(Long menuId, Long userID);

    List<Orders> findByUserUserID(Long userID);

    Optional<Orders> deleteByMenuIdAndUserUserID(Long menuId, Long userID);

    void deleteByUserUserID(Long userID);
}
