package com.sparta.deliveryproject.serviceImpl;

import com.sparta.deliveryproject.entity.Category;
import com.sparta.deliveryproject.entity.Store;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.repository.CategoryRepository;
import com.sparta.deliveryproject.repository.MenuRepository;
import com.sparta.deliveryproject.repository.StoreRepository;
import com.sparta.deliveryproject.requestDto.StoreRequestDto;
import com.sparta.deliveryproject.responseDto.StoreProjection;
import com.sparta.deliveryproject.responseDto.StoreResponseDto;
import com.sparta.deliveryproject.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<StoreProjection> getStoreListByCategory(Long categoryId, int page, int size, String sortBy, Boolean isAsc) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NullPointerException("해당하는 카테고리가 없습니다.")
        );
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<StoreProjection> storeList = storeRepository.findAllByCategory(category, pageable);

        if (storeList.isEmpty()) {
            throw new IllegalArgumentException(category + "에 속하는 식당이 없습니다.");
        }

        return storeList;
    }

    @Override
    public List<StoreResponseDto> getTopCountStoreList(User user) {
        return storeRepository.getTopCountStoreList(user)
                .stream()
                .map(StoreResponseDto::new)
                .toList();
    }

    @Override
    public List<StoreResponseDto> getTopSalesStoreList(User user) {
        return storeRepository.getTopSalesStoreList(user)
                .stream()
                .map(StoreResponseDto::new)
                .toList();
    }

    @Override
    @Transactional
    public void createStore(StoreRequestDto storeRequestDto, User user) {
        Category category = categoryRepository.findByName(storeRequestDto.getCategory()).orElseThrow(
                () -> new NullPointerException("해당하는 카테고리가 없습니다.")
        );

        Store store = new Store(storeRequestDto.getName(), storeRequestDto.getAddress(), storeRequestDto.getIntroduce(), category, user);
        storeRepository.save(store);
    }

    @Override
    @Transactional
    public void editStore(Long id, StoreRequestDto storeRequestDto, User userDetails) {
        Category category = categoryRepository.findByName(storeRequestDto.getCategory()).orElseThrow(
                () -> new NullPointerException("해당하는 카테고리가 없습니다.")
        );

        Store store = storeRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 id의 store가 존재하지 않습니다.")
        );

        if (!store.getUser().getUserID().equals(userDetails.getUserID())) {
            throw new IllegalArgumentException("매장을 수정할 수 있는 권한이 없습니다.");
        }

        store.edit(storeRequestDto.getName(), storeRequestDto.getAddress(), storeRequestDto.getIntroduce(), category);
    }

    @Override
    @Transactional
    public void deleteStore(Long id, User userDetails) {
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 id의 store가 존재하지 않습니다.")
        );

        if (!store.getUser().getUserID().equals(userDetails.getUserID())) {
            throw new IllegalArgumentException("매장을 삭제할 수 있는 권한이 없습니다.");
        }

        menuRepository.deleteAllByStore(store);
        storeRepository.delete(store);
    }
}
