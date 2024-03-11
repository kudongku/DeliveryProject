package com.sparta.deliveryproject.serviceImpl;

import com.sparta.deliveryproject.requestDto.OrderRequestDto;
import com.sparta.deliveryproject.responseDto.OrderResponseDto;
import com.sparta.deliveryproject.entity.Menu;
import com.sparta.deliveryproject.entity.Orders;
import com.sparta.deliveryproject.entity.Store;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.repository.MenuRepository;
import com.sparta.deliveryproject.repository.OrderRepository;
import com.sparta.deliveryproject.repository.StoreRepository;
import com.sparta.deliveryproject.repository.UserRepository;
import com.sparta.deliveryproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void createOrders(Long menuId, OrderRequestDto requestDto, User user) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new NullPointerException("해당 id의 메뉴가 없습니다.")
        );
        if(requestDto.getQuantity() <= 0){
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }

        if(orderRepository.findByMenuIdAndUserUserID(menuId,user.getUserID()).isPresent()){
            Orders orders = orderRepository.findByMenuIdAndUserUserID(menuId,user.getUserID()).orElseThrow();
            orders.add(requestDto);
        }else{
            Orders orders = new Orders(menu, requestDto, user);
            orderRepository.save(orders);
        }
    }

    @Override
    public OrderResponseDto getOrders(User user) {
        List<Orders> ordersList = orderRepository.findByUserUserID(user.getUserID());
        return new OrderResponseDto(ordersList);
    }

    @Override
    @Transactional
    public void updateOrders(Long menuId, OrderRequestDto requestDto, User user) {
        Orders orders = orderRepository.findByMenuIdAndUserUserID(menuId, user.getUserID()).orElseThrow(
                () -> new NullPointerException("해당 id의 메뉴가 장바구니에 없습니다.")
        );
        if(requestDto.getQuantity() <= 0){
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }
        orders.update(requestDto);
    }

    @Override
    @Transactional
    public void deleteOrders(Long menuId, User user) {
        orderRepository.deleteByMenuIdAndUserUserID(menuId, user.getUserID()).orElseThrow(
                () -> new NullPointerException("해당 id의 메뉴가 장바구니에 없습니다.")
        );
    }

    @Override
    @Transactional
    public void clearOrders(User user) {
        orderRepository.deleteByUserUserID(user.getUserID());
    }

    @Override
    @Transactional
    public void purchaseOrders(User user) {
        List<Orders> ordersList = orderRepository.findByUserUserID(user.getUserID());

        for (Orders orders : ordersList) {
            Menu menu = menuRepository.findById(orders.getMenu().getId()).orElseThrow();
            menu.incrementSales(orders.getQuantity());

            Store store = storeRepository.findById(menu.getStore().getId()).orElseThrow();
            store.incrementSales(orders.getTotalPrice());

        }

        clearOrders(user);
        userRepository.save(user);
    }
}
