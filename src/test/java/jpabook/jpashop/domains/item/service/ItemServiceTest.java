package jpabook.jpashop.domains.item.service;


import jpabook.jpashop.domains.item.Item;
import jpabook.jpashop.domains.item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Test
    void 아이템_추가() throws Exception {

        //given
        AddItemRequest addItemRequest = createAddItemRequest();

        BDDMockito.given(itemRepository.save(ArgumentMatchers.any(Item.class)))
                .willReturn(createItem(addItemRequest));

        //when
        itemService.saveItem(addItemRequest);

        //then
        Mockito.verify(itemRepository, Mockito.atLeastOnce()).save(ArgumentMatchers.any(Item.class));
        BDDMockito.then(itemRepository).should(Mockito.atLeastOnce()).save(ArgumentMatchers.any(Item.class));
     }

    @Test
    void 아이템_찾기() throws Exception {
         //given
         Long ITEM_ID = 1L;
         BDDMockito.given(itemRepository.findById(ArgumentMatchers.any(Long.class)))
                 .willReturn(Optional.of(createItem(createAddItemRequest())));
         //when
         itemService.findItem(ITEM_ID);

         //then
         Mockito.verify(itemRepository, Mockito.atLeastOnce())
                 .findById(ArgumentMatchers.any(Long.class));
    }

    @Test
    void 아이템_리스트_조회() throws Exception {
        //given
         BDDMockito.given(itemRepository.findAll())
                .willReturn(createItemList(createItem(createAddItemRequest())));
        //when
        itemService.findItemList();

        //then
        Mockito.verify(itemRepository, Mockito.atLeastOnce())
                .findAll();

    }

    private List<Item> createItemList(Item item) {
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        return itemList;
    }



    private Item createItem(AddItemRequest request) {
        Item item = Item.builder()
                .imagePath(request.getImagePath())
                .itemName(request.getName())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .categoryId(request.getCategoryId())
                .build();

        ReflectionTestUtils.setField(item, "id", 1L);
        return item;
    }

    private AddItemRequest createAddItemRequest() {
        return AddItemRequest.builder()
                .imagePath("Test")
                .name("Test")
                .price(1000)
                .stockQuantity(10)
                .categoryId(1L)
                .build();
    }
}