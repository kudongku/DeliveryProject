package com.sparta.deliveryproject.repository;

import com.sparta.deliveryproject.entity.ApiUseTime;
import com.sparta.deliveryproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {
    Optional<ApiUseTime> findByUser(User loginUser);
}
