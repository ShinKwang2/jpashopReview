package jpabook.jpashop.domains.catalog.presentation;

import jpabook.jpashop.domains.catalog.query.CatalogService;
import jpabook.jpashop.domains.catalog.query.dto.CatalogSummary;
import jpabook.jpashop.domains.catalog.service.ItemSearchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/catalog")
    public String getMainPage(@RequestParam(value = "category", required = false) Long category,
                              @ModelAttribute ItemSearchForm searchForm,
                              Model model) {

        //아이템 검색 form
        if (searchForm == null)
            model.addAttribute("itemSearchForm", new ItemSearchForm());
        else
            model.addAttribute("itemSearchForm", searchForm);

        // 아이템 리스트
        searchForm.setCategoryId(category);
        List<CatalogSummary> items = catalogService.getCatalog(searchForm);
        model.addAttribute("items", items);

        return "catalog";


    }
}
