package jpabook.jpashop.domains.catalog.query;

import jpabook.jpashop.domains.catalog.query.dto.CatalogSummary;
import jpabook.jpashop.domains.catalog.query.repository.CatalogQueryRepository;
import jpabook.jpashop.domains.catalog.service.ItemSearchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CatalogService {

    private final CatalogQueryRepository repository;

    public List<CatalogSummary> getCatalog(ItemSearchForm itemSearchForm) {
        return repository.searchItem(itemSearchForm);
    }
}
