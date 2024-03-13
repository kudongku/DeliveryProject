package com.sparta.deliveryproject.repository;

import com.sparta.deliveryproject.config.JPAConfiguration;
import com.sparta.deliveryproject.entity.Menu;
import com.sparta.deliveryproject.entity.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JPAConfiguration.class)
@Rollback(value = false)
class MenuRepositoryTest {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    StoreRepository storeRepository;

    @Test
    void findAllByStoreTest() {
        Store store = storeRepository.findById(13L).orElse(null);
        assert store != null;

        Sort.Direction direction = Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "salesCount");
        Pageable pageable = PageRequest.of(1, 2, sort);

        List<Menu> stores = menuRepository.findAllByStore(store, pageable);
        assert !stores.isEmpty();
    }

    @Test
    void findAllByStoreTest2() {
        Store store = storeRepository.findById(13L).orElse(null);
        assert store != null;

        List<Menu> stores = menuRepository.findAllByStore(store);
        assert !stores.isEmpty();
    }

    @Test
    void getTopThreeSalesMenuListByStoreTest() {
        Store store = storeRepository.findById(13L).orElse(null);
        assert store != null;
        List<Menu> menus = menuRepository.getTopThreeSalesMenuListByStore(store.getId());
        assert !menus.isEmpty();
    }

    @Test
    void getTopThreeCountsMenuListByStoreTest() {
        Store store = storeRepository.findById(13L).orElse(null);
        assert store != null;
        List<Menu> menus = menuRepository.getTopThreeCountsMenuListByStore(store.getId());
        assert !menus.isEmpty();
    }
}