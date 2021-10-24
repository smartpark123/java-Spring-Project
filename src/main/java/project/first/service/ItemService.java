package project.first.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.first.domain.Item;
import project.first.domain.Member;
import project.first.repository.ItemRepository;
import project.first.repository.MemberRepository;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);

    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public Item findOneUser(Long itemId){
        return itemRepository.findOneUser(itemId);
    }

    @Transactional //변경
    public Item updateItem(Long itemId, String name, int price, int stockQuantity, String title, String category, String sellerComment, String sellerHP){

        Item findItem = itemRepository.findOne(itemId);

        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        findItem.setTitle(title);
        findItem.setSellerComment(sellerComment);
        findItem.setCategory(category);
        findItem.setSellerHP(sellerHP);


        return findItem;
    }

}
