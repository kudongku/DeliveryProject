package com.sparta.deliveryproject.repositoryQuery;

import com.sparta.deliveryproject.entity.Store;
import com.sparta.deliveryproject.entity.User;

import java.util.List;

public interface StoreRepositoryQuery {
    List<Store> getTopCountStoreList(User user);

    List<Store> getTopSalesStoreList(User user);
}
