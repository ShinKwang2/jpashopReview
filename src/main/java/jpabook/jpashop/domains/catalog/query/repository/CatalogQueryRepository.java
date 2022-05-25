package jpabook.jpashop.domains.catalog.query.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domains.catalog.presentation.Sorter;
import jpabook.jpashop.domains.catalog.query.dto.CatalogSummary;
import jpabook.jpashop.domains.catalog.query.dto.QCatalogSummary;
import jpabook.jpashop.domains.catalog.service.ItemSearchForm;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static jpabook.jpashop.domains.item.QItem.*;

@Repository
public class CatalogQueryRepository {

    private JPAQueryFactory queryFactory;

    public CatalogQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<CatalogSummary> searchItem(ItemSearchForm searchForm) {
        return queryFactory
                .select(new QCatalogSummary(item.id, item.imagePath, item.itemName, item.price))
                .from(item)
                .where(nameLike(searchForm.getName()), categoryEq(searchForm.getCategoryId()))
                .orderBy(sorter(searchForm.getSorter()))
                .fetch();
    }

    private BooleanExpression nameLike(String name) {
        return StringUtils.hasText(name) ? item.itemName.like("%" + name + "%") : null;
    }

    private BooleanExpression categoryEq(Long categoryId) {
        return categoryId != null ? item.categoryId.eq(categoryId) : null;
    }

    private OrderSpecifier sorter(Sorter sorter) {

        if (sorter == Sorter.PRICE)
            return item.price.desc();

        return item.createDate.desc();
    }

}
