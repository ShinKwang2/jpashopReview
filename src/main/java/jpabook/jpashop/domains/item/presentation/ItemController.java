package jpabook.jpashop.domains.item.presentation;

import jpabook.jpashop.domains.item.service.AddItemRequest;
import jpabook.jpashop.domains.item.service.ItemDetails;
import jpabook.jpashop.domains.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String getNewItemPage(Model model) {
        model.addAttribute("form", new AddItemRequest());
        return "items/registerItemForm";
    }

    @PostMapping("/items/new")
    public String createItem(@ModelAttribute @Valid AddItemRequest addItemRequest) {
        Long newItemId = itemService.saveItem(addItemRequest);
        return "redirect:/items/"+ newItemId;
    }

    @GetMapping("/items/{itemId}")
    public String getItemDetailsPage(@PathVariable("itemId") Long itemId,
                                     Model model) {
        ItemDetails item = itemService.findItem(itemId);
        model.addAttribute("item", item);
        return "items/itemDetails";
    }
}

