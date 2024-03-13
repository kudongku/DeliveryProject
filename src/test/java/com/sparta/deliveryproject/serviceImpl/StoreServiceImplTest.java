package com.sparta.deliveryproject.serviceImpl;

import com.sparta.deliveryproject.entity.Category;
import com.sparta.deliveryproject.entity.Store;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.entity.UserRoleEnum;
import com.sparta.deliveryproject.repository.CategoryRepository;
import com.sparta.deliveryproject.repository.StoreRepository;
import com.sparta.deliveryproject.repository.UserRepository;
import com.sparta.deliveryproject.responseDto.StoreProjection;
import com.sparta.deliveryproject.responseDto.StoreResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class StoreServiceImplTest {

    @Autowired
    StoreServiceImpl service;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void getTopCountStoreListTest() {
        //given
        User user = new User("kudongku@gmail.com", "kudongku", "1234", "경기도 하남시", UserRoleEnum.ENTRE);
        userRepository.save(user);

        Category category = new Category("Chinese", "중국집");
        categoryRepository.save(category);

        Store store1 = new Store("시옌", "경기도 화성시", "중국음식점", category, user);
        store1.incrementSales(360000L);
        Store store2 = new Store("시옌2", "경기도 화2성시", "중국음2식점", category, user);
        store2.incrementSales(3600L);
        store2.incrementSales(3600L);
        Store store3 = new Store("시옌3", "경기도3 화성시", "중국3음식점", category, user);
        store3.incrementSales(36L);
        store3.incrementSales(36L);
        store3.incrementSales(36L);

        storeRepository.save(store1);
        storeRepository.save(store2);
        storeRepository.save(store3);

        //when
        List<StoreResponseDto> stores = service.getTopSalesStoreList(user);
        List<StoreResponseDto> stores2 = service.getTopCountStoreList(user);

        System.out.println(stores.toString() + stores2.toString());
    }

    @Test
    void getStoreListByCategoryTest() {
        //given
        User user = new User("kudongku@gmail.com", "kudongku", "1234", "경기도 하남시", UserRoleEnum.ENTRE);
        userRepository.save(user);

        Category category = new Category("Chinese", "중국집");
        categoryRepository.save(category);
        Category falseCategory = new Category("1234", "1234");
        categoryRepository.save(falseCategory);

        Store store1 = new Store("시옌1", "경기도 화성시", "중국음식점", category, user);
        Store store2 = new Store("시옌2", "경기도 화성시", "중국음식점", category, user);
        Store store3 = new Store("시옌3", "경기도 화성시", "중국음식점", category, user);
        Store store4 = new Store("시옌4", "경기도 화성시", "중국음식점", category, user);
        Store store5 = new Store("시옌5", "경기도 화성시", "중국음식점", category, user);
        Store store6 = new Store("시옌6", "경기도 화성시", "중국음식점", category, user);
        Store store7 = new Store("시옌7", "경기도 화성시", "중국음식점", category, user);
        Store store8 = new Store("시옌8", "경기도 화성시", "중국음식점", category, user);
        Store store9 = new Store("시옌9", "경기도 화성시", "중국음식점", category, user);
        Store store0 = new Store("시옌0", "경기도 화성시", "중국음식점", category, user);
        Store falseStore = new Store("1234", "경기도 화성시", "중국음식점", falseCategory, user);
        storeRepository.saveAll(List.of(store0,store1, store2, store3, store4, store5, store6, store6, store7, store8, store9, falseStore));

        // when
        Page<StoreProjection> stores = service.getStoreListByCategory(category.getId(), 2, 3, "id", true);

        for(StoreProjection sp : stores){
            System.out.println("---------------------");
            System.out.println(sp.getId());
            System.out.println(sp.getName());
            System.out.println(sp.getIntroduce());
        }

        assert !stores.isEmpty();
    }

    @Test
    void dynamicInsertTest() {
        //given
        User user = new User("kudongku@gmail.com", "kudongku", "1234", "경기도 하남시", UserRoleEnum.ENTRE);
        userRepository.save(user);

        Category category = new Category("Chinese", "중국집");
        categoryRepository.save(category);

        Store store1 = new Store("시옌", "경기도 화성시", "중국음식점", category, user);
        Store store2 = new Store("시옌2", "경기도 화2성시", "중국음2식점", category, user);
        Store store3 = new Store("시옌3", "경기도3 화성시", "중국3음식점", category, user);

        storeRepository.save(store1);
        storeRepository.save(store2);
        storeRepository.save(store3);
    }
    @Test
    void dynamicUpdateTest() {
        //given
        User user = new User("kudongku@gmail.com", "kudongku", "1234", "경기도 하남시", UserRoleEnum.ENTRE);
        userRepository.save(user);

        Category category = new Category("Chinese", "중국집");
        categoryRepository.save(category);

        Store store1 = new Store("시옌", "경기도 화성시", "중국음식점", category, user);
        storeRepository.save(store1);

        store1.edit("1","2","3",category);
        storeRepository.save(store1);
    }

}