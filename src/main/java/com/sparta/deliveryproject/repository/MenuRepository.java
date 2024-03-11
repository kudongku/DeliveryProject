package com.sparta.deliveryproject.repository;

import com.sparta.deliveryproject.entity.Menu;
import com.sparta.deliveryproject.entity.Store;
import com.sparta.deliveryproject.repositoryQuery.MenuRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = Menu.class, idClass = Long.class)
public interface MenuRepository extends JpaRepository<Menu, Long>, MenuRepositoryQuery {
    List<Menu> findAllByStore(Store store);
    void deleteAllByStore(Store store);
}
