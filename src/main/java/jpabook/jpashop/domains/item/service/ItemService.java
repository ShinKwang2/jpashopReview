package jpabook.jpashop.domains.item.service;

import jpabook.jpashop.domains.item.Item;
import jpabook.jpashop.domains.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long saveItem(AddItemRequest request) {
        Item newItem = request.toEntity();
        Item savedItem = itemRepository.save(newItem);

        return savedItem.getId();
    }

    public ItemDetails findItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        return new ItemDetails(item);
    }

    public List<Item> findItemList() {
        return itemRepository.findAll();
    }
}
