package com.sparta.deliveryproject.repositoryQuery;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.deliveryproject.entity.Store;
import com.sparta.deliveryproject.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sparta.deliveryproject.entity.QStore.store;

@RequiredArgsConstructor
public class StoreRepositoryQueryImpl implements StoreRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Store> getTopCountStoreList(User user) {
        return jpaQueryFactory.select(store)
                .from(store)
                .where(store.user.userID.eq(user.getUserID()))
                .orderBy(store.orderCount.desc())
                .limit(3)
                .fetch();
    }

    @Override
    public List<Store> getTopSalesStoreList(User user)  {
        return jpaQueryFactory.select(store)
                .from(store)
                .where(store.user.userID.eq(user.getUserID()))
                .orderBy(store.totalSales.desc())
                .limit(3)
                .fetch();
    }
}
