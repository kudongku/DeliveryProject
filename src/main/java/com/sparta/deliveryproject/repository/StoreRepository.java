package com.sparta.deliveryproject.repository;

import com.sparta.deliveryproject.entity.Category;
import com.sparta.deliveryproject.entity.Store;
import com.sparta.deliveryproject.repositoryQuery.StoreRepositoryQuery;
import com.sparta.deliveryproject.responseDto.StoreProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Store.class, idClass = Long.class)
public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryQuery {
    Page<StoreProjection> findAllByCategory(Category category, Pageable pageable);
}
