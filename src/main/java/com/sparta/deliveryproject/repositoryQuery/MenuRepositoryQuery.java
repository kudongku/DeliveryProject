package com.sparta.deliveryproject.repositoryQuery;

import com.sparta.deliveryproject.entity.Menu;

import java.util.List;

public interface MenuRepositoryQuery {
    List<Menu> getTopThreeSalesMenuListByStore(Long storeId);

    List<Menu> getTopThreeCountsMenuListByStore(Long storeId);
}
