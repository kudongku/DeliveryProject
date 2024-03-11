package com.sparta.deliveryproject.repositoryQuery;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.deliveryproject.entity.Menu;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sparta.deliveryproject.entity.QMenu.menu;

@RequiredArgsConstructor
public class MenuRepositoryQueryImpl implements MenuRepositoryQuery {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Menu> getTopThreeSalesMenuListByStore(Long storeId) {
        return jpaQueryFactory.select(menu)
                .from(menu)
                .where(menu.store.id.eq(storeId))
                .orderBy(menu.salesCount.desc())
                .limit(3)
                .fetch();
    }

    @Override
    public List<Menu> getTopThreeCountsMenuListByStore(Long storeId) {
        return jpaQueryFactory.select(menu)
                .from(menu)
                .where(menu.store.id.eq(storeId))
                .orderBy(menu.totalSales.desc())
                .limit(3)
                .fetch();
    }
}
