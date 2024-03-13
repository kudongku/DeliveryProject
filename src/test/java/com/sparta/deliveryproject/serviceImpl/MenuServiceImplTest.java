package com.sparta.deliveryproject.serviceImpl;

import com.sparta.deliveryproject.entity.*;
import com.sparta.deliveryproject.repository.CategoryRepository;
import com.sparta.deliveryproject.repository.MenuRepository;
import com.sparta.deliveryproject.repository.StoreRepository;
import com.sparta.deliveryproject.repository.UserRepository;
import com.sparta.deliveryproject.responseDto.MenuResponseDto;
import com.sparta.deliveryproject.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MenuServiceImplTest {

    @Autowired
    MenuService menuService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    MenuRepository menuRepository;

    @Test
    void getMenuListByStoreTest() {
        User user = new User("kudongku@gmail.com", "kudongku", "1234", "경기도 하남시", UserRoleEnum.ENTRE);
        userRepository.save(user);

        Category category = new Category("Chinese", "중국집");
        categoryRepository.save(category);

        Store store1 = new Store("시옌", "경기도 화성시", "중국음식점", category, user);
        storeRepository.save(store1);

        Menu menu = new Menu(store1, "짜장면", 3500, "맛있는 짜장");
        Menu menu2 = new Menu(store1, "짜장2면", 35200, "맛있는 짜2장");
        Menu menu3 = new Menu(store1, "짜장3면", 350033, "맛있는 짜3장");
        menu.incrementSales(6L);
        menu2.incrementSales(2L);
        menu3.incrementSales(1L);
        menuRepository.save(menu);
        menuRepository.save(menu2);
        menuRepository.save(menu3);

        menuService.getTopThreeCountsMenuListByStore(store1.getId());
        menuService.getTopThreeSalesMenuListByStore(store1.getId());
    }

    @Test
    void getMenuListByStore() {
        User user = new User("kudongku@gmail.com", "kudongku", "1234", "경기도 하남시", UserRoleEnum.ENTRE);
        userRepository.save(user);

        Category category = new Category("Chinese", "중국집");
        categoryRepository.save(category);

        Store store1 = new Store("시옌", "경기도 화성시", "중국음식점", category, user);
        storeRepository.save(store1);

        Menu menu = new Menu(store1, "짜장면", 3500, "맛있는 짜장");
        Menu menu2 = new Menu(store1, "짜장2면", 35200, "맛있는 짜2장");
        Menu menu3 = new Menu(store1, "짜장3면", 350033, "맛있는 짜3장");
        Menu menu4 = new Menu(store1, "짜장3면4", 350033, "맛있는 짜3장");
        Menu menu5 = new Menu(store1, "짜장3면5", 350033, "맛있는 짜3장");
        Menu menu6 = new Menu(store1, "짜장3면6", 350033, "맛있는 짜3장");
        menuRepository.saveAll(List.of(menu, menu2, menu3, menu4, menu5, menu6));

        List<MenuResponseDto> stores = menuService.getMenuListByStore(store1.getId(),2,4,"id", true);
    }
}