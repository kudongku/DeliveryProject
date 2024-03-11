package com.sparta.deliveryproject.repository;

import com.sparta.deliveryproject.entity.Category;
import com.sparta.deliveryproject.entity.Store;
import com.sparta.deliveryproject.repositoryQuery.StoreRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = Store.class, idClass = Long.class)
public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryQuery {
    List<Store> findAllByCategory(Category category);
}
